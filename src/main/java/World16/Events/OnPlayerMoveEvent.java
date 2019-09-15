package World16.Events;

import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.Translate;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class OnPlayerMoveEvent implements Listener {

    //Lists
    private Map<UUID, Location> afkMap;
    //...

    private Main plugin;
    private API api;

    public OnPlayerMoveEvent(Main plugin) {
        this.plugin = plugin;
        this.api = new API(this.plugin);

        this.afkMap = this.plugin.getSetListMap().getAfkMap();

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        afkChecker();
    }

    @EventHandler
    public void onMoveM(PlayerMoveEvent event) {
        Player p = event.getPlayer();
    }

    private void afkChecker() {

        //Checks if the player moves.
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<UUID, Location> entry : afkMap.entrySet()) {
                    UUID k = entry.getKey();
                    Location v = entry.getValue();
                    Player p = plugin.getServer().getPlayer(k);
                    String color = "&7";

                    //Checks if player is op if so then change the color to red.
                    if (p.isOp()) {
                        color = "&4";
                    }

                    //Only if the player moves more then 3 blocks.
                    if (p.getLocation().distanceSquared(v) > 9) {
                        afkMap.remove(k);
                        plugin.getServer().broadcastMessage(Translate.chat("&7*" + color + " " + p.getDisplayName() + "&r&7 is no longer AFK."));
                    }
                }
            }
        }.runTaskTimer(this.plugin, 40L, 40L);
    }
}
