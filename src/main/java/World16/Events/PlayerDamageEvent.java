package World16.Events;

import World16.Commands.god;
import World16.Main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

public class PlayerDamageEvent implements Listener {

    private static Main plugin;

    ArrayList<String> godm = god.godm;

    public PlayerDamageEvent(Main getPlugin) {
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
