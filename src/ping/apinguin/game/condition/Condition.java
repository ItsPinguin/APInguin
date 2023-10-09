package ping.apinguin.game.condition;

import org.bukkit.Bukkit;
import ping.apinguin.events.condition.ConditionCheckEvent;
import ping.apinguin.utils.DataHolder;

import java.util.HashMap;

public class Condition extends DataHolder {
  private HashMap<String, Object> conditions = new HashMap<>();

  public Condition addCondition(String path, Object value) {
    conditions.put(path, value);
    return this;
  }

  public boolean check(Context context){
    ConditionCheckEvent checkEvent= new ConditionCheckEvent(this, context);
    Bukkit.getPluginManager().callEvent(checkEvent);
    return checkEvent.isCancelled();
  }
}
