package Utils;

import Events.OnJoin;
import Main.Main;
import MysqlAPI.MySQL;
import Translate.Translate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class KeyAPI {

    private static Plugin plugin = Main.getPlugin();
    HashMap<String, String> keyDatam = OnJoin.keyDataM;

    // START
    public void giveKey(Player p, MySQL mysql) { //GIVES DEFAULT ID 1 KEY
        new BukkitRunnable() {

            @Override
            public void run() {
                Material material = Material.TRIPWIRE_HOOK;
                int ammount = 1;
                String name = "Key";

                ItemStack item = new ItemStack(material, ammount);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(name);

                // SELECT * FROM KeyData WHERE Player='Mexico';
                ResultSet rs =
                        mysql.GetResult("SELECT * FROM KeyData WHERE Player='" + p.getDisplayName() + "';");
                try {
                    if (rs.next()) {
                        String PlayerData = rs.getString("Player");
                        String LoreData = rs.getString("Lore");
                        Player pDone = Bukkit.getPlayer(PlayerData);
                        if (pDone != null) {
                            itemMeta.setLore(Arrays.asList(LoreData));
                            item.setItemMeta(itemMeta);
                            p.getInventory().addItem(new ItemStack(item));
                            p.sendMessage(Translate.chat("&aThere You Go."));
                        } else {
                            p.sendMessage(Translate.chat("&4Error With Checking if the Player is you are not."));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTask(plugin);
    }

    public String giveKeyReturn(Player p, MySQL mysql) { //RETURNS KEY
        mysql.Connect();
        ResultSet rs =
                mysql.GetResult("SELECT * FROM KeyData WHERE Player='" + p.getPlayer().getDisplayName() + "';");
        try {
            if (rs.next()) {
                String PlayerData = rs.getString("Player");
                String LoreData = rs.getString("Lore");
                Player pDone = Bukkit.getPlayer(PlayerData);
                mysql.Disconnect();
                if (pDone != null) {
                    mysql.Disconnect();
                    return LoreData.toString();
                } else {
                    p.sendMessage(Translate.chat("&4Error With Checking if the Player is you are not."));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mysql.Disconnect();
        return null;
    }

    public void giveKeyFromTheRam(Player p) { //GETS THE 1 KEY FROM THE MEMORY.
        Material material = Material.TRIPWIRE_HOOK;
        int ammount = 1;
        String name = "Key";

        ItemStack item = new ItemStack(material, ammount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(Arrays.asList(keyDatam.get(p.getDisplayName())));
        item.setItemMeta(itemMeta);
        p.getInventory().addItem(new ItemStack(item));
        p.sendMessage(Translate.chat("&aThere You Go."));
    }


    public void MkeyGive(MySQL mysql, Player p, String KeyDataID) {
        new BukkitRunnable() {

            @Override
            public void run() {
                Material material = Material.TRIPWIRE_HOOK;
                int ammount = 1;
                String name = "Key";

                ItemStack item = new ItemStack(material, ammount);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(name);

                // SELECT * FROM KeyData WHERE Player='Mexico';
                mysql.Connect();
                ResultSet rs = mysql.GetResult("SELECT * FROM KeyData WHERE Player='" + p.getDisplayName()
                        + "' AND KeyDataID=" + KeyDataID + ";");
                try {
                    if (rs.next()) {
                        String KeyDataID = rs.getString("KeyDataID");
                        String PlayerData = rs.getString("Player");
                        String LoreData = rs.getString("Lore");
                        Player pDone = Bukkit.getPlayer(PlayerData);
                        mysql.Disconnect();
                        if (pDone != null) {
                            mysql.Disconnect();
                            itemMeta.setLore(Arrays.asList(LoreData));
                            item.setItemMeta(itemMeta);
                            p.getInventory().addItem(new ItemStack(item));
                            p.sendMessage(Translate.chat("&aThere You Go."));
                        } else {
                            p.sendMessage(Translate.chat("&4Error With Checking if the Player is you are not."));
                        }
                    }
                } catch (IllegalArgumentException | SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.runTask(plugin);
    }

    public void MkeyGetAnotherPlayerKey(MySQL mysql, Player p, String KeyDataDone,
                                        String PlayerNameDataDone) {
        new BukkitRunnable() {

            @Override
            public void run() {
                Material material = Material.TRIPWIRE_HOOK;
                int ammount = 1;
                String name = "Key";

                ItemStack item = new ItemStack(material, ammount);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(name);

                // SELECT * FROM KeyData WHERE Player='Mexico';
                mysql.Connect();
                ResultSet rs = mysql.GetResult("SELECT * FROM KeyData WHERE (KeyDataID='" + KeyDataDone
                        + "' AND Player='" + PlayerNameDataDone + "')");
                try {
                    if (rs.next()) {
                        String KeyDataID = rs.getString("KeyDataID");
                        String PlayerData = rs.getString("Player");
                        String LoreData = rs.getString("Lore");
                        // Player pDone = Bukkit.getPlayer(PlayerData);
                        // if (pDone != null) {
                        itemMeta.setLore(Arrays.asList(LoreData));
                        item.setItemMeta(itemMeta);
                        p.getInventory().addItem(new ItemStack(item));
                        p.sendMessage(Translate.chat("&aThere You Go."));
                        mysql.Disconnect();
                    }
                } catch (IllegalArgumentException | SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    //
//
//
    //1
    public void ClearKeyDataID(MySQL mysql, Player p, int INT) {
        new BukkitRunnable() {
            @Override
            public void run() {
                mysql.Connect();
                mysql.ExecuteCommand("DELETE FROM KeyData WHERE KeyDataID='" + INT + "' AND Player='" + p.getDisplayName() + "'");
                mysql.Disconnect();
            }
        }.runTaskAsynchronously(plugin);
    }

    //2
    public void ClearKeyDataID(MySQL mysql, String p, int INT) {
        new BukkitRunnable() {
            @Override
            public void run() {
                mysql.Connect();
                mysql.ExecuteCommand("DELETE FROM KeyData WHERE KeyDataID='" + INT + "' AND Player='" + p + "'");
                mysql.Disconnect();
            }
        }.runTaskAsynchronously(plugin);
    }

    //1
    public void SetKey(MySQL mysql, int KeyDataID, Player p, String Lore) {
        new BukkitRunnable() {
            @Override
            public void run() {
                mysql.Connect();
                mysql.ExecuteCommand("INSERT INTO KeyData (KeyDataID, Player, Lore) VALUES ('" + KeyDataID + "', '"
                        + p.getDisplayName() + "', '" + Lore + "')");
                mysql.Disconnect();
            }
        }.runTaskAsynchronously(plugin);
    }

    //2
    public void SetKey(MySQL mysql, int KeyDataID, String p, String Lore) {
        new BukkitRunnable() {
            @Override
            public void run() {
                mysql.Connect();
                mysql.ExecuteCommand("INSERT INTO KeyData (KeyDataID, Player, Lore) VALUES ('" + KeyDataID + "', '"
                        + p + "', '" + Lore + "')");
                mysql.Disconnect();
            }
        }.runTaskAsynchronously(plugin);
    }

    //1
    public void ReplaceKey(MySQL mysql, int KeyDataID, Player p, String Lore) {
        new BukkitRunnable() {
            @Override
            public void run() {
                mysql.Connect();
                mysql.ExecuteCommand("UPDATE KeyData SET Lore = '" + Lore + "' WHERE KeyDataID = " + KeyDataID + " AND Player = '" + p.getDisplayName() + "'");
                mysql.Disconnect();
            }
        }.runTaskAsynchronously(plugin);
    }

    //2
    public void ReplaceKey(MySQL mysql, int KeyDataID, String p, String Lore) {
        new BukkitRunnable() {
            @Override
            public void run() {
                mysql.Connect();
                mysql.ExecuteCommand("UPDATE KeyData SET Lore = '" + Lore + "' WHERE KeyDataID = " + KeyDataID + " AND Player = '" + p + "'");
                mysql.Disconnect();
            }
        }.runTaskAsynchronously(plugin);
    }
}