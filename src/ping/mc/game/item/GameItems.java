package ping.mc.game.item;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTListCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import ping.GameAPI;
import ping.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GameItems {
    private static HashMap<String, GameItemBuilder> itemBuilders=new HashMap<>();
    private static HashMap<Object, GameItemBase> itemMap = new HashMap<>();

    public static void addItem(GameItemBase item){
        itemMap.put(item.getId(), item);
    }

    public static GameItemBase getItem(String key){
        return itemMap.get(key);
    }

    public static ItemStack getItemStack(String ID){
        GameItemBase itemBase=getItem(ID);
        ItemStack itemStack = new ItemStack(itemBase.getMaterial());
        NBTItem item=new NBTItem(itemStack);
        item.getOrCreateCompound("Data").setString("id",ID);
        if (itemStack.getType()== Material.PLAYER_HEAD) {
            NBTCompound skull = item.addCompound("SkullOwner");
            skull.setString("Id", "fce0323d-7f50-4317-9720-5f6b14cf78ea");
            NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
            texture.setString("Value", itemBase.getTexture());
        }
        itemStack=item.getItem();
        ItemMeta itm=itemStack.getItemMeta();
        assert itm != null;
        if (itemStack.getType() == Material.LEATHER_HELMET || itemStack.getType() == Material.LEATHER_CHESTPLATE || itemStack.getType() == Material.LEATHER_LEGGINGS || itemStack.getType() == Material.LEATHER_BOOTS){
            ((LeatherArmorMeta) itm).setColor(itemBase.getColor());
        }
        itm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS,ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itm);
        return getItemBuilder("default").build(item.getItem());
    }

    public static HashMap<Object, GameItemBase> getItemMap(){
        return itemMap;
    }

    public static void loadAllFromPath(Path path, String excludePrefix){
        try {
            if (path.toFile().isFile() && !path.toString().startsWith(excludePrefix)){
                addItem(new GameItemBase(FileUtils.readJSONObject(path.toString())));
            } else if (path.toFile().isDirectory() && !path.toString().startsWith(excludePrefix)) {
                GameAPI.LOGGER.info("Loading items from directory: "+path.toFile());
                AtomicInteger loaded= new AtomicInteger();
                Files.walk(path).forEach(filePath ->{
                    if (filePath.toFile().isFile()&& !filePath.toString().startsWith(excludePrefix)){
                        addItem(new GameItemBase(FileUtils.readJSONObject(filePath.toString())));
                        loaded.addAndGet(1);
                    }
                });
                GameAPI.LOGGER.info("Loaded "+loaded+" items from files");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameItemBuilder getItemBuilder(String id) {return itemBuilders.get(id);}

    public static void setItemBuilder(String id , GameItemBuilder itemBuilder) {itemBuilders.put(id,itemBuilder);}
}