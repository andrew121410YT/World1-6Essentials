package World16.Events;

import World16.Main.Main;
import java.util.LinkedHashMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnDeathEvent implements Listener {

  private static Main plugin;
  public static LinkedHashMap<String, Location> backmap = new LinkedHashMap<>();

  public OnDeathEvent(Main getPlugin) {
    this.plugin = getPlugin;

    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }

  @EventHandler
  public void OnDeath(PlayerDeathEvent event) {
    Player p = event.getEntity();

    backmap.remove(p.getDisplayName() + "death");

    backmap.put(p.getDisplayName() + "death", p.getLocation());
  }
}