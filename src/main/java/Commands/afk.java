package Commands;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import Translate.Translate;
import Utils.API;
import World16.World16.World16.Main;

public class afk implements Listener, CommandExecutor {

  public static ArrayList<String> Afk = new ArrayList<>();

  public Main plugin;
  API api = new API();

  public afk(World16.World16.World16.Main plugin) {
    this.plugin = plugin;
    plugin.getCommand("afk").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }

    Player p = (Player) sender;

    if (!p.hasPermission("command.afk.permission")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("afk")) {
    if (args.length == 0) {
      if (!Afk.contains(p.getDisplayName())) {
        Bukkit.broadcastMessage(
            Translate.chat("&8<&4&lAFK&r&8>&r " + p.getDisplayName() + " &chas GONE afk."));
        Afk.add(p.getDisplayName());
      } else if (Afk.contains(p.getDisplayName())) {
        Bukkit.broadcastMessage(
            Translate.chat("&8<&4&lAFK&r&8>&r " + p.getDisplayName() + " &2is now back from afk."));
        Afk.remove(p.getDisplayName());
      }
    }
    }
    return true;
  }
}