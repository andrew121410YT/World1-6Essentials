package World16.Events;

import World16.Main.Main;
import World16.Storage.OldMySQL;
import World16.Utils.API;
import World16.Utils.KeyAPI;
import World16.Utils.Translate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnLeaveEvent implements Listener {
    private static Main plugin;
    KeyAPI keyAPI = new KeyAPI();
    API api = new API();
    OldMySQL mySQL = new OldMySQL();

    public OnLeaveEvent(Main getPlugin) {
        plugin = getPlugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQUIT(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        keyAPI.getAllKeysFromRamAndStoreItInMySql(mySQL, p.getDisplayName());

        new BukkitRunnable() {
            @Override
            public void run() {
                //CLEAR ARRAYLIST AND HASHMAPS ETC
                api.clearListAndMaps(p);
            }
        }.runTaskLaterAsynchronously(plugin, 20 * 3);

        event.setQuitMessage("");
        Bukkit.broadcastMessage(Translate.chat(API.PREFIX + " &5Bye Bye, " + p.getDisplayName()));

    }
}
