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
    public void calculateCorrectCrossProduct() {
        Vector vector1 = new Vector(3, -3, 1);
        Vector vector2 = new Vector(4, 9, 2);
        assertTrue(vector1.crossProduct(vector2).equals(new Vector(-15, -2, 39)));
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
    public void normalVectorIsCorrect() {
        Sphere sphere = new Sphere(new Vector(0, 0, 0), 2, 0.5);
        Vector vector1 = new Vector(0,2,0);
        assertTrue(vector1.normalVectorAtShape(sphere).equals(new Vector(0, 1, 0)));
    }

    @Test
    public void horizontalRotateIsCorrect() {
        Vector vector1 = new Vector(2, 0, 0);
        assertTrue(vector1.horizontalRotate(Math.PI/2).equals(new Vector(0, 0, 2)));
    }

    @Test
    public void horizontalPivotAroundIsCorrect() {
        Vector vector1 = new Vector(2, 0, 0);
        Vector origin = new Vector(0, 0, 0);
        assertTrue(origin.horizontalPivotAround(vector1, Math.PI/2).equals(new Vector(2, 0, -2)));
    }

    @Test
    public void horizontalRotateWithNonzeroYIsCorrect() {
        Vector vector1 = new Vector(2, 1, 0);
        assertTrue(vector1.horizontalRotate(Math.PI/2).equals(new Vector(0, 1, 2)));
    }

    @Test
    public void horizontalPivotAroundWithNonzeroYIsCorrect() {
        Vector vector1 = new Vector(2, 1, 0);
        Vector origin = new Vector(0, 0, 0);
        assertTrue(origin.horizontalPivotAround(vector1, Math.PI/2).equals(new Vector(2, 0, -2)));
    }

    @Test
    public void verticalRotateIsCorrect() {
        Vector vector1 = new Vector(1, 1, 0);
        Vector rotationAxis = new Vector(0,0,1);
        assertTrue(vector1.verticalRotate(rotationAxis, Math.PI/4).equals(new Vector(Math.sqrt(2), 0, 0)));
    }

    @Test
    public void verticalPivotAroundIsCorrect() {
        Vector vector1 = new Vector(1, 1, 0);
        Vector origin = new Vector(0, 0, 0);
        Vector rotationAxis = new Vector(0,0,1);
        assertTrue(origin.verticalPivotAround(vector1, rotationAxis, Math.PI/4).equals(new Vector(1-Math.sqrt(2), 1, 0)));
    }

    @Test
    public void verticalRotateWithNonperpendicularRotationAxisIsCorrect() {
        Vector vector1 = new Vector(1, 1, 1);
        Vector rotationAxis = new Vector(0,0,1);
        assertTrue(vector1.verticalRotate(rotationAxis, Math.PI/4).equals(new Vector(Math.sqrt(2), 0, 1)));
    }

    @Test
    public void verticalPivotAroundWithNonperpendicularRotationAxisIsCorrect() {
        Vector vector1 = new Vector(1, 1, 1);
        Vector origin = new Vector(0, 0, 0);
        Vector rotationAxis = new Vector(0,0,1);
        assertTrue(origin.verticalPivotAround(vector1, rotationAxis, Math.PI/4).equals(new Vector(1-Math.sqrt(2), 1, 0)));
    }

    @Test
    public void projectionToPlaneIsCorrect() {
        Vector vector1 = new Vector(1, 1, 0);
        Vector perpendicularToPlane = new Vector(1,0,0);
        assertTrue(vector1.projectionToPlane(perpendicularToPlane).equals(new Vector(0, 1, 0)));
    }

    @Test
    public void determinantIsCorrect() {
        Vector vector1 = new Vector(2, 2, 1);
        Vector vector2 = new Vector(-3, 0, 4);
        Vector vector3 = new Vector(1, -1, 5);
        assertEquals(49, vector1.determinant(vector2, vector3));
    }
}
