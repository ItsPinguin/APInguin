package ping.mc.game.rarity;

import ping.GameAPI;
import ping.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRarities {
    private static GameRarityBuilder rarityBuilder=new GameRarityBuilder() {
        @Override
        public String build(GameRarity rarity) {
            return GameRarityBuilder.super.build(rarity);
        }
    };
    static HashMap<String, GameRarity> rarities=new HashMap<>();
    public static void addRarity(GameRarity rarity){
        rarities.put(rarity.getId(), rarity);
    }

    public static GameRarity getRarity(String key){
        return rarities.get(key);
    }

    public static GameRarityBuilder getRarityBuilder() {
        return rarityBuilder;
    }

    public static void setRarityBuilder(GameRarityBuilder rarityBuilder) {
        GameRarities.rarityBuilder = rarityBuilder;
    }

    public static void loadAllFromPath(Path path, String excludePrefix){
        try {
            if (path.toFile().isFile() && !path.toString().startsWith(excludePrefix)){
                GameRarity rarity=new GameRarity(FileUtils.readJSONObject(path.toString()));
                rarities.put(rarity.getId(), rarity);
            } else if (path.toFile().isDirectory() && !path.toString().startsWith(excludePrefix)) {
                GameAPI.LOGGER.info("Loading rarities from directory: "+path.toFile());
                AtomicInteger loaded= new AtomicInteger();
                Files.walk(path).forEach(filePath ->{
                    if (filePath.toFile().isFile()&& !filePath.toString().startsWith(excludePrefix)){
                        GameRarity rarity=new GameRarity(FileUtils.readJSONObject(filePath.toString()));
                        rarities.put(rarity.getId(), rarity);
                        loaded.addAndGet(1);
                    }
                });
                GameAPI.LOGGER.info("Loaded "+loaded+" rarities from files");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
