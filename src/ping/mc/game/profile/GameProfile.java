package ping.mc.game.profile;

import org.bukkit.inventory.ItemStack;
import ping.utils.Config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class GameProfile implements Serializable {
    UUID uuid;
    ItemStack[] itemContent;
    ItemStack[] extraItemContent;
    ItemStack[] armorContent;
    HashMap<Object,Object> content;

    public void save(){
        try {
            new ObjectOutputStream(new FileOutputStream(Config.PROFILES_DIRECTORY+uuid)).writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
