package ping.mc.game.profile;

import org.bukkit.inventory.ItemStack;
import ping.utils.Config;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class GameProfile implements Serializable {
    public UUID uuid;
    public ItemStack[] itemContent=null;
    public ItemStack[] extraItemContent=null;
    public ItemStack[] armorContent=null;
    public HashMap<Object,Object> content=new HashMap<>();
    public GameProfile(UUID uuid) {
        this.uuid = uuid;
        if (GameProfiles.profiles.get(uuid)!=null){
            GameProfile profile=GameProfiles.profiles.get(uuid);
            itemContent= profile.itemContent;
            extraItemContent= profile.extraItemContent;
            armorContent= profile.armorContent;
            content= profile.content;
        }
        else if (new File(Config.PLAYER_PROFILES_DIRECTORY+uuid).exists()){
            try {
                GameProfile profile= (GameProfile) new ObjectInputStream(new FileInputStream(Config.PLAYER_PROFILES_DIRECTORY + uuid)).readObject();
                itemContent= profile.itemContent;
                extraItemContent= profile.extraItemContent;
                armorContent= profile.armorContent;
                content= profile.content;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void save(){
        try {
            new ObjectOutputStream(new FileOutputStream(Config.PROFILES_DIRECTORY+uuid)).writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
