package World16Elevators.Objects;

public class FloorQueue {

    private int floorNumber;
    private ElevatorStatus elevatorStatus;

    public FloorQueue(int floorNumber, ElevatorStatus elevatorStatus) {
        this.floorNumber = floorNumber;
        this.elevatorStatus = elevatorStatus;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ElevatorStatus getElevatorStatus() {
        return elevatorStatus;
    }
}
