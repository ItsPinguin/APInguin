package ping.mc.game.profile;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ping.Config;
import ping.mc.game.attribute.GameAttribute;
import ping.mc.game.attribute.GameAttributeModifier;
import ping.mc.game.item.GameItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GamePlayer implements Serializable {
    public UUID uuid;
    private UUID currentProfile;
    private List<UUID> profiles=new ArrayList<>();

    public GamePlayer(UUID uuid) {
        this.uuid = uuid;
        if (GameProfiles.playerProfiles.get(uuid)!=null){
            GamePlayer profile=GameProfiles.playerProfiles.get(uuid);
            currentProfile=profile.currentProfile;
            profiles=profile.profiles;
        }
        else if (new File(Config.PLAYER_PROFILES_DIRECTORY+uuid).exists()){
            try {
                GamePlayer profile= (GamePlayer) new ObjectInputStream(new FileInputStream(Config.PLAYER_PROFILES_DIRECTORY + uuid)).readObject();
                currentProfile=profile.currentProfile;
                profiles=profile.profiles;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            currentProfile=UUID.randomUUID();
            GameProfile profile=getCurrentProfile();
            profiles.add(profile.uuid);
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

    public double getAttribute(GameAttribute attribute){
        Player player=Bukkit.getPlayer(uuid);
        assert player != null;
        GameItem helmet=new GameItem(player.getInventory().getHelmet());
        GameItem chestplate=new GameItem(player.getInventory().getChestplate());
        GameItem leggings=new GameItem(player.getInventory().getLeggings());
        GameItem boots=new GameItem(player.getInventory().getBoots());
        GameItem hand=new GameItem(player.getInventory().getItemInMainHand());
        GameItem offhand=new GameItem(player.getInventory().getItemInOffHand());
        List<GameAttributeModifier> modifiers=new ArrayList<>();
        modifiers.addAll(helmet.getAttribute(attribute));
        modifiers.addAll(chestplate.getAttribute(attribute));
        modifiers.addAll(leggings.getAttribute(attribute));
        modifiers.addAll(boots.getAttribute(attribute));
        modifiers.addAll(hand.getAttribute(attribute));
        modifiers.addAll(offhand.getAttribute(attribute));
        return GameAttributeModifier.calculate(modifiers);
    }
}
