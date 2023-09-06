package ping.apinguin.game.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import ping.apinguin.game.item.PingItem;

import javax.annotation.Nullable;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PingCraftingTable {
  private static HashMap<String, PingCraftingTable> recipeBlocks=new HashMap<>();
  private static HashMap<Location, String> placedRecipeBlocks=new HashMap<>();

  private final String id;

  private Material material=Material.BARRIER;
  private String texture;
  public PingCraftingTable(String id) {
    this.id = id;
    recipeBlocks.put(id,this);
  }

  public static HashMap<String, PingCraftingTable> getRecipeBlocks() {
    return recipeBlocks;
  }

  public static void setRecipeBlocks(HashMap<String, PingCraftingTable> recipeBlocks) {
    PingCraftingTable.recipeBlocks = recipeBlocks;
  }

  public String getId() {
    return id;
  }

  public Material getMaterial() {
    return material;
  }

  public PingCraftingTable setMaterial(Material material) {
    this.material = material;
    return this;
  }

  public String getTexture() {
    return texture;
  }

  public PingCraftingTable setTexture(String texture) {
    this.texture = texture;
    return this;
  }

  public static HashMap<Location, String> getPlacedRecipeBlocks() {
    return placedRecipeBlocks;
  }

  public static void setPlacedRecipeBlocks(HashMap<Location, String> placedRecipeBlocks) {
    PingCraftingTable.placedRecipeBlocks = placedRecipeBlocks;
  }

  public static void load(){
    try {
      new File("plugins/APInguin/data").mkdirs();
      if (!new File("plugins/APInguin/data/placedRecipeBlocks").exists()){
        new File("plugins/APInguin/data/placedRecipeBlocks").createNewFile();
      }
      HashMap<Map<String,Object>, String> toDeserialize= (HashMap<Map<String, Object>, String>) new ObjectInputStream(new FileInputStream("plugins/APInguin/data/placedRecipeBlocks")).readObject();

      placedRecipeBlocks= new HashMap<>();

      toDeserialize.keySet().forEach(key -> {
        key.put("world", Bukkit.getWorld(((String) key.get("world"))));
        placedRecipeBlocks.put(Location.deserialize(key),toDeserialize.get(key));
      });
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static void save(){
    try {
      new File("plugins/APInguin/data").mkdirs();
      HashMap<Map<String,Object>, String> serialize=new HashMap<>();
      placedRecipeBlocks.keySet().forEach(location -> {
        Map<String, Object> loc=location.serialize();
        loc.put("world", ((World) loc.get("world")).getName());
        serialize.put(location.serialize(),placedRecipeBlocks.get(location));
      });
      new ObjectOutputStream(new FileOutputStream("plugins/APInguin/data/placedRecipeBlocks")).writeObject(serialize);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void attemptCraft(Location loc, @Nullable Player player){
    Location loc2=loc.clone();
    if (getPlacedRecipeBlocks().get(loc)!=null && getRecipeBlocks().get(getPlacedRecipeBlocks().get(loc))!=null){
      List<Item> input=new ArrayList<>();
      loc.getWorld().getEntities().forEach(entity -> {
        if (entity instanceof Item item &&
            entity.getLocation().getBlockX() >=loc.getBlockX() && entity.getLocation().getBlockX() <=loc.getBlockX()+1 &&
            entity.getLocation().getBlockY() >=loc.getBlockY() && entity.getLocation().getBlockY() <=loc.getBlockY()+1.5 &&
            entity.getLocation().getBlockZ() >=loc.getBlockZ() && entity.getLocation().getBlockZ() <=loc.getBlockZ()+1 ) {
          input.add(item);
        }
      });
      if (!input.isEmpty()){
        getRecipeBlocks().get(getPlacedRecipeBlocks().get(loc)).craft(loc2, player, input);
      }
    }
  }

  public void craft(Location loc, @Nullable Player player, List<Item> input){
    List<ItemStack> itemStacks=new ArrayList<>();
    input.forEach(item -> {
      itemStacks.add(item.getItemStack());
    });
    PingShapelessRecipe.getRecipes().values().forEach(recipe -> {
      if (recipe.canCraft(itemStacks, getId())){
        input.forEach(Entity::remove);
        recipe.craft(itemStacks).forEach(itemStack -> {
          Item item= loc.getWorld().dropItem(loc.clone().add(0.5,1,0.5), itemStack);
          item.setOwner(player.getUniqueId());
          item.setVelocity(new Vector(0,0,0));
        });
      }
    });
  }

  public void place(Location loc){
    attemptRemove(loc);
    loc.getBlock().setType(getMaterial());
    if (getTexture()!=null){
      ItemDisplay itemDisplay=loc.getWorld().spawn(loc.clone(), ItemDisplay.class);
      itemDisplay.setItemStack(new PingItem("NULL", false).setMaterial(Material.PLAYER_HEAD).setTexture(getTexture()).toItemStack());
      itemDisplay.setTransformation(new Transformation(new Vector3f(0.5f,1,0.5f), new AxisAngle4f(), new Vector3f(2,2,2),new AxisAngle4f()));
    }
    getPlacedRecipeBlocks().put(loc,this.getId());
  }

  public static void attemptRemove(Location loc){
    if (getPlacedRecipeBlocks().get(loc)!=null && getRecipeBlocks().get(getPlacedRecipeBlocks().get(loc))!=null){
      getRecipeBlocks().get(getPlacedRecipeBlocks().get(loc)).remove(loc);
      getPlacedRecipeBlocks().remove(loc);
    }
  }

  public void remove(Location loc){
    loc.getBlock().setType(Material.AIR);
    loc.getWorld().getEntities().forEach(entity -> {
      if (entity instanceof ItemDisplay itemDisplay && itemDisplay.getLocation().distance(loc.clone())<0.1 ) {
        entity.remove();
      }
    });
  }
}
