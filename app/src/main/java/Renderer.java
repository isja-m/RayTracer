import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Renderer {
    RenderPanel panel;
    JFrame frame;
    int width;
    int height;
    Scene scene;

    Renderer(Scene scene) {
        frame = new JFrame("Test");
        this.scene = scene;
        this.width = scene.getScreenWidth();
        this.height = scene.getScreenHeight();
        frame.setSize(this.width, this.height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void update() {
        frame.getContentPane().removeAll();
        panel = new RenderPanel(scene);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.repaint();
        frame.setVisible(true);
        }
}

class RenderPanel extends JPanel {
    Scene scene; //constructor!

    RenderPanel(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        for (int i = 0; i < scene.getScreenWidth(); i++) {
            for (int j = 0; j < scene.getScreenHeight(); j++) {
                int redValue = Math.max(0, (int)Math.min(255, (int)Math.round(255*scene.getPixel(i, j).getBrightness()[0]/100)));
                int greenValue = Math.max(0, (int)Math.min(255, (int)Math.round(255*scene.getPixel(i, j).getBrightness()[1]/100)));
                int blueValue = Math.max(0, (int)Math.min(255, (int)Math.round(255*scene.getPixel(i, j).getBrightness()[2]/100)));
                Color pixelColor = new Color(redValue, greenValue, blueValue);
                g.setColor(pixelColor);
                g.drawLine(i,j,i,j);
            }
        }
    }
}
