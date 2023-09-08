package ping.apinguin.game.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class TestEvents implements Listener {
  @EventHandler
  public void serverEnable(ServerLoadEvent e) {
  }
}
