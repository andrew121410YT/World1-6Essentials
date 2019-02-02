package World16.Commands;

import World16.CustomConfigs.ShitConfig;
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

    public setjail(ShitConfig getCustomYml, Main getPlugin) {
        this.configinstance = getCustomYml.getInstance();
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

        if (!p.hasPermission("world16.setjail")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        configinstance.getConfig().set("Jail.Data.X", Double.valueOf(x));
        configinstance.getConfig().set("Jail.Data.Y", Double.valueOf(y));
        configinstance.getConfig().set("Jail.Data.Z", Double.valueOf(z));
        configinstance.getConfig().set("Jail.Data.Yaw", Float.valueOf(yaw));
        configinstance.getConfig().set("Jail.Data.Pitch", Float.valueOf(pitch));
        configinstance.getConfig().set("Jail.Data.World", worldName);
        configinstance.getConfig().set("Jail.Player.Data.NAME", p.getDisplayName());
        configinstance.getConfig().set("Jail.Player.Data.UUID", p.getUniqueId().toString());
        configinstance.getConfig();
        // plugin.saveConfig();
        p.sendMessage(Translate.chat("&6The jail has been set."));
        return true;
    }
}
