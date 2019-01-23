package World16.Events;

import World16.Commands.back;
import World16.CustomEvents.Events.TpaCustomEvent;
import World16.Main.Main;
import World16.Utils.API;
import java.util.LinkedHashMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnTpaEvent implements Listener {

  private static Main plugin;
  API api = new API();

  //HASHMAPS
  LinkedHashMap<String, Location> backm = back.backm;

  public OnTpaEvent(Main getPlugin) {
    this.plugin = getPlugin;

    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }

  @EventHandler
  public void OnTpa(TpaCustomEvent event) {
    Player p = event.getPlayer();

    backm.remove(p.getDisplayName() + "tp");
    backm.put(p.getDisplayName() + "tp", p.getLocation());
  }

//  public void ClearBackM(Player p) {
//    backm.remove(p.getDisplayName() + "tp");
//    backm.remove(p.getDisplayName() + "death");
//    backm.remove(p.getDisplayName() + "set");
//    backm.remove(p.getDisplayName());
}