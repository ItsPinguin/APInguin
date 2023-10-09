package ping.apinguin.events.drop;

import org.bukkit.event.Cancellable;
import ping.apinguin.events.APInguinEvent;
import ping.apinguin.game.condition.Context;
import ping.apinguin.game.drop.PingDrop;

public class DropEvent extends APInguinEvent implements Cancellable {
  private boolean isCancelled = false;
  private Context context;
  private PingDrop drop;

  public DropEvent(PingDrop drop, Context context) {
    this.drop = drop;
    this.context = context;
  }

  public PingDrop getDrop() {
    return drop;
  }

  public void setDrop(PingDrop drop) {
    this.drop = drop;
  }

  @Override
  public boolean isCancelled() {
    return false;
  }

  @Override
  public void setCancelled(boolean b) {

  }
}
