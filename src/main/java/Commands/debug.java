package Commands;

import Events.OnJoin;
import MysqlAPI.MySQL;
import Translate.Translate;
import Utils.API;
import Utils.CustomYmlManger;
import World16.World16.World16.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class debug implements CommandExecutor {

    HashMap<String, String> keyDataM = OnJoin.keyDataM;
    ArrayList<String> Afk1 = afk.Afk;
    ArrayList<String> Fly1 = fly.Fly;

    private Main plugin;
    API api = new API();
    MySQL mysql = new MySQL();

    private CustomYmlManger configinstance = null;

    public debug(CustomYmlManger getCustomConfig, World16.World16.World16.Main getPlugin) {
        this.configinstance = getCustomConfig;
        this.plugin = getPlugin;

        this.plugin.getCommand("debug1-6").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        // IF THE PLAYER IS THE CONSOLE OR COMMAND BLOCk
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        if (args.length == 0) {
            if (!p.hasPermission("command.debug.permission")) { // Permission
                api.PermissionErrorMessage(p);
                return true;
            }
            p.sendMessage(Translate.chat("&aHow to use /debug1-6"));
            p.sendMessage(Translate.chat("&aFor Oping Staff Use /debug1-6 op"));
            p.sendMessage(Translate.chat("&aFor The Default Tab And Title Use /debug1-6 defaultstuff"));
            p.sendMessage(Translate.chat("&aToo see what's stored in the hashmaps of you do /debug1-6 checkhashmaps"));
            p.sendMessage(
                    Translate.chat("&aFor Using Mysql/sql commands do /debug1-6 sql <CommandGoesHere>"));
            return true;
        } else if (args.length >= 1) {

            //OP
            if (args[0].equalsIgnoreCase("op")) {
                if (!p.hasPermission("command.debug.op.permission")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                p.sendMessage(Translate.chat("&4Debug working oping andrew and tyler and richard"));
                // Ops Andrew
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = "op andrew121410";
                Bukkit.dispatchCommand(console, command);
                String command1 = "op AlphaGibbon43";
                Bukkit.dispatchCommand(console, command1);
                String command3 = "op Robobros3";
                Bukkit.dispatchCommand(console, command3);
                p.sendMessage(Translate.chat("&4There."));
                return true;

                //DEFAULT STUFF
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("defaultstuff"))) {
                if (!p.hasPermission("command.debug.defaultstuff.permission")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                this.plugin.getConfig().set("TittleTOP", "&f&l[&4World 1-6&f&l]");
                this.plugin.getConfig().set("TittleBOTTOM", "&9&oHome Of Minecraft Fire Alarms.");
                this.plugin.getConfig().set("TablistTOP", "&f&l[&4World 1-6&f&l]");
                this.plugin.getConfig().set("TablistBOTTOM", "&9&oHome Of Minecraft Fire Alarms.");
                this.plugin.saveConfig();
                this.plugin.reloadConfig();
                p.sendMessage(Translate.chat("&bOK..."));
                return true;

                //CHECK HASHMAPS
            } else if (args.length >= 1 && (args[0].equalsIgnoreCase("checkhashmaps"))) {
                if (args.length == 1) {
                    p.sendMessage(Translate.chat("[&3/debug1-6 checkhashmaps &5checkmine&r] &9<-- show's what is stored in the HashMap of you."));
                    p.sendMessage(Translate.chat("[&3/debug1-6 checkhashmaps &c@all&r] &9<-- Show's everything in the HashMap"));
                    return true;
                } else {
                    if (args.length >= 1) {
                        if (args[1].equalsIgnoreCase("@all")) {
                            for (String t2 : keyDataM.keySet()) {
                                p.sendMessage(Translate.chat("Key: " + t2 + " Values: " + keyDataM.get(t2)));
                                return true;
                            }
                        } else if (args[1].equalsIgnoreCase("checkmine")) {
                            p.sendMessage(Translate.chat("Here's the HashMap for Events.OnJoin.keyDatam: " + keyDataM.get(p.getDisplayName())));

                        }
                    }
                }

                //CLEAR ALL ARRAYLIST
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("clearallarraylists"))) {
                if (!p.hasPermission("command.debug.clearallarraylists.permission")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                api.clearAllArrayLists();
                p.sendMessage(Translate.chat("&bOK..."));
                return true;

                //CLEAR ALL HASHMAPS
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("clearallhashmaps"))) {
                if (!p.hasPermission("command.debug.clearallhashmaps.permission")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                api.clearAllHashMaps();
                p.sendMessage(Translate.chat("&bOK..."));
                return true;

                //CLEAR ALL HASHMAPS WITH THE NAME.
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("clearallhashmapswithname"))) {
                if (!p.hasPermission("command.debug.clearallhashmapswithname.permission")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                api.clearAllHahsMapsWithName(p);
                return true;

                //DATE
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("date"))) {
                if (!p.hasPermission("command.debug.date.permission")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                String date = api.Time();
                p.sendMessage(Translate.chat("Time/Data:-> " + date));
                return true;

                //SQL
            } else if (args.length >= 2 && (args[0].equalsIgnoreCase("sql"))) {
                if (!p.hasPermission("command.debug.sql.permission")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                // String Builder
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    builder.append(args[i] + " ");
                }
                String msg = builder.toString();
                mysql.Connect();
                mysql.ExecuteCommand(msg);
                p.sendMessage(Translate.chat("&4&lYour command has been executed thru SQL."));
                p.sendMessage(Translate.chat("&aHere's the command you did &r" + msg));
                return true;
            } else {
            }
        }
        return true;
    }
}