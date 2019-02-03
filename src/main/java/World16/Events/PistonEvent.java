package World16.Events;

import World16.Main.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

public class PistonEvent implements Listener {

    /**
     *
     *
     * THIS CLASS IS NOT BEING USED YET.
     *
     *
     */



    private static Main plugin;

    public PistonEvent(Main getPlugin) {
        plugin = getPlugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void ExtendEvent(BlockPistonExtendEvent event) {
        if (event.getBlock().getLocation().add(0, 1, 0).getBlock().getType() == Material.HAY_BLOCK && event.getBlock().getLocation().add(0, 2, 0).getBlock().getType() == Material.SPONGE && event.getBlock().getLocation().add(0, 3, 0).getBlock().getType() == Material.HAY_BLOCK) {
//            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0, 5, 0), new ItemStack(Material.DIAMOND_BLOCK));
        }
    }
}