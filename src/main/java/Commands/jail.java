package Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import Translate.Translate;
import Utils.API;
import Utils.CustomYmlManger;
import World16.World16.World16.Main;

public class jail implements CommandExecutor {

  private static Plugin plugin = Main.plugin;
  API api = new API();

  private CustomYmlManger configinstance = null;

  public jail(CustomYmlManger getConfigInstance, World16.World16.World16.Main plugin) {
    this.configinstance = getConfigInstance;
    this.plugin = plugin;
    plugin.getCommand("jail").setExecutor(this);
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

    if (!p.hasPermission("command.jail.permission")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    p.teleport(jail);
    p.sendMessage(Translate.chat("&6Teleporting..."));
    return true;
  }
}
