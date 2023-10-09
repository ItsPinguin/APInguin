package ping.apinguin.addon;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class PingAddon {
  public void createItems() {
  }

  public void createRecipes() {
  }

  public void createDrops() {
  }

  public ItemHandler getItemHandler() {
    return new ItemHandler() {
      @Override
      public void prepareItemStack(HashMap<String, Object> data) {

      }

      @Override
      public void buildItemStack(ItemStack itemStack, HashMap<String, Object> data) {

      }
    };
  }
}
