package World16.Commands;

import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class fly implements CommandExecutor {

    //Maps
    //...
    //Lists
    private List<String> flyList;
    //....

    private Main plugin;
    private API api;

    public fly(Main plugin) {
        this.plugin = plugin;
        this.api = new API(this.plugin);

        this.flyList = this.plugin.getSetListMap().getFlyList();

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
            if (!flyList.contains(p.getDisplayName()) && (!p.isFlying())) {
                p.setAllowFlight(true);
                p.sendMessage(Translate.chat("&6Set fly mode &cenabled&6 for " + p.getDisplayName()));
                flyList.add(p.getDisplayName());
                return true;
            } else if (flyList.contains(p.getDisplayName())) {
                p.setAllowFlight(false);
                p.sendMessage(Translate.chat("&6Set fly mode &cdisabled&6 for " + p.getDisplayName()));
                flyList.remove(p.getDisplayName());
                return true;
            } else {
                Player target = plugin.getServer().getPlayerExact(args[0]);
                if (args.length >= 1 && target != null && target.isOnline()) {
                    if (!p.hasPermission("world16.fly.other")) {
                        api.PermissionErrorMessage(p);
                        return true;
                    }
                    if (!flyList.contains(target.getDisplayName()) && (!target.isFlying())) {
                        target.setAllowFlight(true);
                        p.sendMessage(
                                Translate.chat("&6Set fly mode &cenabled&6 for " + target.getDisplayName()));
                        flyList.add(target.getDisplayName());
                        return true;
                    } else if (flyList.contains(target.getDisplayName())) {
                        target.setAllowFlight(false);
                        p.sendMessage(
                                Translate.chat("&6Set fly mode &cdisabled&6 for " + target.getDisplayName()));
                        flyList.remove(target.getDisplayName());
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
