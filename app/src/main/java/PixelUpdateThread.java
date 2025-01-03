class PixelUpdateThread implements Runnable {
    private Scene scene;
    private Thread thread;
    private int xStart;
    private int xEnd;

    PixelUpdateThread(Scene scene, int xStart, int xEnd) {
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

    void join(){
        try {
            thread.join();
        } catch (Exception e) {
        }
    }
    
    void start () {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    private void updateBrightnessAtPixel(int x, int y) {
        Vector viewportVector = scene.getVectorAtPixel(x, y);
        boolean rayHitsSomeShape = false;

        Shape closestShape = scene.getShapes().get(0);
        double distanceToClosestShape = Double.POSITIVE_INFINITY;

        Line finalRayFromViewToShape = null;
        Vector finalPointOnShape = null;

        // Find closest shape.
        for (Shape shape : scene.getShapes()) {
            Line rayFromViewToShape = new Line(scene.getViewpoint(), viewportVector);
            Vector pointOnShape = shape.nearestIntersect(rayFromViewToShape);
            rayHitsSomeShape = rayHitsSomeShape || !Double.isNaN(pointOnShape.xCoord);
            if (scene.getViewpoint().distance(pointOnShape) < distanceToClosestShape) {
                closestShape = shape;
                distanceToClosestShape = scene.getViewpoint().distance(pointOnShape);
                finalRayFromViewToShape = rayFromViewToShape;
                finalPointOnShape = pointOnShape;
            }
        }

        if (rayHitsSomeShape) {
            updateBrightnessAtPixelForShape(x, y, closestShape, viewportVector, finalRayFromViewToShape, finalPointOnShape);
        }
    }

    private void updateBrightnessAtPixelForShape(int x, int y, Shape shape, Vector viewportVector, Line rayFromViewToShape,
        Vector pointOnShape) {
        for (int i = 0; i < scene.getNumberOfLights(); i++) {
            Vector locationOfLight = scene.getLightsource(i).location;
            Line rayFromShapeToLightSource = new Line(pointOnShape, locationOfLight);
            double distanceFromPointToLight = pointOnShape.distance(locationOfLight);
            Vector[] intersectionsWithShape = shape.intersect(rayFromShapeToLightSource);

            boolean canSeeLight = true;
            canSeeLight = shapeIsNotBlockingLight(locationOfLight, rayFromShapeToLightSource, intersectionsWithShape, distanceFromPointToLight, canSeeLight, pointOnShape, shape, rayFromViewToShape);
            canSeeLight = otherShapeIsNotBlockingLight(locationOfLight, rayFromShapeToLightSource, distanceFromPointToLight, canSeeLight, pointOnShape, shape, rayFromViewToShape);

            if (canSeeLight) {
                addBrightness(x, y, shape, pointOnShape, scene.getLightsource(i).brightnesses, rayFromShapeToLightSource, rayFromViewToShape);
            }
        }
    }

    private boolean shapeIsNotBlockingLight(Vector locationOfLight, Line rayFromShapeToLightSource, Vector[] intersectionsWithShape, double distanceFromPointToLight,
            boolean canSeeLight, Vector pointOnShape, Shape shape, Line rayFromViewToShape) { // Requires different implementation for triangles.
        for (Vector intersection : intersectionsWithShape) {
            if ((distanceFromPointToLight - intersection.distance(locationOfLight))/distanceFromPointToLight > 1e-13) {
                canSeeLight = false;
            }
            if (intersection.distance(pointOnShape) < 1e-13 && rayFromShapeToLightSource.getParametricLine().direction.dotProduct(pointOnShape.normalVectorAtShape(shape, rayFromViewToShape)) < 0) {
                canSeeLight = false;
            }
        }
        return canSeeLight;
    }

    private boolean otherShapeIsNotBlockingLight(Vector locationOfLight, Line rayFromShapeToLightSource, double distanceFromPointToLight,
            boolean canSeeLight, Vector pointOnShape, Shape shape, Line rayFromViewToShape) {
        for (Shape otherShape : scene.getShapes()) {
            Vector[] intersections = otherShape.intersect(rayFromShapeToLightSource);
            canSeeLight = shapeIsNotBlockingLight(locationOfLight, rayFromShapeToLightSource, intersections, distanceFromPointToLight, canSeeLight, pointOnShape, shape, rayFromViewToShape);
        }
        return canSeeLight;
    }

    private void addBrightness(int x, int y, Shape shape, Vector pointOnShape, Vector brightnesses, Line rayFromShapeToLightSource, Line rayFromViewToShape) {
        float diffusalFactor = shape.getDiffuseCoefficient();
        float dotProductWithLight = (float)pointOnShape.normalVectorAtShape(shape, rayFromViewToShape).dotProduct(rayFromShapeToLightSource.getParametricLine().direction);
        diffusalFactor *= dotProductWithLight;
        diffusalFactor = Math.max(0, diffusalFactor);
        if (dotProductWithLight > 0) {
            scene.getPixel(x, y).addToBrightness(brightnesses.scalarMultiple(diffusalFactor));
        }
}
}
