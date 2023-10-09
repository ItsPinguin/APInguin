package ping.apinguin.events.condition;

import org.bukkit.event.Cancellable;
import ping.apinguin.events.APInguinEvent;
import ping.apinguin.game.condition.Condition;
import ping.apinguin.game.condition.Context;

public class ConditionCheckEvent extends APInguinEvent implements Cancellable {
  private Condition condition;
  private Context context;
  private boolean isCancelled = false;

  public ConditionCheckEvent(Condition condition, Context context) {
    this.condition = condition;
    this.context = context;
  }

  public Condition getCondition() {
    return condition;
  }

  public Context getContext() {
    return context;
  }

  @Override
  public boolean isCancelled() {
    return isCancelled;
  }

  @Override
  public void setCancelled(boolean b) {
    isCancelled = b;
  }
}
