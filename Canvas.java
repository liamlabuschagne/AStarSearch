import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Dimension;

public class Canvas extends JPanel {

    private boolean startedSearch = false;
    private int WIDTH;
    private int TYPE;
    private Path bestPath;
    // 0 - (0,0) in top left and indexing of 1
    // 1 - (0,0) in top left and indexing of 0
    // 2 - (0,0) in bottom left and indexing of 1
    // 3 - (0,0) in bottom left and indexing of 0

    public Canvas(int width, int type) {
        WIDTH = width;
        TYPE = type;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, WIDTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        // Invert Y if required
        if(TYPE == 2 || TYPE == 3){
            g.translate(0, WIDTH);

            g.fillRect(0, -WIDTH, WIDTH, WIDTH);
        } else {
            g.fillRect(0, 0, WIDTH, WIDTH);
        }
        
        if (!startedSearch) {
            bestPath = Stars.startSearch(TYPE);
            startedSearch = true;
        }
        Stars.draw(g, bestPath, TYPE);
    }
}