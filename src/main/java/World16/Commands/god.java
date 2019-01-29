package World16.Commands;

import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class god implements CommandExecutor {

    API api = new API();
    private Main plugin;

    //
    public static ArrayList<String> godm = new ArrayList<>();
    //...

    public god(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("god").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("world16.god")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        if (args.length == 0) {

            if (godm.contains(p.getDisplayName())) {
                godm.remove(p.getDisplayName());
                p.sendMessage(Translate.chat("&e{GOD MODE} &cHas been turned off."));
            }
            if (!godm.contains(p.getDisplayName())) {
                godm.add(p.getDisplayName());
                p.sendMessage(Translate.chat("&e{GOD MODE} &aHas been turned on."));
            }
            return true;
        } else {
            Player target = plugin.getServer().getPlayerExact(args[0]);
            if (args.length >= 1 && target != null && target.isOnline()) {
                if (!p.hasPermission("world16.god.other")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (godm.contains(target.getDisplayName())) {
                    godm.remove(target.getDisplayName());
                    target.sendMessage(Translate.chat("&e{GOD MODE} &cHas been turned off by &a{player}.").replace("{player}", p.getDisplayName()));
                }
                if (!godm.contains(target.getDisplayName())) {
                    godm.add(p.getDisplayName());
                    target.sendMessage(Translate.chat("&e{GOD MODE} &aHas been turned on by &c{player}.").replace("{player}", p.getDisplayName()));
                }
            }
        }
        return true;
    }
}