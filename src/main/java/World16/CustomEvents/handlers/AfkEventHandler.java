package World16.CustomEvents.handlers;

import World16.CustomEvents.Events.AfkEvent;
import World16.Main.Main;

public class AfkEventHandler {

    private static Main plugin = Main.getPlugin();

    public AfkEventHandler(String p) {
        AfkEvent event = new AfkEvent(p);

        plugin.getServer().getPluginManager().callEvent(event);
    }
}
