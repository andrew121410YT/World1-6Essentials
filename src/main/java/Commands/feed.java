package Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import Translate.Translate;
import Utils.API;
import World16.World16.World16.Main;

public class feed implements CommandExecutor {

  private Main plugin;
  API api = new API();

  public feed(World16.World16.World16.Main plugin) {
    this.plugin = plugin;
    plugin.getCommand("feed").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }

    Player p = (Player) sender;

    if (!p.hasPermission("command.feed.permission")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    if (args.length == 0) {
      p.setFoodLevel(20);
      p.sendMessage(Translate.chat("&6There you go."));
      return true;
    } else {
      Player target = plugin.getServer().getPlayerExact(args[0]);
      if (args.length >= 1 && target != null && target.isOnline()) {
        if (!p.hasPermission("command.feed.other.permission")) {
          api.PermissionErrorMessage(p);
          return true;
        }
        target.setFoodLevel(20);
        p.sendMessage(Translate.chat("&6There you go you just feed " + target.getDisplayName()));
      } else {
        p.sendMessage(Translate.chat("&cUsage: for yourself do /feed OR /feed <Player>"));
      }
    }
    return true;
  }
}
