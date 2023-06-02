package ping.mc.game.profile;

import org.bukkit.OfflinePlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ping.utils.FileUtils;

public class GamePlayerProfile {
    private GameProfile currentProfile;
    private OfflinePlayer profileHolder;
    private JSONArray profiles;

    public GamePlayerProfile(OfflinePlayer profileHolder) {
        this.profileHolder = profileHolder;
    }

    public GamePlayerProfile save(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("current_profile", currentProfile.getUuid());
        jsonObject.put("profiles",profiles);

        FileUtils.writeJSONObject(GameProfiles.playerProfilesDirectory+profileHolder.getUniqueId(),jsonObject);
        return this;
    }
    public GamePlayerProfile load(){
        JSONObject jsonObject=FileUtils.readJSONObject(GameProfiles.playerProfilesDirectory+profileHolder.getUniqueId());
        GameProfiles.playerProfiles.put(profileHolder.getUniqueId(),this);
        return this;
    }

    public void unload(){
        save();
        GameProfiles.playerProfiles.remove(profileHolder.getUniqueId());
    }

    public void logout(){
        //todo put player in logout state
    }

    public GameProfile getCurrentProfile() {
        return currentProfile;
    }

    public OfflinePlayer getProfileHolder() {
        return profileHolder;
    }

    public JSONArray getProfiles() {
        return profiles;
    }
}
