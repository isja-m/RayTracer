import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SphereTest {
    @Test
    public void intersectFindsTwoIntersectionsWithSphere() {
        Vector origin = new Vector(0, 0, 0);
        Sphere sphere = new Sphere(origin, 1, 0.5);
        Vector vector1 = new Vector(Math.sqrt(0.5), -1, 0);
        Vector vector2 = new Vector(Math.sqrt(0.5), 1, 0);
        Line line = new Line(vector1, vector2);
        Vector expectedIntersection1 = new Vector(Math.sqrt(0.5), -Math.sqrt(0.5), 0);
        Vector expectedIntersection2 = new Vector(Math.sqrt(0.5), Math.sqrt(0.5), 0);
        Vector[] foundIntersections = sphere.intersect(line);
        assertTrue(expectedIntersection1.equals(foundIntersections[0]));
        assertTrue(expectedIntersection2.equals(foundIntersections[1]));
    }

    @Test
    public void nearestIntersectFindsNearestIntersection() {
        Vector origin = new Vector(0, 0, 0);
        Sphere sphere = new Sphere(origin, 1, 0.5);
        Vector vector1 = new Vector(Math.sqrt(0.5), -1, 0);
        Vector vector2 = new Vector(Math.sqrt(0.5), 1, 0);
        Line line = new Line(vector1, vector2);
        Vector expectedIntersection = new Vector(Math.sqrt(0.5), -Math.sqrt(0.5), 0);
        Vector foundIntersection = sphere.nearestIntersect(line);
        assertTrue(expectedIntersection.equals(foundIntersection));
    }
    
    @Test
    public void intersectFindsOneIntersectionForTangentLineWithSphere() {
        Vector origin = new Vector(0, 0, 0);
        Sphere sphere = new Sphere(origin, 1, 0.5);
        Vector vector1 = new Vector(1, -1, 0);
        Vector vector2 = new Vector(1, 1, 0);
        Line line = new Line(vector1, vector2);
        Vector[] foundIntersections = sphere.intersect(line);
        assertTrue(foundIntersections[0].equals(foundIntersections[1]));
    }
}
