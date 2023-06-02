package ping.mc.game.profile.old;

import ping.utils.Config;
import ping.utils.FileUtils;

import java.util.HashMap;
import java.util.UUID;

public class GameProfiles_Old {
    public static String playerProfilesDirectory="plugins/GameAPI/player_profiles/";
    public static String profilesDirectory="plugins/GameAPI/profiles/";
    public static HashMap<UUID, GameProfile_Old> profiles=new HashMap<>();
    public static HashMap<UUID, GamePlayerProfile_Old> playerProfiles=new HashMap<>();

    public static void loadProfile(String directory, UUID key){
        profiles.put(key,new GameProfile_Old(directory).load());
    }

    public static void saveAll(){
        for (GameProfile_Old value : profiles.values()) {
            value.save();
        }
        for (GamePlayerProfile_Old value : playerProfiles.values()) {

        }
    }
    public static void clearAll(){
        profiles.clear();
        playerProfiles.clear();
    }

    public static GameProfile_Old loadProfile(UUID uuid){
        GameProfile_Old profile=new GameProfile_Old(FileUtils.readJSONObject(Config.PROFILES_DIRECTORY+uuid));
        profiles.put(uuid, profile);
        return profile;
    }

    public static void unloadProfile(UUID uuid){
        profiles.remove(uuid);
    }

    public static GameProfile_Old createProfile(){
        GameProfile_Old profile = new GameProfile_Old();
        profiles.put(profile.getUuid(),profile);
        return profile;
    }

    public static GameProfile_Old getProfile(UUID uuid){
        return profiles.get(uuid);
    }
}
