public class Sphere implements Shape {
    final Vector centre;
    final double radius;
    final float diffuseCoefficient;

    public Sphere(Vector centre, double radius, float diffuseCoefficient) {
        this.centre = centre;
        this.radius = radius;
        this.diffuseCoefficient = diffuseCoefficient;
    }

    public Sphere(Vector centre, double radius, double diffuseCoefficient) {
        this(centre, radius, (float)diffuseCoefficient);
    }

    public Sphere(double x, double y, double z, double radius, double diffuseCoefficient) {
        this(new Vector(x,y,z), radius, diffuseCoefficient);
    }

    public Vector[] intersect(Line line) {
        return this.intersect(line.getParametricLine());
    }

    public Vector[] intersect(ParametricLine line) {
        double calculationPart1 = line.direction.dotProduct(line.start.add(centre.scalarMultiple(-1)));
        double calculationPart2 = line.start.subtract(centre).squaredNorm() - radius*radius;
        double distance1 = -calculationPart1 - Math.sqrt(Math.pow(calculationPart1,2) - calculationPart2);
        double distance2 = -calculationPart1 + Math.sqrt(Math.pow(calculationPart1,2) - calculationPart2);
        return new Vector[] {line.start.add(line.direction.scalarMultiple(distance1)), line.start.add(line.direction.scalarMultiple(distance2))};
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

    public Vector getNormalVector(ParametricLine rayFromViewToShape) {
        Vector pointOnShape = nearestIntersect(rayFromViewToShape);
        Line perpendicularLine = new Line(centre, pointOnShape);
        return perpendicularLine.getParametricLine().direction;
    }
}
