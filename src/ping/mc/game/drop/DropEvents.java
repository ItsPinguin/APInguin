package ping.mc.game.drop;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import ping.apinguin.APInguin;

import java.util.HashMap;
import java.util.List;

public class DropEvents implements Listener {
  static HashMap<Material, List<PingDrop>> DROPS=new HashMap<>();
  static DropProfile DROP_PROFILE= DropProfile.valueOf(APInguin.PLUGIN.getConfig().getString("drops.profile","BOTH"));
  @EventHandler
  public void breakB(BlockBreakEvent e){
    if (e.getPlayer().getGameMode()== GameMode.CREATIVE)
      return;
    switch (DROP_PROFILE){
      case VANILLA -> {
        return;
      }
      case BOTH -> {
      
      }
      case REPLACE -> {
        if (DROPS.get(e.getBlock().getType())!=null) {
          e.setDropItems(false);
        }
      }
      case CUSTOM -> {
        e.setDropItems(false);
      }
    }
    if (DROPS.get(e.getBlock().getType())!=null) {
      DROPS.get(e.getBlock().getType()).forEach(drop -> {
        e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), drop.drop(e.getPlayer())).setOwner(e.getPlayer().getUniqueId());
      });
    }
  }
  
  public enum DropProfile {
    VANILLA,
    BOTH,
    REPLACE,
    CUSTOM
  }
}
