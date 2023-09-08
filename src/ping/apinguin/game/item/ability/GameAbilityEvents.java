package ping.apinguin.game.item.ability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class GameAbilityEvents implements Listener {
  @EventHandler
  public void click(PlayerInteractEvent e) {
    Player player = e.getPlayer();
    if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
      execute(GameAbilityType.RIGHT_CLICK, player);
    } else if ((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)) {
      execute(GameAbilityType.LEFT_CLICK, player);
    }
  }

  @EventHandler
  public void shift(PlayerToggleSneakEvent e) {
    execute(GameAbilityType.SHIFT, e.getPlayer());
  }

  public static boolean execute(GameAbilityType type, Player player) {
        /*GameItemBase gameItemBase=new GameItem(player.getInventory().getItemInMainHand()).getGameItemBase();
        for (String ability : gameItemBase.getAbilities()) {
            GameAbility ability1= GameAbilities.getAbility(ability);
            if (ability1!=null&& ability1.getAbilityInfo().getAbilityType()==type){
                GamePlayer profile=new GamePlayer(player.getUniqueId());
                if(ability1.getAbilityInfo().getManaCost()<= (profile.getMana())){
                    if (ability1.trigger(player)){
                        profile.setMana(profile.getMana()-ability1.getAbilityInfo().getManaCost());
                    }
                }
                return true;
            }
        }
        return false;*/
    return false;
  }
}
