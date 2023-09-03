package ping.apinguin.game.recipe;

import org.bukkit.inventory.ItemStack;
import ping.apinguin.game.item.PingItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class PingShapelessRecipe {
  private static HashMap<String, PingShapelessRecipe> recipes=new HashMap<>();
  private final String id;
  private HashMap<PingItem, Integer> input=new HashMap<>();
  private HashMap<PingItem, Integer> output=new HashMap<>();
  private String tableId;

  public PingShapelessRecipe(String id) {
    this.id=id;
    recipes.put(id,this);
  }

  public static HashMap<String, PingShapelessRecipe> getRecipes() {
    return recipes;
  }

  public static void setRecipes(HashMap<String, PingShapelessRecipe> recipes) {
    recipes = recipes;
  }

  public String getId() {
    return id;
  }

  public HashMap<PingItem, Integer> getInput() {
    return input;
  }

  public PingShapelessRecipe addInput(PingItem pingItem, int amount) {
    input.put(pingItem, amount);
    return this;
  }

  public PingShapelessRecipe addInput(PingItem pingItem){
    addInput(pingItem,1);
    return this;
  }

  public HashMap<PingItem, Integer> getOutput() {
    return output;
  }

  public PingShapelessRecipe addOutput(PingItem pingItem, int amount) {
    output.put(pingItem, amount);
    return this;
  }

  public PingShapelessRecipe addOutput(PingItem pingItem){
    addOutput(pingItem,1);
    return this;
  }

  public String getTableId() {
    return tableId;
  }

  public PingShapelessRecipe setTableId(String tableId) {
    this.tableId = tableId;
    return this;
  }

  public boolean canCraft(List<ItemStack> input){
    AtomicBoolean correct= new AtomicBoolean(false);
    this.getInput().keySet().forEach( pingItem -> {
      correct.set(false);
      input.forEach( it ->{
        if(Objects.equals(new PingItem(it).getId(), pingItem.getId()) && this.getInput().get(pingItem) <= it.getAmount()){
          correct.set(true);
        }
      });
    });
    return correct.get();
  }

  public List<ItemStack> craft(List<ItemStack> input){
    getInput().keySet().forEach(pingItem -> {
      input.forEach(itemStack -> {
        if (Objects.equals(new PingItem(itemStack).getId(), pingItem.getId()) && itemStack.getAmount()>= getInput().get(pingItem)){
          itemStack.setAmount(itemStack.getAmount()- getInput().get(pingItem));
        }
      });
    });
    List<ItemStack> output=new ArrayList<>();
    output.addAll(input);
    getOutput().keySet().forEach(pingItem -> {
      output.add(pingItem.toItemStack());
    });
    return output;
  }
}
