package World16Elevators.Objects;

public enum ElevatorStatus {

    UP,
    DOWN,
    IDLE,
    NOT_GOING_ANYWHERE,
    DONT_KNOW;

    public ElevatorStatus upOrDown(boolean up) {
        if (up) {
            return UP;
        }
        return DOWN;
    }
}
