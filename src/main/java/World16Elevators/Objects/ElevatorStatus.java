package World16Elevators.Objects;

public enum ElevatorStatus {

    UP,
    DOWN,
    NOT_GOING_ANYWHERE;

    public ElevatorStatus upOrDown(boolean up) {
        if (up) {
            return UP;
        }
        return DOWN;
    }
}
