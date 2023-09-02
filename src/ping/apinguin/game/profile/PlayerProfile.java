package ping.apinguin.game.profile;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ping.apinguin.APInguin;
import ping.apinguin.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerProfile implements Serializable, Listener {
    @Serial
    private List<UUID> profiles=new ArrayList<>();
    @Serial
    private UUID currentProfile;
    @Serial
    public UUID getCurrentProfileId(){return currentProfile;}

    public List<UUID> getProfiles() {
        return profiles;
    }

    public static HashMap<UUID, PlayerProfile> getPlayerProfiles() {
        return playerProfiles;
    }

    private static HashMap<UUID, PlayerProfile> playerProfiles=new HashMap<>();

    private final UUID playerUuid;

    public PlayerProfile(){playerUuid=UUID.randomUUID();}

    private PlayerProfile(UUID uuid) {
        playerUuid=uuid;
        load();
    }

    public PingProfile getCurrentProfile(){
        return PingProfile.get(currentProfile);
    }

    public void load(){
        if (getPlayerProfiles().get(playerUuid)==null) {
          APInguin.LOGGER.info("Profile not loaded! Loading ...");
          if (!new File(Config.PLAYER_PROFILES_DIRECTORY + playerUuid).exists()) {
            currentProfile = UUID.randomUUID();
            profiles.add(currentProfile);
            PingProfile.get(currentProfile);
              getPlayerProfiles().put(playerUuid, this);
          } else if (new File(Config.PLAYER_PROFILES_DIRECTORY + playerUuid).exists()){
            try {
                getPlayerProfiles().put(playerUuid, (PlayerProfile) new ObjectInputStream(new FileInputStream(Config.PLAYER_PROFILES_DIRECTORY + playerUuid)).readObject());
              PingProfile profile=getPlayerProfiles().get(playerUuid).getCurrentProfile();
              if (profile.getInventories()==null){
                profile.setInventories(new HashMap<>());
              }
              profile.getInventories().values().forEach(InventoryHolder::deserialize);
            } catch (IOException | ClassNotFoundException e) {
              throw new RuntimeException(e);
            }
          }
        }
    }

    public static PlayerProfile get(UUID playerUuid){
      new PlayerProfile(playerUuid);
      return getPlayerProfiles().get(playerUuid);
    }
    public void save(){
        try {
            new File(Config.PLAYER_PROFILES_DIRECTORY+playerUuid).createNewFile();
            new ObjectOutputStream(new FileOutputStream(Config.PLAYER_PROFILES_DIRECTORY+playerUuid)).writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getCurrentProfile().getInventories().values().forEach(InventoryHolder::serialize);
        getCurrentProfile().save();
    }

    public void update(){
        getPlayerProfiles().put(playerUuid,this);
        getCurrentProfile().update();
    }

    @EventHandler
    public void login(AsyncPlayerPreLoginEvent e){
        PlayerProfile.get(e.getUniqueId()).getCurrentProfile();
    }
    @EventHandler
    public void join(PlayerJoinEvent e){
        //new GamePlayer(e.getPlayer().getUniqueId()).updateAttribute();
        PingProfile  profile=PlayerProfile.get(e.getPlayer().getUniqueId()).getCurrentProfile();
        profile.getData().put("lastJoin",System.currentTimeMillis());
//      JsonObject d = (JsonObject) new Gson().toJsonTree("d");d.asMap();
//      new Gson().fromJson(d, ItemStack.class);
        if (profile.getInventories()==null)
            profile.setInventories(new HashMap<>());
        e.getPlayer().getInventory().setContents(profile.getInventories().getOrDefault(e.getPlayer().getUniqueId()+"$content",
                new InventoryHolder(e.getPlayer().getInventory().getContents())
        ).getItemStacks());
        e.getPlayer().getInventory().setExtraContents(profile.getInventories().getOrDefault(e.getPlayer().getUniqueId()+"$extra",
                new InventoryHolder(e.getPlayer().getInventory().getExtraContents())
        ).getItemStacks());
        e.getPlayer().getInventory().setArmorContents(profile.getInventories().getOrDefault(e.getPlayer().getUniqueId()+"$armor",
                new InventoryHolder(e.getPlayer().getInventory().getArmorContents())
        ).getItemStacks());
    }
    @EventHandler
    public void leave(PlayerQuitEvent e){
        PlayerProfile profile=PlayerProfile.get(e.getPlayer().getUniqueId());
        profile.getCurrentProfile().getInventories().put(e.getPlayer().getUniqueId()+"$content",new InventoryHolder(e.getPlayer().getInventory().getContents()));
        profile.getCurrentProfile().getInventories().put(e.getPlayer().getUniqueId()+"$armor",new InventoryHolder(e.getPlayer().getInventory().getArmorContents()));
        profile.getCurrentProfile().getInventories().put(e.getPlayer().getUniqueId()+"$extra",new InventoryHolder(e.getPlayer().getInventory().getExtraContents()));
        profile.getCurrentProfile().getData().put("lastQuit",System.currentTimeMillis());
        profile.save();
        PingProfile.getProfiles().remove(profile.getCurrentProfileId());
        PlayerProfile.getPlayerProfiles().remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void saveEvery(PluginEnableEvent e){
        new BukkitRunnable(){
            @Override
            public void run() {
                PlayerProfile.getPlayerProfiles().forEach((uuid, gamePlayerProfile) -> gamePlayerProfile.save());
            }
        }.runTaskTimerAsynchronously(APInguin.PLUGIN, Config.SAVE_PROFILES_EVERY_X_TICK,Config.SAVE_PROFILES_EVERY_X_TICK);
    }

    @EventHandler
    public void saveAllOnReload(PluginDisableEvent e){
        PlayerProfile.getPlayerProfiles().forEach((uuid, gamePlayerProfile) -> gamePlayerProfile.save());
    }
}
