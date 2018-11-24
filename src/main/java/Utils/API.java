package Utils;

import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import Commands.afk;
import Commands.fly;
import Translate.Translate;
import World16.World16.World16.Main;

public class API {

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

  public void setHOST(String hOST) {
    HOST = hOST;
  }

  public String getDATABASE() {
    return DATABASE;
  }

  public void setDATABASE(String dATABASE) {
    DATABASE = dATABASE;
  }

  public String getUSER() {
    return USER;
  }

  public void setUSER(String uSER) {
    USER = uSER;
  }

  public String getPASSWORD() {
    return PASSWORD;
  }

  public void setPASSWORD(String pASSWORD) {
    PASSWORD = pASSWORD;
  }

  public String getPORT() {
    return PORT;
  }

  public void setPORT(String pORT) {
    PORT = pORT;
  }
  // END OF MYSQL

  public boolean isAfk(Player playerDONE) {
    if (Afk1.contains(playerDONE)) {
      return true;
    }
    // if (Afk1.contains(p.getDisplayName())) { return true; }
    return false;
  }

  public boolean isFlying(Player p) {
    if (Fly1.contains(p.getDisplayName()) || p.isFlying()) {
      return true;
    }
    return false;
  }



  public void PermissionErrorMessage(Player p) {
    p.sendMessage(
        Translate.chat("[&9World1-6&r] &cYou Do Not Have Permission To Use This Command."));
  }
}
