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

public class setspawn implements CommandExecutor {

    private Main plugin;
    private API api;

    private CustomYmlManger configinstance = null;

    public setspawn(ShitConfig getCustomYml, Main getPlugin) {
        this.configinstance = getCustomYml.getInstance();
        this.plugin = getPlugin;
        api = new API(this.configinstance);
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