package KeyCommands;

import Events.OnJoin;
import MysqlAPI.MySQL;
import Translate.Translate;
import Utils.API;
import Utils.KeyAPI;
import World16.World16.World16.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Key implements CommandExecutor {

    HashMap<String, String> keyDataM = OnJoin.keyDatam;

    private Main plugin;
    API api = new API();
    KeyAPI keyapi = new KeyAPI();
    MySQL mysql = new MySQL();

    public Key(World16.World16.World16.Main getPlugin) {
        this.plugin = getPlugin;

        this.plugin.getCommand("key").setExecutor(this);
    }

    @SuppressWarnings("unused")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // IF THE PLAYER IS THE CONSOLE OR COMMAND BLOCk
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;
        // 1
        if (args.length == 0) {
            if (!p.hasPermission("command.key.permission")) { // Permission
                api.PermissionErrorMessage(p);
                return true;
            }
            p.sendMessage(Translate.chat("---------------"));
            p.sendMessage(Translate.chat("&bAll of key commands."));
            p.sendMessage(Translate.chat("&6/key set < Sets your key."));
            p.sendMessage(Translate.chat("&6/key give < Gives you your key."));
            p.sendMessage(Translate.chat("&6/key reset < Resets/Clears all data from DATABASE."));
            p.sendMessage(Translate.chat("---------------"));
            return true;
        }
        if (args.length > 1) {
            if (args[0].equalsIgnoreCase("set")) {
                if (!p.hasPermission("command.keyset.permission")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                // STRING BUILDER
                StringBuilder setData = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    setData.append(args[i]).append("");
                    // END OF STRING BUILDER
                }
                String setDataDone = setData.toString(); // OUT PUT OF STRING BUILDER
                mysql.Connect();
                mysql.ExecuteCommand("INSERT INTO KeyData (KeyDataID, Player, Lore) VALUES ('1', '"
                        + p.getDisplayName() + "', '" + setDataDone + "')");
                //NEW
                keyDataM.put(p.getDisplayName(), setDataDone);

                p.sendMessage(Translate.chat("&6Your key has been set and stored in the mysql database."));
                return true;
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("give")) {
                if (!p.hasPermission("command.keygive.permission")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                //OLD WAY
                //mysql.Connect();
                //keyapi.giveKey(p, mysql);

                //NEW WAY
                keyapi.giveKeyFromTheRam(p);
                return true;
            }
            if (args[0].equalsIgnoreCase("reset")) {
                if (!p.hasPermission("command.keyreset.permission")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                mysql.Connect();
                mysql.ExecuteCommand("DELETE FROM KeyData WHERE Player='" + p.getDisplayName() + "'");

                //NEW
                keyDataM.remove(p.getDisplayName());

                p.sendMessage(
                        Translate.chat("&cAll of you data from the mysql data base has been cleared."));
                return true;
            }
        }
        return true;
    }
}