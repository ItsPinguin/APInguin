package ping.apinguin.command.apinguin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import ping.apinguin.APInguin;
import ping.apinguin.addon.PingAddonHandler;

import java.util.List;

public class AddonSubCommand implements CommandExecutor, TabCompleter {
  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
    if (PingAddonHandler.getAddons().size() > 0) {
      commandSender.sendMessage("§6" + APInguin.PLUGIN.getName() + " §ais using §6" + PingAddonHandler.getAddons().size() + " §aaddon(s):");
      for (String s1 : PingAddonHandler.getAddons().keySet()) {
        commandSender.sendMessage("- §a" + s1);
      }
    }else {
      commandSender.sendMessage("§aNo addons are loaded.");
    }
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
    return null;
  }
}
