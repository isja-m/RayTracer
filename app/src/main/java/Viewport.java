public class Viewport {
    final Vector corner1;
    final Vector corner2;
    final Vector verticalDirection;
    final Vector corner3;
    final Vector horizontalDirection;
    final int screenWidth;
    final int screenHeight;
    private Pixel[][] pixels;

    public Viewport(Vector corner1, Vector corner2, Vector corner3, int screenWidth, int screenHeight) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.verticalDirection = corner2.add(corner1.scalarMultiple(-1));
        this.corner3 = corner3;
        this.horizontalDirection = corner3.add(corner1.scalarMultiple(-1));
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        generatePixels(screenWidth, screenHeight);
    }

    public void generatePixels(int screenWidth, int screenHeight) {
        pixels = new Pixel[screenWidth][screenHeight];
        for (int i = 0; i < screenWidth; i++) {
            for (int j = 0; j < screenHeight; j++) {
                pixels[i][j] = new Pixel(i, j);
            }
        }
    }

    public Vector getVector(Pixel pixel) {
        double verticalScalar = (double)pixel.yCoord/(double)screenHeight;
        Vector verticalStep = verticalDirection.scalarMultiple(verticalScalar);
        double horizontalScalar = (double)pixel.xCoord/(double)screenWidth;
        Vector horizontalStep = horizontalDirection.scalarMultiple(horizontalScalar);
        return corner1.add(horizontalStep).add(verticalStep);
    }

    public Pixel getPixel(int x, int y) {
        return pixels[x][y];
    }
}
