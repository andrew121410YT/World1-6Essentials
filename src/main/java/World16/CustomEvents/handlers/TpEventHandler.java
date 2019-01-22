package World16.CustomEvents.handlers;

import World16.CustomEvents.Events.TpCustomEvent;
import World16.Main.Main;

public class TpEventHandler {

  private static Main plugin = Main.getPlugin();

  public TpEventHandler(String p) {
    TpCustomEvent event = new TpCustomEvent(p);

    plugin.getServer().getPluginManager().callEvent(event);
  }
}
