package ping.mc.game.attribute;

import org.json.simple.JSONObject;

public class GameAttribute {
    private String id="DEFAULT_ATTRIBUTE";
    private String name="Default Attribute";
    private String symbol="☼";
    private String effect="§c";

    public GameAttribute(String id) {
        this.id = id;
    }

    public GameAttribute(JSONObject jsonObject) {
        id= (String) jsonObject.getOrDefault("id","DEFAULT_ATTRIBUTE");
        name= (String) jsonObject.getOrDefault("name","Default Attribute");
        effect= (String) jsonObject.getOrDefault("effect","§f");
        symbol= (String) jsonObject.getOrDefault("symbol","☼");
    }

    public String getName() {
        return name;
    }

    public GameAttribute setName(String name) {
        this.name = name;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public GameAttribute setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getEffect() {
        return effect;
    }

    public GameAttribute setEffect(String effect) {
        this.effect = effect;
        return this;
    }

    public String getId() {
        return id;
    }
}
