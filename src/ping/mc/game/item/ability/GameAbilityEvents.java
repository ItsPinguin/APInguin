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

public class GameAbilityEvents implements Listener {
    @EventHandler
    public void click(PlayerInteractEvent e){
        if (e.getPlayer().getItemInUse()==null){
            return;
        }
        Player player = e.getPlayer();
        if (e.getPlayer().isSneaking()){
            if (e.getAction()== Action.RIGHT_CLICK_BLOCK && execute(GameAbilityType.SHIFT_BLOCK_RIGHT_CLICK,player)){
                return;
            } else if ((e.getAction()== Action.RIGHT_CLICK_AIR | e.getAction()==Action.RIGHT_CLICK_BLOCK)&&execute(GameAbilityType.SHIFT_RIGHT_CLICK,player)){
                return;
            } else if (e.getAction()== Action.LEFT_CLICK_BLOCK && execute(GameAbilityType.SHIFT_BLOCK_LEFT_CLICK,player)){
                return;
            } else if ((e.getAction()== Action.LEFT_CLICK_AIR | e.getAction()==Action.LEFT_CLICK_BLOCK)&&execute(GameAbilityType.SHIFT_LEFT_CLICK,player)){
                return;
            }
        }
        else {
            if (e.getAction()== Action.RIGHT_CLICK_BLOCK && execute(GameAbilityType.BLOCK_RIGHT_CLICK,player)){
                return;
            } else if ((e.getAction()== Action.RIGHT_CLICK_AIR | e.getAction()==Action.RIGHT_CLICK_BLOCK)&&execute(GameAbilityType.RIGHT_CLICK,player)){
                return;
            } else if (e.getAction()== Action.LEFT_CLICK_BLOCK && execute(GameAbilityType.BLOCK_LEFT_CLICK,player)){
                return;
            } else if ((e.getAction()== Action.LEFT_CLICK_AIR | e.getAction()==Action.LEFT_CLICK_BLOCK)&&execute(GameAbilityType.LEFT_CLICK,player)){
                return;
            }
        }
    }

    @EventHandler
    public void entityClick(PlayerInteractEntityEvent e){
        if (e.getPlayer().getItemInUse()==null){
            return;
        }
        if (e.getPlayer().isSneaking()){
            execute(GameAbilityType.SHIFT_ENTITY_RIGHT_CLICK,e.getPlayer());
        }else {
            execute(GameAbilityType.ENTITY_RIGHT_CLICK,e.getPlayer());
        }
    }

    @EventHandler
    public void entityDmg(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player p){
            if (p.getItemInUse()==null){
                return;
            }
            if (p.isSneaking()){
                execute(GameAbilityType.SHIFT_ENTITY_RIGHT_CLICK,p);
            }else {
                execute(GameAbilityType.ENTITY_RIGHT_CLICK,p);
            }
        }
    }

    public static boolean execute(GameAbilityType type, Player player){
        GameItemBase gameItemBase=new GameItem(player.getItemInHand()).getGameItemBase();
        for (String ability : gameItemBase.getAbilities()) {
            if (GameAbilities.getAbility(ability)!=null&& GameAbilities.getAbility(ability).getAbilityInfo().getAbilityType()==type){
                GameAbility ability1= GameAbilities.getAbility(ability);
                if(ability1.trigger(player)){
                    if (ability1.getAbilityInfo().getManaCost()<= (new GamePlayerProfile(player.getUniqueId()).getCurrentProfile().getDouble("mana"))){
                        GameProfile profile=new GamePlayerProfile(player.getUniqueId()).getCurrentProfile();
                        profile.setDouble("mana",profile.getDouble("mana")-ability1.getAbilityInfo().getManaCost());
                    }
                }
                return true;
            }
        }
        return false;
    }
}
