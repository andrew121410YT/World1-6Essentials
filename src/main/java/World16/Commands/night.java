package World16.Commands;

import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class night implements CommandExecutor {

  API api = new API();
  private Main plugin;

  public night(Main plugin) {
    this.plugin = plugin;
    plugin.getCommand("night").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }

    Player p = (Player) sender;

    if (!p.hasPermission("command.night.permission")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    p.getLocation().getWorld().setTime(13000);
    p.sendMessage(Translate.chat("&6The time was set to &9night&r."));
    return true;
  }
}
