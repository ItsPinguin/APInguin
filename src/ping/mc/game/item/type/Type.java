package ping.mc.game.item.type;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Type {
    String name="Item";
    String id="ITEM";
    List<String> tags=new ArrayList<>();

    public Type() {
    }
    public Type(JSONObject jsonObject) {
        id= (String) jsonObject.getOrDefault("id","ITEM");
        name= (String) jsonObject.getOrDefault("nale","COMMON");
        if (jsonObject.get("tags")!=null){
            JSONArray tagList= (JSONArray) jsonObject.get("tags");
            tags.addAll(tagList);
        }
    }

    public Type(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
