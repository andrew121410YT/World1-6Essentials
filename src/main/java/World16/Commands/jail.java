package World16.Commands;

import World16.CustomEvents.handlers.TpEventHandler;
import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import World16.Utils.CustomYmlManger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class jail implements CommandExecutor {

  API api = new API();
  private Main plugin;
  private CustomYmlManger configinstance = null;

  public jail(CustomYmlManger getCustomYml, Main getPlugin) {
    this.configinstance = getCustomYml;
    this.plugin = getPlugin;
    this.plugin.getCommand("jail").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }

    Player p = (Player) sender;
    double x = configinstance.getshit().getInt("Jail.Data.X");
    double y = configinstance.getshit().getInt("Jail.Data.Y");
    double z = configinstance.getshit().getInt("Jail.Data.Z");
    float yaw = (float) configinstance.getshit().getInt("Jail.Data.Yaw");
    float pitch = (float) configinstance.getshit().getInt("Jail.Data.Pitch");
    World world = Bukkit.getWorld(configinstance.getshit().getString("Jail.Data.World"));

    Location jail = new Location(world, x, y, z, yaw, pitch);
    // FileConfiguration file = plugin.getConfig();

    if (!p.hasPermission("world16.jail")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    p.teleport(jail);
    p.sendMessage(Translate.chat("&6Teleporting..."));
    return true;
  }
}
