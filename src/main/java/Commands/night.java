package Commands;

import Translate.Translate;
import Utils.API;
import World16.World16.World16.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class night implements CommandExecutor {

    private Main plugin;
    API api = new API();

    public night(World16.World16.World16.Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("night").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("command.night.permission")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        p.getLocation().getWorld().setTime(13000);
        p.sendMessage(Translate.chat("&6The time was set to &9night&r."));
        return true;
    }
}
