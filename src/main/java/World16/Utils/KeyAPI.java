package World16.Utils;

import CCUtils.Storage.ISQL;
import World16.Main.Main;
import World16.Objects.KeyObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * The key API for the /key
 *
 * @author Andrew121410
 * @version 1
 * @since 2/4/2019
 */
public class KeyAPI {

    private Main plugin;

    //Maps
    Map<String, KeyObject> keyDatam = SetListMap.keyDataM;
    //...

    public KeyAPI(Main plugin) {
        this.plugin = plugin;
    }

    public KeyAPI(Main plugin, ISQL isql) {
        this.plugin = plugin;
        isql.Connect();
        isql.ExecuteCommand("CREATE TABLE IF NOT EXISTS `KeyData` (" +
                "`KeyDataID` INT," +
                "`Player` TEXT," +
                "`Lore` TEXT" +
                ");");
        isql.Disconnect();
    }

    /**
     * Get's all keys from ram and stores it in the mySQL database
     *
     * @param mySQL      mySQL Object
     * @param playerName Player name String
     */
    public void getAllKeysFromRamAndStoreItInMySql(ISQL mySQL, String playerName) {
        new BukkitRunnable() {
            @Override
            public void run() {
                mySQL.Connect();
                IntStream.range(1, 6).forEach(i -> {
                    SetKeyAndDeleteOldKey(mySQL, i, playerName, keyDatam.get(playerName).getKey(i), false);
                });
                mySQL.Disconnect();
            }
        }.runTaskAsynchronously(plugin);
    }

