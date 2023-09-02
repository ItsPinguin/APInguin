package ping.apinguin.addon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import ping.apinguin.APInguin;
import ping.apinguin.game.item.PingItem;

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
                declaredField.setAccessible(true);
                if (declaredField.getType() == PingAddon.class){
                    PingAddon addon= (PingAddon) declaredField.get(null);
                    APInguin.LOGGER.info("Found addon: "+e.getPlugin().getName());
                    PingAddon.getAddons().put(e.getPlugin().getName(),addon);
                    addon.startAddon();
                    PingItem.getItems().values().forEach(item -> {
                        PingItem.getItems().put(item.getId(),addon.getItemAddon().parseFromJSONMap(item));
                    });
                }
            }
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
