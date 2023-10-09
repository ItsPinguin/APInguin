package ping.apinguin.tests;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.AdvancementDisplayType;
import ping.apinguin.APInguin;

import java.util.Locale;

public class Quest {
  public static void quest(String id, Material material, String title, String description, AdvancementDisplayType frame, boolean showToast, boolean broadcast, boolean hidden, String parent){
    if (Bukkit.getAdvancement(new NamespacedKey(APInguin.PLUGIN,id))!=null)
      Bukkit.getUnsafe().removeAdvancement(new NamespacedKey(APInguin.PLUGIN,id));
    Bukkit.getUnsafe().loadAdvancement(new NamespacedKey(APInguin.PLUGIN,id),"{\n" +
        "  \"display\": {\n" +
        "    \"icon\": {\n" +
        "      \"item\": \"minecraft:"+material.toString().toLowerCase(Locale.ROOT)+"\"\n" +
        "    },\n" +
        "    \"title\": \""+title+"\",\n" +
        "    \"description\": \""+description+"\",\n" +
        "    \"frame\": \""+frame.toString().toLowerCase(Locale.ROOT)+"\",\n" +
        "    \"show_toast\": false,\n" +
        "    \"announce_to_chat\": false,\n" +
        "    \"hidden\": false\n" +
        "  },\n" +
        "  \"parent\": \""+APInguin.PLUGIN.getName().toLowerCase(Locale.ROOT)+":"+parent+"\",\n" +
        "  \"criteria\": {\n" +
        "    \"criteria\": {\n" +
        "      \"trigger\": \"minecraft:impossible\"\n" +
        "    }\n" +
        "  }\n" +
        "}");
  }

  public static void category(String id, Material material, String title, String description, String background, AdvancementDisplayType frame, boolean showToast, boolean broadcast, boolean hidden){
    if (Bukkit.getAdvancement(new NamespacedKey(APInguin.PLUGIN,id))!=null)
      Bukkit.getUnsafe().removeAdvancement(new NamespacedKey(APInguin.PLUGIN,id));
    String s = "{" +
            "  \"display\": {" +
            "    \"icon\": {" +
            "      \"item\": \"minecraft:" + material.toString().toLowerCase(Locale.ROOT) + "\"" +
            "    }," +
            "    \"title\": \"" + title + "\"," +
            "    \"description\": \"" + description + "\"," +
            "    \"background\": \"" + background + "\"," +
            "    \"frame\": \"" + frame.toString().toLowerCase(Locale.ROOT) + "\"," +
            "    \"show_toast\": false," +
            "    \"announce_to_chat\": false," +
            "    \"hidden\": false" +
            "  }," +
            "  \"criteria\": {" +
            "    \"criteria\": {" +
            "      \"trigger\": \"minecraft:inventory_changed\"" +
            "    }" +
            "  }" +
            "}";
    Bukkit.getUnsafe().loadAdvancement(new NamespacedKey(APInguin.PLUGIN,id), s);
    if (Bukkit.getAdvancement(new NamespacedKey(APInguin.PLUGIN,id))==null) {
      Bukkit.getUnsafe().loadAdvancement(new NamespacedKey(APInguin.PLUGIN,id), s);
    }
  }
  void e(){
  }
}
