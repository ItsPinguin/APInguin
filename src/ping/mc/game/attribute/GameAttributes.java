package ping.mc.game.attribute;

import ping.GameAPI;
import ping.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GameAttributes {
    private static GameAttributeBuilder attributeBuilder=new GameAttributeBuilder() {
        @Override
        public String build(GameAttributeModifier gameAttributeModifier) {
            return GameAttributeBuilder.super.build(gameAttributeModifier);
        }
    };
    public static HashMap<String, GameAttribute> attributes=new HashMap<>();

    public static GameAttributeBuilder getAttributeBuilder() {
        return attributeBuilder;
    }

    public static void setAttributeBuilder(GameAttributeBuilder attributeBuilder) {
        GameAttributes.attributeBuilder = attributeBuilder;
    }

    public static void loadAllFromPath(Path path, String excludePrefix){
        try {
            if (path.toFile().isFile() && !path.toString().startsWith(excludePrefix)){
                GameAttribute attribute=new GameAttribute(FileUtils.readJSONObject(path.toString()));
                attributes.put(attribute.getId(), attribute);
            } else if (path.toFile().isDirectory() && !path.toString().startsWith(excludePrefix)) {
                GameAPI.LOGGER.info("Loading attributes from directory: "+path.toFile());
                AtomicInteger loaded= new AtomicInteger();
                Files.walk(path).forEach(filePath ->{
                    if (filePath.toFile().isFile()&& !filePath.toString().startsWith(excludePrefix)){
                        GameAttribute attribute=new GameAttribute(FileUtils.readJSONObject(filePath.toString()));
                        attributes.put(attribute.getId(), attribute);
                        loaded.addAndGet(1);
                    }
                });
                GameAPI.LOGGER.info("Loaded "+loaded+" attributes from files");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
