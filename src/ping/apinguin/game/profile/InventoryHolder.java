package ping.apinguin.game.profile;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class InventoryHolder implements Serializable {
  private static HashMap<UUID, ItemStack[]> inventories = new HashMap<>();

  public InventoryHolder(ItemStack[] inventory) {
    inventoryKey = UUID.randomUUID();
    inventories.put(inventoryKey, inventory);
    serialize();
  }

  @Serial
  private UUID inventoryKey;
  @Serial
  private String serialized;

  @Override
  public String toString() {
    return "InventoryHolder{" +
        "itemStacks=" + Arrays.toString(inventories.get(inventoryKey)) + ", " +
        "serialized=" + (serialized) +
        '}';
  }

  public void serialize() {
    ItemStack[] itemStacks = inventories.get(inventoryKey);
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

      // Write the size of the inventory
      dataOutput.writeInt(itemStacks.length);

      // Save every element in the list
      for (ItemStack itemStack : itemStacks) {
        dataOutput.writeObject(itemStack);
      }

      // Serialize that array
      dataOutput.close();
      serialized = Base64Coder.encodeLines(outputStream.toByteArray());
    } catch (Exception e) {
      throw new IllegalStateException("Unable to save item stacks.", e);
    }
  }

  public void deserialize() {
    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(serialized));
      BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
      ItemStack[] items = new ItemStack[dataInput.readInt()];

      // Read the serialized inventory
      for (int i = 0; i < items.length; i++) {
        items[i] = (ItemStack) dataInput.readObject();
      }

      dataInput.close();
      inventories.put(inventoryKey, items);
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public ItemStack[] getItemStacks() {
    return inventories.get(inventoryKey);
  }
}
