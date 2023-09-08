package ping.apinguin.game.drop;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import ping.apinguin.APInguin;
import ping.apinguin.addon.PingAddonHandler;
import ping.apinguin.game.condition.Condition;
import ping.apinguin.game.item.PingItem;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class PingDrop implements Listener {
  private final Material id;
  private String item;
  private int minimum = 1;
  private int maximum = 1;
  private int chance = 100;
  private Condition conditions= new Condition();

  public Condition getConditions() {
    return conditions;
  }

  public PingDrop(Material id) {
    this.id = id;
  }

  public PingDrop() {
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

  public ItemStack drop(@Nullable Player p) {
    if (!conditions.check(p,this))
      return new ItemStack(Material.AIR);
    ItemStack itemStack = new PingItem(item).toItemStack();
    AtomicInteger amount= new AtomicInteger();
    if (minimum < maximum)
      amount.set(new Random().nextInt(minimum, maximum));
    if (minimum == maximum)
      amount.set(maximum);
    if (minimum > maximum)
      amount.set(maximum);
    PingAddonHandler.getAddons().values().forEach(addon -> amount.set(addon.getDropHandler().handleAmount(this, amount.get())));
    itemStack.setAmount(amount.get());
    return itemStack;
  }

  public boolean check(@Nullable Player p) {
    if (new Random().nextInt(0, 100) <= chance) {
      return customCheck(p);
    }
    return false;
  }

  public boolean customCheck(@Nullable Player p) {
    return true;
  }

  /***
   * @Override run method for custom function
   */
  public void run() {
  }

  @EventHandler
  public void blockBreak(BlockBreakEvent e) {
    if (e.getPlayer().getGameMode() == GameMode.CREATIVE)
      return;
    switch (DROP_PROFILE) {
      case VANILLA -> {
        return;
      }
      case BOTH -> {

      }
      case REPLACE -> {
        if (BLOCK_DROPS.get(e.getBlock().getType()) != null) {
          e.setDropItems(false);
        }
      }
      case CUSTOM -> {
        e.setDropItems(false);
      }
    }
    if (BLOCK_DROPS.get(e.getBlock().getType()) != null) {
      BLOCK_DROPS.get(e.getBlock().getType()).forEach(drop -> {
        Item item= e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), drop.drop(e.getPlayer()));
        item.setOwner(e.getPlayer().getUniqueId());
      });
    }
  }

  @EventHandler
  public void entityDeath(EntityDeathEvent e){
    switch (DROP_PROFILE) {
      case VANILLA -> {
        return;
      }
      case BOTH -> {

      }
      case REPLACE -> {
        if (ENTITY_DROPS.get(e.getEntity().getType()) != null) {
          e.getDrops().clear();
        }
      }
      case CUSTOM -> {
        e.getDrops().clear();
      }
    }
    if (ENTITY_DROPS.get(e.getEntity().getType()) != null) {
      ENTITY_DROPS.get(e.getEntity().getType()).forEach(drop -> {
        Item item= e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), drop.drop(e.getEntity().getKiller()));
        if (e.getEntity().getKiller()!=null)
          item.setOwner(e.getEntity().getKiller().getUniqueId());
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
  static HashMap<Material, List<PingDrop>> BLOCK_DROPS = new HashMap<>();
  static HashMap<EntityType, List<PingDrop>> ENTITY_DROPS = new HashMap<>();

  static PingDrop.DropProfile DROP_PROFILE = PingDrop.DropProfile.valueOf(APInguin.PLUGIN.getConfig().getString("drops.profile", "BOTH"));
}
