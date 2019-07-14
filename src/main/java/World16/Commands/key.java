package World16.Commands;

import CCUtils.Storage.SQLite;
import World16.Main.Main;
import World16.Objects.KeyObject;
import World16.TabComplete.KeyTab;
import World16.Utils.API;
import World16.Utils.KeyAPI;
import World16.Utils.SetListMap;
import World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class key implements CommandExecutor {

    //Maps
    Map<String, KeyObject> keyDataM = SetListMap.keyDataM;
    //...

    //Lists
    //...

    private Main plugin;

    private API api;
    private KeyAPI keyapi;
    private SQLite isql;

    public key(Main getPlugin) {
        this.plugin = getPlugin;

        this.api = new API(this.plugin);
        this.isql = new SQLite(this.plugin.getDataFolder(), "keys");

        this.keyapi = new KeyAPI(this.plugin, this.isql);

        this.plugin.getCommand("key").setExecutor(this);
        this.plugin.getCommand("key").setTabCompleter(new KeyTab(this.plugin));
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
            p.sendMessage(Translate.chat("&6/key list < Show's all of your keys."));
            p.sendMessage(Translate.chat("&6/key set < Sets your key."));
            p.sendMessage(Translate.chat("&6/key give < Gives you your key."));
            p.sendMessage(Translate.chat("&6/key reset < Resets/Clears all data from DATABASE."));
            p.sendMessage(Translate.chat("---------------"));
            return true;
        } else if (args.length >= 1) {

            //SET
            if (args.length >= 1 && args[0].equalsIgnoreCase("set")) {
                if (!p.hasPermission("world16.key.set")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (args.length == 1) {
                    p.sendMessage(Translate.chat("&cUsage: /key set <KeyID> <Lore>"));
                    return true;
                } else if (args.length == 3) {
                    Integer keyID = Integer.valueOf(args[1]);

                    // STRING BUILDER
                    StringBuilder setData = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        setData.append(args[i]);
                        // END OF STRING BUILDER
                    }
                    String setDataDone = setData.toString(); // OUT PUT OF STRING BUILDER

                    if (keyDataM.get(p.getDisplayName()) != null) {
                        keyDataM.get(p.getDisplayName()).setKey(keyID, setDataDone);
                        keyapi.SetKeyAndDeleteOldKey(isql, keyID, p, setDataDone); //<-- MySql
                        p.sendMessage(Translate.chat("&6Your key has been set and stored in the isql database."));
                        return true;
                    } else {
                        p.kickPlayer("Please rejoin the server something went wrong.");
                        return true;
                    }
                } else {
                    p.sendMessage(Translate.chat("&cUsage: /key set <KeyID> <Lore>"));
                    return true;
                }

                //GIVE
            } else if (args.length >= 1 && (args[0].equalsIgnoreCase("give"))) {
                if (!p.hasPermission("world16.key.give")) {
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (args.length == 1) {
                    p.sendMessage(Translate.chat("&cUsage: /key give <KeyID>"));
                } else if (args.length >= 2) {
                    Integer keyID = Integer.valueOf(args[1]);
                    if (keyID <= 5) {
                        keyapi.giveKeyToPlayerFromRam(p, keyID);
                        return true;
                    } else {
                        p.sendMessage(Translate.chat("&cRight now keys can only go up too 5."));
                    }
                }
                //RESET
            } else if (args.length >= 1 && (args[0].equalsIgnoreCase("reset"))) {
                if (!p.hasPermission("world16.key.reset")) { // Permission
                    api.PermissionErrorMessage(p);
                    return true;
                }
                if (args.length == 1) {
                    p.sendMessage(Translate.chat("&cUsage: /key reset <KeyID>"));
                    return true;
                } else if (args.length == 2 && !args[1].equalsIgnoreCase("@everything")) {
                    Integer keyID = Integer.valueOf(args[1]);
                    keyapi.ReplaceKey(isql, keyID, p, "null");
                    keyDataM.remove(p.getDisplayName());
                    p.sendMessage(Translate.chat("&4The lore has been reseted."));
                    return true;
                } else if (args.length == 2 && args[1].equalsIgnoreCase("@everything")) {
                    keyapi.ResetEverythingFromPlayerMySQL(isql, p);
                    keyDataM.remove(p.getDisplayName());
                    p.sendMessage(Translate.chat("&bOK..."));
                    return true;
                } else {
                    p.sendMessage(Translate.chat("&4Something went wrong."));
                }
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("list")) {
                if (args.length == 1) {
                    if (keyDataM.get(p.getDisplayName()) != null) {
                        KeyObject keyObject = keyDataM.get(p.getDisplayName());
                        p.sendMessage(Translate.chat("&6Keys: &aKeyID1 =&9 {key1} &aKeyID2 =&9 {key2} &aKeyID3 =&9 {key3} &aKeyID4 =&9 {key4} &aKeyID5 =&9 {key5}")
                                .replace("{key1}", keyObject.getKey1())
                                .replace("{key2}", keyObject.getKey2())
                                .replace("{key3}", keyObject.getKey3())
                                .replace("{key4}", keyObject.getKey4())
                                .replace("{key5}", keyObject.getKey5()));
                        return true;
                    }
                }
            }
        }
        return true;
    }
}