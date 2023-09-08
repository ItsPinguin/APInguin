package ping.apinguin.addon;

import org.bukkit.inventory.ItemStack;
import ping.apinguin.game.item.PingItem;

public interface PingItemHandler extends PingAddonComponent {
  default PingItem parseFromJSONMap(PingItem gameItem) {
    return gameItem;
  }

  default PingItem parseWithItemStack(PingItem gameItem, ItemStack itemStack) {
    return gameItem;
  }

  default ItemStack buildItemDisplay(PingItem gameItem, ItemStack itemStack) {
    return itemStack;
  }

  default ItemStack constructItem(PingItem gameItem, ItemStack itemStack) {
    return itemStack;
  }
}
