package CustomEvents.handlers;

import CustomEvents.Events.AfkEvent;
import World16.World16.World16.Main;

public class AfkEventHandler {

    private static Main plugin = Main.getPlugin();

    public AfkEventHandler(String p) {
        AfkEvent event = new AfkEvent(p);

        plugin.getServer().getPluginManager().callEvent(event);
    }
}
