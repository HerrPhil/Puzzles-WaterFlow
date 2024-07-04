public final class Position {

    private int i, j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object position) {
        if (position == null) return false;
        if (this == position) return true;
        if (!(position instanceof Position)) return false;
        Position that = (Position) position;
        return this.i == that.i && this.j == that.j;
    }

    @Override
    public String toString() {
        return "Position (" + String.valueOf(i) + ", " + String.valueOf(j) + ")";
    }

    public int rowCoordinate() {
        return i;
    }

    public int columnCoordinate() {
        return j;
    }

}
