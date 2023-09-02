package ping.apinguin.game.event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import ping.apinguin.game.drop.PingDrop;

public class TestEvents implements Listener {
    @EventHandler
    public void serverEnable(ServerLoadEvent e){
        new PingDrop(Material.GLOWSTONE).setPingItem("MENU").setMaximum(5);
    }
}
