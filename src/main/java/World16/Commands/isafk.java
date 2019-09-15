package World16.Commands;

import World16.Main.Main;
import World16.Managers.CustomConfigManager;
import World16.Utils.API;
import World16.Utils.Translate;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class isafk implements CommandExecutor {

    //Maps
    private Map<UUID, Location> afkMap;
    //...

    private Main plugin;
    private API api;

    public isafk(Main plugin, CustomConfigManager customConfigManager) {
        this.plugin = plugin;
        this.api = new API(this.plugin);

        this.afkMap = this.plugin.getSetListMap().getAfkMap();

        this.plugin.getCommand("isafk").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("world16.isafk")) {
            api.PermissionErrorMessage(p);
            return true;
        }

        if (args.length == 0) {
            p.sendMessage(Translate.chat("&e-----&9[AfkChecker]&r&e-----&r"));
            p.sendMessage(Translate.chat("&6/isafk check <User> &9[Check's if user is AFK or not.]"));
            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("check")) {
            Player playerFromArg = this.plugin.getServer().getPlayerExact(args[1]);

            if (playerFromArg == null) {
                p.sendMessage(Translate.chat("&9[AfkChecker]&r&c I don't think that's a player."));
                return true;
            }

            if (api.isAfk(playerFromArg)) {
                p.sendMessage(Translate.chat("&aThe Player: " + playerFromArg.getDisplayName() + " is afk"));
                return true;
            } else {
                p.sendMessage(Translate.chat("&cThe Player: " + playerFromArg.getDisplayName() + " is not afk!"));
                return true;
            }
        }
        return true;
    }
}