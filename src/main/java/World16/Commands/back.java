package World16.Commands;

import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class back implements CommandExecutor {

    API api = new API();
    private Main plugin;
    public static LinkedHashMap<String, Location> backm = new LinkedHashMap<>();

    public back(Main getPlugin) {
        this.plugin = getPlugin;
        this.plugin.getCommand("back").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;

        if (args.length == 0) {
            p.sendMessage(Translate.chat("&cUsage:"));
            p.sendMessage(Translate.chat("/back death"));
            p.sendMessage(Translate.chat("/back tp"));
            p.sendMessage(Translate.chat("/back set"));
            p.sendMessage(Translate.chat("/back goto"));
            return true;
        }
        if (args.length >= 1) {

            //DEATH
            if (args[0].equalsIgnoreCase("death")) {
                if (!p.hasPermission("world16.back.death")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (backm.get(p.getDisplayName() + "death") != null) {
                    p.teleport(backm.get(p.getDisplayName() + "death"));
                    return true;
                } else {
                    p.sendMessage(Translate.chat("&4Look's like you didn't die yet."));
                    return true;
                }

                //TP
            } else if (args[0].equalsIgnoreCase("tp")) {
                if (!p.hasPermission("world16.back.tp")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (backm.get(p.getDisplayName() + "tp") != null) {
                    p.teleport(backm.get(p.getDisplayName() + "tp"));
                } else {
                    p.sendMessage(Translate.chat("&4Something went wrong."));
                    return true;
                }

            } else if (args[0].equalsIgnoreCase("set")) {
                if (!p.hasPermission("world16.back.set")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                backm.remove(p.getDisplayName() + "set");
                backm.put(p.getDisplayName() + "set", p.getLocation());
                p.sendMessage(Translate.chat("[&9Back&r] &aYour back has been set."));
                return true;
            }

        } else if (args[0].equalsIgnoreCase("goto")) {
            if (!p.hasPermission("world16.back.goto")) {
                api.PermissionErrorMessage(p);
                return true;
            }
            if (backm.get(p.getDisplayName() + "set") != null) {
                p.teleport(backm.get(p.getDisplayName() + "set"));
                p.sendMessage(Translate.chat("[&9Back&r] &aTheir you go."));
                return true;
            } else {
                p.sendMessage(Translate.chat("&4Something went wrong."));
                return true;
            }
        }
        return true;
    }
}