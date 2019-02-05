package World16.Events;

import World16.Main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

public class PistonEvent implements Listener {

    /**
     * THIS CLASS IS NOT BEING USED YET.
     */


    private static Main plugin;

    private boolean isUse = false;

    public PistonEvent(Main getPlugin) {
        plugin = getPlugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void ExtendEvent(BlockPistonExtendEvent event) {

        final Block GetBlock = event.getBlock();
        final Location GetBlockAndGetLocation = event.getBlock().getLocation();

        if (event.getBlock().getLocation().add(0, 1, 0).getBlock().getType() == Material.HAY_BLOCK && event.getBlock().getLocation().add(0, 2, 0).getBlock().getType() == Material.SPONGE && event.getBlock().getLocation().add(0, 3, 0).getBlock().getType() == Material.HAY_BLOCK) {
        }
    }
}