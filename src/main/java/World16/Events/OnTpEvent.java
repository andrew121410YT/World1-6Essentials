package World16.Events;

import World16.Commands.back;
import World16.CustomEvents.Events.TpCustomEvent;
import World16.Main.Main;
import java.util.LinkedHashMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class OnTpEvent implements Listener {

  private static Main plugin;
  LinkedHashMap<String, Location> backm = back.backm;

  public OnTpEvent(Main getPlugin) {
    this.plugin = getPlugin;

    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }

  @EventHandler
  public void OnTp(PlayerTeleportEvent event) {
    Player p = event.getPlayer();

    Location to = event.getTo();
    Location from = event.getFrom();

    // only save location if teleporting more than 5 blocks
    if (!to.getWorld().equals(from.getWorld()) || to.distanceSquared(from) > 25) {

      backm.remove(p.getDisplayName() + "tp");
      backm.put(p.getDisplayName() + "tp", from);
    }
  }


  @EventHandler
  public void OnTpCustom(TpCustomEvent event) {
    Player p = event.getPlayer();

    backm.remove(p.getDisplayName() + "tp");
    backm.put(p.getDisplayName() + "tp", p.getLocation());
  }
}