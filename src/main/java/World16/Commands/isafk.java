package World16.Commands;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.CustomYmlManager;
import World16.Utils.SetListMap;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isafk implements CommandExecutor {

    private API api;
    //Maps
    //...
    //Lists
    List<String> Afk1 = SetListMap.afkList;
    //...
    private Main plugin;
    private CustomYmlManager shitYml = null;

    public isafk(CustomConfigManager getCustomYml, Main getPlugin) {
        this.shitYml = getCustomYml.getShitYml();
        this.plugin = getPlugin;
        this.api = new API();
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
            p.sendMessage(Translate.chat("&6To use /isafk:"));
            p.sendMessage(Translate.chat("[&3/isafk check &6<PlayerName>&r]"));
            p.sendMessage(Translate.chat("[&3/isafk &4@all&r] &5<--&r &9show's everyone that is afk."));
            return true;
        } else if (args[0].equalsIgnoreCase("check") && args[1] != null) {
            Player playerFromArg = this.plugin.getServer().getPlayerExact(args[1]);
            if (playerFromArg == null) return true;
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