package ping.mc.game.item;

import org.bukkit.inventory.ItemStack;

public interface GameItemBuilder {
    default ItemStack build(ItemStack itemStack){return itemStack;}
}