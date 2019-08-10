package World16Elevators.Objects;


import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("FloorObject")
public class FloorObject implements ConfigurationSerializable {

    private int floor;
    private Location atDoor;
    private BoundingBox boundingBox;

    public FloorObject(int floor, Location atDoor, BoundingBox boundingBox) {
        this.floor = floor;
        this.atDoor = atDoor;
        this.boundingBox = boundingBox;
    }

    //GETTERS

    public int getFloor() {
        return floor;
    }

    public Location getAtDoor() {
        return atDoor;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("floor", floor);
        map.put("atDoor", atDoor);
        map.put("boundingBox", boundingBox);
        return map;
    }

    public static FloorObject deserialize(Map<String, Object> map) {
        return new FloorObject((int) map.get("floor"), (Location) map.get("atDoor"), (BoundingBox) map.get("boundingBox"));
    }
}
