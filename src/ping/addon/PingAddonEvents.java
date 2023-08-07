package ping.addon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import ping.apinguin.APInguin;
import ping.mc.game.item.GameItems;

public class PingAddonEvents implements Listener {
    @EventHandler
    public void load(PluginEnableEvent e){
        APInguin.LOGGER.info("Checking if plugin \""+e.getPlugin().getName()+"\" is an addon");
        if (e.getPlugin() instanceof PingAddon addon){
            APInguin.LOGGER.info("Found addon: "+e.getPlugin().getName());
            APInguin.addons.add(addon);
            addon.test();
            GameItems.itemMap.values().forEach(item -> {
                GameItems.itemMap.put(item.getId(),addon.itemBase(item));
            });
        }
    }
}
