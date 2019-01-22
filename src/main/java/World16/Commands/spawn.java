package World16.Commands;

import World16.CustomEvents.handlers.TpEventHandler;
import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import World16.Utils.CustomYmlManger;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class spawn implements CommandExecutor {

  private Main plugin;
  private API api;

  private CustomYmlManger configinstance = null;

  public spawn(CustomYmlManger getCustomYml, Main getPlugin) {
    this.configinstance = getCustomYml;
    this.plugin = getPlugin;
    this.api = new API(this.configinstance);
    this.plugin.getCommand("spawn").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }

    Player p = (Player) sender;

    Location spawn = this.api.GetSpawn("default");

    if (!p.hasPermission("world16.spawn")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    if (args.length == 0) {
      p.teleport(spawn);
      p.sendMessage(Translate.chat("&6Teleporting..."));
      return true;
    } else {
      Player target = plugin.getServer().getPlayerExact(args[0]);
      if (args.length >= 1 && target != null && target.isOnline()) {
        if (!p.hasPermission("world16.spawn.other")) {
          api.PermissionErrorMessage(p);
          return true;
        }
        new TpEventHandler(p.getDisplayName()); //RUNS TP EVENT
        target.teleport(spawn);
        target.sendMessage(Translate.chat("&6Teleporting..."));
      } else {
        p.sendMessage(Translate.chat("&cUsage: for yourself do /spawn OR /spawn <Player>"));
      }
    }
    return true;
  }
}
