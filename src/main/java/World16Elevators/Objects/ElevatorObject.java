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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@SerializableAs("ElevatorObject")
public class ElevatorObject implements ConfigurationSerializable {

    private String elevatorName;
    private Location atDoor;
    private int elevatorFloor;
    private Location locationDOWN;
    private Location locationUP;
    private String world;

    private Map<Integer, FloorObject> floorsMap;

    //Config
    private final long ticksPerSecond = 6L;
    private final long doorHolderTicksPerSecond = 20L * 5L;
    private final long elevatorWaiterTicksPerSecond = 20L * 6L;
    //...

    //TEMP DON'T SAVE
    private Main plugin;
    private SimpleMath simpleMath;

    private Boolean isGoing;
    private Boolean isFloorQueueGoing;
    private Boolean isWaiting;
    private Boolean isEmergencyStop;

    private Queue<Integer> floorQueueBuffer;
    private Queue<Integer> floorBuffer;

    public ElevatorObject(Main plugin, String world, String nameOfElevator, FloorObject mainFloor) {
        if (plugin != null) {
            this.plugin = plugin;
        }
        this.floorsMap = new HashMap<>();
        this.simpleMath = new SimpleMath(this.plugin);

        this.elevatorName = nameOfElevator;
        this.world = world;

        this.elevatorFloor = mainFloor.getFloor();
        this.atDoor = mainFloor.getAtDoor();
        this.locationDOWN = mainFloor.getBoundingBox().getVectorDOWN().toLocation(getBukkitWorld());
        this.locationUP = mainFloor.getBoundingBox().getVectorUP().toLocation(getBukkitWorld());

        this.isGoing = false;
        this.floorQueueBuffer = new LinkedList<>();
        this.isFloorQueueGoing = false;
        this.isWaiting = false;
        this.isEmergencyStop = false;
        this.floorBuffer = new LinkedList<>();

        this.floorsMap.putIfAbsent(0, mainFloor);
    }

    private ElevatorObject(Main plugin, String world, String nameOfElevator, Integer floor, Location atDoor, Location locationDOWN, Location locationUP) {
        if (plugin != null) {
            this.plugin = plugin;
        }
        this.floorsMap = new HashMap<>();
        this.simpleMath = new SimpleMath(this.plugin);

        this.elevatorName = nameOfElevator;
        this.world = world;

        this.elevatorFloor = floor;
        this.atDoor = atDoor;
        this.locationDOWN = locationDOWN;
        this.locationUP = locationUP;

        this.isGoing = false;
        this.floorQueueBuffer = new LinkedList<>();
        this.isFloorQueueGoing = false;
        this.isWaiting = false;
        this.isEmergencyStop = false;
        this.floorBuffer = new LinkedList<>();
    }

    public Collection<Entity> getEntities() {
        return simpleMath.getEntitiesInAABB(locationDOWN.toVector(), locationUP.toVector());
    }

    public Collection<Player> getPlayers() {
        List<Player> playerList = new ArrayList<>();
        for (Entity entity : simpleMath.getEntitiesInAABB(locationDOWN.toVector(), locationUP.toVector())) {
            if (entity instanceof Player) {
                playerList.add((Player) entity);
            }
        }
        return playerList;
    }

    public void goToFloor(int floorNum) {
        boolean goUp;

        //Add to the queue if elevator is running or waiting.
        if (isGoing || isWaiting) {
            floorQueueBuffer.add(floorNum);
            if (!isFloorQueueGoing) {
                setupFloorQueue();
            }
            return;
        }

        isGoing = true;
        floorBuffer.clear(); //Clears the floorBuffer

        //Checks if the elevator should go up or down.
        goUp = !(getFloor(floorNum).getBoundingBox().getVectorDOWN().getY() < locationDOWN.getY());

        FloorObject floorObject = getFloor(floorNum);

        calculateFloorBuffer(floorNum, goUp);
        Integer[] integers = floorBuffer.toArray(new Integer[0]);
        plugin.getServer().broadcastMessage(Arrays.toString(integers));

        //Tell the elevator to go down instead of up.
        if (!goUp) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    //Check's if at floor if so then stop the elvator.
                    if (floorObject.getBoundingBox().getMidPointOnFloor().getY() == locationDOWN.getY()) {
                        this.cancel();
                        elevatorFloor = floorNum;
//                        arrivalChime(floorObject.getAtDoor());
                        openDoor(floorNum);
                        floorDone();
                        isGoing = false;
                        return;
                    }

//                    Stop's the elevator if emergencyStop is on.
                    if (isEmergencyStop) {
                        isWaiting = false;
                        isGoing = false;
                        isEmergencyStop = false;
                        this.cancel();
                        return;
                    }

                    worldEditMoveDOWN(floorNum, false);

                    //TP THEM DOWN 1
                    for (Entity entity : getEntities()) {
                        entity.teleport(entity.getLocation().subtract(0, 1, 0));
                    }

                }
            }.runTaskTimer(plugin, ticksPerSecond, ticksPerSecond);
            return;
        }

        //Tell the elevator to go up instead of down.
        new BukkitRunnable() {
            @Override
            public void run() {
//                Check's if at floor if so then stop the elvator.
                if (floorObject.getBoundingBox().isInAABB(locationDOWN.toVector())) {
                    this.cancel();
                    elevatorFloor = floorNum;
//                    arrivalChime(floorObject.getAtDoor());
                    openDoor(floorNum);
                    floorDone();
                    isGoing = false;
                    return;
                }

//                Stop's the elevator if emergencyStop is on.
                if (isEmergencyStop) {
                    isWaiting = false;
                    isGoing = false;
                    isEmergencyStop = false;
                    this.cancel();
                    return;
                }

                worldEditMoveUP(floorNum, false);

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
        locationUP.add(0, 1, 0);
        locationDOWN.add(0, 1, 0);
        atDoor.add(0, 1, 0);
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
        locationUP.subtract(0, 1, 0);
        locationDOWN.subtract(0, 1, 0);
        atDoor.subtract(0, 1, 0);
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

    private void calculateFloorBuffer(int floor, boolean isUp) {
        //Don't calculate if the floor is non existent
        if (getFloor(floor) == null) {
            return;
        }

        if (isUp) {
            for (int num = this.elevatorFloor; num < floor; num++) {
                if (num != this.elevatorFloor) {
                    floorBuffer.add(num);
                }
            }
            return;
        }
        for (int num = this.elevatorFloor; num > floor; num--) {
            if (num != this.elevatorFloor) {
                floorBuffer.add(num);
            }
        }
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
                if (!isGoing && !isWaiting && floorQueueBuffer.peek() != null) {
                    goToFloor(floorQueueBuffer.peek());
                    floorQueueBuffer.remove();
                } else if (floorQueueBuffer.isEmpty()) {
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
    public String getElevatorName() {
        return elevatorName;
    }

    public Location getAtDoor() {
        return atDoor;
    }

    public int getElevatorFloor() {
        return elevatorFloor;
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

    public Boolean getFloorQueueGoing() {
        return isFloorQueueGoing;
    }

    public Boolean isWaiting() {
        return isWaiting;
    }

    public Boolean isEmergencyStop() {
        return isEmergencyStop;
    }

    public Queue<Integer> getFloorQueueBuffer() {
        return floorQueueBuffer;
    }

    public Queue<Integer> getFloorBuffer() {
        return floorBuffer;
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
        map.put("name", elevatorName);
        map.put("floor", elevatorFloor);
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
