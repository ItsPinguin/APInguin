package ping.apinguin.addon;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class PingAddonHandler implements Listener {
  private static HashMap<String, PingAddon> addons = new HashMap<>();

  public static HashMap<String, PingAddon> getAddons() {
    return addons;
  }

  @EventHandler
  public void load(PluginEnableEvent e) {
    boolean allPluginsLoaded = true;
    for (Plugin loadedPlugin : Bukkit.getPluginManager().getPlugins()) {
      if (!loadedPlugin.isEnabled()) {
        allPluginsLoaded = false;
        break;
      }
    }
    if (allPluginsLoaded) {
      for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
        if (plugin instanceof PingAddon addon) {
          getAddons().put(plugin.getName(), addon);
        }
      }
      getAddons().values().forEach(PingAddon::createItems);
      getAddons().values().forEach(PingAddon::createRecipes);
      getAddons().values().forEach(PingAddon::createDrops);
    }
  }
}
