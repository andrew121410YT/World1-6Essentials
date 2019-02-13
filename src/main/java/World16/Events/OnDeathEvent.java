package World16.Events;

import World16.Commands.back;
import World16.Main.Main;
import World16.Objects.LocationObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.LinkedHashMap;
import java.util.Map;

public class OnDeathEvent implements Listener {

    private static Main plugin;

    //HASHMAPS
    Map<String, LocationObject> backm = back.backm;

    public OnDeathEvent(Main getPlugin) {
        plugin = getPlugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void OnDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();

        backm.get(p.getDisplayName()).setLocation("death", 1, p.getLocation());
    }
}