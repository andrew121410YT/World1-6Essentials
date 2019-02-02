package World16.Commands;

import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class day implements CommandExecutor {

    API api = new API();
    private Main plugin;

    public day(Main getPlugin) {
        this.plugin = getPlugin;
        this.plugin.getCommand("day").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("world16.day")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        p.getLocation().getWorld().setTime(1000);
        p.sendMessage(Translate.chat("&6The time was set to &eday&r."));
        return true;
    }
}