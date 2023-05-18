package ping.mc.game.attribute;

import java.util.HashMap;

public class GameAttributes {
    private static GameAttributeBuilder attributeBuilder=new GameAttributeBuilder() {
        @Override
        public String build(GameAttributeModifier gameAttributeModifier) {
            return GameAttributeBuilder.super.build(gameAttributeModifier);
        }
    };
    static HashMap<String, GameAttribute> attributes=new HashMap<>();
    public static void addAttribute(GameAttribute attribute){
        attributes.put(attribute.getId(), attribute);
    }

    public static GameAttribute getAttribute(String key){
        return attributes.get(key);
    }

    public static GameAttributeBuilder getAttributeBuilder() {
        return attributeBuilder;
    }

    public static void setAttributeBuilder(GameAttributeBuilder attributeBuilder) {
        GameAttributes.attributeBuilder = attributeBuilder;
    }

    public static HashMap<String, GameAttribute> getAttributes() {
        return attributes;
    }
}
