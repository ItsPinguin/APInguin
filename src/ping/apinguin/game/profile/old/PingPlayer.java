package ping.apinguin.game.profile.old;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ping.apinguin.APInguin;
import ping.apinguin.Config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class PingPlayer implements Listener {
  private HashMap<String, Object> profile=new HashMap<>();
  public PingPlayer(){}
  private PingPlayer(HashMap<String, Object> profile){
    this.profile=profile;
  }
  public HashMap<String, Object> getProfile() {
    return profile;
  }
  public void setProfile(HashMap<String, Object> profile) {
    this.profile = profile;
  }
  public UUID getUniqueId(){
    return UUID.fromString((String) profile.get("uuid"));
  }
  public HashMap<String, Object> getCurrentProfile(){
    return (HashMap<String, Object>) profile.get("current");
  }
  public void save(){
    save(this);
  }

  public void unload(){
    unload(this);
  }
  public void setItems(String path, ItemStack[] itemStacks){
    JSONArray array=new JSONArray();
    for (int i = 0; i < itemStacks.length; i++) {
      array.add(i,toMap(itemStacks[i]));
    }
    if (getCurrentProfile().get("items")==null)
      getCurrentProfile().put("items",new HashMap<>());
    ((HashMap) getCurrentProfile().get("items")).put(path,array);
  }
  public ItemStack[] getItems(String path){
    JSONArray array=((JSONArray) ((HashMap) getCurrentProfile().getOrDefault("items",new HashMap<>())).getOrDefault(path, new JSONArray()));
    ItemStack[] items=new ItemStack[array.size()];
    for (int i = 0; i < array.size(); i++) {
      items[i]=toItemStack(((HashMap) array.get(i)));
    }
    return items;
  }

  public void newProfile(){
    String previousName= "";
    if (getCurrentProfile()!=null){
      ((HashMap<String, Object>) getProfile().get("profiles")).put((String) this.getCurrentProfile().get("id"),this.getCurrentProfile());
      previousName= (String) getCurrentProfile().get("name");
    }
    getProfile().put("current", new HashMap<>());
    this.getCurrentProfile().put("id",UUID.randomUUID().toString());
    this.getCurrentProfile().put("creation", System.currentTimeMillis());
    String name=getCuteNames()[new Random().nextInt(0,getCuteNames().length-1)];
    while (Objects.equals(name, previousName)){
      name=getCuteNames()[new Random().nextInt(0,getCuteNames().length-1)];
    }
    this.getCurrentProfile().put("name", name);

  }

  private HashMap<String,Object> toMap(ItemStack itemStack){
    HashMap<String,Object> map = null;
    if (itemStack!=null) {
      map=new HashMap<>();
      map.put("id", itemStack.getType().toString().toLowerCase(Locale.ROOT));
    } else
      return null;
    if (itemStack.getType() != Material.AIR && itemStack.hasItemMeta())
      map.put("tag", itemStack.getItemMeta().getAsString());
    if (itemStack.getType() != Material.AIR && itemStack.getAmount() > 1){
      map.put("amount",itemStack.getAmount());
    }
    return map;
  }

  private ItemStack toItemStack(HashMap<String,Object> map){
    HashMap<String,Object> newMap= (HashMap<String, Object>) map.clone();
    ItemStack itemStack=null;
    if (newMap.get("id")!="air"){
      itemStack= Bukkit.getItemFactory().createItemStack(((String) newMap.get("id"))+ ((String) newMap.getOrDefault("tag","{}")));
      itemStack.setAmount((Integer) newMap.getOrDefault("amount",1));
    }
    return itemStack;
  }

  private HashMap<String,Object> toMap(Location location){
    HashMap<String,Object> map= (HashMap<String, Object>) location.serialize();
    map.put("world_name", ((World) map.get("world")).getName());
    map.put("world", ((World) map.get("world")).getUID());
    return map;
  }

  private Location toLocation(HashMap<String,Object> map){
    HashMap<String,Object> newMap= (HashMap<String, Object>) map.clone();
    World world=Bukkit.getWorld((UUID) newMap.get("world"));
    newMap.remove("world_name");
    newMap.put("world",world);
    return Location.deserialize(newMap);
  }

  private static HashMap<String, PingPlayer> loadedProfiles=new HashMap<>();
  public static HashMap<String, PingPlayer> getLoadedProfiles() {
    return loadedProfiles;
  }
  public static String[] getCuteNames(){
    return new String[]{
        "Bubble",
        "Sprinkles",
        "Buttercup",
        "Twinkle",
        "Cupcake",
        "Marshmallow",
        "Dottie",
        "Honeybee",
        "Daisy",
        "Jellybean",
        "Muffin",
        "Petal",
        "Pebble",
        "Cookie"
    };
  }

  public static PingPlayer get(OfflinePlayer p){
    if (!getLoadedProfiles().containsKey(p.getUniqueId().toString())){
      load(p);
    }
    return getLoadedProfiles().get(p.getUniqueId().toString());
  }

  public static PingPlayer load(OfflinePlayer p){
    PingPlayer pingPlayer=new PingPlayer(new HashMap<>());
    if (Paths.get(APInguin.PLUGIN.getConfig().getString("profiles.directory")+"/"+p.getUniqueId()+".json").toFile().exists()) {
      try {
        JSONObject jsonObject= (JSONObject) new JSONParser().parse(new FileReader(Paths.get(APInguin.PLUGIN.getConfig().getString("profiles.directory")+"/"+p.getUniqueId()+".json").toFile()));

        pingPlayer= new PingPlayer(jsonObject);
      } catch (IOException | ParseException e) {
        e.printStackTrace();
      }
    } else {
      //profile creation
      pingPlayer.getProfile().put("uuid", p.getUniqueId().toString());
      pingPlayer.getProfile().put("username", p.getName());
      pingPlayer.getProfile().put("main_creation", System.currentTimeMillis());
      pingPlayer.newProfile();
    }
    getLoadedProfiles().put((String) pingPlayer.getProfile().get("uuid"),pingPlayer);
    return pingPlayer;
  }

  public static void unload(OfflinePlayer p){
    getLoadedProfiles().remove(p.getUniqueId().toString());
  }

  public static void unload(PingPlayer p){
    getLoadedProfiles().remove(p.getUniqueId().toString());
  }

  public static void save(PingPlayer pingPlayer){
    saveTo(pingPlayer,APInguin.PLUGIN.getConfig().getString("profiles.directory")+"/"+pingPlayer.getUniqueId()+".json");
  }

  public static void saveTo(PingPlayer pingPlayer, String path){
    pingPlayer.getProfile().computeIfAbsent("profiles", k -> new HashMap<>());
    ((HashMap<String, Object>) pingPlayer.getProfile().get("profiles")).put((String) pingPlayer.getCurrentProfile().get("id"),pingPlayer.getCurrentProfile());
    new File(new File(path).getParent()).mkdirs();
    try{new File(path).createNewFile();}catch (IOException e){
      e.printStackTrace();
    }
    try (FileWriter file = new FileWriter(path)) {
      file.write(new JSONObject(pingPlayer.getProfile()).toJSONString());
      file.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @EventHandler
  public void login(AsyncPlayerPreLoginEvent e) {
    PingPlayer.load(Bukkit.getOfflinePlayer(e.getUniqueId()));
  }

  @EventHandler
  public void join(PlayerJoinEvent e) {
    PingPlayer profile = PingPlayer.get(e.getPlayer());
    profile.getProfile().put("username",e.getPlayer().getName());
    profile.getCurrentProfile().put("last_join", System.currentTimeMillis());
    e.getPlayer().getInventory().setContents(profile.getItems("inventory"));
    e.getPlayer().getInventory().setExtraContents(profile.getItems("extra"));
    e.getPlayer().getInventory().setArmorContents(profile.getItems("armor"));
  }

  @EventHandler
  public void leave(PlayerQuitEvent e) {
    PingPlayer profile = PingPlayer.get(e.getPlayer());
    profile.setItems("inventory",e.getPlayer().getInventory().getContents());
    profile.setItems("extra",e.getPlayer().getInventory().getExtraContents());
    profile.setItems("armor",e.getPlayer().getInventory().getArmorContents());
    profile.getCurrentProfile().put("last_leave", System.currentTimeMillis());
    profile.save();
    profile.unload();
  }

  @EventHandler
  public void saveEvery(PluginEnableEvent e) {
    new BukkitRunnable() {
      @Override
      public void run() {
        PingPlayer.getLoadedProfiles().values().forEach(profile -> profile.save());
      }
    }.runTaskTimerAsynchronously(APInguin.PLUGIN, Config.SAVE_PROFILES_EVERY_X_TICK, Config.SAVE_PROFILES_EVERY_X_TICK);
  }

  @EventHandler
  public void saveAllOnReload(PluginDisableEvent e) {
    PingPlayer.getLoadedProfiles().values().forEach(profile -> profile.save());
  }
}
