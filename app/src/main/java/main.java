public class main {
    public static void main(String[] args) {
        Vector corner1 = new Vector(-2, -1, -1);
        Vector corner2 = new Vector(-2, 1, -1);
        Vector corner3 = new Vector(-2, -1, 1);
        Viewport viewport = new Viewport(corner1, corner2, corner3, 640, 640);
        Sphere sphere = new Sphere(new Vector(2,0,0), 1, 0.5);
        Vector viewpoint = new Vector(-3, 0, 0);
        Lightsource lightsource = new Lightsource(new Vector(-1, 2, -1), 100,0,0);
        Scene scene = new Scene(lightsource, sphere, viewpoint, viewport);
        scene.addShape(new Sphere(new Vector(2,2,0),1, 0.2));
        scene.addShape(new Sphere(new Vector(2, -2, 0), 1, 0.8));
        scene.addLightsource(new Lightsource(new Vector(0, -2, -2), 0, 100, 0));
        scene.addLightsource(new Lightsource(new Vector(0, -2, 2), 0, 0, 100));
        scene.updateBrightness();

        Renderer renderer = new Renderer(scene);
        renderer.update();
    }
}
