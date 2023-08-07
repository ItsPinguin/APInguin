package ping.addon;

import ping.mc.game.item.GameItem;
import ping.mc.game.item.GameItemBase;

public interface PingAddon {
    public void test();

    default GameItemBase itemBase(GameItemBase itemBase){
        return itemBase;
    }
    default GameItem item(GameItem item){
        return item;
    }
}
