import java.util.ArrayList;
import java.awt.Graphics;

class Path implements Comparable<Path> {
    private double length = 0;
    private double fValue = 0;
    public ArrayList<Star> stars = new ArrayList<>();

    public boolean isEmpty() {
        return stars.size() == 0;
    }

    public boolean endsWith(Star star) {
        if (stars.size() > 0 && stars.get(stars.size() - 1) == star) {
            return true;
        } else {
            return false;
        }
    }

    public boolean sameEndAs(Path other) {
        if (stars.size() > 0 && other.endsWith(stars.get(stars.size() - 1))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addStar(Star newStar) {

        // First check that this star doesn't already exist in the path (prevent cycles)
        if (stars.contains(newStar)) {
            return false;
        }

        // Add the distance to the new star to total length of the path
        if (stars.size() > 0) {
            length += stars.get(stars.size() - 1).distanceTo(newStar);
        }

        stars.add(newStar); // Go ahead and add it to our internal collection

        computeFValue(); // Update the fValue accordingly

        return true; // Successful non-cyclic addition
    }

    private void computeFValue() {
        if (stars.size() == 0) {
            return;
        }
        // Total length of the path plus h value of last item in the path
        fValue = length + stars.get(stars.size() - 1).getHValue();
    }

    public Double getFValue() {
        return fValue;
    }

    public ArrayList<Path> generateMoves(int maxDistanceAway, ArrayList<Star> allStars) {
        ArrayList<Path> moves = new ArrayList<>();

        // Starting at the last star in our path
        Star lastStar = stars.get(stars.size() - 1);

        // Go through every star in the galaxy checking if it is within range
        for (Star other : allStars) {

            if (lastStar.distanceTo(other) <= maxDistanceAway && other != lastStar) {
                // Make a copy of this path to add the additional star to
                Path newPath = (Path) this.clone();
                // Only return this new path if it doesn't have cycles
                if (newPath.addStar(other)) {
                    moves.add(newPath);
                }
            }
        }

        return moves;
    }

    public String toString() {
        String s = "";

        for (Star star : stars) {
            s += star.toString() + "\n";
        }

        s += "f value:" + getFValue();

        return s;
    }

    @Override
    public int compareTo(Path other) {
        return (int) (this.getFValue() - other.getFValue());
    }

    public Path clone() {
        Path clone = new Path();
        for (Star star : stars) {
            clone.addStar(star);
        }
        return clone;
    }

    public void draw(Graphics g, int type) {
        Star prev = stars.get(0);
        for (int i = 1; i < stars.size(); i++) {
            prev.drawLineTo(stars.get(i), g, type);
            prev = stars.get(i);
        }
    }
}