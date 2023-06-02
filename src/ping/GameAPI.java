package ping;

import org.bukkit.entity.Player;
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
        LOGGER=Logger.getLogger("Minecraft");
        GameItems.setItemBuilder("default",new DefaultItemBuilder());

        this.getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {


        LOGGER.info("Plugin disabled!");
    }

    public void sendFormattedMessage(Player p, String json) {
        getServer().dispatchCommand( getServer().getConsoleSender(), "tellraw " + p.getName() + " " + json);
    }
}
