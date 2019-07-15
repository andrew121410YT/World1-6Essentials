package World16.Events;

import World16.Main.Main;
import World16.Objects.LocationObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Map;
import java.util.UUID;

public class OnPlayerDeathEvent implements Listener {

    private Main plugin;

    //Maps
    private Map<UUID, LocationObject> backm;
    //...

    public OnPlayerDeathEvent(Main getPlugin) {
        plugin = getPlugin;

        this.backm = this.plugin.getSetListMap().getBackM();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void OnDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();

        if (backm.get(p.getUniqueId()) == null) {
            backm.put(p.getUniqueId(), new LocationObject());
        } else {
            backm.get(p.getUniqueId()).setLocation("death", 1, p.getLocation());
        }
    }
}