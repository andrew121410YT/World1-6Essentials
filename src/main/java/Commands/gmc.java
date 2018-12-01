package Commands;

import Translate.Translate;
import Utils.API;
import World16.World16.World16.Main;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gmc implements CommandExecutor {

  private Main plugin;
  API api = new API();

  public gmc(World16.World16.World16.Main plugin) {
    this.plugin = plugin;
    plugin.getCommand("gmc").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }

    Player p = (Player) sender;

    if (!p.hasPermission("command.gmc.permission")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    if (args.length == 0) {
      p.setGameMode(GameMode.CREATIVE);
      p.sendMessage(
          Translate.chat("&6Set game mode &ccreative&6 for " + ((Player) sender).getDisplayName()));
      return true;
    } else {
      Player target = plugin.getServer().getPlayerExact(args[0]);
      if (args.length == 1 && target != null && target.isOnline()) {
        if (!p.hasPermission("command.gmc.other.permission")) {
          api.PermissionErrorMessage(p);
          return true;
        }
        target.setGameMode(GameMode.CREATIVE);
        p.sendMessage(
            Translate.chat("&6Set game mode &ccreative&6 for " + target.getDisplayName()));
      } else {
        p.sendMessage(Translate.chat("&aAliases: gmc && gm1"));
        p.sendMessage(Translate
            .chat("&cUsage: for yourself do /gmc OR /gm1 OR /gmc <Player> OR /gm1 <Player>"));
      }
    }
    return true;
  }
}
