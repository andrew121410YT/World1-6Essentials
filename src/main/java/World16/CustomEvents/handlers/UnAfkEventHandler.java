package World16.CustomEvents.handlers;

import World16.CustomEvents.Events.UnAfkCustomEvent;
import World16.Main.Main;

public class UnAfkEventHandler {

    public UnAfkEventHandler(Main plugin, String p) {
        UnAfkCustomEvent event = new UnAfkCustomEvent(plugin, p);

        plugin.getServer().getPluginManager().callEvent(event);
    }
}
