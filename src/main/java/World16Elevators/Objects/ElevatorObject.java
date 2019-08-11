package World16Elevators.Objects;

import World16.Main.Main;
import World16.Utils.SimpleMath;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@SerializableAs("ElevatorObject")
public class ElevatorObject implements ConfigurationSerializable {

    private String name;
    private int floor;
    private Location locationDOWN;
    private Location locationUP;
    private String world;

    private Location atDoor;

    private Map<Integer, FloorObject> floorsMap;

    //Config
    private final long ticksPerSecond = 6L;
    private final long doorHolderTicksPerSecond = 20L * 5L;
    private final long elevatorWaiterTicksPerSecond = 20L * 6L;
    //...

    //TEMP DON'T SAVE
    private Main plugin;
    private SimpleMath simpleMath;
    private ArmorStand armorStand;

    private Boolean isGoing;
    private Boolean isFloorQueueGoing;
    private Queue<Integer> floorQueue;
    private Boolean isWaiting;
    private Boolean isEmergencyStop;

    public ElevatorObject(Main plugin, String world, String nameOfElevator, FloorObject mainFloor) {
        if (plugin != null) {
            this.plugin = plugin;
        }
        this.floorsMap = new HashMap<>();
        this.simpleMath = new SimpleMath(this.plugin);

        this.name = nameOfElevator;
        this.world = world;

        this.floor = mainFloor.getFloor();
        this.atDoor = mainFloor.getAtDoor();
        this.locationDOWN = mainFloor.getBoundingBox().getVectorDOWN().toLocation(getBukkitWorld());
        this.locationUP = mainFloor.getBoundingBox().getVectorUP().toLocation(getBukkitWorld());

        this.isGoing = false;
        this.floorQueue = new LinkedList<>();
        this.isFloorQueueGoing = false;
        this.isWaiting = false;
        this.isEmergencyStop = false;

        this.floorsMap.putIfAbsent(0, mainFloor);
    }

    public ElevatorObject(Main plugin, String world, String nameOfElevator, Integer floor, Location atDoor, Location locationDOWN, Location locationUP) {
        if (plugin != null) {
            this.plugin = plugin;
        }
        this.floorsMap = new HashMap<>();
        this.simpleMath = new SimpleMath(this.plugin);

        this.name = nameOfElevator;
        this.world = world;

        this.floor = floor;
        this.atDoor = atDoor;
        this.locationDOWN = locationDOWN;
        this.locationUP = locationUP;

        this.isGoing = false;
        this.floorQueue = new LinkedList<>();
        this.isFloorQueueGoing = false;
        this.isWaiting = false;
        this.isEmergencyStop = false;
    }

    public Collection<Entity> getEntities() {
        return simpleMath.getEntitiesInAABB(locationDOWN.toVector(), locationUP.toVector());
    }

