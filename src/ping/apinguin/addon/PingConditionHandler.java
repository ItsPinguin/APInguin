package ping.apinguin.addon;

import ping.apinguin.game.condition.Condition;

public interface PingConditionHandler extends PingAddonComponent {
  default boolean check(Condition condition) {
    return true;
  }
}
