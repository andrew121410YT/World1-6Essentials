package World16.Events;

import World16.Commands.back;
import World16.Main.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;
import java.util.Map;

public class OnDeathEvent implements Listener {

    private static Main plugin;

    //HASHMAPS
    Map<String, List<Location>> backm = back.backm;

    public OnDeathEvent(Main getPlugin) {
        plugin = getPlugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void OnDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();

        backm.get(p.getDisplayName()).set(0, p.getLocation());
    }
}