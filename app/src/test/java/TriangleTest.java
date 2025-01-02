import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TriangleTest {
    @Test
    public void lineIntersectsInterior() {
        Triangle triangle = new Triangle(-1,-1,0,-1,2,0,2,-1,0,1);
        ParametricLine line = new ParametricLine(new Vector(0,0,-1), new Vector(0, 0, 1));
        assertTrue(new Vector(0,0,0).equals(triangle.intersect(line)[0]));
    }

    @Test
    public void lineIntersectsEdge() {
        Triangle triangle = new Triangle(-1,-1,0,-1,1,0,1,-1,0,1);
        ParametricLine line = new ParametricLine(new Vector(0,0,-1), new Vector(0, 0, 1));
        assertTrue(new Vector(0,0,0).equals(triangle.intersect(line)[0]));
    }
    
    @Test
    public void lineIntersectsCorner() {
        Triangle triangle = new Triangle(0,0,0,0,2,0,2,0,0,1);
        ParametricLine line = new ParametricLine(new Vector(0,0,-1), new Vector(0, 0, 1));
        assertTrue(new Vector(0,0,0).equals(triangle.intersect(line)[0]));
    }
    
    @Test
    public void lineDoesNotIntersectExterior() {
        Triangle triangle = new Triangle(1,1,0,1,2,0,2,1,0,1);
        ParametricLine line = new ParametricLine(new Vector(0,0,-1), new Vector(0, 0, 1));
        assertTrue(Double.isNaN(triangle.intersect(line)[0].xCoord));
    }
}
