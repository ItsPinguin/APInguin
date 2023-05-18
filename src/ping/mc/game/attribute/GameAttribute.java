package ping.mc.game.attribute;

import org.json.simple.JSONObject;

public class GameAttribute {
    private String id="DEFAULT_STAT";
    private String name="Default Stat";
    private String symbol="☼";
    private String effect="§c";

    public GameAttribute(String id) {
        this.id = id;
    }

    public GameAttribute(JSONObject jsonObject) {
        id= (String) jsonObject.getOrDefault("id","COMMON");
        name= (String) jsonObject.getOrDefault("name","Common");
        effect= (String) jsonObject.getOrDefault("effect","§f");
        symbol= (String) jsonObject.getOrDefault("symbol","☼");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getId() {
        return id;
    }
}
