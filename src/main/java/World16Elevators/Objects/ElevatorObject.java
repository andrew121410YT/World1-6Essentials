package World16Elevators.Objects;

import World16.Main.Main;
import World16.Utils.SimpleMath;
import World16.Utils.SmoothTeleport;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

@SerializableAs("ElevatorObject")
public class ElevatorObject implements ConfigurationSerializable {

    private String elevatorName;
    private String world;
    //PART OF FloorObject
    private Location atDoor;
    private int elevatorFloor;
    private Location locationDOWN;
    private Location locationUP;
    //...

    //Bounding BOX
    private Location locationDownPLUS;
    private Location locationUpPLUS;
    //...

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
    private Boolean isIdling;
    private Boolean isEmergencyStop;

    private int topFloor = 0;
    private int topBottomFloor = 0;

    private Queue<FloorQueue> floorQueueBuffer;
    private Queue<Integer> floorBuffer;

    public ElevatorObject(Main plugin, String world, String nameOfElevator, FloorObject currentFloor, BoundingBox boundingBox) {
        if (plugin != null) {
            this.plugin = plugin;
        }

        this.world = world; //NEEDS TO BE SECOND.

        this.floorsMap = new HashMap<>();
        this.floorQueueBuffer = new LinkedList<>();
        this.floorBuffer = new LinkedList<>();

        this.simpleMath = new SimpleMath(this.plugin);

        this.elevatorName = nameOfElevator;

        this.elevatorFloor = currentFloor.getFloor();
        this.atDoor = currentFloor.getAtDoor();
        this.locationDOWN = currentFloor.getBoundingBox().getVectorDOWN().toLocation(getBukkitWorld());
        this.locationUP = currentFloor.getBoundingBox().getVectorUP().toLocation(getBukkitWorld());

        this.locationDownPLUS = boundingBox.getVectorDOWN().toLocation(getBukkitWorld());
        this.locationUpPLUS = boundingBox.getVectorUP().toLocation(getBukkitWorld());

        this.isGoing = false;
        this.isFloorQueueGoing = false;
        this.isIdling = false;
        this.isEmergencyStop = false;

        this.floorsMap.putIfAbsent(0, currentFloor);
    }

    public Collection<Entity> getEntities() {
        return simpleMath.getEntitiesInAABB(locationDownPLUS.toVector(), locationUpPLUS.toVector());
    }

    public Collection<Player> getPlayers() {
        return simpleMath.getEntitiesInAABB(locationDownPLUS.toVector(), locationUpPLUS.toVector()).stream().filter(entity -> entity instanceof Player).map(entity -> (Player) entity).collect(Collectors.toList());
    }

    public void callElevator(int whatFloor, int toWhatFloor) {
        goToFloor(whatFloor, ElevatorStatus.DONT_KNOW);
        goToFloor(toWhatFloor, ElevatorStatus.DONT_KNOW);
    }

