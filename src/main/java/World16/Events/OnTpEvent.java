package World16.Events;

import World16.Main.Main;
import World16.Objects.LocationObject;
import World16.Utils.SetListMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Map;

public class OnTpEvent implements Listener {

    private Main plugin;

    Map<String, LocationObject> backm = SetListMap.backM;

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
        if (backm.get(p.getDisplayName()) != null && !to.getWorld().equals(from.getWorld()) || to.distanceSquared(from) > 25) {

            backm.computeIfAbsent(p.getDisplayName(), k -> new LocationObject());

            backm.get(p.getDisplayName()).setLocation("tp", 2, from);
        }
    }
}