package ping.apinguin.game.profile;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ping.apinguin.APInguin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JSONProfile extends Profile{

  public JSONProfile(UUID id) {
    super(id);
  }

  @Override
  public Profile load() {
    if (ProfileManager.getProfiles().get(getId())!=null)
      return get(getId());
    if (Paths.get(APInguin.PLUGIN.getConfig().getString("profiles.directory")+"/"+getId()+".json").toFile().exists()) {
      try {
        this.setData ((JSONObject) new JSONParser().parse(new FileReader(Paths.get(APInguin.PLUGIN.getConfig().getString("profiles.directory")+"/"+getId()+".json").toFile())));
      } catch (IOException | ParseException e) {
        e.printStackTrace();
      }
    } else {
      create();
    }
    ProfileManager.getProfiles().put(getId(),this);
    return this;
  }

  @Override
  public Profile unload() {
    ProfileManager.getProfiles().remove(getId());
    return this;
  }

  @Override
  public Profile save() {
    Path path=Paths.get(APInguin.PLUGIN.getConfig().getString("profiles.directory")+"/"+getId()+".json");
    set("profiles."+get("current.UUID"),get("current"));
    new File(path.toFile().getParent()).mkdirs();
    try{
      path.toFile().createNewFile();
    }catch (IOException e){
      e.printStackTrace();
    }
    try (FileWriter file = new FileWriter(path.toFile())) {
      file.write(new JSONObject(getData()).toJSONString());
      file.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return this;
  }

  @Override
  public Profile create() {
    set("mainCreation",System.currentTimeMillis());
    set("UUID",getId().toString());
    set("username",System.currentTimeMillis());
    set("current.creation",System.currentTimeMillis());
    set("current.UUID",UUID.randomUUID().toString());
    List<String> existingNames=new ArrayList<>();
    if (get("profiles")!=null) {
      for (HashMap<String, Object> profiles : ((HashMap<String, HashMap<String, Object>>) get("profiles")).values()) {
        existingNames.add((String) profiles.get("name"));
      }
    }
    List<String> possibleNames= new ArrayList<>(getCuteNames());
    for (String existingName : existingNames) {
      possibleNames.remove(existingName);
    }
    String finalName;
    if (possibleNames.isEmpty())
      finalName="TooManyProfiles!";
    else {
      finalName= possibleNames.get(new Random().nextInt(0, possibleNames.size()-1));
    }
    set("current.name",finalName);
    return this;
  }

  public static Profile get(UUID uuid) {
    return ProfileManager.getProfiles().get(uuid);
  }
}
