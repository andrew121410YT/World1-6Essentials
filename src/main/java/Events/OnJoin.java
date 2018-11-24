package Events;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import MysqlAPI.MySQL;
import Translate.Translate;
import Utils.KeyAPI;
import World16.World16.World16.Main;

public class OnJoin implements Listener {

  private Main plugin;
  
  public static HashMap<String, String> keydatam = new HashMap<String, String>();
  MySQL mysql = new MySQL();
  KeyAPI keyapi = new KeyAPI();

  public OnJoin(Main plugin) {
    this.plugin = plugin;

    Bukkit.getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player p = event.getPlayer();

    if (p.getDisplayName().equals("andrew121410")) {
      p.sendMessage(Translate.chat("&4Hello, Owner."));
    }
    if (p.getDisplayName().equals("AlphaGibbon43")) {
      p.sendMessage(Translate.chat("&4Hello, Owner."));
    }
    event.setJoinMessage("");

    Bukkit.broadcastMessage(Translate.chat("[&9World1-6&r] &6Welcome Back! " + p.getDisplayName()));
    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10.0f, 1.0f);
    version(p);
    
    //GET KEY DATA
    String keydata = keyapi.giveKeyreturn(p, mysql);
    keydatam.put("1", keydata);
    p.sendMessage(keydata);
  }

  public void version(Player p) {
    p.sendMessage(Translate.chat("&4World1-6Ess Last Time Updated Was 11/9/2018"));
  }
}
