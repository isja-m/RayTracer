import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ParametricLineTest {
    @Test
    public void calculateCorrectAngle() {
        Vector origin = new Vector(0, 0, 0);
        Vector vector1 = new Vector(0, 0, 1);
        Vector vector2 = new Vector(0, 1, 1);
        ParametricLine line1 = new ParametricLine(origin, vector1);
        ParametricLine line2 = new ParametricLine(origin, vector2);
        assertEquals(Math.PI/4, line1.angle(line2), 1e-13);
    }

    @Test
    public void calculateCorrectIntersection() {
        Vector origin1 = new Vector(2, 0, 0);
        Vector direction1 = new Vector(0, 1, 0);
        Vector origin2 = new Vector(0, 2, 0);
        Vector direction2 = new Vector(1, 0, 0);
        ParametricLine line1 = new ParametricLine(origin1, direction1);
        ParametricLine line2 = new ParametricLine(origin2, direction2);
        assertTrue(line1.findIntersection(line2).intersectionPoint.equals(new Vector(2,2,0)));
    }
}
