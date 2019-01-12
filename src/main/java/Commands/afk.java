package Commands;

import CustomEvents.handlers.AfkEventHandler;
import CustomEvents.handlers.UnAfkEventHandler;
import Main.Main;
import Translate.Translate;
import Utils.API;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class afk implements Listener, CommandExecutor {

    public static ArrayList<String> Afk = new ArrayList<>();

    public Main plugin;
    API api = new API();

    public afk(Main getPlugin) {
        this.plugin = getPlugin;
        this.plugin.getCommand("afk").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("command.afk.permission")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        if (args.length == 0) {
            if (!Afk.contains(p.getDisplayName())) {
                Bukkit.broadcastMessage(
                        Translate.chat("&8<&4&lAFK&r&8>&r " + p.getDisplayName() + " &chas GONE afk."));
                Afk.add(p.getDisplayName());
                new AfkEventHandler(p.getDisplayName()); //CALLS THE EVENT.
                return true;
            } else if (Afk.contains(p.getDisplayName())) {
                Bukkit.broadcastMessage(
                        Translate.chat("&8<&4&lAFK&r&8>&r " + p.getDisplayName() + " &2is now back from afk."));
                Afk.remove(p.getDisplayName());
                new UnAfkEventHandler(p.getDisplayName());
                return true;
            }
        }
        return true;
    }
}