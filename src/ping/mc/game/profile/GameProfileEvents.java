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
import ping.apinguin.APInguin;
import ping.apinguin.Config;

import java.util.HashMap;

public class GameProfileEvents implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent e){
        //new GamePlayer(e.getPlayer().getUniqueId()).updateAttribute();
        PingProfile  profile=PlayerProfile.get(e.getPlayer().getUniqueId()).getCurrentProfile();
        profile.getData().put("lastJoin",System.currentTimeMillis());
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
    public void invClose(InventoryCloseEvent e){
        //new GamePlayer(e.getPlayer().getUniqueId()).updateAttribute();
    }
    @EventHandler
    public void handSwap(PlayerSwapHandItemsEvent e){
        //new GamePlayer(e.getPlayer().getUniqueId()).updateAttribute();
    }
    @EventHandler
    public void toolChange(PlayerItemHeldEvent e){
        //new GamePlayer(e.getPlayer().getUniqueId()).updateAttribute();
    }

    @EventHandler
    public void leave(PlayerQuitEvent e){
      PlayerProfile profile=PlayerProfile.get(e.getPlayer().getUniqueId());
      profile.getCurrentProfile().getInventories().put(e.getPlayer().getUniqueId()+"$content",new InventoryHolder(e.getPlayer().getInventory().getContents()));
        profile.getCurrentProfile().getInventories().put(e.getPlayer().getUniqueId()+"$armor",new InventoryHolder(e.getPlayer().getInventory().getArmorContents()));
        profile.getCurrentProfile().getInventories().put(e.getPlayer().getUniqueId()+"$extra",new InventoryHolder(e.getPlayer().getInventory().getExtraContents()));
        profile.getCurrentProfile().getData().put("lastQuit",System.currentTimeMillis());
        profile.save();
        APInguin.Registries.PROFILES.remove(profile.getCurrentProfileId());
        APInguin.Registries.PLAYER_PROFILES.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void saveEvery(PluginEnableEvent e){
        new BukkitRunnable(){
            @Override
            public void run() {
                APInguin.Registries.PLAYER_PROFILES.forEach((uuid, gamePlayerProfile) -> gamePlayerProfile.save());
            }
        }.runTaskTimer(APInguin.PLUGIN, Config.SAVE_PROFILES_EVERY_X_TICK,Config.SAVE_PROFILES_EVERY_X_TICK);
    }

    @EventHandler
    public void saveAllOnReload(PluginDisableEvent e){
        APInguin.Registries.PLAYER_PROFILES.forEach((uuid, gamePlayerProfile) -> gamePlayerProfile.save());
    }
}
