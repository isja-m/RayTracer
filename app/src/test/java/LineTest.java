import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class LineTest {
    @Test
    public void correctDirectionForParametricLine() {
        Vector vector1 = new Vector(1,1,1);
        Vector vector2 = new Vector(5,4,1);
        Line line = new Line(vector1, vector2);
        ParametricLine pLine = line.getParametricLine();
        assertEquals(0.8, pLine.direction.xCoord);
    }

    @Test
    public void calculateCorrectIntersection() {
        Vector origin1 = new Vector(2, 0, 0);
        Vector direction1 = new Vector(2, 1, 0);
        Vector origin2 = new Vector(0, 2, 0);
        Vector direction2 = new Vector(1, 2, 0);
        Line line1 = new Line(origin1, direction1);
        Line line2 = new Line(origin2, direction2);
        assertTrue(line1.findIntersection(line2).intersectionPoint.equals(new Vector(2,2,0)));
    }
}