    public void goToFloor(int floor) {
        //Add to the queue if elevator is running or waiting.
        if (isGoing || isWaiting) {
            floorQueue.add(floor);
            if (!isFloorQueueGoing) {
                setupFloorQueue();
            }
            return;
        }

        isGoing = true;

        //Tell the elevator to go down instead of up.
        if (getFloor(floor).getBoundingBox().getVectorDOWN().getY() < locationDOWN.getY()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    FloorObject floorObject = getFloor(floor);
                    if (floorObject.getBoundingBox().getMidPointOnFloor().getY() == locationDOWN.getY()) {
                        this.cancel();
//                        arrivalChime(floorObject.getAtDoor());
                        openDoor(floor);
                        floorDone();
                        isGoing = false;
                        return;
                    }

                    if (isEmergencyStop) {
                        isWaiting = false;
                        isGoing = false;
                        isEmergencyStop = false;
                        this.cancel();
                        return;
                    }

                    worldEditMoveDOWN(floor, false);

                    //TP THEM DOWN 1
                    for (Entity entity : getEntities()) {
                        entity.teleport(entity.getLocation().add(0, -1, 0));
                    }

                }
            }.runTaskTimer(plugin, ticksPerSecond, ticksPerSecond);
            return;
        }

        //Tell the elevator to go up instead of down.
        new BukkitRunnable() {
            @Override
            public void run() {
                FloorObject floorObject = getFloor(floor);
                if (floorObject.getBoundingBox().isInAABB(locationDOWN.toVector())) {
                    this.cancel();
//                    arrivalChime(floorObject.getAtDoor());
                    openDoor(floor);
                    floorDone();
                    isGoing = false;
                    return;
                }

                if (isEmergencyStop) {
                    isWaiting = false;
                    isGoing = false;
                    isEmergencyStop = false;
                    this.cancel();
                    return;
                }

                worldEditMoveUP(floor, false);

                //TP THEM UP 1
                for (Entity entity : getEntities()) {
                    entity.teleport(entity.getLocation().add(0, 1, 0));
                }

            }
        }.runTaskTimer(plugin, ticksPerSecond, ticksPerSecond);
    }

    private void worldEditMoveUP(int floor, boolean debug) {
        WorldEditPlugin worldEditPlugin = plugin.getOtherPlugins().getWorldEditPlugin();

        World world = BukkitUtil.getLocalWorld(plugin.getServer().getWorld("world"));
        Vector vectorD = new Vector(BukkitUtil.toVector(locationDOWN));
        Vector vectorUP = new Vector(BukkitUtil.toVector(locationUP));
        CuboidRegion cuboidRegion = new CuboidRegion(world, vectorD, vectorUP);

        EditSession editSession = worldEditPlugin.getWorldEdit().getEditSessionFactory().getEditSession(world, -1);
        Vector vectorDIR = new Vector(0, 1, 0);
        try {
            editSession.moveRegion(cuboidRegion, vectorDIR, 1, false, null);
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        }
        locationUP.setY(locationUP.getY() + 1);
        locationDOWN.setY(locationDOWN.getY() + 1);
        atDoor.setY(atDoor.getY() + 1);
        if (debug) {
            this.plugin.getServer().broadcastMessage("GOING UP");
            this.plugin.getServer().broadcastMessage("L: X: " + locationDOWN.getBlockX() + " Y:" + locationDOWN.getBlockY() + " Z: " + locationDOWN.getBlockZ());
            this.plugin.getServer().broadcastMessage("L: X: " + locationUP.getBlockX() + " Y:" + locationUP.getBlockY() + " Z: " + locationUP.getBlockZ());
            this.plugin.getServer().broadcastMessage("Vector: X: " + vectorD.getBlockX() + " Y: " + vectorD.getBlockY() + " Z: " + vectorD.getBlockZ());
        }
    }

    private void worldEditMoveDOWN(int floor, boolean debug) {
        WorldEditPlugin worldEditPlugin = plugin.getOtherPlugins().getWorldEditPlugin();

        World world = BukkitUtil.getLocalWorld(plugin.getServer().getWorld("world"));
        Vector vectorD = new Vector(BukkitUtil.toVector(locationDOWN));
        Vector vectorUP = new Vector(BukkitUtil.toVector(locationUP));
        CuboidRegion cuboidRegion = new CuboidRegion(world, vectorD, vectorUP);

        EditSession editSession = worldEditPlugin.getWorldEdit().getEditSessionFactory().getEditSession(world, -1);
        Vector vectorDIR = new Vector(0, -1, 0);
        try {
            editSession.moveRegion(cuboidRegion, vectorDIR, 1, false, null);
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        }
        locationUP.setY(locationUP.getY() - 1);
        locationDOWN.setY(locationDOWN.getY() - 1);
        atDoor.setY(atDoor.getY() - 1);
        if (debug) {
            this.plugin.getServer().broadcastMessage("GOING DOWN:");
            this.plugin.getServer().broadcastMessage("L: X: " + locationDOWN.getBlockX() + " Y:" + locationDOWN.getBlockY() + " Z: " + locationDOWN.getBlockZ());
            this.plugin.getServer().broadcastMessage("L: X: " + locationUP.getBlockX() + " Y:" + locationUP.getBlockY() + " Z: " + locationUP.getBlockZ());
            this.plugin.getServer().broadcastMessage("Vector: X: " + vectorD.getBlockX() + " Y: " + vectorD.getBlockY() + " Z: " + vectorD.getBlockZ());
        }
    }

    public void emergencyStop() {
        this.isEmergencyStop = true;
    }

    private void openDoor(int floor) {
        Material oldBlock = getFloor(floor).getAtDoor().getBlock().getType();
        FloorObject floorObject = getFloor(floor);
        floorObject.getAtDoor().getBlock().setType(Material.REDSTONE_BLOCK);
        new BukkitRunnable() {
            @Override
            public void run() {
                floorObject.getAtDoor().getBlock().setType(oldBlock);
            }
        }.runTaskLater(plugin, doorHolderTicksPerSecond);
    }

    private void floorDone() {
        isWaiting = true;
        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> isWaiting = false, elevatorWaiterTicksPerSecond);
    }

    private void setupFloorQueue() {
        //Don't run if it's running already
        if (isFloorQueueGoing) {
            return;
        }
        isFloorQueueGoing = true;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isGoing && !isWaiting && floorQueue.peek() != null) {
                    goToFloor(floorQueue.peek());
                    floorQueue.remove();
                } else if (floorQueue.isEmpty()) {
                    isFloorQueueGoing = false;
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 40L, 40L);
    }

    private void arrivalChime(Location location) {
        getBukkitWorld().playSound(location, Sound.BLOCK_NOTE_PLING, 10F, 1.8F);
    }

    private void passingChime(Location location) {
        getBukkitWorld().playSound(location, Sound.BLOCK_NOTE_PLING, 10F, 1.3F);
    }

    public void addFloor(FloorObject floorObject) {
        this.floorsMap.putIfAbsent(floorObject.getFloor(), floorObject);
    }

    public FloorObject getFloor(int floor) {
        if (this.floorsMap.get(floor) != null) {
            return this.floorsMap.get(floor);
        }
        return null;
    }

    public void removeFloor(int floor) {
        if (this.floorsMap.get(floor) != null) {
            floorsMap.remove(floor);
        }
    }

    public String listAllFloors() {
        Set<Integer> homeSet = this.floorsMap.keySet();
        Integer[] integers = homeSet.toArray(new Integer[0]);
        Arrays.sort(integers);
        return Arrays.toString(integers);
    }

    public org.bukkit.World getBukkitWorld() {
        return this.plugin.getServer().getWorld(this.world);
    }

    //GETTERS
    public String getName() {
        return name;
    }

    public int getFloor() {
        return floor;
    }

    public Location getLocationDOWN() {
        return locationDOWN;
    }

    public Location getLocationUP() {
        return locationUP;
    }

    public String getWorld() {
        return world;
    }

    public Location getAtDoor() {
        return atDoor;
    }

    public Map<Integer, FloorObject> getFloorsMap() {
        return floorsMap;
    }

    public long getTicksPerSecond() {
        return ticksPerSecond;
    }

    public long getDoorHolderTicksPerSecond() {
        return doorHolderTicksPerSecond;
    }

    public long getElevatorWaiterTicksPerSecond() {
        return elevatorWaiterTicksPerSecond;
    }

    public Boolean isGoing() {
        return isGoing;
    }

    public Boolean isFloorQueueGoing() {
        return isFloorQueueGoing;
    }

    public Queue<Integer> getFloorQueue() {
        return floorQueue;
    }

    public Boolean isWaiting() {
        return isWaiting;
    }

    public Boolean isEmergencyStop() {
        return isEmergencyStop;
    }

    public Main getPlugin() {
        return plugin;
    }

    public void setPlugin(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("floor", floor);
        map.put("locationDOWN", locationDOWN);
        map.put("locationUP", locationUP);
        map.put("world", world);
        map.put("atDoor", atDoor);
        return map;
    }

    public static ElevatorObject deserialize(Map<String, Object> map) {
        return new ElevatorObject(null, (String) map.get("world"), (String) map.get("name"), (int) map.get("floor"), (Location) map.get("atDoor"), (Location) map.get("locationDOWN"), (Location) map.get("locationUP"));
    }
}
