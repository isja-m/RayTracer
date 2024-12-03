public class Pixel {
    final int xCoord;
    final int yCoord;
    private double redBrightness;
    private double greenBrightness;
    private double blueBrightness;

    public Pixel(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        redBrightness = 0;
        greenBrightness = 0;
        blueBrightness = 0;
    }

    public void addToBrightness(Lightsource lightsource) {
        this.redBrightness += lightsource.redBrightness;
        this.greenBrightness += lightsource.greenBrightness;
        this.blueBrightness += lightsource.blueBrightness;
    }

    public double[] getBrightness() {
        return new double[] {redBrightness, greenBrightness, blueBrightness};
    }
}
