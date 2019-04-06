package World16.Events;

import World16.Main.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import java.util.HashMap;
import java.util.Map;

public class PlayerInteractEvent implements Listener {

    private Main plugin;

    public static Map<String, Location> latestClickedBlocked = new HashMap<>();

    public PlayerInteractEvent(Main getPlugin) {
        plugin = getPlugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playeract(org.bukkit.event.player.PlayerInteractEvent event) {
        Player p = event.getPlayer();

        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_BLOCK) {
            latestClickedBlocked.remove(p.getDisplayName()); //Removes old block
            latestClickedBlocked.put(p.getDisplayName(), event.getClickedBlock().getLocation());
        }
    }
}
