package ping.mc.game.profile;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ping.Config;
import ping.GameAPI;

public class GameProfileEvents implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent e){
        new GamePlayer(e.getPlayer().getUniqueId()).getCurrentProfile().load(e.getPlayer());
        new GamePlayer(e.getPlayer().getUniqueId()).updateAttribute();
    }

    @EventHandler
    public void invClose(InventoryCloseEvent e){
        new GamePlayer(e.getPlayer().getUniqueId()).updateAttribute();
    }
    @EventHandler
    public void handSwap(PlayerSwapHandItemsEvent e){
        new GamePlayer(e.getPlayer().getUniqueId()).updateAttribute();
    }
    @EventHandler
    public void toolChange(PlayerItemHeldEvent e){
        new GamePlayer(e.getPlayer().getUniqueId()).updateAttribute();
    }

    @EventHandler
    public void leave(PlayerQuitEvent e){
        GamePlayer profile=new GamePlayer(e.getPlayer().getUniqueId());
        profile.save();
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

    @EventHandler
    public void saveAllOnReload(PluginDisableEvent e){
        GameProfiles.playerProfiles.forEach((uuid, gamePlayerProfile) -> gamePlayerProfile.save());
        GameProfiles.profiles.forEach((uuid, gameProfile) -> gameProfile.save());
    }
}
