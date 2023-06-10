package ping.mc.game.profile;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ping.GameAPI;
import ping.utils.Config;

public class GameProfileEvents implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent e){
        new GamePlayerProfile(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void leave(PlayerQuitEvent e){
        GamePlayerProfile profile=new GamePlayerProfile(e.getPlayer().getUniqueId());
        profile.save();
        GameProfiles.profiles.get(profile.uuid).save();
        GameProfiles.profiles.remove(profile.getCurrentProfile().uuid);
        GameProfiles.playerProfiles.remove(profile.uuid);
    }

    @EventHandler
    public void saveEvery(PluginEnableEvent e){
        new BukkitRunnable(){
            @Override
            public void run() {
                GameProfiles.playerProfiles.forEach((uuid, gamePlayerProfile) -> gamePlayerProfile.save());
                GameProfiles.profiles.forEach((uuid, gameProfile) -> gameProfile.save());
            }
        }.runTaskTimer(GameAPI.PLUGIN, Config.SAVE_PROFILES_EVERY_X_TICK,Config.SAVE_PROFILES_EVERY_X_TICK);
    }
}
