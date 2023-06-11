package ping.mc.game.attribute;

import ping.GameAPI;
import ping.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class GameAttributes {
    private static GameAttributeBuilder attributeBuilder=new GameAttributeBuilder() {
        @Override
        public String build(GameAttributeModifier gameAttributeModifier) {
            return GameAttributeBuilder.super.build(gameAttributeModifier);
        }
    };
    static HashMap<String, GameAttribute> attributes=new HashMap<>();
    public static void addAttribute(GameAttribute attribute){
        attributes.put(attribute.getId(), attribute);
    }

    public static GameAttribute getAttribute(String key){
        return attributes.get(key);
    }

    public static GameAttributeBuilder getAttributeBuilder() {
        return attributeBuilder;
    }

    public static void setAttributeBuilder(GameAttributeBuilder attributeBuilder) {
        GameAttributes.attributeBuilder = attributeBuilder;
    }

    public static HashMap<String, GameAttribute> getAttributes() {
        return attributes;
    }

    public static void loadAllFromPath(Path path, String excludePrefix){
        try {
            if (path.toFile().isFile() && !path.toString().startsWith(excludePrefix)){
                GameAttribute attribute=new GameAttribute(FileUtils.readJSONObject(path.toString()));
                attributes.put(attribute.getId(), attribute);
            } else if (path.toFile().isDirectory() && !path.toString().startsWith(excludePrefix)) {
                GameAPI.LOGGER.info("Loading attributes from directory: "+path.toFile());
                Files.walk(path).forEach(filePath ->{
                    if (filePath.toFile().isFile()&& !filePath.toString().startsWith(excludePrefix)){
                        GameAttribute attribute=new GameAttribute(FileUtils.readJSONObject(path.toString()));
                        attributes.put(attribute.getId(), attribute);
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
