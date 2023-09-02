package ping.apinguin.game.drop;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import ping.apinguin.APInguin;
import ping.apinguin.game.item.PingItem;

import javax.annotation.Nullable;
import java.util.*;

public class PingDrop implements Listener {
  private final Material id;
  private final UUID uuid=UUID.randomUUID();
  private String item;
  private int minimum=1;
  private int maximum=1;
  private int chance=100;
  public PingDrop(Material id) {
    this.id = id;
  }
  public PingDrop(){
    this.id = Material.AIR;
  }

  public Material getId() {
    return id;
  }

  public String getPingItem() {
    return item;
  }

  public PingDrop setPingItem(PingItem item) {
    this.item = item.getId();
    return this;
  }

  public PingDrop setPingItem(String item) {
    this.item = item;
    return this;
  }

  public int getMinimum() {
    return minimum;
  }

  public PingDrop setMinimum(int minimum) {
    this.minimum = minimum;
    return this;
  }

  public int getMaximum() {
    return maximum;
  }

  public PingDrop setMaximum(int maximum) {
    this.maximum = maximum;
    return this;
  }

  public int getChance() {
    return chance;
  }

  public PingDrop setChance(int chance) {
    this.chance = chance;
    return this;
  }

  public ItemStack drop(@Nullable Player p){
    ItemStack itemStack=new PingItem(item).toItemStack();
    if (minimum<maximum)
      itemStack.setAmount(new Random().nextInt(minimum,maximum));
    if (minimum==maximum)
      itemStack.setAmount(minimum);
    if (minimum>maximum)
      itemStack.setAmount(maximum);
    return itemStack;
  }

  public boolean check(@Nullable Player p){
    if (new Random().nextInt(0,100)<=chance){
      return customCheck(p);
    }
    return false;
  }

  public boolean customCheck(@Nullable Player p){
    return true;
  }

  /***
   * @Override run method for custom function
   */
  public void run(){}
  
  public void update(){
    List<PingDrop> list=DROPS.getOrDefault(this.id,new ArrayList<>());
    list.removeIf(drop ->  {
      if (drop.uuid==uuid){
        return true;
      }
      return false;
    });
    list.add(this);
    DROPS.put(this.id,list);
  }
  
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
  //STATIC PART
  static HashMap<Material, List<PingDrop>> DROPS=new HashMap<>();
  
  static PingDrop.DropProfile DROP_PROFILE= PingDrop.DropProfile.valueOf(APInguin.PLUGIN.getConfig().getString("drops.profile","BOTH"));
}
