package World16.Events;

import World16.Main.Main;
import java.util.LinkedHashMap;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class OnTpEvent implements Listener {

  private static Main plugin;
  public static LinkedHashMap<String, Location> backmap = new LinkedHashMap<>();

  public OnTpEvent(Main getPlugin) {
    this.plugin = getPlugin;

    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }

  @EventHandler
  public void OnTp(PlayerTeleportEvent event) {
    Player p = event.getPlayer();
    backmap.remove(p.getDisplayName() + "tp");

    double x = p.getLocation().getX();
    double y = p.getLocation().getY();
    double z = p.getLocation().getZ();
    float yaw = p.getLocation().getYaw();
    float pitch = p.getLocation().getPitch();
    World world = p.getWorld();
    Location location = new Location(world, x, y, z, yaw, pitch);
    backmap.put(p.getDisplayName() + "tp", location);
  }
}
