package KeyCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import MysqlAPI.MySQL;
import Translate.Translate;
import Utils.API;
import Utils.KeyAPI;
import World16.World16.World16.Main;

public class MutiKeys implements CommandExecutor {

  private static Plugin plugin = Main.plugin;
  API api = new API();
  MySQL mysql = new MySQL();
  KeyAPI keyapi = new KeyAPI();

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
      p.sendMessage(Translate.chat("&b---------------"));
      return true;
    } else if (args[0].equalsIgnoreCase("give")) {
      if (args.length >= 2) {
        if (!p.hasPermission("command.mkeygetanotherplayer.permission")) {
          api.PermissionErrorMessage(p);
          return true;
        }
        String KeyDataDone = args[1];
        String PlayerNameDataDone = args[2];

        mysql.Connect();
        keyapi.mkeygetanotherplayerkey(mysql, p, KeyDataDone, PlayerNameDataDone);
        return true;
      }
      if (!p.hasPermission("command.mkeygive.permission")) {
        api.PermissionErrorMessage(p);
        return true;
      }
      String KeyDataID = args[1];
      mysql.Connect();
      keyapi.mkey(mysql, p, KeyDataID);
      return true;
    } else if (args[0].equalsIgnoreCase("set")) {
      if (!p.hasPermission("command.mkeyset.permission")) {
        api.PermissionErrorMessage(p);
        return true;
      }
      if (args.length == 1) {
        String KeyDataid = args[1];
        String Lore = args[2];
        mysql.Connect();
        mysql.ExecuteCommand("INSERT INTO KeyData (KeyDataID, Player, Lore) VALUES ('" + KeyDataid
            + "', '" + p.getDisplayName() + "', '" + Lore + "')");
        p.sendMessage(Translate.chat("&6Your key has been set and stored in the mysql database."));
        return true;
      } else if (args.length != 3) {
        if (!p.hasPermission("command.mkeysetother.permission")) {
          api.PermissionErrorMessage(p);
          return true;
        }
        String KeyDataID = args[1];
        String PlayerNameTarget = args[2];
        String Lore = args[3];
        mysql.Connect();
        mysql.ExecuteCommand("INSERT INTO KeyData (KeyDataID, Player, Lore) VALUES ('" + KeyDataID
            + "', '" + PlayerNameTarget + "', '" + Lore + "')");
        p.sendMessage(Translate.chat("&6Your key has been set and stored in the mysql database."));
        return true;
      }
    }
    return true;
  }
}
