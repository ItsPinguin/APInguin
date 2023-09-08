package ping.apinguin.game.condition;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ping.apinguin.addon.PingAddonHandler;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Condition {
  private HashMap<String, Object> conditions = new HashMap<>();

  public Condition addCondition(String path, Object value) {
    conditions.put(path, value);
    return this;
  }

  public boolean check(Object... input) {
    AtomicBoolean allowed = new AtomicBoolean(true);
    if (conditions.get("world")!=null){
      for (Object o : input) {
        if (o instanceof Player player){
          if (!player.getWorld().getName().equalsIgnoreCase((String) conditions.get("world"))){
            return false;
          }
        }

        if (o instanceof Location location){
          if (!location.getWorld().getName().equalsIgnoreCase((String) conditions.get("world"))){
            return false;
          }
        }

        if (o instanceof World world){
          if (!world.getName().equalsIgnoreCase((String) conditions.get("world"))){
            return false;
          }
        }
      }
    }
    if (conditions.get("entityNameContains")!=null){
      for (Object o : input) {
        if (o instanceof Entity entity){
          if (!entity.getCustomName().contains((CharSequence) conditions.get("entityNameContains"))){
            return true;
          }
        }
      }
    }
    PingAddonHandler.getAddons().values().forEach(pingAddon -> {
      if (allowed.get())
        allowed.set(pingAddon.getConditionHandler().check(this));
    });
    return allowed.get();
  }
}
