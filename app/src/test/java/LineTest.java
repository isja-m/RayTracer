import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
