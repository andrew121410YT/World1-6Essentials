package World16.Events;

import World16.Main.Main;
import World16.Utils.SetListMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import java.util.Map;

public class PlayerInteractEvent implements Listener {

    //Maps
    Map<String, Location> latestClickedBlocked = SetListMap.latestClickedBlocked;
    //...

    private Main plugin;

    public PlayerInteractEvent(Main getPlugin) {
        plugin = getPlugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playeract(org.bukkit.event.player.PlayerInteractEvent event) {
        org.bukkit.event.player.PlayerInteractEvent e = event;
        Player p = event.getPlayer();

        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_BLOCK) {
            latestClickedBlocked.remove(p.getDisplayName()); //Removes old block
            latestClickedBlocked.put(p.getDisplayName(), event.getClickedBlock().getLocation());
        }
    }
}
