package ping.apinguin.addon;

import org.bukkit.inventory.ItemStack;
import ping.apinguin.game.item.PingItem;

import java.util.HashMap;

public abstract class ItemHandler implements PingAddonComponent {
  public PingItem parseFromJSONMap(PingItem gameItem) {
    return gameItem;
  }

  public PingItem parseWithItemStack(PingItem gameItem, ItemStack itemStack) {
    return gameItem;
  }

  public ItemStack buildItemDisplay(PingItem gameItem, ItemStack itemStack) {
    return itemStack;
  }

  public ItemStack constructItem(PingItem gameItem, ItemStack itemStack) {
    return itemStack;
  }

  public abstract void prepareItemStack(HashMap<String, Object> data);
  public abstract void buildItemStack(ItemStack itemStack, HashMap<String, Object> data);
}
