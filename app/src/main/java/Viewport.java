public class Viewport {
    final Vector bottomLeftCorner;
    final Vector topLeftCorner;
    final Vector verticalDirection;
    final Vector bottomRightCorner;
    final Vector horizontalDirection;
    final int screenWidth;
    final int screenHeight;
    private Pixel[][] pixels;

    public Viewport(Vector bottomLeftCorner, Vector topLeftCorner, Vector bottomRightCorner, int screenWidth, int screenHeight) {
        this.bottomLeftCorner = bottomLeftCorner;
        this.topLeftCorner = topLeftCorner;
        this.verticalDirection = topLeftCorner.subtract(bottomLeftCorner);
        this.bottomRightCorner = bottomRightCorner;
        this.horizontalDirection = bottomRightCorner.subtract(bottomLeftCorner);
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
        return bottomLeftCorner.add(horizontalStep).add(verticalStep);
    }

    public Pixel getPixel(int x, int y) {
        return pixels[x][y];
    }
}
