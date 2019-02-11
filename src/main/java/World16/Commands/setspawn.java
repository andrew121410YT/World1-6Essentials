package World16.Commands;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.CustomYmlManager;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setspawn implements CommandExecutor {

    private Main plugin;
    private API api;

    private CustomYmlManager shitYml = null;

    public setspawn(CustomConfigManager getCustomYml, Main getPlugin) {
        this.shitYml = getCustomYml.getShitYml();
        this.plugin = getPlugin;
        api = new API(this.shitYml);
        this.plugin.getCommand("setspawn").setExecutor(this);
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

        if (!p.hasPermission("world16.setspawn")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        this.api.setLocationToFile(null, "Spawn", "default", p, x, y, z, yaw, pitch, worldName);
        p.sendMessage(Translate.chat("&6Spawn location set for group default."));
        return true;
    }
}