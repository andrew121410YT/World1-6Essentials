package KeyCommands;

import MysqlAPI.MySQL;
import Translate.Translate;
import Utils.API;
import Utils.KeyAPI;
import World16.World16.World16.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MutiKeys implements CommandExecutor {

    private Main plugin;
    API api = new API();
    MySQL mysql = new MySQL();
    KeyAPI keyapi = new KeyAPI();

    public MutiKeys(World16.World16.World16.Main getPlugin) {
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
            if (!p.hasPermission("command.mkey.permission")) {
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
                    if (!p.hasPermission("command.mkeygetanotherplayer.permission")) {
                        api.PermissionErrorMessage(p);
                        return true;
                    }
                    String KeyDataDone = args[1];
                    String PlayerNameDataDone = args[2];

                    keyapi.MkeyGetAnotherPlayerKey(mysql, p, KeyDataDone, PlayerNameDataDone);
                    return true;
                } else if (args.length == 2) {
                    if (!p.hasPermission("command.mkeygive.permission")) {
                        api.PermissionErrorMessage(p);
                        return true;
                    }
                    String KeyDataID = args[1];

                    keyapi.MkeyGive(mysql, p, KeyDataID);
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("reset")) {
                if (args.length == 1) {
                    p.sendMessage(Translate.chat("/mkey reset KeyDataID Player"));
                } else {
                    if (args.length == 3) {
                        String keydataid = args[1];
                        String playertarget = args[2];
                        Integer keydataidDONE = Integer.valueOf(keydataid);

                        keyapi.ClearKeyDataID(mysql, playertarget, keydataidDONE);
                        p.sendMessage(Translate.chat("&bOK..."));
                    }
                }
                //SET
            } else if (args[0].equalsIgnoreCase("set")) {
                if (!p.hasPermission("command.mkeyset.permission")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (args.length == 3) {
                    String KeyDataid = args[1];
                    String Lore = args[2];
                    mysql.Connect();
                    mysql.ExecuteCommand("INSERT INTO KeyData (KeyDataID, Player, Lore) VALUES ('" + KeyDataid
                            + "', '" + p.getDisplayName() + "', '" + Lore + "')");
                    p.sendMessage(Translate.chat("&6Your key has been set and stored in the mysql database."));
                    mysql.Disconnect();
                    return true;
                } else if (args.length >= 4) {
                    if (!p.hasPermission("command.mkeysetother.permission")) {
                        api.PermissionErrorMessage(p);
                        return true;
                    }
                    String KeyDataID = args[1];
                    Player PlayerNmaeTarget = this.plugin.getServer().getPlayerExact(args[2]);
                    String Lore = args[3];
                    Integer KeyDataIDDONE = Integer.valueOf(KeyDataID);

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