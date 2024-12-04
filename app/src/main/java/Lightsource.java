public class Lightsource {
    final Vector location;
    final Vector brightnesses;

    public Lightsource(Vector location, double redBrightness, double greenBrightness, double blueBrightness) {
        this.location = location;
        this.brightnesses = new Vector(redBrightness, greenBrightness, blueBrightness);
    }

    public Lightsource(double x, double y, double z, double redBrightness, double greenBrightness, double blueBrightness) {
        this(new Vector(x,y,z), redBrightness, greenBrightness, blueBrightness);
    }
}
