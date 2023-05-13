package ping.mc.game.item;

import ping.mc.game.item.type.Type;
import ping.mc.game.rarity.Rarity;
import org.bukkit.Material;
import org.json.simple.JSONObject;

public class GameItemBase {
    private String id="DEFAULT_ITEM";
    private Material material=Material.STICK;
    private String name="Default Item";
    private String description =null;
    private Rarity rarity=Rarity.COMMON;
    private Type type=Type.ITEM;
    private String itemBuilder="default";

    public GameItemBase(){}
    public GameItemBase(JSONObject jsonObject){
    }

    public String getId() {
        return id;
    }

    public GameItemBase setId(String id) {
        this.id = id;
        return this;
    }

    public Material getMaterial() {
        return material;
    }

    public GameItemBase setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public String getName() {
        return name;
    }

    public GameItemBase setName(String name) {
        this.name = name;
        return this;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public GameItemBase setRarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public Type getType() {
        return type;
    }

    public GameItemBase setType(Type type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GameItemBase setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getItemBuilder() {
        return itemBuilder;
    }

    public void setItemBuilder(String itemBuilder) {
        this.itemBuilder = itemBuilder;
    }
}
