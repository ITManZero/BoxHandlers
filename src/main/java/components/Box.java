package components;

public class Box {

    private Point from;
    private Point to;


    public Box(){

    }
    public Box(int fromX, int fromY, int toX, int toY) {
        this.from = new Point(fromX, fromY);
        this.to = new Point(toX, toY);
    }

    public void setFrom(Point from) {
        this.from = from;
    }


    public void setTo(Point to) {
        this.to = to;
    }

    public Point getTo() {
        return to;
    }

    public Point getFrom() {
        return from;
    }
}
