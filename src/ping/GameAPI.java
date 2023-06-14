package ping;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ping.mc.game.attribute.GameAttributes;
import ping.mc.game.item.DefaultItemBuilder;
import ping.mc.game.item.GameItems;
import ping.mc.game.item.ability.GameAbilityEvents;
import ping.mc.game.item.type.GameTypes;
import ping.mc.game.profile.GameProfileEvents;
import ping.mc.game.rarity.GameRarities;
import ping.utils.Config;

import java.io.File;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class GameAPI extends JavaPlugin {
    public static Plugin PLUGIN;
    public static Logger LOGGER;
    @Override
    public void onEnable() {
        PLUGIN=this;
        LOGGER=Logger.getLogger("GameAPI");
        LOGGER.info("Enabling plugin ...");
        new File(Config.PLAYER_PROFILES_DIRECTORY).mkdirs();
        new File(Config.PROFILES_DIRECTORY).mkdirs();
        Bukkit.getPluginManager().registerEvents(new GameAbilityEvents(),this);
        Bukkit.getPluginManager().registerEvents(new GameProfileEvents(),this);
        GameItems.setItemBuilder("default",new DefaultItemBuilder());
        GameAttributes.loadAllFromPath(Paths.get(Config.ATTRIBUTE_LOADING_PATH),"-");
        GameRarities.loadAllFromPath(Paths.get(Config.RARITIES_LOADING_PATH),"-");
        GameTypes.loadAllFromPath(Paths.get(Config.TYPE_LOADING_PATH),"-");
        GameItems.loadAllFromPath(Paths.get(Config.ITEMS_LOADING_PATH),"-");
        LOGGER.info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        LOGGER.info("Plugin disabled!");
    }
}
