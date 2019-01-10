package CustomEvents.handlers;

import CustomEvents.Events.UnAfkEvent;
import World16.World16.World16.Main;

public class UnAfkEventHandler {

    private static Main plugin = Main.getPlugin();

    public UnAfkEventHandler(String p) {
        UnAfkEvent event = new UnAfkEvent(p);

        plugin.getServer().getPluginManager().callEvent(event);
    }
}
