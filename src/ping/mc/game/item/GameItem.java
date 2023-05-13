package ping.mc.game.item;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class GameItem {
    ItemStack itemStack;
    GameItemBase gameItemBase;
    boolean isGameItem=false;

    public GameItem(ItemStack itemStack){
        this.identify(itemStack);
    }

    public GameItem(){}

    public GameItem identify(ItemStack itemStack){
        this.itemStack=itemStack;
        String ID=new NBTItem(itemStack).getCompound("Data").getString("id");
        isGameItem= GameItems.getItem(ID) != null;
        if (isGameItem){
            gameItemBase=GameItems.getItem(ID);
        }
        return this;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public GameItemBase getGameItemBase() {
        return gameItemBase;
    }

    public boolean isGameItem() {
        return isGameItem;
    }
}
