package ping.apinguin.game.rarity;

public interface GameRarityBuilder {
  default String build(GameRarity rarity) {
    return rarity.getEffect() + rarity.getName();
  }
}
