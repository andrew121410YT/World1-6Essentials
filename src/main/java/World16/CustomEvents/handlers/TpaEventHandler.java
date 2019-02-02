package World16.CustomEvents.handlers;

import World16.CustomEvents.Events.TpaCustomEvent;
import World16.Main.Main;

public class TpaEventHandler {

    private static Main plugin = Main.getPlugin();

    public TpaEventHandler(String p, String target) {
        TpaCustomEvent event = new TpaCustomEvent(p, target);

        plugin.getServer().getPluginManager().callEvent(event);
    }
}
