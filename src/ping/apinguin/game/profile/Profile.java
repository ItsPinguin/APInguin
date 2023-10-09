package ping.apinguin.game.profile;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import ping.apinguin.utils.DataHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public abstract class Profile extends DataHolder {
  private UUID id;
  public Profile(UUID id){
    this.id=id;
  }
  public UUID getId(){
    return id;
  }
  public abstract Profile load();
  public abstract Profile unload();
  public abstract Profile save();
  public abstract Profile create();

  public ItemStack getItemStack(String path){
    return toItemStack((HashMap<String, Object>) get(path));
  }

  public void setItemStack(String path, ItemStack itemStack){
    set(path,toMap(itemStack));
  }

  public static HashMap<String,Object> toMap(ItemStack itemStack){
    HashMap<String,Object> map = null;
    if (itemStack!=null) {
      map=new HashMap<>();
      map.put("id", itemStack.getType().toString().toLowerCase(Locale.ROOT));
    } else
      return null;
    if (itemStack.getType() != Material.AIR && itemStack.hasItemMeta())
      map.put("tag", itemStack.getItemMeta().getAsString());
    if (itemStack.getType() != Material.AIR && itemStack.getAmount() > 1){
      map.put("amount",itemStack.getAmount());
    }
    return map;
  }

  public static ItemStack toItemStack(HashMap<String,Object> map){
    HashMap<String,Object> newMap= (HashMap<String, Object>) map.clone();
    ItemStack itemStack=null;
    if (newMap.get("id")!="air"){
      itemStack= Bukkit.getItemFactory().createItemStack(((String) newMap.get("id"))+ ((String) newMap.getOrDefault("tag","{}")));
      itemStack.setAmount((Integer) newMap.getOrDefault("amount",1));
    }
    return itemStack;
  }

  public static HashMap<String,Object> toMap(Location location){
    HashMap<String,Object> map= (HashMap<String, Object>) location.serialize();
    map.put("world_name", ((World) map.get("world")).getName());
    map.put("world", ((World) map.get("world")).getUID());
    return map;
  }

  public static Location toLocation(HashMap<String,Object> map){
    HashMap<String,Object> newMap= (HashMap<String, Object>) map.clone();
    World world=Bukkit.getWorld((UUID) newMap.get("world"));
    newMap.remove("world_name");
    newMap.put("world",world);
    return Location.deserialize(newMap);
  }

  public static List<String> getCuteNames() {
    return List.of(
        "Bubble",
        "Sprinkles",
        "Buttercup",
        "Twinkle",
        "Cupcake",
        "Marshmallow",
        "Dottie",
        "Honeybee",
        "Daisy",
        "Jellybean",
        "Muffin",
        "Petal",
        "Pebble",
        "Cookie"
    );
  }
}
