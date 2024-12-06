
public class main {
    public static void main(String[] args) {
        renderScene2();
    }

    private static void renderScene1() {
        Vector corner1 = new Vector(-2, -1, -1);
        Vector corner2 = new Vector(-2, 1, -1);
        Vector corner3 = new Vector(-2, -1, 1);
        Viewport viewport = new Viewport(corner1, corner2, corner3, 1000, 1000);
        Vector viewpoint = new Vector(-3, 0, 0);

        Sphere sphere1 = new Sphere(2,0,0, 1, 0.5);
        Sphere sphere2 = new Sphere(2,2,0,1, 0.2);
        Sphere sphere3 = new Sphere(2, -2, 0, 1, 0.8);
        Sphere sphere4 = new Sphere(0.5, 0, 1.5, 0.3, 0.8);
        Sphere sphere5 = new Sphere(20, 0,0,15,0.5);
        Sphere sphere6 = new Sphere(-3,2,0,1,0.5);

        Lightsource lightsource1 = new Lightsource(-2, 0, 4, 100,0,0);
        Lightsource lightsource2 = new Lightsource(-2, -2, -2, 0, 100, 0);
        Lightsource lightsource3 = new Lightsource(-2, 2, -2, 0, 0, 100);

        Scene scene = new Scene(lightsource1, sphere1, viewpoint, viewport);
        scene.addShape(sphere2);
        scene.addShape(sphere3);
        scene.addShape(sphere4);
        scene.addShape(sphere5); // Background sphere
        scene.addShape(sphere6);

        scene.addLightsource(lightsource2);
        scene.addLightsource(lightsource3);

        Renderer renderer = new Renderer(scene);
        CameraOperator cameraOperator = new CameraOperator(scene, renderer, Math.PI/8, 0.4);
        cameraOperator.run();
    }

    private static void renderScene2() {
        Vector corner1 = new Vector( -400.0, -300.0, 50.0);
        Vector corner2 = new Vector(-400.0, 300.0, 50.0);
        Vector corner3 = new Vector( 400.0, -300.0, 50.0);
        Vector viewpoint = new Vector(0, 0, 1000.0);
        Viewport viewport = new Viewport(corner1, corner2, corner3, 800, 600);

        Sphere sphere1 = new Sphere(new Vector(0.0, 0.0, 100.0), 200.0, 0.5);
        Sphere sphere2 = new Sphere(new Vector(100.0, 150.0, 130.0), 50, 0.5);
        Lightsource lightsource1= new Lightsource(new Vector(500.0, 500.0, 155.0), 100, 100, 100);
        Lightsource lightsource2 = new Lightsource(new Vector(500.0, -100.0, 75.0), 50, 50, 50);

        Scene scene = new Scene(lightsource1, sphere1, viewpoint, viewport);
        scene.addShape(sphere2);
        scene.addLightsource(lightsource2);

        Renderer renderer = new Renderer(scene);
        CameraOperator cameraOperator = new CameraOperator(scene, renderer, Math.PI/16, 20);
        cameraOperator.run();
    }

    private static void renderScene3() {
        Vector corner1 = new Vector(-4, -3, -3);
        Vector corner2 = new Vector(-4, 3, -3);
        Vector corner3 = new Vector(-4, -3, 3);
        Viewport viewport = new Viewport(corner1, corner2, corner3, 480, 640);
        Vector viewpoint = new Vector(-6, 0, 0);

        Sphere sphere1 = new Sphere(0,0,0, 1, 0.5);
        Sphere sphere2 = new Sphere(0,0,-3,2,0.5);
        Sphere sphere3 = new Sphere(20, 0,0,15,0.5);
        Lightsource lightsource1 = new Lightsource(0, 0, -6, 100,0,0);
        Lightsource lightsource2 = new Lightsource(-2, 3,0, 0, 100, 0);

        Scene scene = new Scene(lightsource1, sphere1, viewpoint, viewport);
        scene.addShape(sphere2);
        scene.addShape(sphere3);
        scene.addLightsource(lightsource2);
        scene.updateBrightness();

        Renderer renderer = new Renderer(scene);
        renderer.update();
    }

