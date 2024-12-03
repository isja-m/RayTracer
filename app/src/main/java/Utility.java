public class Utility {
    public static double angle(ParametricLine line1, ParametricLine line2) {
        double dotProd = dotProduct(line1.direction, line2.direction);
        double norm1 = line1.direction.norm(); 
        double norm2 = line2.direction.norm(); 
        return Math.acos(dotProd/(norm1 * norm2));
    }

    public static double dotProduct(Vector vector1, Vector vector2) {
        return vector1.xCoord*vector2.xCoord + vector1.yCoord*vector2.yCoord + vector1.zCoord*vector2.zCoord;
    }
}
