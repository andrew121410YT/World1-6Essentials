package World16.Commands;

import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class fly implements CommandExecutor {

    public static ArrayList<String> Fly = new ArrayList<>();
    API api = new API();
    private Main plugin;

    public fly(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("fly").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("world16.fly")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("fly")) {
        }
        if (args.length == 0) {
            if (!Fly.contains(p.getDisplayName()) && (!p.isFlying())) {
                p.setAllowFlight(true);
                p.sendMessage(Translate.chat("&6Set fly mode &cenabled&6 for " + p.getDisplayName()));
                Fly.add(p.getDisplayName());
                return true;
            } else if (Fly.contains(p.getDisplayName())) {
                p.setAllowFlight(false);
                p.sendMessage(Translate.chat("&6Set fly mode &cdisabled&6 for " + p.getDisplayName()));
                Fly.remove(p.getDisplayName());
                return true;
            } else {
                Player target = plugin.getServer().getPlayerExact(args[0]);
                if (args.length >= 1 && target != null && target.isOnline()) {
                    if (!p.hasPermission("world16.fly.other")) {
                        api.PermissionErrorMessage(p);
                        return true;
                    }
                    if (!Fly.contains(target.getDisplayName()) && (!target.isFlying())) {
                        target.setAllowFlight(true);
                        p.sendMessage(
                                Translate.chat("&6Set fly mode &cenabled&6 for " + target.getDisplayName()));
                        Fly.add(target.getDisplayName());
                        return true;
                    } else if (Fly.contains(target.getDisplayName())) {
                        target.setAllowFlight(false);
                        p.sendMessage(
                                Translate.chat("&6Set fly mode &cdisabled&6 for " + target.getDisplayName()));
                        Fly.remove(target.getDisplayName());
                        return true;
                    }
                } else {
                    p.sendMessage(Translate.chat("&cUsage: for yourself do /fly OR /fly <Player>"));
                }
            }
        }
        return true;
    }
}
