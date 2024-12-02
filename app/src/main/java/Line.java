
public class Line {
    final Vector point1;
    final Vector point2;

    Line(Vector point1, Vector point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public ParametricLine getParametricLine() {
        double dirXCoord = point2.xCoord - point1.xCoord;
        double dirYCoord = point2.yCoord - point1.yCoord;
        double dirZCoord = point2.zCoord - point1.zCoord;
        double normalizationFactor = Math.sqrt(dirXCoord*dirXCoord + dirYCoord*dirYCoord + dirZCoord*dirZCoord);
        dirXCoord /= normalizationFactor;
        dirYCoord /= normalizationFactor;
        dirZCoord /= normalizationFactor;
        return new ParametricLine(point1, new Vector(dirXCoord, dirYCoord, dirZCoord));
    }
}
