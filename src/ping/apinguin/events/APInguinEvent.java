package ping.apinguin.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class APInguinEvent extends Event {

  public APInguinEvent() {
  }




  private static final HandlerList handlers = new HandlerList();

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

    /*

    TestEvent event = new TestEvent(%player%);
    Bukkit.getPluginManager().callEvent(event);
     */
}
