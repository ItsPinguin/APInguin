package ping.apinguin.game.item.ability;

import org.bukkit.entity.Player;

public interface GameAbility {
  GameAbilityInfo getAbilityInfo();

  default boolean trigger(Player player) {
    return true;
  }
}
