package ping.mc.game.item;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTListCompound;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.json.simple.JSONObject;
import ping.addon.PingAddon;
import ping.apinguin.APInguin;

import java.util.HashMap;

public class GameItem {
    HashMap<String, Object> data = new HashMap<>();

    public GameItem(ItemStack itemStack){
        data=identify(itemStack).getData();
    }

    public GameItem(){}

    public HashMap<String, Object> getData() {
        return data;
    }

    public static GameItem identify(ItemStack itemStack){
        GameItem gameItem=new GameItem();
        gameItem.getData().put("gameItem",true);
        if (itemStack==null || itemStack.getType()== Material.AIR || new NBTItem(itemStack).getCompound("Data")==null){
            gameItem.getData().put("gameItem",false);
            return gameItem;
        }
        String id=new NBTItem(itemStack).getCompound("Data").getString("id");
        if (APInguin.Registries.ITEM_BASES.get(id) != null){
            gameItem.data=APInguin.Registries.ITEM_BASES.get(id).getData();
        }
        for (PingAddon addon : APInguin.Registries.ADDONS.values()) {
            gameItem=addon.getItemAddon().parseWithItemStack(gameItem,itemStack);
        }
        return gameItem;
    }

    public ItemStack toItemStack(){
        ItemStack itemStack = new ItemStack(this.getMaterial());
        NBTItem item=new NBTItem(itemStack);
        item.getOrCreateCompound("Data").setString("id", this.getId());
        if (itemStack.getType()== Material.PLAYER_HEAD) {
            NBTCompound skull = item.addCompound("SkullOwner");
            skull.setString("Id", "fce0323d-7f50-4317-9720-5f6b14cf78ea");
            NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
            texture.setString("Value", this.getTexture());
        }
        itemStack=item.getItem();
        ItemMeta itm=itemStack.getItemMeta();
        assert itm != null;
        if (itemStack.getType() == Material.LEATHER_HELMET || itemStack.getType() == Material.LEATHER_CHESTPLATE || itemStack.getType() == Material.LEATHER_LEGGINGS || itemStack.getType() == Material.LEATHER_BOOTS){
            ((LeatherArmorMeta) itm).setColor(this.getColor());
        }
        itm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS,ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itm);
        for (PingAddon addon : APInguin.Registries.ADDONS.values()) {
            itemStack=addon.getItemAddon().constructItem(this, itemStack);
        }
        for (PingAddon addon : APInguin.Registries.ADDONS.values()) {
            itemStack=addon.getItemAddon().buildItemDisplay(this, itemStack);
        }
        return itemStack;
    }

    public GameItem(JSONObject jsonObject){
        data=jsonObject;
        data.put("material",Material.valueOf((String) data.get("material")));
        String[] color1= ((String) data.getOrDefault("color", "92:58:29")).split(":");
        data.put("color", Color.fromRGB(Integer.parseInt(color1[0]),Integer.parseInt(color1[1]),Integer.parseInt(color1[2])));
    }

    public String getTexture() {
        return (String) data.getOrDefault("texture","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZiYjlmYjk3YmE4N2NiNzI3Y2QwZmY0NzdmNzY5MzcwYmVhMTljY2JmYWZiNTgxNjI5Y2Q1NjM5ZjJmZWMyYiJ9fX0=");
    }

    public GameItem setTexture(String texture) {
        data.put("texture",texture);
        return this;
    }

    public Color getColor() {
        return (Color) data.getOrDefault("color", Color.MAROON);
    }

    public GameItem setColor(Color color) {
        data.put("color",color);
        return this;
    }

    public GameItem(String id){
        data.put("id",id);
    }

    public boolean isShiny() {
        return (boolean) data.getOrDefault("shiny",false);
    }

    public GameItem setShiny(boolean shiny) {
        data.put("shiny",shiny);
        return this;
    }

    public String getId() {
        return (String) data.get("id");
    }

    public Material getMaterial() {
        return (Material) data.get("material");
    }

    public GameItem setMaterial(Material material) {
        data.put("material",material);
        return this;
    }

    public String getName() {
        return (String) data.get("name");
    }

    public GameItem setName(String name) {
        data.put("name",name);
        return this;
    }
}
