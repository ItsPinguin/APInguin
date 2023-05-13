package ping.mc.game.rarity;

public interface RarityBuilder {
    default String build(Rarity rarity){
        return rarity.getEffect()+rarity.getName();
    }
}
