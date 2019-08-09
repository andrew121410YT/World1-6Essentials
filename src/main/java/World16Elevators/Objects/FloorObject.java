package World16Elevators.Objects;


import org.bukkit.Location;

public class FloorObject {

    private int floor;
    private Location middleOfShaft;
    private Location atDoor;

    public FloorObject(int floor, Location middleOfShaft, Location atDoor) {
        this.floor = floor;
        this.middleOfShaft = middleOfShaft;
        this.atDoor = atDoor;
    }

    public FloorObject(int floor, Location atDoor) {
        this.floor = floor;
        this.atDoor = atDoor;
    }

    //GETTERS
    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public Location getMiddleOfShaft() {
        return middleOfShaft;
    }

    public void setMiddleOfShaft(Location middleOfShaft) {
        this.middleOfShaft = middleOfShaft;
    }

    public Location getAtDoor() {
        return atDoor;
    }

    public void setAtDoor(Location atDoor) {
        this.atDoor = atDoor;
    }
}
