import java.util.ArrayList;

public class Scene {
    private Vector viewpoint;
    private Viewport viewport;
    private ArrayList<Lightsource> lightsources;
    private ArrayList<Shape> shapes;
    final private int threadCount = 4;

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

    public void cameraStrafe(Vector strafeVector) {
        cameraStrafe(strafeVector.xCoord, strafeVector.yCoord, strafeVector.zCoord);
    }

    public void cameraStrafe(double xDireciton, double yDirection, double zDirection) {
        Vector strafeDirection = new Vector(xDireciton, yDirection, zDirection);
        viewpoint = viewpoint.add(strafeDirection);
        Vector corner1 = viewport.bottomLeftCorner.add(strafeDirection);
        Vector corner2 = viewport.topLeftCorner.add(strafeDirection);
        Vector corner3 = viewport.bottomRightCorner.add(strafeDirection);
        viewport = new Viewport(corner1, corner2, corner3, viewport.screenWidth, viewport.screenHeight);
    }

    public void cameraRotate(double rightRotate, double upRotate) {
        // Horizontal rotate.
        Vector newCorner1 = viewport.bottomLeftCorner.horizontalPivotAround(viewpoint, rightRotate);
        Vector newCorner2 = viewport.topLeftCorner.horizontalPivotAround(viewpoint, rightRotate);
        Vector newCorner3 = viewport.bottomRightCorner.horizontalPivotAround(viewpoint, rightRotate);
        
        // Vertical rotate.
        Vector verticalRotationAxis = viewport.horizontalDirection.normalize();
        newCorner1 = newCorner1.verticalPivotAround(viewpoint, verticalRotationAxis, upRotate);
        newCorner2 = newCorner2.verticalPivotAround(viewpoint, verticalRotationAxis, upRotate);
        newCorner3 = newCorner3.verticalPivotAround(viewpoint, verticalRotationAxis, upRotate);
        viewport = new Viewport(newCorner1, newCorner2, newCorner3, getScreenWidth(), getScreenHeight());
    }

    public void cameraPivotAroundPoint(Vector pivotPoint, double rightRotate, double upRotate) {
        // Horizontal pivot.
        Vector newCorner1 = viewport.bottomLeftCorner.horizontalPivotAround(pivotPoint, rightRotate);
        Vector newCorner2 = viewport.topLeftCorner.horizontalPivotAround(pivotPoint, rightRotate);
        Vector newCorner3 = viewport.bottomRightCorner.horizontalPivotAround(pivotPoint, rightRotate);
        viewpoint = viewpoint.horizontalPivotAround(pivotPoint, rightRotate);

        // Vertical pivot.
        Vector verticalRotationAxis = new Vector(pivotPoint.xCoord-viewpoint.xCoord, 0, pivotPoint.zCoord-viewpoint.zCoord);
        verticalRotationAxis = verticalRotationAxis.horizontalRotate(Math.PI/2);
        verticalRotationAxis = verticalRotationAxis.normalize();
        newCorner1 = newCorner1.verticalPivotAround(pivotPoint, verticalRotationAxis, -upRotate);
        newCorner2 = newCorner2.verticalPivotAround(pivotPoint, verticalRotationAxis, -upRotate);
        newCorner3 = newCorner3.verticalPivotAround(pivotPoint, verticalRotationAxis, -upRotate);
        viewpoint = viewpoint.verticalPivotAround(pivotPoint, verticalRotationAxis, -upRotate);
        viewport = new Viewport(newCorner1, newCorner2, newCorner3, getScreenWidth(), getScreenHeight());
    }

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

    public int getScreenHeight() {
        return viewport.screenHeight;
    }

    public int getScreenWidth() {
        return viewport.screenWidth;
    }

    public Vector getViewpoint() {
        return viewpoint;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public Vector getHorizontalDirection() {
        return viewport.horizontalDirection;
    }

    public Vector getVerticalDirection() {
        return viewport.verticalDirection;
    }

    public Vector getVectorAtPixel(int x, int y) {
        return viewport.getVector(getPixel(x, y));
    }

    public Pixel getPixel(int x, int y) {
        return viewport.getPixel(x,y);
    }

    public int getNumberOfLights() {
        return lightsources.size();
    }

    public Lightsource getLightsource(int i) {
        return lightsources.get(i);
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }
}
