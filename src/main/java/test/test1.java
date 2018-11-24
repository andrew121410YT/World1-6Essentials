package test;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import MysqlAPI.MySQL;
import Utils.API;

public class test1 implements CommandExecutor {

  MySQL mysql = new MySQL();
  API api = new API();

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }
    Player p = (Player) sender;
    // START
    if (args.length == 0) {
      // DO SOMETHING HERE.
      return true;
    } else if (args[0].equalsIgnoreCase("test2")) { // FIRST ARG
      if (!p.hasPermission("command.test2.command")) {
        api.PermissionErrorMessage(p);
        return true;
      }
      if (args.length == 1) {
        // STUFF HERE
      }
    } else if (args[0].equalsIgnoreCase("test3")) {
      if (!p.hasPermission("command.test3.command")) {
        api.PermissionErrorMessage(p);
        return true;
      }
      if (args.length == 1) {
        // STUFF HERE
      }
    }
    return true;
  }
}