    public void goToFloor(int floorNum, ElevatorStatus elevatorStatus) {
        boolean goUp;

        //Check if the floor is a thing or not.
        if (getFloor(floorNum) == null) {
            return;
        }

        //Add to the queue if elevator is running or idling.
        if (isGoing || isIdling) {
            floorQueueBuffer.add(new FloorQueue(floorNum, elevatorStatus));
            setupFloorQueue();
            return;
        }

        isGoing = true;
        floorBuffer.clear(); //Clears the floorBuffer

        //Gets the floor before the elevator starts ticking.
        FloorObject floorObject = getFloor(floorNum);

        //Checks if the elevator should go up or down.
        goUp = floorObject.getAtDoor().getY() > this.atDoor.getY();

        //This caculates what floors it's going to pass going up or down this has to be run before it sets this.elevatorFloor to not a floor.
        calculateFloorBuffer(floorNum, goUp);

        this.elevatorFloor = Integer.MIN_VALUE; //Not on a floor.

        //Tell the elevator to go down instead of up.
        if (!goUp) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    //Check's if at floor if so then stop the elvator.
                    if (atDoor.getY() == floorObject.getAtDoor().getY()) {
                        this.cancel();
                        elevatorFloor = floorNum;
                        floorDone(floorNum, elevatorStatus);
                        doFloorIdle();
                        isGoing = false;
                        return;
                    }

//                    Stop's the elevator if emergencyStop is on.
                    if (isEmergencyStop) {
                        isIdling = false;
                        isGoing = false;
                        isEmergencyStop = false;
                        this.cancel();
                        return;
                    }

                    worldEditMoveDOWN(floorNum);

                    //TP THEM DOWN 1
                    for (Player player : getPlayers()) {
                        SmoothTeleport.teleport(player, player.getLocation().subtract(0, 1, 0));
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
                if (atDoor.getY() == floorObject.getAtDoor().getY()) {
                    this.cancel();
                    elevatorFloor = floorNum;
                    floorDone(floorNum, elevatorStatus);
                    doFloorIdle();
                    isGoing = false;
                    return;
                }

//                Stop's the elevator if emergencyStop is on.
                if (isEmergencyStop) {
                    isIdling = false;
                    isGoing = false;
                    isEmergencyStop = false;
                    this.cancel();
                    return;
                }

                worldEditMoveUP(floorNum);

                //TP THEM UP 1
                for (Player player : getPlayers()) {
                    SmoothTeleport.teleport(player, player.getLocation().add(0, 1, 0));
                }

            }
        }.runTaskTimer(plugin, ticksPerSecond, ticksPerSecond);
    }

    private void worldEditMoveUP(int floor) {
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
        locationUpPLUS.add(0, 1, 0);
        locationDownPLUS.add(0, 1, 0);
    }

    private void worldEditMoveDOWN(int floor) {
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
        locationUpPLUS.subtract(0, 1, 0);
        locationDownPLUS.subtract(0, 1, 0);
    }

    public void emergencyStop() {
        this.isEmergencyStop = true;
    }

    private void floorDone(int floor, ElevatorStatus elevatorStatus) {
        Material oldBlock = getFloor(floor).getAtDoor().getBlock().getType();

        FloorObject floorObject = getFloor(floor);
        floorObject.getAtDoor().getBlock().setType(Material.REDSTONE_BLOCK);

        ElevatorStatus elevatorStatus1 = isNextFloorGoingUp();

        //SIGNS
        if (elevatorStatus1 == ElevatorStatus.UP) {
            if (floorObject.getSignObject() != null) {
                floorObject.getSignObject().doUpArrow();
            }
        } else if (elevatorStatus1 == ElevatorStatus.DOWN) {
            if (floorObject.getSignObject() != null) {
                floorObject.getSignObject().doDownArrow();
            }
        } else if (elevatorStatus1 == ElevatorStatus.NOT_GOING_ANYWHERE && elevatorStatus != ElevatorStatus.DONT_KNOW) {
            if (floorObject.getSignObject() != null) {
                switch (elevatorStatus) {
                    case UP:
                        floorObject.getSignObject().doUpArrow();
                        break;
                    case DOWN:
                        floorObject.getSignObject().doDownArrow();
                        break;
                    default:
                        break;
                }
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (floorObject.getSignObject() != null) {
                    floorObject.getSignObject().clearSign();
                }
                floorObject.getAtDoor().getBlock().setType(oldBlock);
            }
        }.runTaskLater(plugin, doorHolderTicksPerSecond);
    }

    private void doFloorIdle() {
        isIdling = true;
        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> isIdling = false, elevatorWaiterTicksPerSecond);
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

        //Checks every 2 seconds to see if the elevator isn't running or idling if not then go to floor.
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isGoing && !isIdling && !floorQueueBuffer.isEmpty()) {
                    FloorQueue floorQueue = floorQueueBuffer.peek();
                    goToFloor(floorQueue.getFloorNumber(), floorQueue.getElevatorStatus());
                    floorQueueBuffer.remove();
                } else if (floorQueueBuffer.isEmpty()) {
                    isFloorQueueGoing = false;
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 40L, 40L);
    }

    public ElevatorStatus isNextFloorGoingUp() {
        if (!floorQueueBuffer.isEmpty()) {
            FloorObject floorObject = getFloor(floorQueueBuffer.peek());
            if (floorObject == null) {
                return ElevatorStatus.NOT_GOING_ANYWHERE;
            }
            ElevatorStatus elevatorStatus = ElevatorStatus.NOT_GOING_ANYWHERE;
            return elevatorStatus.upOrDown(floorObject.getAtDoor().getY() > this.atDoor.getY());
        } else if (this.elevatorFloor == 0) {
            return ElevatorStatus.UP;
        } else if (this.elevatorFloor == this.topFloor) {
            return ElevatorStatus.DOWN;
        } else if (this.elevatorFloor == this.topBottomFloor) {
            return ElevatorStatus.UP;
        }

        return ElevatorStatus.NOT_GOING_ANYWHERE;
    }

    private void arrivalChime(Location location) {
        getBukkitWorld().playSound(location, Sound.BLOCK_NOTE_PLING, 10F, 1.8F);
    }

    private void passingChime(Location location) {
        getBukkitWorld().playSound(location, Sound.BLOCK_NOTE_PLING, 10F, 1.3F);
    }

    public void addFloor(FloorObject floorObject) {
        if (!(floorObject.getFloor() <= 0)) {
            this.topFloor++;
        } else if (floorObject.getFloor() < 0) {
            this.topBottomFloor--;
        } else if (floorObject.getFloor() == 0) {
            this.floorsMap.remove(floorObject.getFloor());
        }

        this.floorsMap.putIfAbsent(floorObject.getFloor(), floorObject);
    }

    public FloorObject getFloor(int floor) {
        if (this.floorsMap.get(floor) != null) {
            return this.floorsMap.get(floor);
        }
        return null;
    }

    private FloorObject getFloor(FloorQueue floorQueue) {
        if (this.floorsMap.get(floorQueue.getFloorNumber()) != null) {
            return this.floorsMap.get(floorQueue.getFloorNumber());
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

    public Integer[] listAllFloorsInt() {
        Set<Integer> homeSet = this.floorsMap.keySet();
        Integer[] integers = homeSet.toArray(new Integer[0]);
        Arrays.sort(integers);
        return integers;
    }

    public void clickMessageGoto(Player player) {
        String messageD = "- Click a Floor to take the elevator to. -";

        List<BaseComponent[]> componentBuilders = new ArrayList<>();
        for (Integer integer : listAllFloorsInt()) {
            componentBuilders.add(new ComponentBuilder(String.valueOf(integer)).color(ChatColor.GOLD).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/elevator call " + elevatorName.toLowerCase() + " " + integer)).create());
        }

        ComponentBuilder componentBuilder = new ComponentBuilder(messageD).color(ChatColor.YELLOW).bold(true).append("\n");
        for (BaseComponent[] builder : componentBuilders) {
            componentBuilder.append(" ");
            componentBuilder.append(builder);
        }

        player.spigot().sendMessage(componentBuilder.create());
    }

    public org.bukkit.World getBukkitWorld() {
        return Bukkit.getServer().getWorld(this.world);
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
        return isIdling;
    }

    public Boolean isEmergencyStop() {
        return isEmergencyStop;
    }

    public Queue<FloorQueue> getFloorQueueBuffer() {
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
        map.put("world", world);
        map.put("shaft", new FloorObject(elevatorFloor, atDoor, new BoundingBox(this.locationDOWN.toVector(), this.locationUP.toVector())));
        map.put("shaftPlus", new BoundingBox(this.locationDownPLUS.toVector(), locationUpPLUS.toVector()));
        return map;
    }

    public static ElevatorObject deserialize(Map<String, Object> map) {
        return new ElevatorObject(null, (String) map.get("world"), (String) map.get("name"), (FloorObject) map.get("shaft"), (BoundingBox) map.get("shaftPlus"));
    }
}
