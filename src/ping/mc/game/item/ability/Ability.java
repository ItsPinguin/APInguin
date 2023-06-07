package ping.mc.game.item.ability;

import org.bukkit.entity.Player;
import ping.mc.game.profile.GamePlayerProfile;
import ping.mc.game.profile.GameProfile;

public interface Ability {
    AbilityInfo getAbilityInfo();
    default void trigger(Player player){
        GamePlayerProfile profile=new GamePlayerProfile(player.getUniqueId());
        if (getAbilityInfo().manaCost<= ((double) new GameProfile(profile.currentProfile).content.get("mana"))){

        }
    }
}
