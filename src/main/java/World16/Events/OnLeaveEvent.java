package World16.Events;

import World16.Main.Main;
import World16.Translate.Translate;
import World16.Utils.API;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnLeaveEvent implements Listener {
    private Main plugin;
    API api = new API();

    public OnLeaveEvent(Main getPlugin) {
        this.plugin = getPlugin;

        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQUIT(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        //CLEAR ARRAYLIST AND HASHMAPS ETC
        api.clearArrayListAndHashMaps(p);

        event.setQuitMessage("");
        Bukkit.broadcastMessage(Translate.chat(api.PREFIX+" &5Bye Bye, " + p.getDisplayName()));

    }
}
