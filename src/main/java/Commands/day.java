package Commands;

import Main.Main;
import Translate.Translate;
import Utils.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class day implements CommandExecutor {

    private Main plugin;
    API api = new API();

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

        if (!p.hasPermission("command.day.permission")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        p.getLocation().getWorld().setTime(1000);
        p.sendMessage(Translate.chat("&6The time was set to &eday&r."));
        return true;
    }
}