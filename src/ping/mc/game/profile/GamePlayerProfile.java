package ping.mc.game.profile;

import ping.utils.Config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GamePlayerProfile implements Serializable {
    UUID uuid;
    String currentProfile;
    List<String> profiles=new ArrayList<>();

    public void save(){
        try {
            new ObjectOutputStream(new FileOutputStream(Config.PLAYER_PROFILES_DIRECTORY+uuid)).writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
