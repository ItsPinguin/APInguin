package ping.mc.game.rarity;

public class RarityBuilder {
    public String build(Rarity rarity){
        return rarity.getEffect()+rarity.getName();
    }
}
