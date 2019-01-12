package World16.Commands;

import World16.Events.OnDeathEvent;
import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import java.util.LinkedHashMap;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class back implements CommandExecutor {

  API api = new API();
  private Main plugin;
  LinkedHashMap<String, Location> backmap = OnDeathEvent.backmap;

  public back(Main getPlugin) {
    this.plugin = getPlugin;
    this.plugin.getCommand("back").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }
    Player p = (Player) sender;

    if (args.length >= 0) {
      if (backmap.get(p.getDisplayName()) != null) {
        p.teleport(backmap.get(p.getDisplayName()));
      } else {
        p.sendMessage(Translate.chat("&4Looks like you didn't die yet."));
      }
      return true;
    }
    return true;
  }
}