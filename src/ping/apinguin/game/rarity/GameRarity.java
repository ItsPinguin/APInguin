package ping.apinguin.game.rarity;

import org.json.simple.JSONObject;

public class GameRarity {
    private String id="COMMON";
    private String name="Common";
    private String effect = "§f";
    int rank=0;
    public GameRarity(String id){
        this.id=id;
    }
    public GameRarity(JSONObject jsonObject){
        id= (String) jsonObject.getOrDefault("id","COMMON");
        name= (String) jsonObject.getOrDefault("name","Common");
        effect= (String) jsonObject.getOrDefault("effect","§f");
        rank= (Integer) jsonObject.getOrDefault("rank",0);
    }
    public GameRarity(String name, String effect, int rank) {
        this.name = name;
        this.effect = effect;
        this.rank = rank;
    }
    public GameRarity(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public GameRarity setName(String name) {
        this.name = name;
        return  this;
    }

    public String getEffect() {
        return effect;
    }

    public GameRarity setEffect(String effect) {
        this.effect = effect;
        return this;
    }

    public int getRank() {
        return rank;
    }

    public GameRarity setRank(int rank) {
        this.rank = rank;
        return this;
    }

    public String getId() {
        return id;
    }
}
