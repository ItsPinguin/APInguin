package ping.mc.game.drop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PingDrop {
  private final Material id;
  private final UUID uuid=UUID.randomUUID();
  private ItemStack itemStack;
  private int minimum=1;
  private int maximum=1;
  private int chance=100;
  public PingDrop(Material material) {
    this.id = material;
  }

  @Override
  public String toString() {
    return "PingDrop{" +
            "id='" + id + '\'' +
            ", itemStack=" + itemStack +
            ", minimum=" + minimum +
            ", maximum=" + maximum +
            ", chance=" + chance +
            '}';
  }

  public Material getId() {
    return id;
  }

  public ItemStack getItemStack() {
    return itemStack;
  }

  public PingDrop setItemStack(ItemStack itemStack) {
    this.itemStack = itemStack;
    update();
    return this;
  }

  public int getMinimum() {
    return minimum;
  }

  public PingDrop setMinimum(int minimum) {
    this.minimum = minimum;
    update();
    return this;
  }

  public int getMaximum() {
    return maximum;
  }

  public PingDrop setMaximum(int maximum) {
    this.maximum = maximum;
    update();
    return this;
  }

  public int getChance() {
    return chance;
  }

  public PingDrop setChance(int chance) {
    this.chance = chance;
    update();
    return this;
  }

  public ItemStack drop(@Nullable Player p){
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
    List<PingDrop> list=DropEvents.DROPS.getOrDefault(this.id,new ArrayList<>());
    list.removeIf(drop ->  {
      if (drop.uuid==uuid){
        return true;
      }
      return false;
    });
    list.add(this);
    DropEvents.DROPS.put(this.id,list);
  }
}
