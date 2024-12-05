import java.util.ArrayList;

public class Scene {
    Vector viewpoint;
    Viewport viewport;
    ArrayList<Lightsource> lightsources;
    ArrayList<Shape> shapes;
    final private int threadCount = 16;

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

    public void cameraStrafe(double xDireciton, double yDirection, double zDirection) {
        Vector strafeDirection = new Vector(xDireciton, yDirection, zDirection);
        viewpoint = viewpoint.add(strafeDirection);
        Vector corner1 = viewport.corner1.add(strafeDirection);
        Vector corner2 = viewport.corner2.add(strafeDirection);
        Vector corner3 = viewport.corner3.add(strafeDirection);
        viewport = new Viewport(corner1, corner2, corner3, viewport.screenWidth, viewport.screenHeight);
    }

    // public void cameraPivot(double rightRotate, double upRotate) {

    // }

    public void updateBrightness(){
        int numberOfCols = (int)Math.ceil((double)getScreenHeight()/(double)threadCount);
        PixelUpdateThread[] threads = new PixelUpdateThread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            PixelUpdateThread pixelThread = new PixelUpdateThread(this, i*numberOfCols,Math.min((i+1)*numberOfCols, getScreenWidth()));
            pixelThread.start();
            threads[i] = pixelThread;
        }

        // Wait for all threads to finish
        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }
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
