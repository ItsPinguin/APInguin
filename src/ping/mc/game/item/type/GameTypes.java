package ping.mc.game.item.type;

import ping.GameAPI;
import ping.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GameTypes {
    static HashMap<String, GameType> types=new HashMap<>();
    private static GameTypeBuilder typeBuilder=new GameTypeBuilder() {
        @Override
        public String build(GameType type) {
            return GameTypeBuilder.super.build(type);
        }
    };
    public static void addType(GameType type){
        types.put(type.getId(), type);
    }
    public static GameType getType(String key){
        return types.get(key);
    }

    public static GameTypeBuilder getTypeBuilder() {
        return typeBuilder;
    }

    public static void setTypeBuilder(GameTypeBuilder typeBuilder) {
        GameTypes.typeBuilder = typeBuilder;
    }

    public static void loadAllFromPath(Path path, String excludePrefix){
        try {
            if (path.toFile().isFile() && !path.toString().startsWith(excludePrefix)){
                GameType type=new GameType(FileUtils.readJSONObject(path.toString()));
                types.put(type.getId(), type);
            } else if (path.toFile().isDirectory() && !path.toString().startsWith(excludePrefix)) {
                GameAPI.LOGGER.info("Loading types from directory: "+path.toFile());
                AtomicInteger loaded= new AtomicInteger();
                Files.walk(path).forEach(filePath ->{
                    if (filePath.toFile().isFile()&& !filePath.toString().startsWith(excludePrefix)){
                        GameType type=new GameType(FileUtils.readJSONObject(path.toString()));
                        types.put(type.getId(), type);
                        loaded.addAndGet(1);
                    }
                });
                System.out.println("Loaded "+loaded+" types from files");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
