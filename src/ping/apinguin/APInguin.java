package ping.apinguin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementDisplayType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import ping.apinguin.addon.PingAddon;
import ping.apinguin.command.APInguinCommand;
import ping.apinguin.events.AddonLoadEvent;
import ping.apinguin.game.attribute.GameAttributes;
import ping.apinguin.game.drop.PingDrop;
import ping.apinguin.game.event.TestEvents;
import ping.apinguin.game.item.ability.GameAbilityEvents;
import ping.apinguin.game.item.type.GameTypes;
import ping.apinguin.game.profile.ProfileManager;
import ping.apinguin.game.rarity.GameRarities;
import ping.apinguin.game.rarity.GameRarity;
import ping.apinguin.game.recipe.PingCraftingTable;
import ping.apinguin.tests.Quest;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Logger;

import static ping.apinguin.addon.PingAddonHandler.getAddons;

public class APInguin extends JavaPlugin implements Listener {
  public static Plugin PLUGIN;
  public static Logger LOGGER;

  @Override
  public void onLoad() {
    PLUGIN = this;
    LOGGER = this.getLogger();
    Translations.load();
    loadConfigAndDefaults();
    loadAssets();
    PingCraftingTable.load();
    Quest.category("quests/root", Material.CLOCK, "§8[ §aQuests §8]", "Complete Quests", "minecraft:textures/block/gold_block.png", AdvancementDisplayType.TASK,false,false,false);
  }

  @Override
  public void onEnable() {
    registerEvents();
    new File(Config.PLAYER_PROFILES_DIRECTORY).mkdirs();
    new File(Config.PROFILES_DIRECTORY).mkdirs();
    getCommand("apinguin").setExecutor(new APInguinCommand());
    GameRarities.addRarity(new GameRarity("COMMON"));
    /*Quest.quest("quests/a_long_dialog", Material.FEATHER,"§8A long dialog","§7Talk with the crazy old man",AdvancementDisplayType.TASK,false,false,false,"quests/root");
    Quest.quest("quests/a_short_dialog", Material.INK_SAC,"§8A short dialog","§7Don't talk with the crazy old man",AdvancementDisplayType.GOAL,false,false,false,"quests/a_long_dialog");
    Bukkit.reloadData();*/
    new BukkitRunnable() {
      @Override
      public void run() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
          try {
            for (Field field : plugin.getClass().getFields()) {
              if (field.get(plugin) instanceof PingAddon addon){
                getAddons().put(plugin.getName(),addon);
                Bukkit.getPluginManager().callEvent(new AddonLoadEvent(addon));
                LOGGER.info("Found addon "+plugin.getName()+" v"+plugin.getDescription().getVersion()+". Loading ...");
              }
            }
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        }
        getAddons().values().forEach(PingAddon::createItems);
        getAddons().values().forEach(PingAddon::createRecipes);
        getAddons().values().forEach(PingAddon::createDrops);
        LOGGER.info("Loaded all addons!");
      }
    }.runTaskLater(this,0);
    LOGGER.info("Plugin enabled!");

  }

  @Override
  public void onDisable() {
    Translations.save();
    PingCraftingTable.save();
    Iterator<Advancement> iterator=Bukkit.advancementIterator();
    while (iterator.hasNext()){
      Advancement advancement= iterator.next();
      if (advancement.getKey().getNamespace().equals(PLUGIN.getName().toLowerCase(Locale.ROOT))){
        Bukkit.getUnsafe().removeAdvancement(advancement.getKey());
      }
    }
    LOGGER.info("Plugin disabled!");
  }

  public static void loadAssets() {
    GameAttributes.loadAllFromPath(Paths.get(Config.ATTRIBUTE_LOADING_PATH), "-");
    GameRarities.loadAllFromPath(Paths.get(Config.RARITIES_LOADING_PATH), "-");
    GameTypes.loadAllFromPath(Paths.get(Config.TYPE_LOADING_PATH), "-");
  }

  public void registerEvents() {
    Bukkit.getPluginManager().registerEvents(new ProfileManager(), this);
    Bukkit.getPluginManager().registerEvents(new PingDrop(), this);
    Bukkit.getPluginManager().registerEvents(new TestEvents(), this);
    Bukkit.getPluginManager().registerEvents(this, this);

    Bukkit.getPluginManager().registerEvents(new GameAbilityEvents(), this);
  }

  public void loadConfigAndDefaults() {
    getConfig().addDefault("server.name", "My server");
    getConfig().addDefault("server.ip", "my.server.ip.gg");
    getConfig().addDefault("server.discord", "https://discord.gg/tzwCuDKdhn");
    getConfig().addDefault("game.max_mana", 50d);
    getConfig().addDefault("game.max_health", 50d);
    getConfig().addDefault("game.assets.items", "plugins/GameAPI/item");
    getConfig().addDefault("game.assets.rarities", "plugins/GameAPI/rarity");
    getConfig().addDefault("game.assets.types", "plugins/GameAPI/type");
    getConfig().addDefault("game.assets.attributes", "plugins/GameAPI/attribute");
    getConfig().addDefault("game.profile.profiles_directory", "plugins/GameAPI/profiles/");
    getConfig().addDefault("game.profile.player_profiles_directory", "plugins/GameAPI/player_profiles/");
    getConfig().addDefault("game.profile.ticks_save_every", 20 * 60 * 5);
    getConfig().addDefault("drops.profile", "BOTH");
    getConfig().addDefault("profiles.directory","plugins/APInguin/profiles/");
    getConfig().addDefault("profiles.saveIntervalTicks",20 * 60 * 5);
    getConfig().options().copyDefaults(true);
    saveConfig();
    reloadConfig();
  }

  @EventHandler
  public void startup(ServerLoadEvent e){
    if (e.getType()== ServerLoadEvent.LoadType.STARTUP){

    }
  }
}
