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

public class Key implements CommandExecutor {

  private static Plugin plugin = Main.plugin;
  API api = new API();
  KeyAPI keyapi = new KeyAPI();
  MySQL mysql = new MySQL();

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
        p.sendMessage(Translate.chat("&6Your key has been set and stored in the mysql database."));
        return true;
      }
    } else if (args.length == 1) {
      if (args[0].equalsIgnoreCase("give")) {
        if (!p.hasPermission("command.keygive.permission")) {
          api.PermissionErrorMessage(p);
          return true;
        }
        mysql.Connect();
        keyapi.giveKey(p, mysql);
        return true;
      }
      if (args[0].equalsIgnoreCase("reset")) {
        if (!p.hasPermission("command.keyreset.permission")) { // Permission
          api.PermissionErrorMessage(p);
          return true;
        }
        mysql.Connect();
        mysql.ExecuteCommand("DELETE FROM KeyData WHERE Player='" + p.getDisplayName() + "'");
        p.sendMessage(
            Translate.chat("&cAll of you data from the mysql data base has been cleared."));
        return true;
      }
    }
    return true;
  }
}
/*
 * public void giveKey(Player p) { new BukkitRunnable() {
 * 
 * @Override public void run() { Material material = Material.TRIPWIRE_HOOK; int ammount = 1; String
 * name = "Key";
 * 
 * ItemStack item = new ItemStack(material, ammount); ItemMeta itemMeta = item.getItemMeta();
 * itemMeta.setDisplayName(name);
 * 
 * // SELECT * FROM KeyData WHERE Player='Mexico'; ResultSet rs =
 * mysql.GetResult("SELECT * FROM KeyData WHERE Player='" + p.getDisplayName() + "';"); try { if
 * (rs.next()) { String PlayerData = rs.getString("Player"); String LoreData = rs.getString("Lore");
 * Player pDone = Bukkit.getPlayer(PlayerData); if (pDone != null) {
 * itemMeta.setLore(Arrays.asList(LoreData)); item.setItemMeta(itemMeta);
 * p.getInventory().addItem(new ItemStack(item)); p.sendMessage(Translate.chat("&aThere You Go."));
 * } else { p.sendMessage(Translate.chat("&4Error With Checking if the Player is you are not.")); }
 * } } catch (IllegalArgumentException | SQLException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); } } }.runTask(plugin);
 */
