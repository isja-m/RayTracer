import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SceneTest {
    static Vector corner1;
    static Vector corner2;
    static Vector corner3;
    static Viewport viewport;
    static Scene scene;
    static Vector viewpoint;
    static Lightsource lightsource;
    static Sphere sphere;

    @BeforeAll
    public static void setupBeforeAll() {
        corner1 = new Vector(-2, -1, -1);
        corner2 = new Vector(-2, 1, -1);
        corner3 = new Vector(-2, -1, 1);
        viewport = new Viewport(corner1, corner2, corner3, 480, 640);
        sphere = new Sphere(new Vector(0,0,0), 1, 0.5);
        viewpoint = new Vector(-3, 0, 0);
        lightsource = new Lightsource(new Vector(-1, 0, -2), 50,0,0);
        scene = new Scene(lightsource, sphere, viewpoint, viewport);
        scene.addShape(new Sphere(new Vector(4,0,0),1, 0.5));
        scene.addLightsource(new Lightsource(new Vector(2, 0, 0), 100,0,0));
    }

    @Test
    public void getPixelReturnsCorrectPixel() {
        assertEquals(100, scene.getPixel(100, 100).xCoord);
        assertEquals(100, scene.getPixel(100, 100).yCoord);
    }

    @Test
    public void detectBrightnessAtBrightVisiblePoint() {
        scene.updateBrightnessAtPixel(230,320);
        assertTrue(scene.getPixel(230,320).getBrightness()[0] > 0);
    }

    @Test
    public void doNotDetectBrightnessAtDarkVisiblePoint() {
        scene.updateBrightnessAtPixel(250,320);
        assertFalse(scene.getPixel(250,320).getBrightness()[0] > 0);
    }

    @Test
    public void onlyDetectBrightnessAtNearestObject() {
        scene.updateBrightness();
        assertTrue(scene.getPixel(230,320).getBrightness()[0] < 100);
    }

    @Test
    public void doNotDetectBrightnessIfNoShapeIsHit() {
        scene.updateBrightnessAtPixel(0,0);
        assertFalse(scene.getPixel(0, 0).getBrightness()[0] > 0);
    }

    @Test
    public void doNotDetectBrightnessIfOtherShapeBlocksLightsource() {
        Sphere sphere1 = new Sphere(0,0,0, 1, 0.5);
        Viewport viewport2 = new Viewport(corner1, corner2, corner3, 500, 500);
        Lightsource lightsource2 = new Lightsource(new Vector(0, 0, -20), 100,0,0);
        Scene scene2 = new Scene(lightsource2, sphere1, viewpoint, viewport2);
        scene2.addShape(new Sphere(0,0,-4,2,0.5));
        scene2.updateBrightnessAtPixel(240,250);
        assertEquals(0,scene2.getPixel(240, 250).getBrightness()[0]);
    }
}