    /**
     * Gets the key stright from the database and gives it too the player
     *
     * @param p           Player that your gonna give the key too
     * @param mysql       mysql object
     * @param keyDataID   the id
     * @param playerToGet the player to get.
     */
    public void giveKeyToPlayerFromMySQL(Player p, ISQL mysql, Integer keyDataID, String playerToGet) { //GIVES DEFAULT ID 1 KEY
        new BukkitRunnable() {

            @Override
            public void run() {
                Material material = Material.TRIPWIRE_HOOK;
                int ammount = 1;
                String name = "key";

                ItemStack item = new ItemStack(material, ammount);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(name);

//                ResultSet rs =
//                        mysql.GetResult("SELECT * FROM KeyData WHERE Player='" + p.getDisplayName() + "';");
                ResultSet rs = mysql.GetResult("SELECT * FROM KeyData WHERE (KeyDataID='" + keyDataID
                        + "' AND Player='" + playerToGet + "')");
                try {
                    if (rs.next()) {
                        String KeyDataID = rs.getString("KeyDataID");
                        String PlayerData = rs.getString("Player");
                        String LoreData = rs.getString("Lore");

                        itemMeta.setLore(Arrays.asList(LoreData));
                        item.setItemMeta(itemMeta);
                        p.getInventory().addItem(new ItemStack(item));
                        p.sendMessage(Translate.chat("&aThere You Go."));
                        mysql.Disconnect();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    mysql.Disconnect();
                }
            }
        }.runTask(plugin);
    }

    /**
     * Get's a key by it's ID and then it returns it.
     *
     * @param playerName Player Name String
     * @param mysql      mysql object
     * @param keyDataID  key ID
     * @return returns the key string from mysql.
     */
    public String getKeyFromMysqlReturn(String playerName, ISQL mysql, Integer keyDataID) { //RETURNS KEY
        mysql.Connect();
//        ResultSet rs =
//                mysql.GetResult("SELECT * FROM KeyData WHERE Player='" + p.getPlayer().getDisplayName() + "';");
        ResultSet rs = mysql.GetResult("SELECT * FROM KeyData WHERE (KeyDataID='" + keyDataID
                + "' AND Player='" + playerName + "')");
        try {
            if (rs.next()) {
                String KeyID = rs.getString("KeyDataID");
                String PlayerData = rs.getString("Player");
                String LoreData = rs.getString("Lore");
                mysql.Disconnect();
                return LoreData;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mysql.Disconnect();
        return null;
    }

    /**
     * Gets the key from the mysql database and stores in ram for easy access.
     *
     * @param playerName Player name
     * @param mysql      mysql object
     * @param keyDataID  key ID
     */
    public void getKeyFromMysqlAndThenGiveItTooRam(String playerName, ISQL mysql, Integer keyDataID) { //RETURNS KEY

        new BukkitRunnable() {
            @Override
            public void run() {

                mysql.Connect();
//        ResultSet rs =
//                mysql.GetResult("SELECT * FROM KeyData WHERE Player='" + p.getPlayer().getDisplayName() + "';");
                ResultSet rs = mysql.GetResult("SELECT * FROM KeyData WHERE (KeyDataID='" + keyDataID
                        + "' AND Player='" + playerName + "')");
                try {
                    if (rs.next()) {
                        String KeyID = rs.getString("KeyDataID");
                        String PlayerData = rs.getString("Player");
                        String LoreData = rs.getString("Lore");
                        if (keyDatam.get(playerName) != null) {
                            keyDatam.get(playerName).setKey(Integer.valueOf(KeyID), LoreData);
                        }

                    }
                } catch (SQLException e) {
                    mysql.Disconnect();
                    e.printStackTrace();
                } finally {
                    mysql.Disconnect();
                }
                mysql.Disconnect();
            }
        }.runTaskAsynchronously(this.plugin);
    }

    /**
     * Gets every key for the player and it stores it in ram for easy access.
     *
     * @param playerName Player name string
     * @param mysql      mysql object
     */
    public void getAllKeysFromMysqlTooRam(String playerName, ISQL mysql) { //RETURNS KEY

        new BukkitRunnable() {
            @Override
            public void run() {

                mysql.Connect();
                ResultSet rs = mysql.GetResult("SELECT * FROM KeyData WHERE (Player='" + playerName + "')");
                try {
                    while (rs.next()) {
                        String KeyID = rs.getString("KeyDataID");
                        String PlayerData = rs.getString("Player");
                        String LoreData = rs.getString("Lore");
                        if (keyDatam.get(playerName) != null) {
                            keyDatam.get(playerName).setKey(Integer.valueOf(KeyID), LoreData);
                        }

                    }
                } catch (SQLException e) {
                    mysql.Disconnect();
                    e.printStackTrace();
                } finally {
                    mysql.Disconnect();
                }
            }
        }.runTaskAsynchronously(this.plugin);
    }

    /**
     * Gets the key from the ram and it gives it too the player
     *
     * @param p         Player
     * @param keyDataID key ID
     */
    public void giveKeyToPlayerFromRam(Player p, Integer keyDataID) { //GETS THE 1 KEY FROM THE MEMORY.
        Material material = Material.TRIPWIRE_HOOK;
        int ammount = 1;
        String name = "key";

        ItemStack item = new ItemStack(material, ammount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(Arrays.asList(keyDatam.get(p.getDisplayName()).getKey(keyDataID)));

        item.setItemMeta(itemMeta);
        p.getInventory().addItem(new ItemStack(item));
        p.sendMessage(Translate.chat("&aThere You Go."));
    }

    //
//
//
    //1
    public void ResetEverythingFromPlayerMySQL(ISQL mysql, Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                mysql.Connect();
                mysql.ExecuteCommand("DELETE FROM KeyData WHERE Player='" + p.getDisplayName() + "'");
                mysql.Disconnect();
            }
        }.runTaskAsynchronously(plugin);
    }

    //2
    public void ResetEverythingFromPlayerMySQL(ISQL mysql, String p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                mysql.Connect();
                mysql.ExecuteCommand("DELETE FROM KeyData WHERE Player='" + p + "'");
                mysql.Disconnect();
            }
        }.runTaskAsynchronously(plugin);
    }

