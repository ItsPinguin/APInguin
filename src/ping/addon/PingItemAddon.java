package ping.addon;

import org.bukkit.inventory.ItemStack;
import ping.mc.game.item.GameItem;

public interface PingItemAddon extends PingAddonComponent {
    default GameItem parseFromJSONMap(GameItem gameItem){
        return gameItem;
    }

    default GameItem parseWithItemStack(GameItem gameItem, ItemStack itemStack){return gameItem;}

    default ItemStack buildItemDisplay(GameItem gameItem, ItemStack itemStack){return itemStack;}
    default ItemStack constructItem(GameItem gameItem, ItemStack itemStack){return itemStack;}
}
