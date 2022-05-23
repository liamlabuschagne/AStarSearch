import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Window extends JFrame{

    Window(int width, int type) {
        super();
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

        this.setTitle(title);

        JPanel container = new JPanel();
        JScrollPane scrPane = new JScrollPane(container);
        getContentPane().add(scrPane);
        container.setBackground(Color.BLACK);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.add(new Canvas(width, type));
        pack();
        setVisible(true);

        setSize(500, 500);
    }
}
