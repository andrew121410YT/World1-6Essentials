package World16.KeyCommands;

import World16.Main.Main;
import World16.MysqlAPI.MySQL;
import World16.Utils.API;
import World16.Utils.KeyAPI;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MutiKeys implements CommandExecutor {

    private Main plugin;
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

                    keyapi.MkeyGetAnotherPlayerKey(mysql, p, KeyDataDone, PlayerNameDataDone);
                    return true;
                } else if (args.length == 2) {
                    if (!p.hasPermission("world16.mkey.give")) {
                        api.PermissionErrorMessage(p);
                        return true;
                    }
                    String KeyDataID = args[1];

                    keyapi.MkeyGive(mysql, p, KeyDataID);
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
                        keyapi.ClearKeyDataID(mysql, playertarget, keydataidDONE);
                        p.sendMessage(Translate.chat("&bOK..."));
                        return true;
                    } else {
                        if (args.length >= 4) {
                            String keydataid = args[1];
                            String playertarget = args[2];
                            Integer keydataidDONE = Integer.valueOf(keydataid);
                            if (args[3].equalsIgnoreCase("@bypass")) {
                                keyapi.ClearKeyDataID(mysql, playertarget, keydataidDONE);
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

//                    mysql.Connect();
//                    mysql.ExecuteCommand("INSERT INTO KeyData (KeyDataID, Player, Lore) VALUES ('" + KeyDataid
//                            + "', '" + p.getDisplayName() + "', '" + Lore + "')");

                    p.sendMessage(Translate.chat("&6Your key has been set and stored in the mysql database."));
//                    mysql.Disconnect();
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

                    try {
                        keyapi.SetKey(mysql, KeyDataIDDONE, PlayerNmaeTarget, Lore);
                        p.sendMessage(Translate.chat("&6This key has been set and stored in the mysql database."));
                    } catch (NullPointerException e) {
                        p.sendMessage(Translate.chat("&cThat Player Does Not Exist"));
                    } finally {
                        mysql.Disconnect();
                    }
                    return true;
                } else {
                }
            }
        }
        return true;
    }
}