package World16.Events;

import World16.Main.Main;
import World16.Objects.LocationObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Map;
import java.util.UUID;

public class OnPlayerTeleportEvent implements Listener {

    private Main plugin;

    private Map<UUID, LocationObject> backm;

    public OnPlayerTeleportEvent(Main getPlugin) {
        plugin = getPlugin;

        this.backm = this.plugin.getSetListMap().getBackM();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void OnTp(PlayerTeleportEvent event) {
        Player p = event.getPlayer();

        Location to = event.getTo();
        Location from = event.getFrom();

        if (backm.get(p.getUniqueId()) == null) {
            backm.put(p.getUniqueId(), new LocationObject());
        } else {
            // Only save location if teleporting more than 5 blocks.
            if (!to.getWorld().equals(from.getWorld()) || to.distanceSquared(from) > 25) {
                backm.get(p.getUniqueId()).setLocation("tp", 2, from);
            }
        }

    }
}