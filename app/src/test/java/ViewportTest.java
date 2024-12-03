import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ViewportTest {
    static Vector corner1;
    static Vector corner2;
    static Vector corner3;
    static Viewport viewport;

    @BeforeAll
    static public void setupBeforeAll() {
        corner1 = new Vector(-2, -1, -1);
        corner2 = new Vector(-2, 1, -1);
        corner3 = new Vector(-2, -1, 1);
        viewport = new Viewport(corner1, corner2, corner3, 480, 640);
    }
    
    @Test
    public void correctVectorFromCoordinate() {
        Pixel coordinate = new Pixel(240, 320);
        assertTrue(viewport.getVector(coordinate).equals(new Vector(-2, 0,0)));
    }
    
    @Test
    public void anotherCorrectVectorFromCoordinate() {
        Pixel coordinate = new Pixel(0, 0);
        assertTrue(viewport.getVector(coordinate).equals(new Vector(-2, -1,-1)));
    }
}
