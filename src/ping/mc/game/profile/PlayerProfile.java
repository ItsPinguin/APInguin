package ping.mc.game.profile;

import ping.apinguin.APInguin;
import ping.apinguin.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerProfile implements Serializable {
    @Serial
    private List<UUID> profiles=new ArrayList<>();
    @Serial
    private UUID currentProfile;
    @Serial
    public UUID getCurrentProfileId(){return currentProfile;}

    public List<UUID> getProfiles() {
        return profiles;
    }

    private final UUID playerUuid;

    private PlayerProfile(UUID uuid) {
        playerUuid=uuid;
        load();
    }

    public PingProfile getCurrentProfile(){
        return PingProfile.get(currentProfile);
    }

    public void load(){
        if (APInguin.Registries.PLAYER_PROFILES.get(playerUuid)==null) {
          APInguin.LOGGER.info("Profile not loaded! Loading ...");
          if (!new File(Config.PLAYER_PROFILES_DIRECTORY + playerUuid).exists()) {
            currentProfile = UUID.randomUUID();
            profiles.add(currentProfile);
            PingProfile.get(currentProfile);
            APInguin.Registries.PLAYER_PROFILES.put(playerUuid, this);
          } else if (new File(Config.PLAYER_PROFILES_DIRECTORY + playerUuid).exists()){
            try {
              APInguin.Registries.PLAYER_PROFILES.put(playerUuid, (PlayerProfile) new ObjectInputStream(new FileInputStream(Config.PLAYER_PROFILES_DIRECTORY + playerUuid)).readObject());
              PingProfile profile=APInguin.Registries.PLAYER_PROFILES.get(playerUuid).getCurrentProfile();
              if (profile.getInventories()==null){
                profile.setInventories(new HashMap<>());
                APInguin.LOGGER.info("Inventories was empty!!!!!!!!!!!!!");
              }
              profile.getInventories().values().forEach(InventoryHolder::deserialize);
            } catch (IOException | ClassNotFoundException e) {
              throw new RuntimeException(e);
            }
          }
        }
    }

    public static PlayerProfile get(UUID playerUuid){
      new PlayerProfile(playerUuid);
      return APInguin.Registries.PLAYER_PROFILES.get(playerUuid);
    }
    public void save(){
        try {
            new File(Config.PLAYER_PROFILES_DIRECTORY+playerUuid).createNewFile();
            new ObjectOutputStream(new FileOutputStream(Config.PLAYER_PROFILES_DIRECTORY+playerUuid)).writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getCurrentProfile().getInventories().values().forEach(InventoryHolder::serialize);
        getCurrentProfile().save();
    }

    public void update(){
        APInguin.Registries.PLAYER_PROFILES.put(playerUuid,this);
        getCurrentProfile().update();
    }
}
