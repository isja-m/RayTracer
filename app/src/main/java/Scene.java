
import java.util.ArrayList;

public class Scene {
    final Vector viewpoint;
    final Viewport viewport;
    ArrayList<Lightsource> lightsources;
    ArrayList<Shape> shapes;

    public Scene(Lightsource lightsource, Shape shape, Vector viewpoint, Viewport viewport) {
        this.lightsources = new ArrayList<Lightsource>();
        this.lightsources.add(lightsource);
        this.shapes = new ArrayList<Shape>();
        this.shapes.add(shape);
        this.viewpoint = viewpoint;
        this.viewport = viewport;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void addLightsource(Lightsource lightsource) {
        lightsources.add(lightsource);
    }

    public Pixel getPixel(int x, int y) {
        return viewport.getPixel(x,y);
    }

    public void updateBrightness() {
        for (int i = 0; i < viewport.screenWidth; i++) {
            for (int j = 0; j < viewport.screenHeight; j++) {
                updateBrightnessAtPixel(i,j);
            }
        }
        System.out.println("Threads are finished.");
    }

    public void updateBrightnessAtPixel(int x, int y) {
        Boolean rayHitsSomeShape = false;
        Shape closestShape = shapes.get(0);
        double distanceToClosestShape = Double.POSITIVE_INFINITY;
        Vector viewportVector = viewport.getVector(getPixel(x, y));
        Line finalRayFromViewToShape = null;
        Vector finalPointOnShape = null;

        // Find closest shape.
        for (Shape shape : shapes) {
            Line rayFromViewToShape = new Line(viewpoint, viewportVector);
            Vector pointOnShape = shape.nearestIntersect(rayFromViewToShape);
            rayHitsSomeShape = rayHitsSomeShape || !Double.isNaN(pointOnShape.xCoord);
            if (viewpoint.distance(pointOnShape) < distanceToClosestShape) {
                closestShape = shape;
                distanceToClosestShape = viewpoint.distance(pointOnShape);
                finalRayFromViewToShape = rayFromViewToShape;
                finalPointOnShape = pointOnShape;
            }
        }

        if (rayHitsSomeShape) {
            updateBrightnessAtPixelForShape(x, y, closestShape, viewportVector, finalRayFromViewToShape, finalPointOnShape);
        }
    }

    public void updateBrightnessAtPixelForShape(int x, int y, Shape shape, Vector viewportVector, Line rayFromViewToShape,
        Vector pointOnShape) {
        for (int i = 0; i < lightsources.size(); i++) {
            Vector locationOfLight = lightsources.get(i).location;
            Line rayFromShapeToLightSource = new Line(pointOnShape, locationOfLight);
            Vector[] intersectionsWithShape = shape.intersect(rayFromShapeToLightSource);
            double distanceFromPointToLight = pointOnShape.distance(locationOfLight);

            Boolean canSeeLight = true;
            canSeeLight = shapeIsNotBlockingLight(locationOfLight, intersectionsWithShape, distanceFromPointToLight, canSeeLight);
            
            canSeeLight = otherShapeIsNotBlockingLight(locationOfLight, rayFromShapeToLightSource, distanceFromPointToLight, canSeeLight);

            if (canSeeLight) {
                addBrightness(x, y, shape, pointOnShape, lightsources.get(i).brightnesses, rayFromShapeToLightSource);
            }
        }
    }

    private Boolean shapeIsNotBlockingLight(Vector locationOfLight, Vector[] intersectionsWithShape, double distanceFromPointToLight,
            Boolean canSeeLight) {
        for (Vector intersection : intersectionsWithShape) {
            if ((distanceFromPointToLight - intersection.distance(locationOfLight))/distanceFromPointToLight > 1e-13) {
                canSeeLight = false;
            }
        }
        return canSeeLight;
    }

    private Boolean otherShapeIsNotBlockingLight(Vector locationOfLight, Line rayFromShapeToLightSource, double distanceFromPointToLight,
            Boolean canSeeLight) {
        for (Shape otherShape : shapes) {
            Vector[] intersections = otherShape.intersect(rayFromShapeToLightSource);
            canSeeLight = shapeIsNotBlockingLight(locationOfLight, intersections, distanceFromPointToLight, canSeeLight);
        }
        return canSeeLight;
    }

    private void addBrightness(int x, int y, Shape shape, Vector pointOnShape, Vector brightnesses, Line rayFromShapeToLightSource) {
        float diffusalFactor = shape.getDiffuseCoefficient();
        diffusalFactor *= pointOnShape.perpendicularVector(shape).dotProduct(rayFromShapeToLightSource.getParametricLine().direction);
        diffusalFactor = Math.max(0, diffusalFactor);
        viewport.getPixel(x, y).addToBrightness(brightnesses.scalarMultiple(diffusalFactor));
}

    public int getScreenHeight() {
        return viewport.screenHeight;
    }

    public int getScreenWidth() {
        return viewport.screenWidth;
    }
}
