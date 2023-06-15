package ping.mc.game.item.type;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameType {
    String name="Item";
    String id="ITEM";
    List<String> tags=new ArrayList<>();
    GameAttributeSlot gameAttributeSlot=GameAttributeSlot.ANY;

    public GameType(String id) {
        this.id=id;
    }
    public GameType(JSONObject jsonObject) {
        id= (String) jsonObject.getOrDefault("id","ITEM");
        name= (String) jsonObject.getOrDefault("name","COMMON");
        gameAttributeSlot= GameAttributeSlot.valueOf((String) jsonObject.getOrDefault("slot","ANY"));
        if (jsonObject.get("tags")!=null){
            JSONArray tagList= (JSONArray) jsonObject.get("tags");
            tags.addAll(tagList);
        }
    }

    public GameType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GameType setName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public GameAttributeSlot getGameAttributeSlot() {
        return gameAttributeSlot;
    }

    public GameType setGameAttributeSlot(GameAttributeSlot gameAttributeSlot) {
        this.gameAttributeSlot = gameAttributeSlot;
        return this;
    }
}
