import javax.swing.JFrame;

public class Window {

    public Window(int width, int type) {
        // Identify appropriate title
        String title = "A* Galaxy Search -- (0,0) in top left and indexing of 1";
        switch (type) {
            case 1:
                title = "A* Galaxy Search -- (0,0) in top left and indexing of 0";
                break;
            case 2:
                title = "A* Galaxy Search -- (0,0) in bottom left and indexing of 1";
                break;
            case 3:
                title = "A* Galaxy Search -- (0,0) in bottom left and indexing of 0";
                break;
        }
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Canvas(width, type));
        frame.pack();
        frame.setVisible(true);
    }
}
