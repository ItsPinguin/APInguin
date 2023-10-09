package ping.apinguin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ping.apinguin.APInguin;
import ping.apinguin.command.apinguin.AddonSubCommand;
import ping.apinguin.command.apinguin.HelpSubCommand;
import ping.apinguin.game.item.PingItem;
import ping.apinguin.game.recipe.PingCraftingTable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class APInguinCommand implements CommandExecutor, TabCompleter {
  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
    if (args.length == 0) {
      commandSender.sendMessage("§aYou are running §6" + APInguin.PLUGIN.getName() + " §aversion §6" + APInguin.PLUGIN.getDescription().getVersion(),
          "§aRun §6/apinguin help§a for more help");

    }else {
      for (int i = 0; i < args.length-1; i++) {
        args[i]="";
      }

      switch (args[0]){
        case "addon" -> {
          System.arraycopy(args,1,args,0,args.length-1);
          new AddonSubCommand().onCommand(commandSender, command, s, args);
        }

        case "item"-> {
          switch (args[1]){
            case "list" ->{
              if (!PingItem.getItems().isEmpty()){
                String msg="§aThere are §6"+PingItem.getItems().size()+" §aitems loaded:";
                commandSender.sendMessage(msg);
                int page;
                try {
                  page=Integer.parseInt(args[2]);
                }catch (NumberFormatException e){
                  page=1;
                }
                page-=1;
                AtomicInteger i=new AtomicInteger(page*50);
                int finalPage = page;
                PingItem.getItems().values().forEach(pingItem -> {
                  if (i.get()<PingItem.getItems().size() && i.get()< finalPage *50+50){
                    commandSender.sendMessage("§7- §6"+pingItem.getName()+" §a(§7"+pingItem.getId()+"§a)");
                  } else {
                    return;
                  }
                  i.addAndGet(1);
                });
              }
            }
            case "identify" ->{
              if (commandSender instanceof Player p){
                PingItem pingItem=new PingItem(p.getInventory().getItemInMainHand());
                commandSender.sendMessage("§aYou are holding §6"+pingItem.getName()+" §a(§7"+pingItem.getId()+"§a).");
              } else {
                commandSender.sendMessage(mustBePlayer);
              }
            }
            case "give" -> {
              if (commandSender instanceof Player player){
                if (PingItem.getItems().get(args[2])!=null){
                  player.getInventory().addItem(PingItem.getItems().get(args[2]).toItemStack());
                } else {
                  commandSender.sendMessage("§cItem \"§e"+args[2]+"§c\" doesn't exist! Run §e/apinguin item list§c to get a list of available items");
                }
              }else {
                commandSender.sendMessage(mustBePlayer);
              }
            }
            default ->  commandSender.sendMessage("""
                §aThe following sub commands are available for §6/apinguin item§a:
                §7- §alist <page>§7-> Shows a list of all loaded items
                §7- §aidentify §7-> Identifies held item
                """);
          }
          break;
        }

        case "crafting_table"-> {
          switch (args[1]){
            case "remove"-> {
              if (commandSender instanceof Player player){
                if (PingCraftingTable.attemptRemove(player.getTargetBlockExact(100).getLocation())){
                  commandSender.sendMessage("§aSuccessfully removed the custom crafting table from the block you're looking at!");
                }else {
                  commandSender.sendMessage("§cThe block you're looking at isn't a custom crafting table!");
                }
              }else {
                commandSender.sendMessage(mustBePlayer);
              }
            }
            case "place"-> {
              if (commandSender instanceof Player player){
                if (PingCraftingTable.getRecipeBlocks().get(args[2])!=null){
                  PingCraftingTable.getRecipeBlocks().get(args[2]).place(player.getTargetBlockExact(100).getLocation());
                }else {
                  commandSender.sendMessage("§cCrafting table \"§e"+args[2]+"§c\" doesn't exist! Run §e/apinguin crafting_table list§c to get a list of available crafting tables");
                  return true;
                }
              } else {
                commandSender.sendMessage(mustBePlayer);
                return true;
              }
            }
            case "craft"-> {
              if (commandSender instanceof Player player){
                if (PingCraftingTable.attemptCraft(player.getTargetBlockExact(100).getLocation(),player)){
                  commandSender.sendMessage("§aSuccessfully crafted on the custom crafting table you're looking at!");
                }else {
                  commandSender.sendMessage("§cThe block you're looking at isn't a custom crafting table!");
                }
              }else {
                commandSender.sendMessage(mustBePlayer);
              }
            }
            case "list"->{
              if (!PingCraftingTable.getRecipeBlocks().isEmpty()){
                String msg="§aThere are §6"+PingCraftingTable.getRecipeBlocks().size()+" §acrafting tables loaded:";
                commandSender.sendMessage(msg);
                int page;
                try {
                  page=Integer.parseInt(args[2]);
                }catch (NumberFormatException e){
                  page=1;
                }
                page-=1;
                AtomicInteger i=new AtomicInteger(page*50);
                int finalPage = page;
                PingCraftingTable.getRecipeBlocks().values().forEach(craftingTable -> {
                  if (i.get()<PingCraftingTable.getRecipeBlocks().size() && i.get()< finalPage *50+50){
                    commandSender.sendMessage("§7- §6"+craftingTable.getName()+" §a(§7"+craftingTable.getId()+"§a)");
                  } else {
                    return;
                  }
                  i.addAndGet(1);
                });
              }
            }
          }
        }

        default -> {
          new HelpSubCommand().onCommand(commandSender, command, s, args);
        }
      }
    }
    return true;
  }
  private static String mustBePlayer="§cYou must be a player in order to use this command!";

  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
    if (strings.length==0){
      return Arrays.stream(new String[]{"help","addon","item","crafting_table"}).toList();
    } else {
      String[] args=new String[64];
      for (int i = 0; i < args.length-1; i++) {
        args[i]="";
      }
      System.arraycopy(strings, 0, args, 0, strings.length);
      switch (args[0]){
        case "item" -> {
          switch (args[1]){
            case "give" -> {
              return PingItem.getItems().keySet().stream().toList();
            }
            default -> {
              return Arrays.stream(new String[]{"help","give","list","identify"}).toList();
            }
          }
        }
        case "crafting_table" -> {
          switch (args[1]){
            default -> {
              return Arrays.stream(new String[]{"place","remove","list","craft"}).toList();
            }
          }
        }
        default -> {
          if (Objects.equals(args[1], ""))
            return Arrays.stream(new String[]{"help","addon","item","crafting_table"}).toList();
          return null;
        }
      }
    }
  }
}