    //1
    public void DeleteKeyDataID(ISQL mysql, Player p, int INT) {
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
    public void DeleteKeyDataID(ISQL mysql, String p, int INT) {
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
    public void SetKey(ISQL mysql, int KeyDataID, Player p, String Lore) {
        new BukkitRunnable() {
            @Override
            public void run() {
                mysql.Connect();
                try {
                    PreparedStatement preparedStatement = mysql.ExecuteCommandPreparedStatement("INSERT INTO KeyData (KeyDataID, Player, Lore) VALUES (?,?,?);");
                    preparedStatement.setInt(1, KeyDataID);
                    preparedStatement.setString(2, p.getDisplayName());
                    preparedStatement.setString(3, Lore);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    mysql.Disconnect();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    //2
    public void SetKey(ISQL mysql, int KeyDataID, String p, String Lore) {
        new BukkitRunnable() {
            @Override
            public void run() {
                mysql.Connect();
                try {
                    PreparedStatement preparedStatement = mysql.ExecuteCommandPreparedStatement("INSERT INTO KeyData (KeyDataID, Player, Lore) VALUES (?,?,?);");
                    preparedStatement.setInt(1, KeyDataID);
                    preparedStatement.setString(2, p);
                    preparedStatement.setString(3, Lore);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    mysql.Disconnect();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    //1
    public void SetKeyAndDeleteOldKey(ISQL mysql, int KeyDataID, Player p, String Lore) {
        new BukkitRunnable() {
            @Override
            public void run() {
                mysql.Connect();
                mysql.ExecuteCommand("DELETE FROM KeyData WHERE KeyDataID='" + KeyDataID + "' AND Player='" + p.getDisplayName() + "'");
                try {
                    PreparedStatement preparedStatement = mysql.ExecuteCommandPreparedStatement("INSERT INTO KeyData (KeyDataID, PLayer, Lore) VALUES (?,?,?);");
                    preparedStatement.setInt(1, KeyDataID);
                    preparedStatement.setString(2, p.getDisplayName());
                    preparedStatement.setString(3, Lore);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    mysql.Disconnect();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    //2
    public void SetKeyAndDeleteOldKey(ISQL mysql, int KeyDataID, String p, String Lore) {
        new BukkitRunnable() {
            @Override
            public void run() {
                mysql.Connect();
                mysql.ExecuteCommand("DELETE FROM KeyData WHERE KeyDataID='" + KeyDataID + "' AND Player='" + p + "'");
                try {
                    PreparedStatement preparedStatement = mysql.ExecuteCommandPreparedStatement("INSERT INTO KeyData (KeyDataID, PLayer, Lore) VALUES (?,?,?);");
                    preparedStatement.setInt(1, KeyDataID);
                    preparedStatement.setString(2, p);
                    preparedStatement.setString(3, Lore);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    mysql.Disconnect();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    //3
    public void SetKeyAndDeleteOldKey(ISQL mysql, int KeyDataID, String p, String Lore, boolean autoConnectAndAutoDisconnect) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (autoConnectAndAutoDisconnect) {
                    mysql.Connect();
                }
                mysql.ExecuteCommand("DELETE FROM KeyData WHERE KeyDataID='" + KeyDataID + "' AND Player='" + p + "'");
                try {
                    PreparedStatement preparedStatement = mysql.ExecuteCommandPreparedStatement("INSERT INTO KeyData (KeyDataID, PLayer, Lore) VALUES (?,?,?);");
                    preparedStatement.setInt(1, KeyDataID);
                    preparedStatement.setString(2, p);
                    preparedStatement.setString(3, Lore);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    mysql.Disconnect();
                    if (autoConnectAndAutoDisconnect) {
                        mysql.Disconnect();
                    }
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    //1
    public void ReplaceKey(ISQL mysql, int KeyDataID, Player p, String Lore) {
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
    public void ReplaceKey(ISQL mysql, int KeyDataID, String p, String Lore) {
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