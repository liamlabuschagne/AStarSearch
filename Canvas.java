import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Dimension;

public class Canvas extends JPanel {

    private boolean startedSearch = false;
    private int WIDTH;
    private int HEIGHT;
    private int TYPE;
    private static final int borderOffset = 50;
    private Path bestPath;
    // 0 - (0,0) in top left and indexing of 1
    // 1 - (0,0) in top left and indexing of 0
    // 2 - (0,0) in bottom left and indexing of 1
    // 3 - (0,0) in bottom left and indexing of 0

    public Canvas(int width, int type) {
        WIDTH = width + borderOffset * 2;
        HEIGHT = width + borderOffset * 2;
        TYPE = type;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        // Invert Y if required
        if(TYPE == 2 || TYPE == 3){
            g.translate(0, HEIGHT);

            g.fillRect(0, -HEIGHT, WIDTH, HEIGHT);
            // Draw x and y line
            g.setColor(Color.WHITE);
            g.drawLine(borderOffset/5, -(borderOffset/5), WIDTH,-(borderOffset/5));
            g.drawLine(borderOffset/5, -(borderOffset/5), borderOffset/5, -HEIGHT);

            
        } else {
            g.fillRect(0, 0, WIDTH, getHeight());

            // Draw x and y line
            g.setColor(Color.WHITE);
            g.drawLine(borderOffset/5, (borderOffset/5), WIDTH,borderOffset/5);
            g.drawLine(borderOffset/5, (borderOffset/5), borderOffset/5, HEIGHT);
        }

        
        if (!startedSearch) {
            bestPath = Stars.startSearch(TYPE);
            startedSearch = true;
        }

        

        // Draw stars
        Stars.draw(g, bestPath, TYPE);
    }
}