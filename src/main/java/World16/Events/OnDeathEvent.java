package World16.Events;

import World16.Commands.back;
import World16.Main.Main;
import java.util.LinkedHashMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnDeathEvent implements Listener {

  private static Main plugin;

  //HASHMAPS
  LinkedHashMap<String, Location> backm = back.backm;

  public OnDeathEvent(Main getPlugin) {
    this.plugin = getPlugin;

    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }

  @EventHandler
  public void OnDeath(PlayerDeathEvent event) {
    Player p = event.getEntity();

    backm.remove(p.getDisplayName() + "death");

    backm.put(p.getDisplayName() + "death", p.getLocation());
  }
}