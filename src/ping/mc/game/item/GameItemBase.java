package ping.mc.game.item;

import org.bukkit.Material;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ping.mc.game.attribute.GameAttributeModifier;
import ping.mc.game.attribute.GameAttributes;
import ping.mc.game.item.type.Type;
import ping.mc.game.rarity.Rarity;

import java.util.ArrayList;
import java.util.List;

public class GameItemBase {
    private String id="DEFAULT_ITEM";
    private Material material=Material.STICK;
    private String name="Default Item";
    private String description =null;
    private Rarity rarity=new Rarity("COMMON");
    private Type type=new Type("ITEM");
    private String itemBuilder="default";
    private List<GameAttributeModifier> attributes=new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    public GameItemBase(String id){
        this.id=id;
    }
    public GameItemBase(JSONObject jsonObject){
        id= (String) jsonObject.getOrDefault("id",id);
        name= (String) jsonObject.getOrDefault("name",name);
        material= (Material) jsonObject.getOrDefault("material", material);
        description= (String) jsonObject.getOrDefault("description",description);
        rarity= (Rarity) jsonObject.getOrDefault("rarity",rarity);
        type= (Type) jsonObject.getOrDefault("type", type);
        itemBuilder= (String) jsonObject.getOrDefault("item_builder",itemBuilder);
        if (jsonObject.get("attributes")!=null){
            for (Object o : ((JSONArray) jsonObject.get("attributes"))) {
                JSONObject jsono= ((JSONObject) o);
                attributes.add(new GameAttributeModifier(
                        GameAttributeModifier.Operation.valueOf((String) jsono.getOrDefault("operation","ADD")),
                        GameAttributes.getAttribute((String) jsono.getOrDefault("attribute","DEFAULT_ATTRIBUTE")))
                );
            }
        }
        if (jsonObject.get("tags")!=null){
            tags.addAll(((JSONArray) jsonObject.get("tags")));
        }
    }

    public String getId() {
        return id;
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

    public List<GameAttributeModifier> getAttributes() {
        return attributes;
    }

    public GameItemBase setAttributes(List<GameAttributeModifier> attributes) {
        this.attributes = attributes;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public GameItemBase setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }
}
