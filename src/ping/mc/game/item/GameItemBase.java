package ping.mc.game.item;

import org.bukkit.Color;
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
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GameItemBase {
    private String id="DEFAULT_ITEM";
    private String name="Default Item";

    private HashMap<String,Object> data=new HashMap<>();
    private Material material=Material.STICK;
    private String description =null;
    private GameRarity rarity=new GameRarity("COMMON").setEffect("§f").setName("Common").setRank(0);
    private GameType type=new GameType("ITEM").setGameAttributeSlot(GameAttributeSlot.ANY);
    private String itemBuilder="default";

    private boolean shiny=false;
    private List<GameAttributeModifier> attributes=new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private List<String> abilities = new ArrayList<>();
    private Color color=Color.MAROON;
    private String texture="eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZiYjlmYjk3YmE4N2NiNzI3Y2QwZmY0NzdmNzY5MzcwYmVhMTljY2JmYWZiNTgxNjI5Y2Q1NjM5ZjJmZWMyYiJ9fX0=";

    public String getTexture() {
        return texture;
    }

    public GameItemBase setTexture(String texture) {
        this.texture = texture;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public GameItemBase setColor(Color color) {
        this.color = color;
        return this;
    }

    public GameItemBase(String id){
        this.id=id;
    }

    public boolean isShiny() {
        return shiny;
    }

    public GameItemBase setShiny(boolean shiny) {
        this.shiny = shiny;
        return this;
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
        texture= (String) jsonObject.getOrDefault("texture",texture);
        rarity= GameRarities.getRarity((String) jsonObject.getOrDefault("rarity","COMMON"));
        type= (GameType) jsonObject.getOrDefault("type", type);
        itemBuilder= (String) jsonObject.getOrDefault("item_builder",itemBuilder);
        shiny= (boolean) jsonObject.getOrDefault("shiny",shiny);
        String[] color1= ((String) jsonObject.getOrDefault("color", "92:58:29")).split(":");
        color=Color.fromRGB(Integer.parseInt(color1[0]),Integer.parseInt(color1[1]),Integer.parseInt(color1[2]));
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
