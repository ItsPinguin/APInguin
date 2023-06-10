package ping.mc.game.rarity;

public interface GameRarityBuilder {
    default String build(GameRarity rarity){
        return rarity.getEffect()+rarity.getName();
    }
}
