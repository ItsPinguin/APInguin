package ping.apinguin.game.drop;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import ping.apinguin.APInguin;
import ping.apinguin.events.drop.DropEvent;
import ping.apinguin.game.condition.Condition;
import ping.apinguin.game.condition.Context;
import ping.apinguin.game.item.PingItem;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PingDrop implements Listener {
  private final Material material;
  private final EntityType entityType;
  private final UUID uuid=UUID.randomUUID();
  private String item;
  private int minimum = 1;
  private int maximum = 1;
  private double chance = 100;
  private Condition conditions= new Condition();

  public Condition getConditions() {
    return conditions;
  }

  public PingDrop(Material id, boolean updates) {
    this.material = id;
    entityType=null;
    if (updates)
      update();
  }

  public PingDrop(Material material){
    this.material = material;
    entityType=null;
    update();
  }

  public PingDrop(EntityType entityType, boolean updates){
    this.entityType=entityType;
    this.material=null;
    if (updates)
      update();
  }

  public PingDrop(EntityType entityType){
    this.entityType=entityType;
    this.material=null;
    update();
  }

  private void update(){
    if (material!=null) {
      List<PingDrop> list = BLOCK_DROPS.getOrDefault(this.material, new ArrayList<>());
      list.removeIf(drop -> {
        return drop.uuid == uuid;
      });
      list.add(this);
      BLOCK_DROPS.put(this.material, list);
    } else if (entityType!=null){
      List<PingDrop> list = ENTITY_DROPS.getOrDefault(this.entityType, new ArrayList<>());
      list.removeIf(drop -> {
        return drop.uuid == uuid;
      });
      list.add(this);
      ENTITY_DROPS.put(this.entityType, list);
    }
  }

  public PingDrop() {
    this.material = null;
    entityType=null;
  }

  public Material getMaterial() {
    return material;
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

  public double  getChance() {
    return chance;
  }

  public PingDrop setChance(int chance) {
    this.chance = chance;
    return this;
  }

  public ItemStack drop(Context context){
    if (!conditions.check(context)) return new ItemStack(Material.AIR);
    ItemStack itemStack = new PingItem(item).toItemStack();
    AtomicInteger amount= new AtomicInteger();
    if (minimum < maximum)
      amount.set(new Random().nextInt(minimum, maximum));
    if (minimum == maximum)
      amount.set(maximum);
    if (minimum > maximum)
      amount.set(maximum);
    itemStack.setAmount(amount.get());
    DropEvent event = new DropEvent(this, context);
    Bukkit.getPluginManager().callEvent(event);
    if (event.isCancelled()) return new ItemStack(Material.AIR);
    return itemStack;
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
        Context context=new Context();
        context.set("block.breaker",e.getPlayer());
        Item item= e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), drop.drop(context));
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
        Context context=new Context();
        context.set("attack.victim",e.getEntity());
        context.set("attack.attacker",e.getEntity());
        Item item= e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), drop.drop(context));
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
  private static HashMap<Material, List<PingDrop>> BLOCK_DROPS = new HashMap<>();
  private static HashMap<EntityType, List<PingDrop>> ENTITY_DROPS = new HashMap<>();
  private static PingDrop.DropProfile DROP_PROFILE = PingDrop.DropProfile.valueOf(APInguin.PLUGIN.getConfig().getString("drops.profile", "BOTH"));


  public static HashMap<Material, List<PingDrop>> getBlockDrops() {
    return BLOCK_DROPS;
  }

  public static HashMap<EntityType, List<PingDrop>> getEntityDrops() {
    return ENTITY_DROPS;
  }

  public static DropProfile getDropProfile() {
    return DROP_PROFILE;
  }
}
