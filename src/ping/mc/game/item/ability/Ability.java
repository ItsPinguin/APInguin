package ping.mc.game.item.ability;

import org.bukkit.entity.Player;
import ping.mc.game.profile.GamePlayerProfile;
import ping.mc.game.profile.GameProfile;

public interface Ability {
    AbilityInfo getAbilityInfo();
    default boolean trigger(Player player){
        GamePlayerProfile profile=new GamePlayerProfile(player.getUniqueId());
        if (getAbilityInfo().getManaCost()<= ((double) new GameProfile(profile.currentProfile).content.get("mana"))){

        }
        return true;
    }
}
