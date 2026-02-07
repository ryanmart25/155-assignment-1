package a1;

public enum Direction {
    UP(1),
    DOWN(2),
    LEFT(3),
    RIGHT(4);
    private int direction;
    Direction(int i) {
        this.direction = i;
    }
    public int getDirection(){
        return direction;
    }
    public Direction next(){
        Direction[] values = Direction.values();
        int nextOrdinal = (this.ordinal() + 1) % values.length;
        return values[nextOrdinal];
    }
}
