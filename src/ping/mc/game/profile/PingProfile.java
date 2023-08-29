package ping.mc.game.profile;

import ping.apinguin.Config;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class PingProfile implements Serializable {

    private static HashMap<UUID, PingProfile> profiles=new HashMap<>();

    @Serial
    private UUID profileId;
    @Serial
    private HashMap<String,InventoryHolder> inventories=new HashMap<>();
    @Serial
    private HashMap<String,Object> data=new HashMap<>();
    @Serial
    private boolean locked=false;
    public static HashMap<UUID, PingProfile> getProfiles() {
        return profiles;
    }

    public HashMap<String, InventoryHolder> getInventories() {
        return inventories;
    }

    public void setInventories(HashMap<String, InventoryHolder> inventories) {
        this.inventories = inventories;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    private PingProfile(UUID uuid) {
        profileId=uuid;
        load();
    }

    @Override
    public String toString() {
        return "PingProfile{" +
                "profileId=" + profileId +
                ", inventories=" + inventories +
                ", data=" + data +
                ", locked=" + locked +
                '}';
    }

    public void load(){
        if (getProfiles().get(profileId)==null){
            if (!new File(Config.PROFILES_DIRECTORY+profileId).exists()){
                getProfiles().put(profileId,this);
            } else {
                try {
                    getProfiles().put(profileId, (PingProfile) new ObjectInputStream(new FileInputStream(Config.PROFILES_DIRECTORY+profileId)).readObject());
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static PingProfile get(UUID uuid){
        new PingProfile(uuid);
        return getProfiles().get(uuid);
    }

    public void save(){
        try {
          new File(Config.PROFILES_DIRECTORY+profileId).createNewFile();
          new ObjectOutputStream(new FileOutputStream(Config.PROFILES_DIRECTORY+profileId)).writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(){
        getProfiles().put(profileId,this);
    }
}
