package World16.CustomEvents.handlers;

import World16.CustomEvents.Events.UnAfkCustomEvent;
import World16.Main.Main;

public class UnAfkEventHandler {

    private static Main plugin = Main.getPlugin();

    public UnAfkEventHandler(String p) {
        UnAfkCustomEvent event = new UnAfkCustomEvent(p);

        plugin.getServer().getPluginManager().callEvent(event);
    }
}
