package ping.apinguin.events;

import org.bukkit.event.Cancellable;

public class CancellableExample extends APInguinEvent implements Cancellable {
  private boolean isCancelled;
  public CancellableExample(){
    this.isCancelled = false;
  }

  @Override
  public boolean isCancelled() {
    return this.isCancelled;
  }

  @Override
  public void setCancelled(boolean b) {
    this.isCancelled =b;
  }
}
