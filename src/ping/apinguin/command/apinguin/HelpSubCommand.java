package ping.apinguin.command.apinguin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class HelpSubCommand implements CommandExecutor, TabCompleter {
  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
    commandSender.sendMessage("""
            §aThe following sub commands are available for §6/apinguin§a:
            §7- §aaddon §7-> Shows all loaded addons
            §7- §aitem §7-> Used for all item related tasks
            """);
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
    return List.of();
  }
}
