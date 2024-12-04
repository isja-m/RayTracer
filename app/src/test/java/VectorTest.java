import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class VectorTest {
    @Test
    public void normCalculatesCorrectValue() {
        Vector vector = new Vector(2, 3, 6);
        assertEquals(7, vector.norm());
    }

    @Test
    public void calculateCorrectDotProduct() {
        Vector vector1 = new Vector(3, 2, 2);
        Vector vector2 = new Vector(1, 1, 4);
        assertEquals(13, vector1.dotProduct(vector2));
    }

    @Test
    public void calculateAdditionCorrectly() {
        Vector vector1 = new Vector(3, 2, 2);
        Vector vector2 = new Vector(1, 1, 4);
        assertTrue(vector1.add(vector2).equals(new Vector(4,3,6)));
    }

    @Test
    public void calculateScalarMultipleCorrectly() {
        Vector vector1 = new Vector(3, 2, 2);
        assertTrue(vector1.scalarMultiple(3).equals(new Vector(9,6,6)));
    }

    @Test
    public void perpendicularVectorIsCorrect() {
        Sphere sphere = new Sphere(new Vector(0, 0, 0), 2, 0.5);
        Vector vector1 = new Vector(0,2,0);
        assertTrue(vector1.perpendicularVector(sphere).equals(new Vector(0, 1, 0)));
    }
}
