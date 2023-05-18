package ping.mc.game.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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
        itemMeta.setDisplayName(name(gameItem));
        List<String> lore=new ArrayList<>();
        lore.add(typeAndRarity(gameItem));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static String name(GameItem gameItem){
        return gameItem.gameItemBase.getRarity().getEffect()+gameItem.getGameItemBase().getName();
    }

    public static String typeAndRarity(GameItem gameItem){
        return "§l"+gameItem.gameItemBase.getRarity().getEffect()+gameItem.getGameItemBase().getRarity().getName()+" "+gameItem.getGameItemBase().getType().getName();
    }
}
