package World16.Events;

import World16.Main.Main;
import World16.Utils.API;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class OnVehicleMoveEvent implements Listener {

    private Main plugin;
    private API api;

    public OnVehicleMoveEvent(Main plugin) {
        this.plugin = plugin;
        this.api = this.plugin.getApi();

        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void OnVehicleMove(VehicleMoveEvent event) {

        if (event.getVehicle() instanceof Minecart) {
            Minecart cart = (Minecart) event.getVehicle();

            Location cartLocation = cart.getLocation();
            World cartsWorld = cart.getWorld();
            Block rail = cartsWorld.getBlockAt(cartLocation);

            Block redstoneBlock = rail.getRelative(BlockFace.DOWN);
            Block slimeBlock = redstoneBlock.getRelative(BlockFace.DOWN);

            if (rail.getType() == Material.POWERED_RAIL && redstoneBlock.getType() == Material.REDSTONE_BLOCK && slimeBlock.getType() == Material.SLIME_BLOCK) {
                cart.setMaxSpeed(1.50D);
                cart.setVelocity(cart.getVelocity().multiply(1.50D));
            }
        }


    }
}
