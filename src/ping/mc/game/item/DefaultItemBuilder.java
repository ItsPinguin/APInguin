package ping.mc.game.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DefaultItemBuilder implements ping.mc.game.item.ItemBuilder {
    @Override
    public ItemStack build(ItemStack itemStack) {
        GameItem gameItem=new GameItem(itemStack);
        if (!gameItem.isGameItem()){
            return itemStack;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(gameItem.getGameItemBase().getName());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
