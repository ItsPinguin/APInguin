package ping.mc.game.profile;

import org.bukkit.inventory.ItemStack;
import ping.utils.Config;

import java.io.*;
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
        } else {
            GameProfile profile= new GameProfile(UUID.randomUUID());
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
