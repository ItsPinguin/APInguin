package ping.mc.game.rarity;

import java.util.HashMap;

public class Rarities {
    private static RarityBuilder rarityBuilder=new RarityBuilder() {
        @Override
        public String build(Rarity rarity) {
            return RarityBuilder.super.build(rarity);
        }
    };
    static HashMap<String,Rarity> rarities=new HashMap<>();
    public static void addRarity(Rarity rarity){
        rarities.put(rarity.getId(), rarity);
    }

    public static Rarity getRarity(String key){
        return rarities.get(key);
    }

    public static RarityBuilder getRarityBuilder() {
        return rarityBuilder;
    }

    public static void setRarityBuilder(RarityBuilder rarityBuilder) {
        Rarities.rarityBuilder = rarityBuilder;
    }
}
