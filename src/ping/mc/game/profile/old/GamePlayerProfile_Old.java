package ping.mc.game.profile.old;

import org.bukkit.OfflinePlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ping.utils.FileUtils;

public class GamePlayerProfile_Old {
    private GameProfile_Old currentProfile;
    private OfflinePlayer profileHolder;
    private JSONArray profiles;

    public GamePlayerProfile_Old(OfflinePlayer profileHolder) {
        this.profileHolder = profileHolder;
    }

    public GamePlayerProfile_Old save(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("current_profile", currentProfile.getUuid());
        jsonObject.put("profiles",profiles);

        FileUtils.writeJSONObject(GameProfiles_Old.playerProfilesDirectory+profileHolder.getUniqueId(),jsonObject);
        return this;
    }
    public GamePlayerProfile_Old load(){
        JSONObject jsonObject=FileUtils.readJSONObject(GameProfiles_Old.playerProfilesDirectory+profileHolder.getUniqueId());
        GameProfiles_Old.playerProfiles.put(profileHolder.getUniqueId(),this);
        return this;
    }

    public void unload(){
        save();
        GameProfiles_Old.playerProfiles.remove(profileHolder.getUniqueId());
    }

    public void logout(){
        //todo put player in logout state
    }

    public GameProfile_Old getCurrentProfile() {
        return currentProfile;
    }

    public OfflinePlayer getProfileHolder() {
        return profileHolder;
    }

    public JSONArray getProfiles() {
        return profiles;
    }
}
