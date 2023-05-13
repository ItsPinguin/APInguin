package ping.mc.game.rarity;

import org.json.simple.JSONObject;

public class  Rarity {
    private String id="COMMON";
    private String name="Common";
    private String effect = "§f";
    int rank=0;
    public Rarity(){}
    public Rarity(JSONObject jsonObject){
        id= (String) jsonObject.getOrDefault("id","COMMON");
        name= (String) jsonObject.getOrDefault("name","Common");
        effect= (String) jsonObject.getOrDefault("effect","§f");
        rank= (Integer) jsonObject.getOrDefault("rank",0);
    }
    public Rarity(String name, String effect, int rank) {
        this.name = name;
        this.effect = effect;
        this.rank = rank;
    }
    public Rarity(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public Rarity setName(String name) {
        this.name = name;
        return  this;
    }

    public String getEffect() {
        return effect;
    }

    public Rarity setEffect(String effect) {
        this.effect = effect;
        return this;
    }

    public int getRank() {
        return rank;
    }

    public Rarity setRank(int rank) {
        this.rank = rank;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
