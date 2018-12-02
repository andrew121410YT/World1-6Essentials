package Events;

import Translate.Translate;
import Utils.API;
import World16.World16.World16.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnLeave implements Listener {
    private Main plugin;
    API api = new API();

    public OnLeave(World16.World16.World16.Main getPlugin) {
        this.plugin = getPlugin;
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onQUIT(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        //CLEAR ARRAYLIST AND HASHMAPS ETC
        api.clearArrayListandHashMaps(p);

        event.setQuitMessage("");
        Bukkit.broadcastMessage(Translate.chat("[&cWorld1-6&r] &5Bye Bye, " + p.getDisplayName()));

    }
}
