public class Triangle implements Shape {
    final Vector corner1;
    final Vector corner2;
    final Vector corner3;
    final float diffuseCoefficient;
    final private double epsilon = 0.00000001;

    public Triangle(Vector corner1, Vector corner2, Vector corner3, float diffuseCoefficient) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.corner3 = corner3;
        this.diffuseCoefficient = diffuseCoefficient;
    }

    public Triangle(Vector corner1, Vector corner2, Vector corner3, double diffuseCoefficient) {
        this(corner1, corner2, corner3, (float)diffuseCoefficient);
    }

    public Triangle(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, float diffuseCoefficient) {
        this(new Vector(x1, y1, z1), new Vector(x2, y2, z2), new Vector(x3, y3, z3), diffuseCoefficient);
    }

    public Triangle(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double diffuseCoefficient) {
        this(x1, y1, z1, x2, y2, z2, x3, y3, z3, (float)diffuseCoefficient);
    }

    public Vector[] intersect(Line line) {
        return this.intersect(line.getParametricLine());
    }

    public Vector[] intersect(ParametricLine line) {
        /* Finds intersection using the Möller–Trumbore intersection algorithm:
         * https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm
        */
        if (isParallel(line)) {
            return new Vector[] {new Vector(Math.sqrt(-1),Math.sqrt(-1),Math.sqrt(-1))};
        }
        Vector column1 = line.direction.scalarMultiple(-1);
        Vector column2 = corner2.subtract(corner1);
        Vector column3 = corner3.subtract(corner1);
        Vector lineStartToCorner1 = line.start.subtract(corner1);
        double det = column1.determinant(column2, column3);
        double distToLine = lineStartToCorner1.determinant(column2, column3)/det;
        double distToCorner2 = column1.determinant(lineStartToCorner1, column3)/det;
        double distToCorner3 = column1.determinant(column2, lineStartToCorner1)/det;
        if (distToCorner2  + distToCorner3> 1 || distToCorner2 < 0 || distToCorner3 < 0 || distToLine < epsilon) {
            return new Vector[] {new Vector(Math.sqrt(-1),Math.sqrt(-1),Math.sqrt(-1))};
        }
        return new Vector[] {line.start.add(line.direction.scalarMultiple(distToLine))};
    }

    private Boolean isParallel(ParametricLine line) {
        double dotProduct = line.direction.dotProduct(getNormalVector());
        return dotProduct < epsilon && dotProduct > -dotProduct;
    }

    public Vector getNormalVector() {
        return corner1.subtract(corner2).crossProduct(corner1.subtract(corner3)).normalize();
    }

    public Vector getNormalVector(ParametricLine rayFromViewToShape) {
        Vector normal = getNormalVector();
        if (normal.dotProduct(rayFromViewToShape.direction) < 0) {
            return normal;
        }
        return normal.scalarMultiple(-1);
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
