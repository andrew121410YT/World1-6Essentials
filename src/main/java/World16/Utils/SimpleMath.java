package World16.Utils;

import World16.Main.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleMath {

    //REMINDERS
//    https://www.spigotmc.org/threads/create-particles-in-cube-outline-shape.65991/

    private Main plugin;

    public SimpleMath(Main plugin) {
        this.plugin = plugin;
    }

    public List<Location> getHollowCube(Location corner1, Location corner2) {
        List<Location> result = new ArrayList<Location>();
        World world = corner1.getWorld();
        double minX = Math.min(corner1.getX(), corner2.getX());
        double minY = Math.min(corner1.getY(), corner2.getY());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxX = Math.max(corner1.getX(), corner2.getX());
        double maxY = Math.max(corner1.getY(), corner2.getY());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());

        // 2 areas
        for (double x = minX; x <= maxX; x += 0.2D) {
            for (double z = minZ; z <= maxZ; z += 0.2D) {
                result.add(new Location(world, x, minY, z));
                result.add(new Location(world, x, maxY, z));
            }
        }

        // 2 sides (front & back)
        for (double x = minX; x <= maxX; x += 0.2D) {
            for (double y = minY; y <= maxY; y += 0.2D) {
                result.add(new Location(world, x, y, minZ));
                result.add(new Location(world, x, y, maxZ));
            }
        }

        // 2 sides (left & right)
        for (double z = minZ; z <= maxZ; z += 0.2D) {
            for (double y = minY; y <= maxY; y += 0.2D) {
                result.add(new Location(world, minX, y, z));
                result.add(new Location(world, maxX, y, z));
            }
        }

        return result;
    }

    public List<Location> getHollowCubeNoOutline(Location corner1, Location corner2) {
        List<Location> result = new ArrayList<Location>();
        World world = corner1.getWorld();
        double minX = Math.min(corner1.getX(), corner2.getX());
        double minY = Math.min(corner1.getY(), corner2.getY());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxX = Math.max(corner1.getX(), corner2.getX());
        double maxY = Math.max(corner1.getY(), corner2.getY());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());

        for (double x = minX; x <= maxX; x++) {
            for (double y = minY; y <= maxY; y++) {
                for (double z = minZ; z <= maxZ; z++) {
                    int components = 0;
                    if (x == minX || x == maxX) components++;
                    if (y == minY || y == maxY) components++;
                    if (z == minZ || z == maxZ) components++;
                    if (components >= 2) {
                        result.add(new Location(world, x, y, z));
                    }
                }
            }
        }

        return result;
    }

    public List<Location> getHollowCubeNoOutline(Location corner1, Location corner2, double particleDistance) {
        List<Location> result = new ArrayList<Location>();
        World world = corner1.getWorld();
        double minX = Math.min(corner1.getX(), corner2.getX());
        double minY = Math.min(corner1.getY(), corner2.getY());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxX = Math.max(corner1.getX(), corner2.getX());
        double maxY = Math.max(corner1.getY(), corner2.getY());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());

        for (double x = minX; x <= maxX; x += particleDistance) {
            for (double y = minY; y <= maxY; y += particleDistance) {
                for (double z = minZ; z <= maxZ; z += particleDistance) {
                    int components = 0;
                    if (x == minX || x == maxX) components++;
                    if (y == minY || y == maxY) components++;
                    if (z == minZ || z == maxZ) components++;
                    if (components >= 2) {
                        result.add(new Location(world, x, y, z));
                    }
                }
            }
        }

        return result;
    }

    public Collection<Entity> getEntitiesInAABB(World world, Vector one, Vector two) {
        Vector center = getCenter(world, one, two).toVector();
        center.setY(one.getY());
        Vector radiusA = one.subtract(center);
        Vector radiusB = two.subtract(center);
        this.plugin.getServer().broadcastMessage(Translate.chat("&6RADIUS: X: " + radiusA.getBlockX() + " Y: " + radiusB.getBlockY() + " Z: " + radiusA.getBlockZ()));
        return world.getNearbyEntities(center.toLocation(world), radiusA.getBlockX(), radiusB.getBlockY(), radiusA.getBlockZ()).stream().filter(nearbyEntity -> isInAABBSimple(nearbyEntity.getLocation().toVector(), one, two)).collect(Collectors.toList());
    }

    public boolean isInAABBSimple(Vector entity, Vector one, Vector two) {
        return entity.isInAABB(Vector.getMinimum(one, two), Vector.getMaximum(one, two));
    }

    public Location getCenter(World world, Vector one, Vector two) {
        Vector done = one.getMidpoint(two);
        return new Location(world, done.getX(), done.getY(), done.getZ());
    }
}
