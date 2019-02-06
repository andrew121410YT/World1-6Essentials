package World16.Utils;

import World16.Events.OnJoinEvent;
import World16.Main.Main;
import World16.MysqlAPI.MySQL;
import World16.Objects.KeyObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

public class KeyAPI {

    private static Plugin plugin = Main.getPlugin();

    //Maps
    HashMap<String, KeyObject> keyDatam = OnJoinEvent.keyDataM;
    //...

    // START

    public void giveKeyFromMysql(Player p, MySQL mysql, Integer keyDataID, String playerToGet) { //GIVES DEFAULT ID 1 KEY
        new BukkitRunnable() {

            @Override
            public void run() {
                Material material = Material.TRIPWIRE_HOOK;
                int ammount = 1;
                String name = "Key";

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

    public String giveKeyReturn(String playerName, MySQL mysql, Integer keyDataID) { //RETURNS KEY
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

    public void giveKeyToRam(String playerName, MySQL mysql, Integer keyDataID) { //RETURNS KEY

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
                            switch (Integer.valueOf(KeyID)) {
                                case 1:
                                    keyDatam.get(playerName).setKey1(LoreData);
                                    break;
                                case 2:
                                    keyDatam.get(playerName).setKey2(LoreData);
                                    break;
                                case 3:
                                    keyDatam.get(playerName).setKey3(LoreData);
                                    break;
                                case 4:
                                    keyDatam.get(playerName).setKey4(LoreData);
                                    break;
                                case 5:
                                    keyDatam.get(playerName).setKey5(LoreData);
                                default:
                                    keyDatam.get(playerName).setKey1(LoreData);
                            }
                        } else {
                            plugin.getServer().getConsoleSender().sendMessage(Translate.chat("&4Something went wrong in KeyAPI giveKeyReturnToRam Method."));
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

    public void giveAllKeysToRam(String playerName, MySQL mysql) { //RETURNS KEY

        new BukkitRunnable() {
            @Override
            public void run() {

                mysql.Connect();
//        ResultSet rs =
//                mysql.GetResult("SELECT * FROM KeyData WHERE Player='" + p.getPlayer().getDisplayName() + "';");
                ResultSet rs = mysql.GetResult("SELECT * FROM KeyData WHERE (Player='" + playerName + "')");
                try {
                    if (rs.next()) {
                        String KeyID = rs.getString("KeyDataID");
                        String PlayerData = rs.getString("Player");
                        String LoreData = rs.getString("Lore");
                        if (keyDatam.get(playerName) != null) {
                            switch (Integer.valueOf(KeyID)) {
                                case 1:
                                    keyDatam.get(playerName).setKey1(LoreData);
                                    break;
                                case 2:
                                    keyDatam.get(playerName).setKey2(LoreData);
                                    break;
                                case 3:
                                    keyDatam.get(playerName).setKey3(LoreData);
                                    break;
                                case 4:
                                    keyDatam.get(playerName).setKey4(LoreData);
                                    break;
                                case 5:
                                    keyDatam.get(playerName).setKey5(LoreData);
                                default:
                                    keyDatam.get(playerName).setKey1(LoreData);
                            }
                        } else {
                            plugin.getServer().getConsoleSender().sendMessage(Translate.chat("&4Something went wrong in KeyAPI giveKeyReturnToRam Method."));
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

    public void giveKeyToPlayerFromRam(Player p, Integer keyDataID) { //GETS THE 1 KEY FROM THE MEMORY.
        Material material = Material.TRIPWIRE_HOOK;
        int ammount = 1;
        String name = "Key";

        ItemStack item = new ItemStack(material, ammount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        switch (keyDataID) {
            case 1:
                itemMeta.setLore(Arrays.asList(keyDatam.get(p.getDisplayName()).getKey1()));
                break;
            case 2:
                itemMeta.setLore(Arrays.asList(keyDatam.get(p.getDisplayName()).getKey2()));
                break;
            case 3:
                itemMeta.setLore(Arrays.asList(keyDatam.get(p.getDisplayName()).getKey3()));
                break;
            case 4:
                itemMeta.setLore(Arrays.asList(keyDatam.get(p.getDisplayName()).getKey4()));
                break;
            case 5:
                itemMeta.setLore(Arrays.asList(keyDatam.get(p.getDisplayName()).getKey5()));
                break;
            default:
                itemMeta.setLore(Arrays.asList(keyDatam.get(p.getDisplayName()).getKey1()));
        }
        item.setItemMeta(itemMeta);
        p.getInventory().addItem(new ItemStack(item));
        p.sendMessage(Translate.chat("&aThere You Go."));
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