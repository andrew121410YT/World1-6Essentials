package World16.CustomEvents.handlers;

import World16.CustomEvents.Events.AfkCustomEvent;
import World16.Main.Main;

public class AfkEventHandler {

    private static Main plugin = Main.getPlugin();

    public AfkEventHandler(String p) {
        AfkCustomEvent event = new AfkCustomEvent(p);

        plugin.getServer().getPluginManager().callEvent(event);
    }
}
