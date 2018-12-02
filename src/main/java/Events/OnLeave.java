package Events;

import World16.World16.World16.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import Translate.Translate;

import java.util.HashMap;

public class OnLeave implements Listener {

    private static HashMap<String, String> keyDataM = OnJoin.keyDatam;
    private Main plugin;

    public OnLeave(World16.World16.World16.Main getPlugin){
        this.plugin = getPlugin;
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }
    @EventHandler
    public void onQUIT(PlayerQuitEvent event){
        Player p = event.getPlayer();

        keyDataM.remove(p.getDisplayName());
        this.plugin.getLogger().info("Class: Events.OnLeave has cleared the HashMap of " + p.getDisplayName());

        event.setQuitMessage("");
        Bukkit.broadcastMessage(Translate.chat("[&cWorld1-6&r] &5Bye Bye, " + p.getDisplayName()));

    }
}
