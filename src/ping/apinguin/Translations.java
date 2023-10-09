package ping.apinguin;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Translations {
  private static YamlConfiguration translations=new YamlConfiguration();
  public static void load(){
    try {
      File config = new File(APInguin.PLUGIN.getDataFolder(), APInguin.PLUGIN.getConfig().getString("translationsFile", "translations.yml"));
      if (!config.exists()) {
        config.getParentFile().mkdirs();
        config.createNewFile();
      }
      translations.load(config);
    } catch (IOException | InvalidConfigurationException e) {
      throw new RuntimeException(e);
    }

    addDefault("plugin.load","Plugin loaded successfully!");
    addDefault("plugin.enable","Plugin enabled successfully!");
    addDefault("plugin.disable","Plugin disabled successfully!");


    save();
  }

  public static void save(){
    try {
      translations.save(new File(APInguin.PLUGIN.getDataFolder(), APInguin.PLUGIN.getConfig().getString("translationsFile", "translations.yml")));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  public static String get(String path){
    return translations.getString(path,"translationNotFound");
  }
  public static void set(String path, String message){
    translations.set(path,message);
  }

  public static void addDefault(String path, String message){
    translations.addDefault(path,message);
  }
}
