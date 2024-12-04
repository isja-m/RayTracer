public class PixelUpdateThread implements Runnable {
    Scene scene;
    Thread thread;
    int x;
    int y;

    public void run() {
        updateBrightnessAtPixel(x, y);
    }
    
    public void start () {
        System.out.println("Run at " + x + " " + y);
        if (thread == null) {
            thread = new Thread (this);
            thread.start ();
        }
    }

    public PixelUpdateThread(Scene scene, int x, int y) {
        this.scene = scene;
        this.x = x;
        this.y = y;
    }

    public void updateBrightnessAtPixel(int x, int y) {
        Boolean rayHitsSomeShape = false;
        Shape closestShape = scene.shapes.get(0);
        double distanceToClosestShape = Double.POSITIVE_INFINITY;
        Vector viewportVector = scene.viewport.getVector(scene.getPixel(x, y));
        Line finalRayFromViewToShape = null;
        Vector finalPointOnShape = null;
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
            for (Vector intersection : intersectionsWithShape) {
                double distance = intersection.distance(locationOfLight);
                if (distanceFromPointToLight - intersection.distance(locationOfLight) > 1e-13) {
                    canSeeLight = false;
                }
            }
            if (canSeeLight) {
                float diffusalFactor = shape.getDiffuseCoefficient();
                diffusalFactor *= pointOnShape.perpendicularVector(shape).dotProduct(rayFromShapeToLightSource.getParametricLine().direction);
                diffusalFactor = Math.max(0, diffusalFactor);
                scene.viewport.getPixel(x, y).addToBrightness(scene.lightsources.get(i).brightnesses.scalarMultiple(diffusalFactor));
            }
        }
    }
}
