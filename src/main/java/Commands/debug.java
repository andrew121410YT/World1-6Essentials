package Commands;

import MysqlAPI.MySQL;
import Translate.Translate;
import Utils.API;
import World16.World16.World16.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class debug implements CommandExecutor {

  private Main plugin;
  API api = new API();
  MySQL mysql = new MySQL();

  public debug(World16.World16.World16.Main getPlugin){
    this.plugin = getPlugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    Player p = (Player) sender;

    // IF THE PLAYER IS THE CONSOLE OR COMMAND BLOCk
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }
    if (args.length == 0) {
      if (!p.hasPermission("command.debug.permission")) { // Permission
        api.PermissionErrorMessage(p);
        return true;
      }
      p.sendMessage(Translate.chat("&aHow to use /debug1-6"));
      p.sendMessage(Translate.chat("&aFor Oping Staff Use /debug1-6 op"));
      p.sendMessage(
          Translate.chat("&aFor Using Mysql/sql commands do /debug1-6 sql <CommandGoesHere>"));
      return true;
    }
    // 2
    else if (args.length == 1) {
      if (args[0].equalsIgnoreCase("op")) {
        if (!p.hasPermission("command.debugop.permission")) { // Permission
          api.PermissionErrorMessage(p);
          return true;
        }
        p.sendMessage(Translate.chat("&4Debug working oping andrew and tyler and richard"));
        // Ops Andrew
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String command = "op andrew121410";
        Bukkit.dispatchCommand(console, command);
        String command1 = "op AlphaGibbon43";
        Bukkit.dispatchCommand(console, command1);
        String command3 = "op Robobros3";
        Bukkit.dispatchCommand(console, command3);
        p.sendMessage(Translate.chat("&4There."));
        return true;
      }
      // 3
      else if (args.length > 1) {
        if (args[0].equalsIgnoreCase("sql")) {
          if (!p.hasPermission("command.debugsql.permission")) { // Permission
            api.PermissionErrorMessage(p);
            return true;
          }
          // String Builder
          StringBuilder builder = new StringBuilder();
          for (int i = 1; i < args.length; i++) {
            builder.append(args[i] + " ");
          }
          String msg = builder.toString();
          mysql.Connect();
          mysql.ExecuteCommand(msg);
          p.sendMessage(Translate.chat("&4&lYour command has been executed thru SQL."));
          p.sendMessage(Translate.chat("&aHere's the command you did &r" + msg));
          return true;
        } else {
          p.sendMessage(Translate.chat("&aError With you command."));
          return true;
        }
      }
    }
    return true;
  }
}
