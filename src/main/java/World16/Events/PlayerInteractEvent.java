package World16.Events;

import World16.Main.Main;
import World16.Utils.SetListMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PlayerInteractEvent implements Listener {

    //Maps
    Map<String, Location> latestClickedBlocked = SetListMap.latestClickedBlocked;
    Map<Player, Location[]> sessions = SetListMap.sessions;
    //...

    private Main plugin;

    public PlayerInteractEvent(Main getPlugin) {
        plugin = getPlugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playeract(org.bukkit.event.player.PlayerInteractEvent event) {
        org.bukkit.event.player.PlayerInteractEvent e = event;
        Player p = event.getPlayer();

        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_BLOCK) {
            latestClickedBlocked.remove(p.getDisplayName()); //Removes old block
            latestClickedBlocked.put(p.getDisplayName(), event.getClickedBlock().getLocation());
        }

        /* 102 */
        if (p.getItemInHand() != null) {
            /* 104 */
            ItemStack is = p.getItemInHand();

            /* 106 */
            if (is.getType() == Material.SLIME_BALL) {
                /* 108 */
                if (p.isOp()) {
                    /* 110 */
                    if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                        /* 112 */
                        e.setCancelled(true);

                        /* 114 */
                        if (!sessions.containsKey(p)) {
                            /* 116 */
                            Location[] locs = new Location[2];
                            /* 117 */
                            Location block = e.getClickedBlock().getLocation();
                            /* 118 */
                            locs[0] = block;

                            /* 120 */
                            sessions.put(p, locs);

                            /* 122 */
                            p.sendMessage("§aPosition 1 set (" + block.getBlockX() + ", " + block.getBlockY() + ", " + block.getBlockZ() + ")");
                        } else {
                            /* 126 */
                            Location[] locs = (Location[]) sessions.get(p);

                            /* 128 */
                            Location block = e.getClickedBlock().getLocation();

                            /* 130 */
                            if (locs[0] != block) {
                                /* 132 */
                                ((Location[]) sessions.get(p))[0] = block;

                                /* 134 */
                                p.sendMessage("§aPosition 1 set (" + block.getBlockX() + ", " + block.getBlockY() + ", " + block.getBlockZ() + ")");
                            }

                        }

                    }
                    /* 140 */
                    else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        /* 142 */
                        e.setCancelled(true);

                        /* 144 */
                        if (!sessions.containsKey(p)) {
                            /* 146 */
                            Location[] locs = new Location[2];
                            /* 147 */
                            Location block = e.getClickedBlock().getLocation();
                            /* 148 */
                            locs[1] = block;

                            /* 150 */
                            sessions.put(p, locs);

                            /* 152 */
                            p.sendMessage("§aPosition 2 set (" + block.getBlockX() + ", " + block.getBlockY() + ", " + block.getBlockZ() + ")");
                        } else {
                            /* 156 */
                            Location[] locs = (Location[]) sessions.get(p);

                            /* 158 */
                            Location block = e.getClickedBlock().getLocation();

                            /* 160 */
                            if (locs[1] != block) {
                                /* 162 */
                                ((Location[]) sessions.get(p))[1] = block;

                                /* 164 */
                                p.sendMessage("§aPosition 2 set (" + block.getBlockX() + ", " + block.getBlockY() + ", " + block.getBlockZ() + ")");
                            }
                        }
                    }
                }
            }
        }
    }
}
