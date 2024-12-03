
import java.util.ArrayList;

public class Scene {
    Vector viewpoint;
    Viewport viewport;
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
    }

    public void updateBrightnessAtPixel(int x, int y) {
        Shape closestShape = shapes.get(0);
        double distanceToClosestShape = Double.POSITIVE_INFINITY;
        for (Shape shape : shapes) {
            Vector viewportVector = viewport.getVector(getPixel(x, y));
            Line rayFromViewToShape = new Line(viewpoint, viewportVector);
            Vector pointOnShape = shape.nearestIntersect(rayFromViewToShape);
            if (viewpoint.distance(pointOnShape) < distanceToClosestShape) {
                closestShape = shape;
                distanceToClosestShape = viewpoint.distance(pointOnShape);
            }
        }
        updateBrightnessAtPixelForShape(x, y, closestShape);
    }

    public void updateBrightnessAtPixelForShape(int x, int y, Shape shape) {
        Vector viewportVector = viewport.getVector(getPixel(x, y));
        Line rayFromViewToShape = new Line(viewpoint, viewportVector);
        Vector pointOnShape = shape.nearestIntersect(rayFromViewToShape);
        for (int i = 0; i < lightsources.size(); i++) {
            Vector locationOfLight = lightsources.get(i).location;
            Line rayFromShapeToLightSource = new Line(pointOnShape, locationOfLight);
            Vector[] intersectionsWithShape = shape.intersect(rayFromShapeToLightSource);
            double distanceFromPointToLight = pointOnShape.distance(locationOfLight);

            Boolean canSeeLight = true;
            for (Vector intersection : intersectionsWithShape) {
                double distance = intersection.distance(locationOfLight);
                if (distanceFromPointToLight - intersection.distance(locationOfLight) > 1e-13) {
                    canSeeLight = false;
                }
            }
            if (canSeeLight) {
                viewport.getPixel(x, y).addToBrightness(lightsources.get(i));
            }
        }
    }
}
