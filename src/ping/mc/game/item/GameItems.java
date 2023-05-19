package ping.mc.game.item;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ping.GameAPI;
import ping.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class GameItems {
    private static HashMap<String,ItemBuilder> itemBuilders=new HashMap<>();
    private static HashMap<Object, GameItemBase> itemMap = new HashMap<>();

    public static void addItem(GameItemBase item){
        itemMap.put(item.getId(), item);
    }

    public static GameItemBase getItem(String key){
        return itemMap.get(key);
    }

    public static ItemStack getItemStack(String ID){
        ItemStack itemStack = new ItemStack(Material.STICK);
        NBTItem item=new NBTItem(itemStack);
        item.getOrCreateCompound("Data").setString("id",ID);
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
                GameAPI.LOGGER.info("Loading directory: "+path.toFile());
                Files.walk(path).forEach(filePath ->{
                    if (filePath.toFile().isFile()&& !filePath.toString().startsWith(excludePrefix)){
                        addItem(new GameItemBase(FileUtils.readJSONObject(filePath.toString())));
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ItemBuilder getItemBuilder(String id) {return itemBuilders.get(id);}

    public static void setItemBuilder(String id ,ItemBuilder itemBuilder) {itemBuilders.put(id,itemBuilder);}
}