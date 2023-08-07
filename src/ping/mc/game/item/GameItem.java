package ping.mc.game.item;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ping.addon.PingAddon;
import ping.apinguin.APInguin;
import ping.mc.game.attribute.GameAttributeModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameItem {
    ItemStack itemStack;
    GameItemBase gameItemBase=new GameItemBase("NULL");
    boolean isGameItem=false;

    public GameItem(ItemStack itemStack){
        this.identify(itemStack);
    }

    public GameItem(){}

    public static GameItem identify(ItemStack itemStack){
        GameItem gameItem=new GameItem();
        gameItem.itemStack=itemStack;
        if (itemStack==null || itemStack.getType()== Material.AIR || new NBTItem(itemStack).getCompound("Data")==null){
            return gameItem;
        }
        String ID=new NBTItem(itemStack).getCompound("Data").getString("id");
        gameItem.isGameItem= GameItems.getItem(ID) != null;
        if (gameItem.isGameItem){
            gameItem.gameItemBase=GameItems.getItem(ID);
        }
        for (PingAddon addon : APInguin.addons) {
            gameItem=addon.item(gameItem);
        }
        return gameItem;
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

    public List<GameAttributeModifier> getAttribute(String attribute){
        List<GameAttributeModifier> modifiers=new ArrayList<>(gameItemBase.getAttributes());
        modifiers.removeIf(modifier -> !Objects.equals(modifier.getAttribute(), attribute));
        return modifiers;
    }
}
