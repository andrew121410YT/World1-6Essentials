package World16.CustomEvents.Events;

import World16.Main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TpaCustomEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private static Main plugin = Main.getPlugin();

    String p;
    String target;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    //CODE STARTS HERE

    public TpaCustomEvent(String p, String target) {
        this.p = p;
        this.target = target;
    }

    public String getPlayerName() {
        return this.p;
    }

    public Player getPlayer() {
        return plugin.getServer().getPlayerExact(p);
    }

    public Main getPlugin() {
        return plugin;
    }

    public String getTargetName() {
        return this.target;
    }

    public Player getTargetPlayer() {
        if (target != null) {
            return plugin.getServer().getPlayerExact(target);
        }
        return null;
    }
}
