package CustomEvents.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AfkEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    Player p;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    //CODE STARTS HERE

    public AfkEvent(Player p) {
        this.p = p;
    }

    public Player getPlayer() {
        return this.p;
    }
}
