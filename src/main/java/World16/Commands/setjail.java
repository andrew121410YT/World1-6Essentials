package World16.Commands;

import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import World16.Utils.CustomYmlManger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setjail implements CommandExecutor {

  API api = new API();
  private Main plugin;
  private CustomYmlManger configinstance = null;

  public setjail(CustomYmlManger getCustomYml, Main getPlugin) {
    this.configinstance = getCustomYml;
    this.plugin = getPlugin;
    this.plugin.getCommand("setjail").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only Players Can Use This Command.");
      return true;
    }

    Player p = (Player) sender;
    double x = p.getLocation().getX();
    double y = p.getLocation().getY();
    double z = p.getLocation().getZ();
    float yaw = p.getLocation().getYaw();
    float pitch = p.getLocation().getPitch();
    String worldName = p.getWorld().getName();
    // Location loc = p.getLocation();
    // FileConfiguration file = plugin.getConfig();

    if (!p.hasPermission("command.setjail.permission")) {
      api.PermissionErrorMessage(p);
      return true;
    }
    configinstance.getshit().set("Jail.Data.X", Double.valueOf(x));
    configinstance.getshit().set("Jail.Data.Y", Double.valueOf(y));
    configinstance.getshit().set("Jail.Data.Z", Double.valueOf(z));
    configinstance.getshit().set("Jail.Data.Yaw", Float.valueOf(yaw));
    configinstance.getshit().set("Jail.Data.Pitch", Float.valueOf(pitch));
    configinstance.getshit().set("Jail.Data.World", worldName);
    configinstance.getshit().set("Jail.Player.Data.NAME", p.getDisplayName());
    configinstance.getshit().set("Jail.Player.Data.UUID", p.getUniqueId().toString());
    configinstance.saveshit();
    // plugin.saveConfig();
    p.sendMessage(Translate.chat("&6The jail has been set."));
    return true;
  }
}
