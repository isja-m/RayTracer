import java.util.ArrayList;

public class Pyramid extends Polyhedron {

    Pyramid(Vector topCorner, Polygon basePolygon, float diffuseCoefficient) throws InvalidGeometryException {
        super(generateFaces(topCorner, basePolygon, diffuseCoefficient), diffuseCoefficient);
    }

    Pyramid(Vector topCorner, Polygon basePolygon, double diffuseCoefficient) throws InvalidGeometryException {
        this(topCorner, basePolygon, (float)diffuseCoefficient);
    }

    private static Polygon[] generateFaces(Vector topCorner, Polygon basePolygon, double diffuseCoefficient) throws InvalidGeometryException {
        ArrayList<Polygon> faceList = new ArrayList<Polygon>();
        faceList.add(basePolygon);
        Vector[] baseCorners = basePolygon.getCorners();
        Vector previousCorner = baseCorners[baseCorners.length-1];
        for(Vector corner : baseCorners) {
            faceList.add(new Polygon(diffuseCoefficient, topCorner, corner, previousCorner));
            previousCorner = corner;
        }
        return faceList.toArray(new Polygon[0]);
    }
}
