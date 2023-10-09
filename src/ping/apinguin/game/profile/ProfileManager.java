package ping.apinguin.game.profile;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ping.apinguin.APInguin;

import java.util.HashMap;
import java.util.UUID;

public class ProfileManager implements Listener {
  private static Class<? extends Profile> profile= JSONProfile.class;
  private static HashMap<UUID, Profile> profiles=new HashMap<>();

  public static HashMap<UUID, Profile> getProfiles() {
    return profiles;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void login(AsyncPlayerPreLoginEvent e) {
    new JSONProfile(e.getUniqueId()).load();
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void join(PlayerJoinEvent e) {
    JSONProfile profile = (JSONProfile) JSONProfile.get(e.getPlayer().getUniqueId());
    profile.set("username",e.getPlayer().getName());
    profile.set("lastJoin", System.currentTimeMillis());
    /*e.getPlayer().getInventory().setContents(profile.getItems("inventory"));
    e.getPlayer().getInventory().setExtraContents(profile.getItems("extra"));
    e.getPlayer().getInventory().setArmorContents(profile.getItems("armor"));*/
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void leave(PlayerQuitEvent e) {
    JSONProfile profile = (JSONProfile) JSONProfile.get(e.getPlayer().getUniqueId());
    /*profile.setItems("inventory",e.getPlayer().getInventory().getContents());
    profile.setItems("extra",e.getPlayer().getInventory().getExtraContents());
    profile.setItems("armor",e.getPlayer().getInventory().getArmorContents());*/
    profile.set("lastLeave", System.currentTimeMillis());
    profile.save();
    profile.unload();
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void saveEvery(PluginEnableEvent e) {
    if (e.getPlugin() == APInguin.PLUGIN) {
      new BukkitRunnable() {
        @Override
        public void run() {
          getProfiles().values().forEach(Profile::save);
        }
      }.runTaskTimerAsynchronously(APInguin.PLUGIN, APInguin.PLUGIN.getConfig().getLong("profiles.saveIntervalTicks"), APInguin.PLUGIN.getConfig().getLong("profiles.saveIntervalTicks"));
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void saveAllOnReload(PluginDisableEvent e) {
    if (e.getPlugin()==APInguin.PLUGIN)
      getProfiles().values().forEach(Profile::save);
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void loadForAllOnline(ServerLoadEvent e){
    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      new JSONProfile(onlinePlayer.getUniqueId()).load();
    }
  }
}
