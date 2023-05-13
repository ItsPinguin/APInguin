package ping.mc.game.item.type;

public interface TypeBuilder {
    default String build(Type type){
        return type.name;
    }
}
