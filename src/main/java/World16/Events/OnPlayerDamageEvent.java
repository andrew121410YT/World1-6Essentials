package World16.Events;

import World16.Main.Main;
import World16.Utils.SetListMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class OnPlayerDamageEvent implements Listener {

    private static Main plugin;

    List<String> godm = SetListMap.godmList;

    public OnPlayerDamageEvent(Main getPlugin) {
        plugin = getPlugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void eventdamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) event.getEntity();

        if (godm.contains(p.getDisplayName())) {
            event.setCancelled(true);
        } else {
            return;
        }
    }

    @EventHandler
    public void eventdamageby(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) event.getEntity();

        if (godm.contains(p.getDisplayName())) {
            event.setCancelled(true);
        } else {
            return;
        }
    }
}
