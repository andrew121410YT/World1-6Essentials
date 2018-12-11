package CustomEvents.handlers;

import CustomEvents.Events.AfkEvent;
import World16.World16.World16.Main;
import org.bukkit.entity.Player;

public class AfkEventHandler {

    private static Main plugin = Main.plugin;

    public AfkEventHandler(Player p) {

        AfkEvent event = new AfkEvent(p);

        plugin.getServer().getPluginManager().callEvent(event);
    }
}
