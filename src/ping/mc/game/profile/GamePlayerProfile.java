package ping.mc.game.profile;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ping.utils.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GamePlayerProfile implements Serializable {
    public UUID uuid;
    private UUID currentProfile;
    private List<UUID> profiles=new ArrayList<>();

    public GamePlayerProfile(UUID uuid) {
        this.uuid = uuid;
        if (GameProfiles.playerProfiles.get(uuid)!=null){
            GamePlayerProfile profile=GameProfiles.playerProfiles.get(uuid);
            currentProfile=profile.currentProfile;
            profiles=profile.profiles;
        }
        else if (new File(Config.PLAYER_PROFILES_DIRECTORY+uuid).exists()){
            try {
                GamePlayerProfile profile= (GamePlayerProfile) new ObjectInputStream(new FileInputStream(Config.PLAYER_PROFILES_DIRECTORY + uuid)).readObject();
                currentProfile=profile.currentProfile;
                profiles=profile.profiles;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            GameProfile profile=getCurrentProfile();
            profiles.add(profile.uuid);
            currentProfile=profile.uuid;
        }
    }

    public void save(){
        try {
            GameProfile profile= getCurrentProfile();
            Player player= Bukkit.getPlayer(uuid);
            assert player != null;
            profile.saveData(player);
            profile.save();
            new ObjectOutputStream(new FileOutputStream(Config.PLAYER_PROFILES_DIRECTORY+uuid)).writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToProfile(UUID uuid){
        save();
        GameProfiles.profiles.remove(currentProfile);
        currentProfile=uuid;
        getCurrentProfile();
    }

    public GameProfile getCurrentProfile() {
        if (GameProfiles.profiles.get(currentProfile) == null) {
            try {
                GameProfiles.profiles.put(currentProfile, new GameProfile(currentProfile.toString()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return GameProfiles.profiles.get(currentProfile);
    }

    public List<UUID> getProfileList(){
        return profiles;
    }
}
