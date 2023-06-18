package ping.mc.game.item;

import org.bukkit.Material;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ping.mc.game.attribute.GameAttribute;
import ping.mc.game.attribute.GameAttributeModifier;
import ping.mc.game.attribute.GameAttributeSlot;
import ping.mc.game.attribute.GameAttributes;
import ping.mc.game.item.type.GameType;
import ping.mc.game.rarity.GameRarities;
import ping.mc.game.rarity.GameRarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameItemBase {
    private String id="DEFAULT_ITEM";
    private Material material=Material.STICK;
    private String name="Default Item";
    private String description =null;
    private GameRarity rarity=new GameRarity("COMMON");
    private GameType type=new GameType("ITEM").setGameAttributeSlot(GameAttributeSlot.ANY);
    private String itemBuilder="default";

    private boolean shiny=false;
    private List<GameAttributeModifier> attributes=new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    private List<String> abilities = new ArrayList<>();

    public GameItemBase(String id){
        this.id=id;
    }

    public boolean isShiny() {
        return shiny;
    }

    public void setShiny(boolean shiny) {
        this.shiny = shiny;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }

    public GameItemBase(JSONObject jsonObject){
        id= (String) jsonObject.getOrDefault("id",id);
        name= (String) jsonObject.getOrDefault("name",name);
        material= Material.valueOf((String) jsonObject.getOrDefault("material", "STICK"));
        description= (String) jsonObject.getOrDefault("description",description);
        rarity= GameRarities.getRarity((String) jsonObject.getOrDefault("rarity",rarity));
        type= (GameType) jsonObject.getOrDefault("type", type);
        itemBuilder= (String) jsonObject.getOrDefault("item_builder",itemBuilder);
        shiny= (boolean) jsonObject.getOrDefault("shiny",shiny);
        if (jsonObject.get("attributes")!=null){
            JSONObject jsonObject1=((JSONObject) jsonObject.get("attributes"));
            for (GameAttribute value : GameAttributes.attributes.values()) {
                for (Object o : jsonObject1.keySet()) {
                    if (Objects.equals(o, value.getId())) {
                        attributes.add(new GameAttributeModifier(jsonObject1.get(o),value.getId()));
                        break;
                    }
                }
            }
        }

        if (jsonObject.get("tags")!=null){
            tags.addAll(((JSONArray) jsonObject.get("tags")));
        }

        if (jsonObject.get("abilities")!=null){
            abilities.addAll(((JSONArray) jsonObject.get("abilities")));
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

    public GameRarity getRarity() {
        return rarity;
    }

    public GameItemBase setRarity(GameRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public GameType getType() {
        return type;
    }

    public GameItemBase setType(GameType type) {
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
