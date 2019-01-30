package World16.Events;

import World16.Commands.back;
import World16.Main.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.LinkedHashMap;

public class OnTpEvent implements Listener {

    private static Main plugin;
    LinkedHashMap<String, Location> backm = back.backm;

    public OnTpEvent(Main getPlugin) {
        plugin = getPlugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void OnTp(PlayerTeleportEvent event) {
        Player p = event.getPlayer();

        Location to = event.getTo();
        Location from = event.getFrom();

        // only save location if teleporting more than 5 blocks
        if (!to.getWorld().equals(from.getWorld()) || to.distanceSquared(from) > 25) {

            backm.remove(p.getDisplayName() + "tp");
            backm.put(p.getDisplayName() + "tp", from);
        }
    }
}