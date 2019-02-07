package World16.KeyCommands;

import World16.Events.OnJoinEvent;
import World16.Main.Main;
import World16.MysqlAPI.MySQL;
import World16.Objects.KeyObject;
import World16.Utils.API;
import World16.Utils.KeyAPI;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MutiKeys implements CommandExecutor {

    private Main plugin;

    //Maps
    HashMap<String, KeyObject> keyDataM = OnJoinEvent.keyDataM;
    //...

    API api = new API();
    MySQL mysql = new MySQL();
    KeyAPI keyapi = new KeyAPI();

    public MutiKeys(Main getPlugin) {
        this.plugin = getPlugin;

        this.plugin.getCommand("mkey").setExecutor(this);
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
            if (!p.hasPermission("world16.mkey")) {
                api.PermissionErrorMessage(p);
                return true;
            }
            p.sendMessage(Translate.chat("&b---------------"));
            p.sendMessage(Translate.chat("/mkey give <KeyID>"));
            p.sendMessage(Translate.chat("&c&lOR"));
            p.sendMessage(Translate.chat("/mkey give <KeyID> <PlayerName>"));
            p.sendMessage(Translate.chat("&9To Set A Key Do"));
            p.sendMessage(Translate.chat("&c/mkey set <KeyID> <Lore>"));
            p.sendMessage(Translate.chat("&3/mkey set <KeyID> <PlayerName> <Lore>"));
            p.sendMessage(Translate.chat("&c/mkey reset <KeyID> <PlayerName>"));
            p.sendMessage(Translate.chat("&c/mkey fetch"));
            p.sendMessage(Translate.chat("&c/mkey list"));
            p.sendMessage(Translate.chat("&b---------------"));
            return true;
        } else {

            if (args[0].equalsIgnoreCase("give")) {
                if (args.length == 3) {
                    if (!p.hasPermission("world16.mkey.give.other")) {
                        api.PermissionErrorMessage(p);
                        return true;
                    }
                    String KeyDataDone = args[1];
                    String PlayerNameDataDone = args[2];

                    keyapi.giveKeyToPlayerFromMySQL(p, mysql, Integer.valueOf(KeyDataDone), PlayerNameDataDone);
                    return true;
                } else if (args.length == 2) {
                    if (!p.hasPermission("world16.mkey.give")) {
                        api.PermissionErrorMessage(p);
                        return true;
                    }
                    String KeyDataID = args[1];

                    keyapi.giveKeyToPlayerFromMySQL(p, mysql, Integer.valueOf(KeyDataID), p.getDisplayName());
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("reset")) {
                if (!p.hasPermission("world16.mkey.reset")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (args.length == 1) {
                    p.sendMessage(Translate.chat("/mkey reset <KeyDataID> <Player>"));
                } else {
                    if (args.length == 3) {
                        String keydataid = args[1];
                        String playertarget = args[2];
                        Integer keydataidDONE = Integer.valueOf(keydataid);
                        if (args[1].equals("1")) {
                            p.sendMessage(Translate.chat("&cFor reseting KeyID 1 please use /key reset"));
                            return true;
                        }
                        keyapi.DeleteKeyDataID(mysql, playertarget, keydataidDONE);
                        p.sendMessage(Translate.chat("&bOK..."));
                        return true;
                    } else {
                        if (args.length >= 4) {
                            String keydataid = args[1];
                            String playertarget = args[2];
                            Integer keydataidDONE = Integer.valueOf(keydataid);
                            if (args[3].equalsIgnoreCase("@bypass")) {
                                keyapi.DeleteKeyDataID(mysql, playertarget, keydataidDONE);
                                p.sendMessage(Translate.chat("&bOK..."));
                                p.sendMessage(Translate.chat("YOU Bypassed!!!"));
                            } else {
                                p.sendMessage(Translate.chat("&6Hmm?"));
                            }
                        }
                    }
                }
                //SET
            } else if (args[0].equalsIgnoreCase("set")) {
                if (!p.hasPermission("world16.mkey.set")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (args.length == 3) {
                    String KeyDataID = args[1]; //TAKES THE KEYDATAID.
                    String Lore = args[2]; //TAKES THE LORE.
                    Integer KeyDataIDDONE = Integer.valueOf(KeyDataID); //Makes KeyDataID Turn into an INT.

                    keyapi.SetKey(mysql, KeyDataIDDONE, p, Lore);

                    p.sendMessage(Translate.chat("&6Your key has been set and stored in the mysql database."));
                    return true;
                } else if (args.length >= 4) {
                    if (!p.hasPermission("world16.mkey.set.other")) {
                        api.PermissionErrorMessage(p);
                        return true;
                    }

                    String KeyDataID = args[1]; //TAKES THE KEYDATAID
                    String PlayerNmaeTarget = args[2]; //TAKES THE PLAYER
                    String Lore = args[3]; //TAKES THE LORE
                    Integer KeyDataIDDONE = Integer.valueOf(KeyDataID); //CHANGES STRING TO INT

                    keyapi.SetKey(mysql, KeyDataIDDONE, PlayerNmaeTarget, Lore);
                    p.sendMessage(Translate.chat("&6This key has been set and stored in the mysql database."));
                }
            } else if (args[0].equalsIgnoreCase("fetch")) {
                if (!p.hasPermission("world16.mkey.fetch")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                keyapi.giveAllKeysToRam(p.getDisplayName(), mysql);
                return true;
            } else if (args[0].equalsIgnoreCase("list")) {
                if (args.length == 1) {
                    p.sendMessage(Translate.chat("/mkey list @ram"));
                } else if (args.length == 2 && args[1].equalsIgnoreCase("@ram")) {
                    KeyObject keyo = keyDataM.get(p.getDisplayName());
                    p.sendMessage(Translate.chat("Keys: " + keyo.getKey1() + " " + keyo.getKey2() + " " + keyo.getKey3() + " " + keyo.getKey4() + " " + keyo.getKey5()));
                }
            }
        }
        return true;
    }
}