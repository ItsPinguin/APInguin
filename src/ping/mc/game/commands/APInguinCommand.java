package ping.mc.game.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ping.apinguin.APInguin;

public class APInguinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length==0){
            commandSender.sendMessage("§aYou are running §6"+APInguin.PLUGIN.getName()+" §aversion §6"+ APInguin.PLUGIN.getDescription().getVersion());
            if (APInguin.Registries.ADDONS.size()>0){
                commandSender.sendMessage("§6"+APInguin.PLUGIN.getName()+" §ais using §6"+APInguin.Registries.ADDONS.size()+" §aaddon(s):");
                for (String s1 : APInguin.Registries.ADDONS.keySet()) {
                    commandSender.sendMessage("- §a"+s1);
                }
            }
        }
        return true;
    }
}
