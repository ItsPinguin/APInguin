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
import ping.mc.game.rarity.GameRarity;

import java.io.File;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class GameAPI extends JavaPlugin {
    public static Plugin PLUGIN;
    public static Logger LOGGER;
    @Override
    public void onEnable() {
        PLUGIN=this;
        LOGGER=this.getLogger();
        LOGGER.info("Enabling plugin ...");
        loadConfigAndDefaults();
        new File(Config.PLAYER_PROFILES_DIRECTORY).mkdirs();
        new File(Config.PROFILES_DIRECTORY).mkdirs();
        GameItems.setItemBuilder("default",new DefaultItemBuilder());
        registerEvents();
        GameRarities.addRarity(new GameRarity("COMMON"));
        loadAssets();
        LOGGER.info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        LOGGER.info("Plugin disabled!");
    }

    public static void loadAssets(){
        GameAttributes.loadAllFromPath(Paths.get(Config.ATTRIBUTE_LOADING_PATH),"-");
        GameRarities.loadAllFromPath(Paths.get(Config.RARITIES_LOADING_PATH),"-");
        GameTypes.loadAllFromPath(Paths.get(Config.TYPE_LOADING_PATH),"-");
        GameItems.loadAllFromPath(Paths.get(Config.ITEMS_LOADING_PATH),"-");
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new GameAbilityEvents(),this);
        Bukkit.getPluginManager().registerEvents(new GameProfileEvents(),this);
    }

    public void loadConfigAndDefaults(){
        getConfig().addDefault("server.name","My server");
        getConfig().addDefault("server.ip","my.server.ip.gg");
        getConfig().addDefault("server.discord","https://discord.gg/tzwCuDKdhn");
        getConfig().addDefault("game.max_mana",50d);
        getConfig().addDefault("game.max_health",50d);
        getConfig().addDefault("game.assets.items","plugins/GameAPI/item");
        getConfig().addDefault("game.assets.rarities","plugins/GameAPI/rarity");
        getConfig().addDefault("game.assets.types","plugins/GameAPI/type");
        getConfig().addDefault("game.assets.attributes","plugins/GameAPI/attribute");
        getConfig().addDefault("game.profile.profiles_directory","plugins/GameAPI/profiles/");
        getConfig().addDefault("game.profile.player_profiles_directory","plugins/GameAPI/player_profiles/");
        getConfig().addDefault("game.profile.ticks_save_every",20*60*5);
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }
}
