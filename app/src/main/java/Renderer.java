import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Renderer {
    private RenderPanel panel;
    final private JFrame frame;
    final private int width;
    final private int height;
    final private Scene scene;

    public Renderer(Scene scene) {
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
        try { // Wait to avoid graphical glitches.
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (Exception e) {
        }
    }
}

class RenderPanel extends JPanel {
    Scene scene;

    RenderPanel(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        for (int i = 0; i < scene.getScreenWidth(); i++) {
            for (int j = 0; j < scene.getScreenHeight(); j++) {
                paintPixel(g, i, j);
            }
        }
    }

    private void paintPixel(Graphics g, int xCoord, int yCoord) {
        int redValue = Math.max(0, (int)Math.min(255, (int)Math.round(255*scene.getPixel(xCoord, yCoord).getBrightness()[0]/100)));
        int greenValue = Math.max(0, (int)Math.min(255, (int)Math.round(255*scene.getPixel(xCoord, yCoord).getBrightness()[1]/100)));
        int blueValue = Math.max(0, (int)Math.min(255, (int)Math.round(255*scene.getPixel(xCoord, yCoord).getBrightness()[2]/100)));
        Color pixelColor = new Color(redValue, greenValue, blueValue);
        g.setColor(pixelColor);
        g.drawLine(xCoord,yCoord,xCoord,yCoord);
    }
}
