package World16.Events;

import World16.Main.Main;
import World16.Objects.LocationObject;
import World16.Utils.SetListMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Map;
import java.util.UUID;

public class OnPlayerDeathEvent implements Listener {

    private static Main plugin;

    //Maps
    Map<UUID, LocationObject> backm = SetListMap.backM;
    //...

    public OnPlayerDeathEvent(Main getPlugin) {
        plugin = getPlugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void OnDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();

        if (backm.get(p.getUniqueId()) != null) {
            backm.get(p.getUniqueId()).setLocation("death", 1, p.getLocation());
        }
    }
}