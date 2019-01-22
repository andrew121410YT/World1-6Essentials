package World16.Commands;

import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import World16.Utils.CustomYmlManger;
import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class isafk implements CommandExecutor {

  API api = new API();
  ArrayList<String> Afk1 = afk.Afk;
  private Main plugin;
  private CustomYmlManger configinstance = null;

  public isafk(CustomYmlManger getCustomYml, Main getPlugin) {
    this.configinstance = getCustomYml;
    this.plugin = getPlugin;
    this.plugin.getCommand("isafk").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
//        if (!(sender instanceof Player)) {
//            sender.sendMessage("Only Players Can Use This Command.");
//            return true;
//        }
    Player p = (Player) sender;

    if (!p.hasPermission("world16.isafk")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    if (args.length == 0) {
      p.sendMessage(Translate.chat("&9To use do:"));
      p.sendMessage(Translate.chat("[&3/isafk check &6<PlayerName>&r]"));
      p.sendMessage(Translate.chat("[&3/isafk &4@all&r] &5<--&r &9show's everyone that is afk."));
    } else {
      if (args.length >= 1) {
        if (args[0].equalsIgnoreCase("check") && args[1] != null) {
          Player playerFromArg = this.plugin.getServer().getPlayerExact(args[1]);
          if (api.isAfk(playerFromArg)) {
            p.sendMessage(
                Translate.chat("&aThe Player: " + playerFromArg.getDisplayName() + " is afk"));
          } else {
            p.sendMessage(
                Translate.chat("&cThe Player: " + playerFromArg.getDisplayName() + " is not afk!"));
          }
        } else {
          if (args[0].equalsIgnoreCase("@all")) {
            for (String t1 : Afk1) {
              p.sendMessage(Translate.chat("Here: " + t1));
            }
          }
        }
      }
    }
    return true;
  }
}