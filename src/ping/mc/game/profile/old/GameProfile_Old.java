package ping.mc.game.profile.old;

import org.json.simple.JSONObject;
import ping.utils.FileUtils;

import java.io.File;
import java.util.UUID;

public class GameProfile_Old {
    private UUID uuid=UUID.randomUUID();

    public UUID getUuid() {
        return uuid;
    }

    private JSONObject data=new JSONObject();
    private String directory="plugins/GameAPI/profiles/"+uuid;

    public GameProfile_Old() {
    }
    public GameProfile_Old(String directory) {
        this.directory=directory;
    }
    public GameProfile_Old(JSONObject data) {
        this.data = data;
        this.uuid = (UUID) data.getOrDefault("uuid",uuid);
    }
    public GameProfile_Old(JSONObject data, String directory) {
        this.data = data;
        this.directory = directory;
    }
    public GameProfile_Old load(){
        data= FileUtils.readJSONObject(directory);
        GameProfiles_Old.profiles.put(uuid, this);
        return this;
    }
    public void unload(){
        save();
        GameProfiles_Old.profiles.remove(uuid);
    }
    public GameProfile_Old save(){
        FileUtils.writeJSONObject(directory,data);
        return this;
    }
    public void delete(){
        unload();
        new File(GameProfiles_Old.profilesDirectory+uuid).delete();
        for (GamePlayerProfile_Old profile : GameProfiles_Old.playerProfiles.values()) {
            if (profile.getCurrentProfile().getUuid()==uuid){
                profile.logout();
            }
            for (Object profileProfile : profile.getProfiles()) {
                if(((JSONObject) profileProfile).get("profile")==uuid){
                    profile.getProfiles().remove(profileProfile);
                }
            }
        }
    }
    public Object getObjectAt(String key, String... path){
        JSONObject current=data;
        for (String s : path) {
            if (current.get(s)==null){
                return null;
            }
            if (!(current.get(s) instanceof JSONObject)){
                return current.get(s);
            }
            current= (JSONObject) current.get(s);
        }
        return current.get(key);
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
