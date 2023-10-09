package ping.apinguin.game.item;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTListCompound;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.json.simple.JSONObject;
import ping.apinguin.addon.PingAddon;
import ping.apinguin.addon.PingAddonHandler;

import java.util.HashMap;

public class PingItem {
  private static HashMap<String, PingItem> items = new HashMap<>();

  private HashMap<String, Object> data = new HashMap<>();

  public PingItem(ItemStack itemStack) {
    data = identify(itemStack).getData();
  }

  public PingItem() {
  }

  public PingItem(PingItem gameItem) {
    data = gameItem.getData();
  }

  public static HashMap<String, PingItem> getItems() {
    return items;
  }

  public HashMap<String, Object> getData() {
    return data;
  }

  public static PingItem identify(ItemStack itemStack) {
    PingItem gameItem = new PingItem();
    gameItem.getData().put("gameItem", true);
    if (itemStack == null || itemStack.getType() == Material.AIR || new NBTItem(itemStack).getCompound("Data") == null) {
      gameItem.getData().put("gameItem", false);
      gameItem.getData().put("id", "unknown");
      gameItem.getData().put("name", "Unknown");
      return gameItem;
    }
    String id = new NBTItem(itemStack).getCompound("Data").getString("id");
    if (items.get(id) != null) {
      gameItem.data = items.get(id).getData();
    }
    for (PingAddon addon : PingAddonHandler.getAddons().values()) {
      gameItem = addon.getItemHandler().parseWithItemStack(gameItem, itemStack);
    }
    return gameItem;
  }

  public ItemStack toItemStack() {
    ItemStack itemStack = new ItemStack(this.getMaterial());
    NBTItem item = new NBTItem(itemStack);
    item.getOrCreateCompound("Data").setString("id", this.getId());
    if (itemStack.getType() == Material.PLAYER_HEAD) {
      NBTCompound skull = item.addCompound("SkullOwner");
      skull.setString("Id", "fce0323d-7f50-4317-9720-5f6b14cf78ea");
      NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
      texture.setString("Value", this.getTexture());
    }
    itemStack = item.getItem();
    ItemMeta itm = itemStack.getItemMeta();
    assert itm != null;
    itm.setDisplayName("§f" + data.get("name"));
    if (itemStack.getType() == Material.LEATHER_HELMET || itemStack.getType() == Material.LEATHER_CHESTPLATE || itemStack.getType() == Material.LEATHER_LEGGINGS || itemStack.getType() == Material.LEATHER_BOOTS) {
      ((LeatherArmorMeta) itm).setColor(this.getColor());
    }
    if (itemStack.getType() == Material.POTION || itemStack.getType() == Material.SPLASH_POTION || itemStack.getType() == Material.LINGERING_POTION) {
      ((PotionMeta) itm).setColor(this.getColor());
    }
    itm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
        ItemFlag.HIDE_DESTROYS,
        ItemFlag.HIDE_DYE,
        ItemFlag.HIDE_ENCHANTS,
        ItemFlag.HIDE_PLACED_ON,
        ItemFlag.HIDE_POTION_EFFECTS,
        ItemFlag.HIDE_UNBREAKABLE
    );
    itemStack.setItemMeta(itm);
    for (PingAddon addon : PingAddonHandler.getAddons().values()) {
      itemStack = addon.getItemHandler().constructItem(this, itemStack);
    }
    for (PingAddon addon : PingAddonHandler.getAddons().values()) {
      itemStack = addon.getItemHandler().buildItemDisplay(this, itemStack);
    }
    return itemStack;
  }

  public PingItem(String id) {
    data.put("id", id);
    if (getItems().get(id) != null)
      data = getItems().get(id).getData();
    update();
  }

  public PingItem(String id, boolean updates) {
    data.put("id", id);
    if (getItems().get(id) != null)
      data = getItems().get(id).getData();
    if (updates)
      update();
  }

  public PingItem(JSONObject jsonObject) {
    data = jsonObject;
    data.put("material", Material.valueOf((String) data.get("material")));
    String[] color1 = ((String) data.getOrDefault("color", "92:58:29")).split(":");
    data.put("color", Color.fromRGB(Integer.parseInt(color1[0]), Integer.parseInt(color1[1]), Integer.parseInt(color1[2])));
  }

  public String getTexture() {
    return (String) data.getOrDefault("texture", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZiYjlmYjk3YmE4N2NiNzI3Y2QwZmY0NzdmNzY5MzcwYmVhMTljY2JmYWZiNTgxNjI5Y2Q1NjM5ZjJmZWMyYiJ9fX0=");
  }

  public PingItem setTexture(String texture) {
    data.put("texture", texture);
    return this;
  }

  public Color getColor() {
    return (Color) data.getOrDefault("color", Color.MAROON);
  }

  public PingItem setColor(Color color) {
    data.put("color", color);
    return this;
  }

  public boolean isShiny() {
    return (boolean) data.getOrDefault("shiny", false);
  }

  public PingItem setShiny(boolean shiny) {
    data.put("shiny", shiny);
    return this;
  }

  public String getId() {
    return (String) data.get("id");
  }

  public Material getMaterial() {
    return (Material) data.get("material");
  }

  public PingItem setMaterial(Material material) {
    data.put("material", material);
    return this;
  }

  public String getName() {
    return (String) data.get("name");
  }

  public PingItem setName(String name) {
    data.put("name", name);
    return this;
  }

  public void update() {
    getItems().put(getId(), this);
  }
}
