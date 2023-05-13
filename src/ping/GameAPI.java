package ping;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ping.mc.game.item.DefaultItemBuilder;
import ping.mc.game.item.GameItems;

import java.util.logging.Logger;

public class GameAPI extends JavaPlugin {
    public static Plugin PLUGIN;
    public static Logger LOGGER;
    @Override
    public void onEnable() {
        PLUGIN=this;
        LOGGER=PLUGIN.getLogger();
        GameItems.setItemBuilder("default",new DefaultItemBuilder());

        LOGGER.fine("Plugin enabled!");
    }

    @Override
    public void onDisable() {


        LOGGER.fine("Plugin disabled!");
    }
}
