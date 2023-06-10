package ping.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import ping.GameAPI;

import java.io.IOException;
import java.lang.reflect.Field;

public class Config {
    public static String SERVER_NAME="My Server";
    public static String SERVER_IP="my.server.ip";
    public static String DISCORD="https://discord.gg/tzwCuDKdhn";
    public static String PLAYER_PROFILES_DIRECTORY="plugins/GameAPI/player_profiles/";
    public static String PROFILES_DIRECTORY="plugins/GameAPI/profiles/";
    public static double MAX_MANA=50;
    public static double MAX_HEALTH=50;

    public static void load() {
        try {

            GameAPI.PLUGIN.getConfig().load("config.yml");
            FileConfiguration configuration=GameAPI.PLUGIN.getConfig();
            for (Field declaredField : Config.class.getDeclaredFields()) {
                configuration.addDefault(declaredField.getName().toLowerCase(),declaredField.get(null));
                if (configuration.isSet(declaredField.getName().toLowerCase())){
                    declaredField.set(null,configuration.get(declaredField.getName().toLowerCase()));
                }
            }
            GameAPI.PLUGIN.getConfig().save("config.yml");
        }catch (IOException | InvalidConfigurationException | IllegalAccessException e){
            e.printStackTrace();
        }
    }
}
