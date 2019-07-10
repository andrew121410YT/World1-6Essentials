package World16.Commands;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.TabComplete.JailTab;
import World16.Utils.API;
import World16.Utils.JailManager;
import World16.Utils.SetListMap;
import World16.Utils.Translate;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class jail implements CommandExecutor {

    //Maps
    Map<String, Location> jailsMap = SetListMap.jails;
    //...

    private API api;
    private Main plugin;
    private JailManager jailManager;

    public jail(CustomConfigManager getCustomYml, Main getPlugin, JailManager jailManager) {
        this.plugin = getPlugin;
        this.api = new API();
        this.jailManager = jailManager;

        this.plugin.getCommand("jail").setExecutor(this);
        this.plugin.getCommand("jail").setTabCompleter(new JailTab());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("world16.jail")) {
            api.PermissionErrorMessage(p);
            return true;
        }

        if (args.length == 0) {
            p.sendMessage(Translate.chat("&e-----&2[Jails]&r&e-----"));
            p.sendMessage(Translate.chat("&6/jail list &9[Show's all of the jails]"));
            p.sendMessage(Translate.chat("&6/jail set <JailName> &9[Set's the jail.]"));
            p.sendMessage(Translate.chat("&6/jail delete <JailName> &9[Remove's the jail]"));
            p.sendMessage(Translate.chat("&6/jail <JailName> &9[Brings you to the jail.]"));
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            String jailsFormat = "&2[Jails]&r";
            Set<String> keySet = jailsMap.keySet();
            String[] homeString = keySet.toArray(new String[0]);
            Arrays.sort(homeString);
            String str = String.join(", ", homeString);
            if (str.equalsIgnoreCase("")) {
                p.sendMessage(Translate.chat("&2[Jail] &9Looks lke there's no jails?"));
                return true;
            }
            p.sendMessage(Translate.chat(jailsFormat + " " + str));
            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            if (!p.hasPermission("world16.setjail")) {
                api.PermissionErrorMessage(p);
                return true;
            }
            this.jailManager.set(args[1].toLowerCase(), p.getLocation());
            p.sendMessage(Translate.chat("&2[SetJail]&r&6 The Jail has been set."));
            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
            if (!p.hasPermission("world16.jail.delete")) {
                api.PermissionErrorMessage(p);
                return true;
            }
            if (jailManager.delete(args[1].toLowerCase())) {
                p.sendMessage(Translate.chat("&2[Jail]&r&c The Jail has been deleted."));
                return true;
            }
            p.sendMessage(Translate.chat("&2[Jail] &cThe Jail was not deleted because there is no jail with that name."));
            return true;
        } else if (args.length == 1 && jailsMap.get(args[0]) != null) {
            p.teleport(jailsMap.get(args[0]));
            p.sendMessage(Translate.chat("&6Teleporting..."));
            return true;
        } else {
            p.sendMessage(Translate.chat("&c[Jails]&r&c Something went wrong with your command."));
            return true;
        }
    }
}
