// 1575313 - Liam Labuschagne
// 1544572 - Charles Annals
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;

class Stars {

    private static ArrayList<Star> stars = new ArrayList<>();
    private static int maxDistanceAway = 10;
    private static int startOriginal = -1;
    private static int start = -1;
    private static int endOriginal = -1;
    private static int end = -1;
    public static final int SCALING_FACTOR = 10;
    private static final int MAX_DIMENSION = 100;
    // 0 - (0,0) in top left and indexing of 1
    // 1 - (0,0) in top left and indexing of 0
    // 2 - (0,0) in bottom left and indexing of 1
    // 3 - (0,0) in bottom left and indexing of 0

    public static Star getGoal() {
        return stars.get(end);
    }

    private static Path doAStarSearch() {
        PriorityQueue<Path> frontier = new PriorityQueue<>();

        // Create first path with just start in it
        Path initial = new Path();
        initial.addStar(stars.get(start));
        frontier.add(initial);
        Timer timer = new Timer();
        int begin = 0;
        int timeInterval = 1000;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Frontier size: " + frontier.size());
            }
        }, begin, timeInterval);

        while (frontier.size() > 0) {

            // Get the most promising path from the front of the queue
            Path current = frontier.remove();

            // Check if this path ends with the goal state
            if (current.endsWith(stars.get(end))) {
                timer.cancel();
                return current; // Hooray!
            }

            // Otherwise expand it and add them to the priority queue based on their f value
            ArrayList<Path> pathsToAdd = current.generateMoves(maxDistanceAway, stars);

            // For each path, check that there isn't already a better path that ends at the
            // same place
            for (Path pathToAdd : pathsToAdd) {
                boolean shouldAdd = true;
                for (Path frontierPath : frontier) {
                    if (pathToAdd.sameEndAs(frontierPath)) {
                        if (pathToAdd.getFValue() < frontierPath.getFValue()) {
                            frontier.remove(frontierPath);
                            break;
                        } else {
                            // We've found a path in the frontier that is better and ends at the same
                            // place.
                            shouldAdd = false;
                        }
                    }
                }
                if (shouldAdd) {
                    frontier.add(pathToAdd);
                }
            }
        }
        timer.cancel();
        return new Path();
    }

    public static void loadStars(String fileName) {
        File file = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] positionVector = line.split("[,]", 0);
            double x = Double.parseDouble(positionVector[0]);
            double y = Double.parseDouble(positionVector[1]);
            stars.add(new Star(x, y));
        }
        scanner.close();
    }

    public static void draw(Graphics g, Path bestPath, int type) {
        // Draw the stars and their labels
        for (int i = 0; i < stars.size(); i++) {
            if (type % 2 == 0)
                stars.get(i).draw(i + 1, g, type);
            else 
                stars.get(i).draw(i, g, type);

        }

        // Draw our best path
        if (bestPath != null && bestPath.stars.size() > 0) {
            bestPath.draw(g, type);
        }
    }

    public static Path startSearch(int type) {
        boolean doSearch = true;
        // Set appropriate start values based on type
        if (type == 0 || type == 2) {
            start = startOriginal - 1;
            end = endOriginal - 1;
            System.out.println("Searching based on 1 based index:");
            // Means value wasn't entered for 1 based indexing
            if (start < 0 || end < 0){
                doSearch = false;
                System.out.println("Entered indexs weren't 1 based indexs");
            }
        } else {
            start = startOriginal;
            end = endOriginal;
            System.out.println("Searching based on 0 based index:");            
        }

        if (start >= stars.size() || end >= stars.size()){
            doSearch = false;
            System.out.println("An index was larger than the number of stars in the csv file.");
            System.out.println("Number of stars: " + stars.size());
        }

        Path bestPath = null;

        if (doSearch){
            final long startTime = System.currentTimeMillis();
            bestPath = doAStarSearch();
            if (bestPath.isEmpty()) {
                System.out.println("No solution found.");
            } else {
                System.out.println("Best path found:\n" + bestPath);
            }
            final long endTime = System.currentTimeMillis();
            System.out.println("Search took " + (endTime - startTime) + "ms to run.");
        }     

        System.out.println();
        
        return bestPath;
        

    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 4) {
            System.out.println("Usage: java Stars [galaxy_csv_filename] [start_index] [end_index] [D]");
            return;
        }
        loadStars(args[0]);

        try {
            startOriginal = Integer.parseInt(args[1]);
            endOriginal = Integer.parseInt(args[2]);
            maxDistanceAway = Integer.parseInt(args[3]);

            if (startOriginal < 0 || endOriginal < 0 || maxDistanceAway < 1) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            System.out.println("Start index, end index and D must all be positive integers.");
            return;
        }

        if (start >= stars.size() || end >= stars.size()) {
            System.out.println("Start or end index is not within the dataset.");
            return;
        }

        // Create the window which will trigger startSearch when it is ready to be
        // painted to
        for (int i = 0; i < 4; i++) {
            new Window(MAX_DIMENSION * SCALING_FACTOR,i);   
        }
    }
}