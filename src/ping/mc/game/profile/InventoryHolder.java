package ping.mc.game.profile;

import org.bukkit.inventory.ItemStack;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryHolder implements Serializable {
  private static HashMap<UUID, ItemStack[]> inventories=new HashMap<>();

  public InventoryHolder(ItemStack[] inventory) {
    inventoryKey=UUID.randomUUID();
    inventories.put(inventoryKey,inventory);
    serialize();
  }

  @Serial
  private UUID inventoryKey;
  @Serial
  private Map<String,Object>[] serialized;

  @Override
  public String toString() {
    return "InventoryHolder{" +
        "itemStacks=" + Arrays.toString(inventories.get(inventoryKey)) +
        "serialized=" + Arrays.toString(serialized) +
        '}';
  }

  public void serialize(){
    ItemStack[] itemStacks=inventories.get(inventoryKey);
    serialized=new Map[itemStacks.length];
    for (int i = 0; i < itemStacks.length; i++) {
      if (itemStacks[i]!=null) {
        serialized[i] = itemStacks[i].serialize();
        if (itemStacks[i].getItemMeta()!=null)
          serialized[i].put("meta", itemStacks[i].getItemMeta().serialize());
      }
    }
  }

  public void deserialize(){
    ItemStack[] itemStacks=new ItemStack[serialized.length];
    for (int i = 0; i < serialized.length; i++) {
      if (serialized[i]!=null) {
        itemStacks[i]=ItemStack.deserialize(serialized[i]);
      } else {
        itemStacks[i]=null;
      }
    }
    inventories.put(inventoryKey,itemStacks);

  }

  public ItemStack[] getItemStacks() {
    return inventories.get(inventoryKey);
  }
}
