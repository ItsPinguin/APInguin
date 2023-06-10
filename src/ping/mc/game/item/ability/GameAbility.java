package ping.mc.game.item.ability;

import org.bukkit.entity.Player;
import ping.mc.game.profile.GamePlayerProfile;

public interface GameAbility {
    GameAbilityInfo getAbilityInfo();
    default boolean trigger(Player player){
        GamePlayerProfile profile=new GamePlayerProfile(player.getUniqueId());
        if (getAbilityInfo().getManaCost()<= ((double) profile.getCurrentProfile().content.get("mana"))){

        }
        return true;
    }
}
