public class PixelUpdateThread implements Runnable {
    Scene scene;
    Thread thread;
    int xStart;
    int xEnd;

    public PixelUpdateThread(Scene scene, int xStart, int xEnd) {
        this.scene = scene;
        this.xEnd = xEnd;
        this.xStart = xStart;
    }

    public void run() {
        for (int i = xStart; i < xEnd; i++) {
            for (int j = 0; j < scene.getScreenHeight(); j++) {
                updateBrightnessAtPixel(i,j);
            }
        }
    }

    public void join(){
        try {
            thread.join();
        } catch (Exception e) {
        }
    }
    
    public void start () {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void updateBrightnessAtPixel(int x, int y) {
        Boolean rayHitsSomeShape = false;
        Shape closestShape = scene.shapes.get(0);
        double distanceToClosestShape = Double.POSITIVE_INFINITY;
        Vector viewportVector = scene.viewport.getVector(scene.getPixel(x, y));
        Line finalRayFromViewToShape = null;
        Vector finalPointOnShape = null;

        // Find closest shape.
        for (Shape shape : scene.shapes) {
            Line rayFromViewToShape = new Line(scene.viewpoint, viewportVector);
            Vector pointOnShape = shape.nearestIntersect(rayFromViewToShape);
            rayHitsSomeShape = rayHitsSomeShape || !Double.isNaN(pointOnShape.xCoord);
            if (scene.viewpoint.distance(pointOnShape) < distanceToClosestShape) {
                closestShape = shape;
                distanceToClosestShape = scene.viewpoint.distance(pointOnShape);
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
        for (int i = 0; i < scene.lightsources.size(); i++) {
            Vector locationOfLight = scene.lightsources.get(i).location;
            Line rayFromShapeToLightSource = new Line(pointOnShape, locationOfLight);
            Vector[] intersectionsWithShape = shape.intersect(rayFromShapeToLightSource);
            double distanceFromPointToLight = pointOnShape.distance(locationOfLight);

            Boolean canSeeLight = true;
            canSeeLight = shapeIsNotBlockingLight(locationOfLight, intersectionsWithShape, distanceFromPointToLight, canSeeLight);
            
            canSeeLight = otherShapeIsNotBlockingLight(locationOfLight, rayFromShapeToLightSource, distanceFromPointToLight, canSeeLight);

            if (canSeeLight) {
                addBrightness(x, y, shape, pointOnShape, scene.lightsources.get(i).brightnesses, rayFromShapeToLightSource);
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
        for (Shape otherShape : scene.shapes) {
            Vector[] intersections = otherShape.intersect(rayFromShapeToLightSource);
            canSeeLight = shapeIsNotBlockingLight(locationOfLight, intersections, distanceFromPointToLight, canSeeLight);
        }
        return canSeeLight;
    }

    private void addBrightness(int x, int y, Shape shape, Vector pointOnShape, Vector brightnesses, Line rayFromShapeToLightSource) {
        float diffusalFactor = shape.getDiffuseCoefficient();
        diffusalFactor *= pointOnShape.perpendicularVector(shape).dotProduct(rayFromShapeToLightSource.getParametricLine().direction);
        diffusalFactor = Math.max(0, diffusalFactor);
        scene.viewport.getPixel(x, y).addToBrightness(brightnesses.scalarMultiple(diffusalFactor));
}
}
