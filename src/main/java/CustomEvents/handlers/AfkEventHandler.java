package CustomEvents.handlers;

import CustomEvents.Events.AfkEvent;
import Main.Main;

public class AfkEventHandler {

    private static Main plugin = Main.getPlugin();

    public AfkEventHandler(String p) {
        AfkEvent event = new AfkEvent(p);

        plugin.getServer().getPluginManager().callEvent(event);
    }
}
