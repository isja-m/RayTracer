import java.util.ArrayList;

public class Polyhedron implements Shape {
    protected Polygon[] faces;
    protected float diffuseCoefficient;

    public Polyhedron(Polygon[] faces, float diffuseCoefficient) {
        this.faces = faces;
        this.diffuseCoefficient = diffuseCoefficient;
    }

    public Polyhedron(Polygon[] faces, double diffuseCoefficient) {
        this(faces, (float)diffuseCoefficient);
    }
    
    public Vector[] intersect(Line line) {
        return this.intersect(line.getParametricLine());
    }

    public Vector[] intersect(ParametricLine line) {
        ArrayList<Vector> intersections = new ArrayList<Vector>();
        for (Polygon face : faces) {
            Vector intersection = face.intersect(line)[0];
            if (!Double.isNaN(intersection.xCoord)){
                intersections.add(intersection);
            }
        }
        if (intersections.size() == 0) {
            return new Vector[] {new Vector(Math.sqrt(-1),Math.sqrt(-1),Math.sqrt(-1))};
        }
        return intersections.toArray(new Vector[0]);
    }

    public Vector nearestIntersect(Line line) {
        return nearestIntersect(line.getParametricLine());
    }

    public Vector nearestIntersect(ParametricLine line) {
        Vector nearestIntersection = new Vector(Math.sqrt(-1),Math.sqrt(-1),Math.sqrt(-1));
        for (Vector intersection : intersect(line)) {
            if (Double.isNaN(nearestIntersection.xCoord)) {
                nearestIntersection = intersection;
            } else if (intersection.distance(line.start) < nearestIntersection.distance(line.start)) {
                nearestIntersection = intersection;
            }
        }
        return nearestIntersection;
    }

    public float getDiffuseCoefficient() {
        return diffuseCoefficient;
    }

    private Polygon getFaceHitByRay(ParametricLine line) {
        Polygon nearestFace = null;
        double nearestDistance = Double.POSITIVE_INFINITY;
        for (Polygon face : faces) {
            Vector intersection = face.intersect(line)[0];
            if (!Double.isNaN(intersection.xCoord)){
                if (line.start.distance(intersection) < nearestDistance) {
                    nearestDistance = line.start.distance(intersection);
                    nearestFace = face;
                }
            }
        }
        return nearestFace;
    }

    public Vector getNormalVector(ParametricLine rayFromViewToShape) {
        return getFaceHitByRay(rayFromViewToShape).getNormalVector(rayFromViewToShape);
    }
}
