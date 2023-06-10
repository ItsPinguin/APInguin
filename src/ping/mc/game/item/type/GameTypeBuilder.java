package ping.mc.game.item.type;

public interface GameTypeBuilder {
    default String build(GameType type){
        return type.name;
    }
}
