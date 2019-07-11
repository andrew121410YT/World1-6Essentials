package World16.Commands;

import World16.CustomEvents.handlers.AfkEventHandler;
import World16.CustomEvents.handlers.UnAfkEventHandler;
import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.SetListMap;
import World16.Utils.Translate;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

public class afk implements Listener, CommandExecutor {

    //Maps
    //...
    //Lists
    List<String> Afk = SetListMap.afkList;
    //....

    private Main plugin;
    private API api;

    public afk(Main getPlugin) {
        this.plugin = getPlugin;
        this.api = new API();
        this.plugin.getCommand("afk").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("world16.afk")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        if (args.length == 0) {
            if (!Afk.contains(p.getDisplayName())) {
                Bukkit.broadcastMessage(
                        Translate.chat("&8<&4&lAFK&r&8>&r " + p.getDisplayName() + " &chas GONE afk."));
                Afk.add(p.getDisplayName());
                new AfkEventHandler(this.plugin, p.getDisplayName()); //CALLS THE EVENT.
                return true;
            } else if (Afk.contains(p.getDisplayName())) {
                Bukkit.broadcastMessage(
                        Translate.chat("&8<&4&lAFK&r&8>&r " + p.getDisplayName() + " &2is now back from afk."));
                Afk.remove(p.getDisplayName());
                new UnAfkEventHandler(this.plugin, p.getDisplayName());
                return true;
            }
        }
        return true;
    }
}