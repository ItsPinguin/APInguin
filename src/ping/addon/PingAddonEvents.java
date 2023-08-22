package ping.addon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import ping.apinguin.APInguin;

public class PingAddonEvents implements Listener {
    @EventHandler
    public void load(PluginEnableEvent e){
        if (e.getPlugin() instanceof PingAddon addon){
            APInguin.LOGGER.info("Found addon: "+e.getPlugin().getName());
            APInguin.Registries.ADDONS.put(e.getPlugin().getName(),addon);
            addon.startAddon();
            APInguin.Registries.ITEM_BASES.values().forEach(item -> {
                APInguin.Registries.ITEM_BASES.put(item.getId(),addon.getItemAddon().parseFromJSONMap(item));
            });
        }
    }
}