    private static void renderScene4() {
        Vector corner1 = new Vector(2, -1, -1);
        Vector corner2 = new Vector(2, 1, -1);
        Vector corner3 = new Vector(2, -1, 1);
        Viewport viewport = new Viewport(corner1, corner2, corner3, 1000, 1000);
        Vector viewpoint = new Vector(0, 0, 0);

        Sphere sphere1 = new Sphere(4,0,0, 1, 0.5);
        Sphere sphere2 = new Sphere(0,0,4,1, 0.5);
        Sphere sphere3 = new Sphere(-4, 0, 0, 1, 0.5);
        Sphere sphere4 = new Sphere(0, 0, -4, 1, 0.5);

        Sphere sphere5 = new Sphere(4,0,4, 1, 0.5);
        Sphere sphere6 = new Sphere(-4,0,4,1, 0.5);
        Sphere sphere7 = new Sphere(-4, 0, -4, 1, 0.5);
        Sphere sphere8 = new Sphere(4, 0, -4, 1, 0.5);

        Lightsource lightsource1 = new Lightsource(-1, 1, 0, 100,0,0);
        Lightsource lightsource2 = new Lightsource(0, 1, 1, 0, 100, 0);
        Lightsource lightsource3 = new Lightsource(1, 1, 0, 0, 0, 100);

        Scene scene = new Scene(lightsource1, sphere1, viewpoint, viewport);
        scene.addShape(sphere2);
        scene.addShape(sphere3);
        scene.addShape(sphere4);
        scene.addShape(sphere5);
        scene.addShape(sphere6);
        scene.addShape(sphere7);
        scene.addShape(sphere8);

        scene.addLightsource(lightsource2);
        scene.addLightsource(lightsource3);

        scene.updateBrightness();

        Renderer renderer = new Renderer(scene);
        renderer.update();
        for (int i = 0; i < 100; i++) {
            scene.cameraStrafe(0, 0, 0);
            scene.cameraRotate(Math.PI/40, 0);
            scene.updateBrightness();
            renderer.update();
        }
        for (int i = 0; i < 10; i++) {
            scene.cameraStrafe(0, 0, 0);
            scene.cameraRotate(-Math.PI/40, 0);
            scene.updateBrightness();
            renderer.update();
        }
    }

    private static void renderScene5() {
        Vector corner1 = new Vector(2, -1, -1);
        Vector corner2 = new Vector(2, 1, -1);
        Vector corner3 = new Vector(2, -1, 1);
        Viewport viewport = new Viewport(corner1, corner2, corner3, 1000, 1000);
        Vector viewpoint = new Vector(0, 0, 0);

        Sphere sphere1 = new Sphere(4,0,0, 1, 0.5);
        Sphere sphere2 = new Sphere(0,4,0,1, 0.5);
        Sphere sphere3 = new Sphere(-4, 0, 0, 1, 0.5);
        Sphere sphere4 = new Sphere(0, -4, 0, 1, 0.5);

        Sphere sphere5 = new Sphere(4,4,0, 1, 0.5);
        Sphere sphere6 = new Sphere(-4,4,0,1, 0.5);
        Sphere sphere7 = new Sphere(-4, -4, 0, 1, 0.5);
        Sphere sphere8 = new Sphere(4, -4, 0, 1, 0.5);

        Lightsource lightsource1 = new Lightsource(-1, 0, 0, 100,0,0);
        Lightsource lightsource2 = new Lightsource(0, 0, 1, 0, 100, 0);
        Lightsource lightsource3 = new Lightsource(-1, 0, 0, 0, 0, 100);

        Scene scene = new Scene(lightsource1, sphere1, viewpoint, viewport);
        scene.addShape(sphere2);
        scene.addShape(sphere3);
        scene.addShape(sphere4);
        scene.addShape(sphere5);
        scene.addShape(sphere6);
        scene.addShape(sphere7);
        scene.addShape(sphere8);

        scene.addLightsource(lightsource2);
        scene.addLightsource(lightsource3);

        scene.updateBrightness();

        Renderer renderer = new Renderer(scene);
        renderer.update();
        for (int i = 0; i < 5; i++) {
            scene.cameraStrafe(0, 0, 0);
            scene.cameraRotate(0, Math.PI/40);
            scene.updateBrightness();
            renderer.update();
        }
        for (int i = 0; i < 10; i++) {
            scene.cameraStrafe(0, 0, 0);
            scene.cameraRotate(0, -Math.PI/40);
            scene.updateBrightness();
            renderer.update();
        }
    }

    private static void renderScene6() {
        Vector corner1 = new Vector(10, -1, -1);
        Vector corner2 = new Vector(10, 1, -1);
        Vector corner3 = new Vector(10, -1, 1);
        Viewport viewport = new Viewport(corner1, corner2, corner3, 1000, 1000);
        Vector viewpoint = new Vector(15, 0, 0);

        Sphere sphere1 = new Sphere(0,0,0, 1, 0.5);
        Sphere sphere2 = new Sphere(3,-1,0,0.3, 0.5);
        Sphere sphere3 = new Sphere(-3, 1, 0, 1, 0.5);

        Lightsource lightsource1 = new Lightsource(-5, -1, 0, 100,0,0);
        Lightsource lightsource2 = new Lightsource(0, 5, 0, 0, 100, 0);
        Lightsource lightsource3 = new Lightsource(5, -1, 0, 0, 0, 100);

        Scene scene = new Scene(lightsource1, sphere1, viewpoint, viewport);
        scene.addShape(sphere2);
        scene.addShape(sphere3);

        scene.addLightsource(lightsource2);
        scene.addLightsource(lightsource3);

        scene.updateBrightness();

        Renderer renderer = new Renderer(scene);
        renderer.update();
        for (int i = 0; i < 5; i++) {
            scene.cameraPivotAroundPoint(new Vector(0, 0, 0), 0, Math.PI/40);
            scene.updateBrightness();
            renderer.update();
        }
        for (int i = 0; i < 10; i++) {
            scene.cameraPivotAroundPoint(new Vector(0, 0, 0), 0, -Math.PI/40);
            scene.updateBrightness();
            renderer.update();
        }
    }


}
