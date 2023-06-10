package ping;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ping.mc.game.item.DefaultItemBuilder;
import ping.mc.game.item.GameItems;
import ping.mc.game.item.ability.GameAbilityEvents;
import ping.mc.game.profile.GameProfileEvents;
import ping.utils.Config;

import java.util.logging.Logger;

public class GameAPI extends JavaPlugin {
    public static Plugin PLUGIN;
    public static Logger LOGGER;
    @Override
    public void onEnable() {
        PLUGIN=this;
        LOGGER=Logger.getLogger("GameAPI");
        LOGGER.info("Enabling plugin ...");
        Bukkit.getPluginManager().registerEvents(new GameAbilityEvents(),this);
        Bukkit.getPluginManager().registerEvents(new GameProfileEvents(),this);
        GameItems.setItemBuilder("default",new DefaultItemBuilder());
        Config.load();
        LOGGER.info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        LOGGER.info("Plugin disabled!");
    }
}
