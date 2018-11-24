package Events;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.connorlinfoot.titleapi.TitleAPI;
import Translate.Translate;
import World16.World16.World16.Main;

public class OnJoinTitle implements Listener {

  private Main plugin;

  public OnJoinTitle(Main plugin) {
    this.plugin = plugin;

    Bukkit.getPluginManager().registerEvents(this, plugin);
  }

  FileConfiguration file = Main.plugin.getConfig();

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    TitleAPI.sendTitle(e.getPlayer(), 10, 5 * 20, 10, Translate.chat(file.getString("TittleTOP")),
        Translate.chat(file.getString("TittleBOTTOM")));
    TitleAPI.sendTabTitle(e.getPlayer(), Translate.chat(file.getString("TablistTOP")),
        Translate.chat(file.getString("TablistBOTTOM")));
  }
}
