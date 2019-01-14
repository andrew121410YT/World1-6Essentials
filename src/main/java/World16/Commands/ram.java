package World16.Commands;

import World16.Main.Main;
import World16.Utils.API;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ram implements CommandExecutor {

  API api = new API();
  private Main plugin;

  public ram(Main plugin) {
    this.plugin = plugin;
    plugin.getCommand("ram").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }

    Player p = (Player) sender;

    if (!p.hasPermission("command.ram.permission")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    // RAM START
    Runtime runtime = Runtime.getRuntime();
    sender.sendMessage(ChatColor.GREEN + "[Used / Total / Free]  " + ChatColor.BLUE
        + (runtime.totalMemory() - runtime.freeMemory()) / 1048576L + " MB / "
        + runtime.totalMemory() / 1048576L + " MB / " + runtime.freeMemory() / 1048576L + " MB");
    // RAM FINISH
    return true;
  }
}
