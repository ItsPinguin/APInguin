package ping.apinguin.world.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ping.apinguin.addon.PingAddon;
import ping.apinguin.addon.PingAddonHandler;
import ping.apinguin.utils.DataHolder;

import java.util.HashMap;

public class PingItem extends DataHolder {
  public ItemStack toItemStack(){
    ItemStack result=new ItemStack(Material.AIR);
    HashMap<String ,Object> data= (HashMap<String, Object>) this.getData().clone();
    for (PingAddon addon : PingAddonHandler.getAddons().values()) {
      addon.getItemHandler().prepareItemStack(data);
    }
    for (PingAddon addon : PingAddonHandler.getAddons().values()){
      addon.getItemHandler().buildItemStack(result,data);
    }
    return result;
  }
}
