package ping.apinguin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ping.apinguin.addon.PingAddon;
import ping.apinguin.game.attribute.GameAttributes;
import ping.apinguin.game.drop.PingDrop;
import ping.apinguin.game.event.TestEvents;
import ping.apinguin.game.item.ability.GameAbilityEvents;
import ping.apinguin.game.item.type.GameTypes;
import ping.apinguin.game.profile.PlayerProfile;
import ping.apinguin.game.rarity.GameRarities;
import ping.apinguin.game.rarity.GameRarity;
import ping.apinguin.game.recipe.PingRecipeBlock;

import java.io.File;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class APInguin extends JavaPlugin implements CommandExecutor {
    public static Plugin PLUGIN;
    public static Logger LOGGER;
    @Override
    public void onLoad(){
        PLUGIN=this;
        LOGGER=this.getLogger();
        loadConfigAndDefaults();
        loadAssets();
        PingRecipeBlock.load();
    }
    @Override
    public void onEnable() {
        registerEvents();
        new File(Config.PLAYER_PROFILES_DIRECTORY).mkdirs();
        new File(Config.PROFILES_DIRECTORY).mkdirs();
        getCommand("apinguin").setExecutor(this);
        GameRarities.addRarity(new GameRarity("COMMON"));
        LOGGER.info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        PingRecipeBlock.save();
        LOGGER.info("Plugin disabled!");
    }

    public static void loadAssets(){
        GameAttributes.loadAllFromPath(Paths.get(Config.ATTRIBUTE_LOADING_PATH),"-");
        GameRarities.loadAllFromPath(Paths.get(Config.RARITIES_LOADING_PATH),"-");
        GameTypes.loadAllFromPath(Paths.get(Config.TYPE_LOADING_PATH),"-");
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new PlayerProfile(),this);
        Bukkit.getPluginManager().registerEvents(new PingAddon(),this);
        Bukkit.getPluginManager().registerEvents(new PingDrop(), this);
        Bukkit.getPluginManager().registerEvents(new TestEvents(), this);

        Bukkit.getPluginManager().registerEvents(new GameAbilityEvents(),this);
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

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length==0){
            commandSender.sendMessage("§aYou are running §6"+APInguin.PLUGIN.getName()+" §aversion §6"+ APInguin.PLUGIN.getDescription().getVersion());
            if (PingAddon.getAddons().size()>0){
                commandSender.sendMessage("§6"+APInguin.PLUGIN.getName()+" §ais using §6"+PingAddon.getAddons().size()+" §aaddon(s):");
                for (String s1 : PingAddon.getAddons().keySet()) {
                    commandSender.sendMessage("- §a"+s1);
                }
            }
        }
        return true;
    }
}
