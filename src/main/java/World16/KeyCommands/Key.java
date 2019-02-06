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

public class Key implements CommandExecutor {

    HashMap<String, KeyObject> keyDataM = OnJoinEvent.keyDataM;

    private Main plugin;
    API api = new API();
    KeyAPI keyapi = new KeyAPI();
    MySQL mysql = new MySQL();

    public Key(Main getPlugin) {
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
            if (!p.hasPermission("world16.key")) { // Permission
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
        } else if (args.length >= 1) {

            //SET
            if (args[0].equalsIgnoreCase("set")) {
                if (!p.hasPermission("world16.key.set")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                // STRING BUILDER
                StringBuilder setData = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    setData.append(args[i]);
                    // END OF STRING BUILDER
                }
                String setDataDone = setData.toString(); // OUT PUT OF STRING BUILDER

                keyapi.ReplaceKey(mysql, 1, p, setDataDone);
                if (keyDataM.get(p.getDisplayName()) != null) {
                    keyDataM.get(p.getDisplayName()).setKey1(setDataDone);
                } else {
                    p.kickPlayer("Please rejoin the server something went wrong.");
                }

                p.sendMessage(Translate.chat("&6Your key has been set and stored in the mysql database."));
                return true;

                //GIVE
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("give"))) {
                if (!p.hasPermission("world16.key.give")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                keyapi.giveKeyToPlayerFromRam(p, 1);
                return true;

                //RESET
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("reset"))) {
                if (!p.hasPermission("world16.key.reset")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }

//                keyapi.ClearKeyDataID(mysql, p, 1);
                keyapi.ReplaceKey(mysql, 1, p, "null");
                keyDataM.remove(p.getDisplayName());

                p.sendMessage(
                        Translate.chat("&The lore has been reseted."));
                return true;
            }
        }
        return true;
    }
}