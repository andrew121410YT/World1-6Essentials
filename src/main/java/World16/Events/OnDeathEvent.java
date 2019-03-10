package World16.Events;

import World16.Commands.back;
import World16.Main.Main;
import World16.Objects.UserObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Map;

public class OnDeathEvent implements Listener {

    private static Main plugin;

    //Maps
    Map<String, UserObject> backm = back.backm;
    //...

    public OnDeathEvent(Main getPlugin) {
        plugin = getPlugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void OnDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();

        if (backm.get(p.getDisplayName()) == null) return;

        backm.get(p.getDisplayName()).setLocation("death", 1, p.getLocation());
    }
}