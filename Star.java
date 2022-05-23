import java.awt.Graphics;
import java.security.InvalidKeyException;
import java.awt.Color;

class Star {

    private double x;
    private double y;
    private double hValue = -1;

    public Star(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Star other) {
        return Math.sqrt(Math.pow(Math.abs(this.x - other.x), 2) + Math.pow(Math.abs(this.y - other.y), 2));
    }

    public double getHValue() {
        if (hValue == -1) {
            computeHValue(); // The h value never changes so only needs to be computed once
        }
        return hValue;
    }

    private void computeHValue() {
        // Distance between me and goal
        hValue = distanceTo(Stars.getGoal());
    }

    public String toString() {
        return x + "," + y;
    }

    public void draw(int label, Graphics g, int type) {
        int invert = 1;
        if (type == 2 || type == 3)
            invert = -1;
        g.setColor(Color.YELLOW);
        g.fillOval((int) x * Stars.SCALING_FACTOR - 5 + 20, ((int) y * Stars.SCALING_FACTOR - (5 * invert) + 20) * invert, 10, 10);
        int xOffset = -10;
        int yOffset = -10;
        if (x < 10 / Stars.SCALING_FACTOR) {
            xOffset = 0;
        }
        if (y < 10 / Stars.SCALING_FACTOR) {
            yOffset = 20;
        }
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(label), (int) x * Stars.SCALING_FACTOR + xOffset + 20,
                ((int) y * Stars.SCALING_FACTOR + (yOffset * invert) + 20) * invert);
    }

    public void drawLineTo(Star other, Graphics g, int type) {
        int invert = 1;
        if (type == 2 || type == 3)
            invert = -1;
        g.setColor(Color.RED);
        g.drawLine((int) x * Stars.SCALING_FACTOR + 20, ((int) y * Stars.SCALING_FACTOR + 20) * invert, (int) other.x * Stars.SCALING_FACTOR + 20,
                ((int) other.y * Stars.SCALING_FACTOR + 20) * invert);
    }

}