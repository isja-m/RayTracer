public class Polygon implements Shape {
    private Vector[] corners;
    private Triangle[] triangles;
    final float diffuseCoefficient;

    public Polygon(float diffuseCoefficient, Vector... corners) throws InvalidGeometryException {
        this.corners = corners;
        this.diffuseCoefficient = diffuseCoefficient;
        this.triangles = new Triangle[corners.length-2];
        generateTriangles();
        if (!cornersLieOnPlane()) {
            throw(new InvalidGeometryException("The given corners do not lie on a 2D plane."));
        }
    }

    public Polygon(double diffuseCoefficient, Vector... corners) throws InvalidGeometryException {
        this((float)diffuseCoefficient, corners);
    }

    public Polygon(float diffuseCoefficient, Vector centre, Vector firstCorner, Vector normalVector, int numberOfCorners) throws InvalidGeometryException {
        this(diffuseCoefficient, generateCorners(centre, firstCorner, normalVector, numberOfCorners));
    }

    public Polygon(double diffuseCoefficient, Vector centre, Vector firstCorner, Vector normalVector, int numberOfCorners) throws InvalidGeometryException {
        this((float)diffuseCoefficient, centre, firstCorner, normalVector, numberOfCorners);
    }

    private static Vector[] generateCorners(Vector centre, Vector firstCorner, Vector normalVector, int numberOfCorners) {
        Vector[] generatedCorners = new Vector[numberOfCorners];
        generatedCorners[0] = firstCorner;
        for (int i = 1; i < numberOfCorners; i++) {
            generatedCorners[i] = firstCorner.verticalPivotAround(centre, normalVector, i*2*Math.PI/numberOfCorners);
        }
        return generatedCorners;
    }

    private void generateTriangles() {
        for (int i = 0; i < corners.length-2; i++) {
            triangles[i] = new Triangle(corners[0], corners[i+1], corners[i+2], diffuseCoefficient);
        }
    }

    private boolean cornersLieOnPlane() {
        Vector normal = getNormalVector();
        for (Vector corner : corners) {
            double dotProductOfCorners = normal.dotProduct(corners[0].subtract(corner));
            if (dotProductOfCorners > 1e-13 || -dotProductOfCorners > 1e-13) {
                return false;
            }
        }
        return true;
    }

    public Vector[] intersect(Line line) {
        return this.intersect(line.getParametricLine());
    }

    public Vector[] intersect(ParametricLine line) {
        for (Triangle triangle : triangles) {
            Vector[] intersections = triangle.intersect(line);
            if (!Double.isNaN(intersections[0].xCoord)){
                return intersections;
            }
        }
        return new Vector[] {new Vector(Math.sqrt(-1),Math.sqrt(-1),Math.sqrt(-1))};
    }

    public Vector getNormalVector() {
        return triangles[0].getNormalVector();
    }

    public Vector getNormalVector(ParametricLine rayFromViewToShape) {
        return triangles[0].getNormalVector(rayFromViewToShape);
    }

    public Vector nearestIntersect(Line line) {
        return nearestIntersect(line.getParametricLine());
    }

    public Vector nearestIntersect(ParametricLine line) {
        return intersect(line)[0];
    }

    public float getDiffuseCoefficient() {
        return diffuseCoefficient;
    }
}
