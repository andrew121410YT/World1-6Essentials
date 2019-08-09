package World16Elevators.Objects;

import World16.Main.Main;
import World16.Utils.SimpleMath;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Collection;

public class ElevatorObject {

    private String name;
    private int floor;

    private Location middleOfShaft;
    private Location locationDOWN;
    private Location locationUP;

    private FloorObject floorObject;

    //TEMP
    private Main plugin;
    private SimpleMath simpleMath;
    private ArmorStand armorStand;

    public ElevatorObject(Main plugin, String name, int floor, Location middleOfShaft, Location locationDOWN, Location locationUP) {
        this.plugin = plugin;
        this.simpleMath = new SimpleMath(this.plugin);

        this.name = name;
        this.floor = floor;
        this.middleOfShaft = middleOfShaft;
        this.locationDOWN = locationDOWN;
        this.locationUP = locationUP;

        this.floorObject = new FloorObject(floor, middleOfShaft, null);
    }

    public Collection<Entity> getEntities() {
        return simpleMath.getEntitiesInAABB(locationUP.toVector(), locationDOWN.toVector());
    }

    public void goToFloor(int floor) {
        WorldEditPlugin worldEditPlugin = plugin.getOtherPlugins().getWorldEditPlugin();

        World world = BukkitAdapter.adapt(this.plugin.getServer().getWorld("world"));
        BlockVector3 blockVector3L = BukkitAdapter.asBlockVector(locationDOWN);
        BlockVector3 blockVector3U = BukkitAdapter.asBlockVector(locationUP);

        CuboidRegion cuboidRegion = new CuboidRegion(blockVector3L, blockVector3U);

        EditSession editSession = worldEditPlugin.getWorldEdit().getEditSessionFactory().getEditSession(world, -1);
        cuboidRegion.get
    }

    public void armorStandSetup() {
        boolean found = false;
        for (Entity entity : getEntities()) {
            if (entity.getType() == EntityType.ARMOR_STAND) {
                this.armorStand = (ArmorStand) entity;
                found = true;
            }
        }

        if (!found) {
            System.out.println("ARMOR STAND NOT FOUND.");
        }

        if (armorStand == null) {
            Entity entity = middleOfShaft.getWorld().spawnEntity(middleOfShaft, EntityType.ARMOR_STAND);
            this.armorStand = (ArmorStand) entity;
        }

        this.armorStand.setGravity(false);

        this.armorStand.setCustomName(name);
        this.armorStand.setCustomNameVisible(true);

        this.armorStand.setVisible(true);
    }

    //GETTERS
}
