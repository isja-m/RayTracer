import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CameraOperatorTest {
    static Vector corner1;
    static Vector corner2;
    static Vector corner3;
    static Viewport viewport;
    static Vector viewpoint;
    static Sphere sphere1;
    static Lightsource lightsource1;
    static Scene scene;
    static Renderer renderer;
    static CameraOperator cameraOperator;

    @BeforeEach
    public void beforeAllSetup() {
        corner1 = new Vector(-2, -1, -1);
        corner2 = new Vector(-2, 1, -1);
        corner3 = new Vector(-2, -1, 1);
        viewport = new Viewport(corner1, corner2, corner3, 1000, 1000);
        viewpoint = new Vector(-3, 0, 0);

        sphere1 = new Sphere(new Vector(15, 0, 0), 10, 0.5);
        lightsource1 = new Lightsource(-2, 0, 4, 100,0,0);

        scene = new Scene(lightsource1, sphere1, viewpoint, viewport);

        renderer = new Renderer(scene, false);
        cameraOperator = new CameraOperator(scene, renderer, Math.PI/2, 1);
    }

    @Test
    public void rotateRight() {
        cameraOperator.executeInput(true, "l");
        assertTrue(scene.getViewport().bottomLeftCorner.equals(new Vector(-2, -1, 1)));
    }

    @Test
    public void rotateLeft() {
        cameraOperator.executeInput(true, "j");
        assertTrue(scene.getViewport().bottomLeftCorner.equals(new Vector(-4, -1, -1)));
    }

    @Test
    public void rotateUp() {
        cameraOperator.executeInput(true, "k");
        assertTrue(scene.getViewport().bottomLeftCorner.equals(new Vector(-2, 1, -1)));
    }

    @Test
    public void rotateDown() {
        cameraOperator.executeInput(true, "i");
        assertTrue(scene.getViewport().bottomLeftCorner.equals(new Vector(-4, -1, -1)));
    }

    @Test
    public void StrafeRight() {
        cameraOperator.executeInput(true, "d");
        assertTrue(scene.getViewport().bottomLeftCorner.equals(new Vector(-2, -1, 0)));
    }

    @Test
    public void StrafeLeft() {
        cameraOperator.executeInput(true, "a");
        assertTrue(scene.getViewport().bottomLeftCorner.equals(new Vector(-2, -1, -2)));
    }

    @Test
    public void StrafeForeward() {
        cameraOperator.executeInput(true, "w");
        assertTrue(scene.getViewport().bottomLeftCorner.equals(new Vector(-1, -1, -1)));
    }

    @Test
    public void StrafeBackward() {
        cameraOperator.executeInput(true, "s");
        assertTrue(scene.getViewport().bottomLeftCorner.equals(new Vector(-3, -1, -1)));
    }

    @Test
    public void StrafeUp() {
        cameraOperator.executeInput(true, "e");
        assertTrue(scene.getViewport().bottomLeftCorner.equals(new Vector(-2, -2, -1)));
    }

    @Test
    public void StrafeDown() {
        cameraOperator.executeInput(true, "q");
        assertTrue(scene.getViewport().bottomLeftCorner.equals(new Vector(-2, 0, -1)));
    }

    @Test
    public void quit() {
        assertFalse(cameraOperator.executeInput(true, "quit"));
    }

    @Test
    public void instructionsPrinted() {
        cameraOperator.printInstructions();
        assertTrue(true);
    }
}
