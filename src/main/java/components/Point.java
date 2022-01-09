package components;

import java.util.Objects;

public class Point {

    private int i;
    private int j;

    public Point(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getJ() {
        return j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point position = (Point) o;
        return i == position.i && j == position.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public String toString() {
        return "{" + i + "," + j + '}';
    }
}
