import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class UtilityTest {
    @Test
    public void calculateCorrectAngle() {
        Vector origin = new Vector(0, 0, 0);
        Vector vector1 = new Vector(0, 0, 1);
        Vector vector2 = new Vector(0, 1, 1);
        ParametricLine line1 = new ParametricLine(origin, vector1);
        ParametricLine line2 = new ParametricLine(origin, vector2);
        assertEquals(Math.PI/4, Utility.angle(line1,line2), 1e-13);
    }

    @Test
    public void calculateCorrectDotProduct() {
        Vector vector1 = new Vector(3, 2, 2);
        Vector vector2 = new Vector(1, 1, 4);
        assertEquals(13, Utility.dotProduct(vector1, vector2));
    }
}
