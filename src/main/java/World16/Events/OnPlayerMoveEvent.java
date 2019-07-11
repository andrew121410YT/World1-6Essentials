package World16.Events;

import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.SetListMap;
import World16.Utils.Translate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class OnPlayerMoveEvent implements Listener {

    //Lists
    List<String> afkList = SetListMap.afkList;
    //...

    private Main plugin;
    private API api;

    public OnPlayerMoveEvent(Main plugin) {
        this.plugin = plugin;

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onMoveM(PlayerMoveEvent event) {
        Player p = event.getPlayer();

        if (!event.getFrom().equals(event.getTo())) {
            String color = "&7";

            //Checks if player is op if so then change the color to red.
            if (p.isOp()) {
                color = "&4";
            }
            if (afkList.contains(p.getDisplayName())) {
                afkList.remove(p.getDisplayName());
                this.plugin.getServer().broadcastMessage(Translate.chat("&7*" + color + " " + p.getDisplayName() + "&r&7 is no longer AFK."));
            }
        }
    }
}
