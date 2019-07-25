package World16.Events;

import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.Translate;
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

//            this.plugin.getServer().broadcastMessage(Translate.chat("&4[VERY OLD]getMaxSpeed: " + cart.getMaxSpeed()));
//            this.plugin.getServer().broadcastMessage(Translate.chat("&4[VERY OLD]getVelocity: " + cart.getVelocity()));

            double oldMaxSpeed = 0.4D;
            double newMaxSpeed = 1.50D;
            double overLimit = 0.500D;

            Location cartLocation = cart.getLocation();
            World cartsWorld = cart.getWorld();
            Block rail = cartsWorld.getBlockAt(cartLocation);

            Block redstoneBlock = rail.getRelative(BlockFace.DOWN);
            Block slimeBlock = redstoneBlock.getRelative(BlockFace.DOWN);

            double temp = 0;

            if (cart.getVelocity().getX() > 0) temp = cart.getVelocity().getX();
            if (cart.getVelocity().getY() > 0) temp = cart.getVelocity().getY();
            if (cart.getVelocity().getZ() > 0) temp = cart.getVelocity().getZ();

            if (cart.getVelocity().getX() < 0) temp = cart.getVelocity().getX();
            if (cart.getVelocity().getY() < 0) temp = cart.getVelocity().getY();
            if (cart.getVelocity().getZ() < 0) temp = cart.getVelocity().getZ();

            if (temp < 0) {
                temp = Math.abs(temp);
            }

            if (rail.getType() == Material.POWERED_RAIL && redstoneBlock.getType() == Material.REDSTONE_BLOCK && slimeBlock.getType() == Material.SLIME_BLOCK) {
                this.plugin.getServer().broadcastMessage("[OLD]getMaxSpeed: " + cart.getMaxSpeed());
                this.plugin.getServer().broadcastMessage("[OLD]getVelocity: " + cart.getVelocity());
                this.plugin.getServer().broadcastMessage("TEMP: " + temp);
                if (temp < 0.500D) {
                    this.plugin.getServer().broadcastMessage(Translate.chat("&6IF statement ran"));
                    cart.setMaxSpeed(1.50D);
                    cart.setVelocity(cart.getVelocity().multiply(1.50D));
                }
                this.plugin.getServer().broadcastMessage(Translate.chat("&b[NEW]getMaxSpeed: " + cart.getMaxSpeed()));
                this.plugin.getServer().broadcastMessage(Translate.chat("&b[NEW]getVelocity: " + cart.getVelocity()));
            }
        }


    }
}
