package CustomEvents.handlers;

import CustomEvents.Events.UnAfkEvent;
import Main.Main;

public class UnAfkEventHandler {

    private static Main plugin = Main.getPlugin();

    public UnAfkEventHandler(String p) {
        UnAfkEvent event = new UnAfkEvent(p);

        plugin.getServer().getPluginManager().callEvent(event);
    }
}
