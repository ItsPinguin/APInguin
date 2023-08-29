package ping.mc.game.event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import ping.apinguin.APInguin;
import ping.mc.game.drop.PingDrop;

public class TestEvents implements Listener {
    @EventHandler
    public void serverEnable(ServerLoadEvent e){
        new PingDrop(Material.GLOWSTONE).setItemStack(APInguin.Registries.ITEM_BASES.get("MENU").toItemStack()).setMaximum(5);

    }
}
