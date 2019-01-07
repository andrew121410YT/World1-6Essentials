package Commands;

import Translate.Translate;
import Utils.API;
import Utils.CustomYmlManger;
import World16.World16.World16.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setspawn implements CommandExecutor {

    private Main plugin;
    API api = new API();

    private CustomYmlManger configinstance = null;

    public setspawn(CustomYmlManger getCustomYml, World16.World16.World16.Main getPlugin) {
        this.configinstance = getCustomYml;
        this.plugin = getPlugin;
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

        if (!p.hasPermission("command.setspawn.permission")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        this.configinstance.apiSetSpawn(p, x, y, z, yaw, pitch, worldName, "default");
        p.sendMessage(Translate.chat("&6Spawn location set for group default."));
        return true;
    }
}