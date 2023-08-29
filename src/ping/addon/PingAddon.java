package ping.addon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import ping.apinguin.APInguin;
import ping.mc.game.item.GameItem;

import java.lang.reflect.Field;
import java.util.HashMap;

public class PingAddon implements Listener {
    private static HashMap<String, PingAddon> addons=new HashMap<>();

    public static HashMap<String, PingAddon> getAddons() {
        return addons;
    }

    public void startAddon() {
    }
    public PingItemAddon getItemAddon(){
        return new PingItemAddon() {};
    }

    @EventHandler
    public void load(PluginEnableEvent e){
        try {
            for (Field declaredField : e.getPlugin().getClass().getDeclaredFields()) {
                if (declaredField.get(null) instanceof PingAddon addon){
                    APInguin.LOGGER.info("Found addon: "+e.getPlugin().getName());
                    PingAddon.getAddons().put(e.getPlugin().getName(),addon);
                    addon.startAddon();
                    GameItem.getItems().values().forEach(item -> {
                        GameItem.getItems().put(item.getId(),addon.getItemAddon().parseFromJSONMap(item));
                    });
                }
            }
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
