package World16.CustomEvents.handlers;

import World16.CustomEvents.Events.TpaCustomEvent;
import World16.Main.Main;

public class TpaEventHandler {

    public TpaEventHandler(Main plugin, String p, String target) {
        TpaCustomEvent event = new TpaCustomEvent(plugin, p, target);

        plugin.getServer().getPluginManager().callEvent(event);
    }
}
