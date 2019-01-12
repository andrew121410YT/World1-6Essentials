package Events;

import Main.Main;
import Translate.Translate;
import Utils.API;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnLeave implements Listener {
    private Main plugin;
    API api = new API();

    public OnLeave(Main getPlugin) {
        this.plugin = getPlugin;
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onQUIT(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        //CLEAR ARRAYLIST AND HASHMAPS ETC
        api.clearArrayListandHashMapsWithName(p);

        event.setQuitMessage("");
        Bukkit.broadcastMessage(Translate.chat(api.PREFIX+" &5Bye Bye, " + p.getDisplayName()));

    }
}
