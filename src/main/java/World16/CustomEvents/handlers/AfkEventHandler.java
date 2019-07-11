package World16.CustomEvents.handlers;

import World16.CustomEvents.Events.AfkCustomEvent;
import World16.Main.Main;

public class AfkEventHandler {

    public AfkEventHandler(Main plugin, String p) {
        AfkCustomEvent event = new AfkCustomEvent(plugin, p);

        plugin.getServer().getPluginManager().callEvent(event);
    }
}
