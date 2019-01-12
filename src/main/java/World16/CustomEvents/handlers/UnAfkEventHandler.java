package World16.CustomEvents.handlers;

import World16.CustomEvents.Events.UnAfkEvent;
import World16.Main.Main;

public class UnAfkEventHandler {

    private static Main plugin = Main.getPlugin();

    public UnAfkEventHandler(String p) {
        UnAfkEvent event = new UnAfkEvent(p);

        plugin.getServer().getPluginManager().callEvent(event);
    }
}
