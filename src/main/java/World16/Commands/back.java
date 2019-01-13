package World16.Commands;

import World16.Events.OnDeathEvent;
import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import java.util.LinkedHashMap;
import org.bukkit.Location;
import org.bukkit.World;
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

    if (args.length == 0) {
      p.sendMessage(Translate.chat("&cUsage:"));
      p.sendMessage(Translate.chat("/back death"));
      p.sendMessage(Translate.chat("/back tp"));
      p.sendMessage(Translate.chat("/back set"));
      p.sendMessage(Translate.chat("/back goto"));
      return true;
    }
    if (args.length >= 1) {

      if (args[0].equalsIgnoreCase("death")) {
        if (backmap.get(p.getDisplayName() + "death") != null) {
          p.teleport(backmap.get(p.getDisplayName() + "death"));
          return true;
        } else {
          p.sendMessage(Translate.chat("&4Look's like you didn't die yet."));
          return true;
        }
      } else if (args[0].equalsIgnoreCase("tp")) {
        if (backmap.get(p.getDisplayName() + "tp") != null) {
          p.teleport(backmap.get(p.getDisplayName() + "tp"));
        } else {
          p.sendMessage(Translate.chat("&4Something went wrong."));
          return true;
        }
      } else if (args[0].equalsIgnoreCase("set")) {
        backmap.remove(p.getDisplayName());
        double x = p.getLocation().getX();
        double y = p.getLocation().getY();
        double z = p.getLocation().getZ();
        float yaw = p.getLocation().getYaw();
        float pitch = p.getLocation().getPitch();
        World world = p.getWorld();
        Location location = new Location(world, x, y, z, yaw, pitch);
        backmap.put(p.getDisplayName() + "set", location);
        return true;
      } else if (args[0].equalsIgnoreCase("goto")) {
        if (backmap.get(p.getDisplayName() + "set") != null) {
          p.teleport(backmap.get(p.getDisplayName() + "set"));
          return true;
        } else {
          p.sendMessage(Translate.chat("&4Something went wrong."));
          return true;
        }
      }
    }
    return true;
  }
}