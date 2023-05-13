package ping.mc.game.item.type;

import java.util.HashMap;

public class Types {
    static HashMap<String, Type> types=new HashMap<>();
    private static TypeBuilder typeBuilder=new TypeBuilder() {
        @Override
        public String build(Type type) {
            return TypeBuilder.super.build(type);
        }
    };
    public static void addType(Type type){
        types.put(type.getId(), type);
    }
    public static Type getType(String key){
        return types.get(key);
    }

    public static TypeBuilder getTypeBuilder() {
        return typeBuilder;
    }

    public static void setTypeBuilder(TypeBuilder typeBuilder) {
        Types.typeBuilder = typeBuilder;
    }
}
