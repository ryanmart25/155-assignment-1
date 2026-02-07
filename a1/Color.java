package a1;

public enum Color {
    YELLOW,
    PURPLE,
    GRADIENT;
    public Color next(){
        Color[] values = Color.values();
        int nextOrdinal = (this.ordinal() + 1) % values.length;
        return values[nextOrdinal];
    }
}
