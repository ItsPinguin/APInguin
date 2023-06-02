package ping.mc.game.profile;

import ping.utils.Config;
import ping.utils.FileUtils;

import java.util.HashMap;
import java.util.UUID;

public class GameProfiles {
    public static String playerProfilesDirectory="plugins/GameAPI/player_profiles/";
    public static String profilesDirectory="plugins/GameAPI/profiles/";
    public static HashMap<UUID,GameProfile> profiles=new HashMap<>();
    public static HashMap<UUID,GamePlayerProfile> playerProfiles=new HashMap<>();

    public static void loadProfile(String directory, UUID key){
        profiles.put(key,new GameProfile(directory).load());
    }

    public static void saveAll(){
        for (GameProfile value : profiles.values()) {
            value.save();
        }
        for (GamePlayerProfile value : playerProfiles.values()) {

        }
    }
    public static void clearAll(){
        profiles.clear();
        playerProfiles.clear();
    }

    public static GameProfile loadProfile(UUID uuid){
        GameProfile profile=new GameProfile(FileUtils.readJSONObject(Config.PROFILES_DIRECTORY+uuid));
        profiles.put(uuid, profile);
        return profile;
    }

    public static void unloadProfile(UUID uuid){
        profiles.remove(uuid);
    }

    public static GameProfile createProfile(){
        GameProfile profile = new GameProfile();
        profiles.put(profile.getUuid(),profile);
        return profile;
    }

    public static GameProfile getProfile(UUID uuid){
        return profiles.get(uuid);
    }
}
