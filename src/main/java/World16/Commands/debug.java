package World16.Commands;

import World16.Commands.tp.tpa;
import World16.Events.OnJoinEvent;
import World16.Main.Main;
import World16.MysqlAPI.MySQL;
import World16.Objects.KeyObject;
import World16.Utils.API;
import World16.Utils.Translate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class debug implements CommandExecutor {

    //Maps
    Map<String, KeyObject> keyDataM = OnJoinEvent.keyDataM;
    Map<String, List<Location>> backm = back.backm;
    Map<Player, Player> tpam = tpa.tpam;
    //...

    //Lists
    //...

    private API api;
    private MySQL mysql = new MySQL();

    private Main plugin;

    public debug(Main getPlugin) {
        this.plugin = getPlugin;
        api = new API();

        this.plugin.getCommand("debug1-6").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            if (!p.hasPermission("world16.debug")) { // Permission
                api.PermissionErrorMessage(p);
                return true;
            }
            p.sendMessage(Translate.chat("/debug1-6 op"));
            p.sendMessage(Translate.chat("/debug1-6 defaultstuff"));
            p.sendMessage(Translate.chat("/debug1-6 checkmaps"));
            p.sendMessage(Translate.chat("/debug1-6 clearalllists")); //1
            p.sendMessage(Translate.chat("/debug1-6 clearallmaps")); //2
            p.sendMessage(Translate.chat("/debug1-6 clearalllistswithname"));
            p.sendMessage(Translate.chat("/debug1-6 clearallmapswithname")); //2
            p.sendMessage(Translate.chat("/debug1-6 date"));
            p.sendMessage(Translate.chat("/debug1-6 playerversion"));
            p.sendMessage(Translate.chat("/debug1-6 checkuuid"));
            p.sendMessage(Translate.chat("/debug1-6 debugmessages"));
            p.sendMessage(Translate.chat("/debug1-6 finddefaultspawn"));
            p.sendMessage(Translate.chat("/debug1-6 sql"));
            //p.sendMessage(World16.Translate.chat("/debug1-6 "));
            return true;
        } else if (args.length >= 1) {

            //OP
            if (args[0].equalsIgnoreCase("op")) {
                if (!p.hasPermission("world16.debug.op")) { // Permission
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
                if (!p.hasPermission("world16.debug.defaultstuff")) { // Permission
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

                //CHECK MAPS
            } else if (args.length >= 1 && (args[0].equalsIgnoreCase("checkmaps"))) {
                if (!p.hasPermission("world16.debug.checkmaps")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (args.length == 1) {
                    p.sendMessage(Translate.chat(
                            "[&3/debug1-6 checkmaps &5@checkmine&r] &9<-- Show's what is stored in the HashMap of you."));
                    p.sendMessage(Translate.chat(
                            "[&3/debug1-6 checkmaps &c@all&r] &9<-- Show's everything in the Maps"));
                    p.sendMessage(Translate.chat("[&3/debug-1-6 checkmaps &c@AllWithDepth] &9<-- Show's Everything but you can see what's in the classes."));
                    return true;
                } else if (args.length >= 2) {
                    if (args[1].equalsIgnoreCase("@all")) {
                        p.sendMessage(String.valueOf(Arrays.asList(keyDataM)));
                        p.sendMessage(String.valueOf(Arrays.asList(backm)));
                        p.sendMessage(String.valueOf(Arrays.asList(tpam)));
                        return true;
                    } else if (args[1].equalsIgnoreCase("@checkmine")) {
                        p.sendMessage(Translate.chat("&4This is no longer working."));
                        return true;
                    } else if (args[1].equalsIgnoreCase("@AllWithDepth")) {
                        p.sendMessage(Translate.chat(String.valueOf(Arrays.asList(tpam))));
                        p.sendMessage(Translate.chat("&b{SPACE}"));

                        for (Map.Entry<String, List<Location>> entry : backm.entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            p.sendMessage(Translate.chat(value.toString()));
                            p.sendMessage(Translate.chat("&b{SPACE}"));
                        }
                        p.sendMessage(Translate.chat("&b{SPACE}"));
                        for (Map.Entry<String, KeyObject> entry : keyDataM.entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            p.sendMessage(Translate.chat(value.toString()));
                            p.sendMessage(Translate.chat("&b{SPACE}"));
                        }
                        return true;
                    }
                }

                //CLEAR ALL LISTS
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("clearalllists"))) {
                if (!p.hasPermission("world16.debug.clearalllists")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                api.clearAllLists();
                p.sendMessage(Translate.chat("&bOK..."));
                return true;

                //CLEAR ALL MAPS
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("clearallmaps"))) {
                if (!p.hasPermission("world16.debug.clearallmaps")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                api.clearAllMaps();
                p.sendMessage(Translate.chat("&bOK..."));
                return true;

                //CLEAR ALL LISTS WITH THE NAME.
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("clearalllistswithname"))) {
                if (!p.hasPermission(
                        "world16.debug.clearalllistswithname")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                api.clearAllLists(p);
                p.sendMessage(Translate.chat("&bOK..."));
                return true;

                //CLEAR ALL MAPS WITH THE NAME.
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("clearallmapswithname"))) {
                if (!p.hasPermission(
                        "world16.debug.clearallmapswithname")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                api.clearAllMaps(p);
                p.sendMessage(Translate.chat("&bOK..."));
                return true;

                //DATE
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("date"))) {
                if (!p.hasPermission("world16.debug.date")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                String date = api.Time();
                p.sendMessage(Translate.chat("Time/Data:-> " + date));
                return true;

                //PLAYER VERSION
            } else if (args.length >= 1 && (args[0].equalsIgnoreCase("playerversion"))) {
                if (!p.hasPermission("world16.debug.playerversion")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (args.length == 1) {
                    p.sendMessage(api.getPlayerVersion(p));
                } else {
                    Player target = plugin.getServer().getPlayerExact(args[1]);
                    if (args.length >= 1 && target != null && target.isOnline()) {
                        if (!p.hasPermission("world16.debug.playerversion.other")) {
                            api.PermissionErrorMessage(p);
                            return true;
                        }
                        p.sendMessage("The Player: " + target.getDisplayName() + " Version: " + api
                                .getPlayerVersion(target) + " Server Version: " + api
                                .getServerVersion());
                    } else {
                        return true;
                    }
                }
            } else if (args.length >= 1 && (args[0].equalsIgnoreCase("checkuuid"))) {
                if (!p.hasPermission("world16.debug.checkuuid")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (args.length == 1) {
                    p.sendMessage(Translate.chat(p.getUniqueId().toString()));
                } else {
                    Player target = plugin.getServer().getPlayerExact(args[1]);
                    if (args.length == 2 && target != null && target.isOnline()) {
                        if (!p.hasPermission("world16.debug.checkuuid.other")) {
                            api.PermissionErrorMessage(p);
                            return true;
                        }
                        try {
                            String uuidtarget = api.getUUIDFromMojangAPI(target.getDisplayName());
                            p.sendMessage(Translate
                                    .chat("UUID: " + uuidtarget + " FOR " + target.getDisplayName()));

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else if (args.length >= 3 && args[1] != null && args[2] != null && args[2]
                            .equalsIgnoreCase("@offline")) {
                        try {
                            String uuidtarget2 = api.getUUIDFromMojangAPI(args[1]);
                            p.sendMessage(
                                    Translate.chat("UUID: " + uuidtarget2 + " FOR " + args[1]));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //DEBUG MESSAGES
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("debugmessages")) {
                if (!p.hasPermission("world16.debug.debugmessages")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (args.length == 1) {
                    p.sendMessage(Translate.chat("&4Usage: /debug1-6 debugmessages on OR off"));
                }
                if (args.length == 2 && args[1].equalsIgnoreCase("on")) {
                    this.plugin.getConfig().set("debug", "true");
                    this.plugin.saveConfig();
                    this.plugin.reloadConfig();
                    p.sendMessage(Translate.chat("&bOK..."));
                } else if (args.length == 2 && args[1].equalsIgnoreCase("off")) {
                    this.plugin.getConfig().set("debug", "false");
                    this.plugin.saveConfig();
                    this.plugin.reloadConfig();
                    p.sendMessage(Translate.chat("&bOK..."));
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("finddefaultspawn")) {
                if (!p.hasPermission("world16.debug.finddefaultspawn")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                p.teleport(this.plugin.getServer().getWorld(p.getWorld().getName()).getSpawnLocation());
                return true;

                //SQL
            } else if (args.length >= 2 && (args[0].equalsIgnoreCase("sql"))) {
                if (!p.hasPermission("world16.debug.sql")) { // Permission
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
                mysql.Disconnect();
                p.sendMessage(Translate.chat("&4&lYour command has been executed thru SQL."));
                p.sendMessage(Translate.chat("&aHere's the command you did &r" + msg));
                return true;
            } else {
                return true;
            }
        }
        return true;
    }
}
