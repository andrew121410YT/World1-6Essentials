package Utils;

import Commands.afk;
import Commands.fly;
import Events.OnJoin;
import Translate.Translate;
import World16.World16.World16.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;

public class API {

    HashMap<String, String> keyDataM = OnJoin.keyDataM;
    ArrayList<String> Afk1 = afk.Afk;
    ArrayList<String> Fly1 = fly.Fly;

    private static Plugin plugin = Main.plugin;

    CustomYmlManger yml = new CustomYmlManger();

    // FOR MYSQL
    public String HOST = plugin.getConfig().getString("MysqlHOST");
    public String DATABASE = plugin.getConfig().getString("MysqlDATABASE");
    public String USER = plugin.getConfig().getString("MysqlUSER");
    public String PASSWORD = plugin.getConfig().getString("MysqlPASSWORD");
    public String PORT = plugin.getConfig().getString("MysqlPORT");
    // END MYSQL

    // MAIN
    public API() {

    }

    // END MAIN
    // START OF MYSQL

    public String getHOST() {
        return HOST;
    }

    public String getDATABASE() {
        return DATABASE;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getPORT() {
        return PORT;
    }
    // END OF MYSQL

    public boolean isAfk(Player p) {
        if (Afk1.contains(p.getDisplayName())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFlying(Player p) {
        if (Fly1.contains(p.getDisplayName()) || p.isFlying()) {
            return true;
        } else {
            return false;
        }
    }

    public void clearArrayListandHashMapsWithName(Player p) {
        keyDataM.remove(p.getDisplayName());
        this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat(this.USELESS + " Class: Utils.API has cleared the HashMap of Events.OnJoin.keyDataM For Player: " + p.getDisplayName()));
        Afk1.remove(p.getDisplayName());
        this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat(this.USELESS + " Class: Utils.API has cleared the ArrayList of Commands.afk.Afk For Player: " + p.getDisplayName()));
        Fly1.remove(p.getDisplayName());
        this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat(this.USELESS + " Class: Utils.API has cleared the ArrayList of Commands.fly.Fly For Player: " + p.getDisplayName()));
    }

    public void clearArrayListandHashMaps() {
        keyDataM.clear();
        this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat(this.USELESS + " Class: Utils.API has cleared the HashMap of Events.OnJoin.keyDataM For EVERY PLAYER"));
        Afk1.clear();
        this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat(this.USELESS + " Class: Utils.API has cleared the ArrayList of Commands.afk.Afk For EVERY PLAYER"));
        Fly1.clear();
        this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat(this.USELESS + " Class: Utils.API has cleared the ArrayList of Commands.fly.Fly For EVERY PLAYER"));
    }

    public void clearAllHahsMapsWithName(Player p) {
        keyDataM.remove(p.getDisplayName());
        this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat(this.USELESS + " Class: Utils.API has cleared the HashMap of Events.OnJoin.keyDataM For Player: " + p.getDisplayName()));
    }

    public void clearAllArrayLists() {
        Afk1.clear();
        this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat(this.USELESS + " Class: Utils.API has cleared the ArrayList of Commands.afk.Afk For EVERY PLAYER"));
        Fly1.clear();
        this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat(this.USELESS + " Class: Utils.API has cleared the ArrayList of Commands.fly.Fly For EVERY PLAYER"));
    }

    public void clearAllHashMaps() {
        keyDataM.clear();
        this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat(this.USELESS + " Class: Utils.API has cleared the HashMap of Events.OnJoin.keyDataM For EVERY PLAYER"));
    }

    public void PermissionErrorMessage(Player p) {
        p.sendMessage(
                Translate.chat(this.PREFIX + " &cYou Do Not Have Permission To Use This Command."));
    }

    public static final String PREFIX = "[&9World1-6Ess&r]";
    public static final String USELESS = "" + PREFIX + "->[&bUSELESS&r]";
}
