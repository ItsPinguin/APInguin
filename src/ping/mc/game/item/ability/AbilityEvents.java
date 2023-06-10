package ping.mc.game.item.ability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import ping.mc.game.item.GameItem;
import ping.mc.game.item.GameItemBase;
import ping.mc.game.profile.GamePlayerProfile;
import ping.mc.game.profile.GameProfile;

public class AbilityEvents implements Listener {
    @EventHandler
    public void click(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if (e.getPlayer().isSneaking()){
            if (e.getAction()== Action.RIGHT_CLICK_BLOCK && execute(AbilityType.SHIFT_BLOCK_RIGHT_CLICK,player)){
                return;
            } else if ((e.getAction()== Action.RIGHT_CLICK_AIR | e.getAction()==Action.RIGHT_CLICK_BLOCK)&&execute(AbilityType.SHIFT_RIGHT_CLICK,player)){
                return;
            } else if (e.getAction()== Action.LEFT_CLICK_BLOCK && execute(AbilityType.SHIFT_BLOCK_LEFT_CLICK,player)){
                return;
            } else if ((e.getAction()== Action.LEFT_CLICK_AIR | e.getAction()==Action.LEFT_CLICK_BLOCK)&&execute(AbilityType.SHIFT_LEFT_CLICK,player)){
                return;
            }
        }
        else {
            if (e.getAction()== Action.RIGHT_CLICK_BLOCK && execute(AbilityType.BLOCK_RIGHT_CLICK,player)){
                return;
            } else if ((e.getAction()== Action.RIGHT_CLICK_AIR | e.getAction()==Action.RIGHT_CLICK_BLOCK)&&execute(AbilityType.RIGHT_CLICK,player)){
                return;
            } else if (e.getAction()== Action.LEFT_CLICK_BLOCK && execute(AbilityType.BLOCK_LEFT_CLICK,player)){
                return;
            } else if ((e.getAction()== Action.LEFT_CLICK_AIR | e.getAction()==Action.LEFT_CLICK_BLOCK)&&execute(AbilityType.LEFT_CLICK,player)){
                return;
            }
        }
    }

    @EventHandler
    public void entityClick(PlayerInteractEntityEvent e){
        if (e.getPlayer().isSneaking()){
            execute(AbilityType.SHIFT_ENTITY_RIGHT_CLICK,e.getPlayer());
        }else {
            execute(AbilityType.ENTITY_RIGHT_CLICK,e.getPlayer());
        }
    }

    @EventHandler
    public void entityDmg(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player p){
            if (p.isSneaking()){
                execute(AbilityType.SHIFT_ENTITY_RIGHT_CLICK,p);
            }else {
                execute(AbilityType.ENTITY_RIGHT_CLICK,p);
            }
        }
    }

    public static boolean execute(AbilityType type, Player player){
        GameItemBase gameItemBase=new GameItem(player.getItemInHand()).getGameItemBase();
        for (String ability : gameItemBase.getAbilities()) {
            if (Abilities.getAbility(ability)!=null&&Abilities.getAbility(ability).getAbilityInfo().getAbilityType()==type){
                Ability ability1=Abilities.getAbility(ability);
                if(ability1.trigger(player)){
                    if (ability1.getAbilityInfo().getManaCost()<=new GameProfile(new GamePlayerProfile(player.getUniqueId()).currentProfile).playerInfo.mana){
                        new GameProfile(new GamePlayerProfile(player.getUniqueId()).currentProfile).playerInfo.mana-=ability1.getAbilityInfo().getManaCost();
                    }
                }
                return true;
            }
        }
        return false;
    }
}
