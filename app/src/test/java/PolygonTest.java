import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

public class PolygonTest {
    @Test
    public void polygonNotOnPlaneIsRejected() {
        Vector corner1 = new Vector(1, 0, 0);
        Vector corner2 = new Vector(0, 1, 0);
        Vector corner3 = new Vector(0, 0, 1);
        Vector corner4 = new Vector(1, 1, 1);
        assertThrows(InvalidGeometryException.class, () -> new Polygon(0.5, corner1, corner2, corner3, corner4));
    }

    @Test
    public void polygonOnPlaneIsNotRejected() {
        Vector corner1 = new Vector(1, 0, 0);
        Vector corner2 = new Vector(0, 1, 0);
        Vector corner3 = new Vector(0, 0, 1);
        Vector corner4 = new Vector(0.5, 0.5, 0);
        assertDoesNotThrow(() -> new Polygon(0.5, corner1, corner2, corner3, corner4));
    }
}
