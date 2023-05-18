package ping.mc.game.attribute;

import java.util.HashMap;

public class GameAttributes {
    static HashMap<String, GameAttribute> attributes=new HashMap<>();
    public static void addAttribute(GameAttribute attribute){
        attributes.put(attribute.getId(), attribute);
    }

    public static GameAttribute getAttribute(String key){
        return attributes.get(key);
    }
}
