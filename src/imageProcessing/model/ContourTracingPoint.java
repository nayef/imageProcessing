package imageProcessing.model;

/**
 * Created by nayef on 1/27/15.
 */
public class ContourTracingPoint extends Point {

    private static final int DIR_NORTH = 0;
    private static final int DIR_EAST = 1;
    private static final int DIR_SOUTH = 2;
    private static final int DIR_WEST = 3;
    private int direction;

    public ContourTracingPoint(int x, int y) {
        super(x, y);
        this.direction = DIR_NORTH;
    }

    public ContourTracingPoint(int x, int y, int direction) {
        super(x, y);
        this.direction = direction;
    }

    public ContourTracingPoint(ContourTracingPoint refPoint) {
        super(refPoint.getX(), refPoint.getY());
        this.direction = refPoint.direction;
    }

    private void faceRight() {
        direction = (direction + 1) % 4;
    }

    private void faceLeft() {
        if (direction == 0) {
            direction = 3;
        } else {
            direction--;
        }
    }

    private void goForward() {
        if (direction == DIR_NORTH) {
            setY(getY() - 1);
        }
        if (direction == DIR_EAST) {
            setX(getX() + 1);
        }
        if (direction == DIR_SOUTH) {
            setY(getY() + 1);
        }
        if (direction == DIR_WEST) {
            setX(getX() - 1);
        }

    }

    public void advanceToLeft() {
        faceLeft();
        goForward();
    }

    public void advanceToRight() {
        faceRight();
        goForward();
    }

    public ContourTracingPoint getClone() {
        return new ContourTracingPoint(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (getX() != other.getX()) {
            return false;
        }
        if (getY() != other.getY()) {
            return false;
        }
        return true;
    }

}
