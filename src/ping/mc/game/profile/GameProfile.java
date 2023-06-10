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
    public UUID uuid;
    public ItemStack[] itemContent;
    public ItemStack[] extraItemContent;
    public ItemStack[] armorContent;
    public HashMap<Object,Object> content;
    public GameProfile(UUID uuid) {
        this.uuid = uuid;
    }

    public void save(){
        try {
            new ObjectOutputStream(new FileOutputStream(Config.PROFILES_DIRECTORY+uuid)).writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
