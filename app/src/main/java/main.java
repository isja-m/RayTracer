/* TODO:
 * Improve code coverage
 * Polyhedra
 *  - Cube
 *  - Diamonds (Octahedron, etc.)
 *  - Dodecahedron
 *  - Icosahedron
 * Refactoring
 *  - public/private/package-private
 * Update readme
 * Shape stretching
 * Light gets dimmer at longer distances
 * Better filemanagement
 */

public class main {
    public static void main(String[] args) throws InvalidGeometryException {
        polyhedraManual1();
    }

    private static void spheresManual1() {
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

        Renderer renderer = new Renderer(scene, true);
        CameraOperator cameraOperator = new CameraOperator(scene, renderer, Math.PI/8, 0.4);
        cameraOperator.run();
    }

    private static void spheresManual2() {
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

        Renderer renderer = new Renderer(scene, true);
        CameraOperator cameraOperator = new CameraOperator(scene, renderer, Math.PI/16, 20);
        cameraOperator.run();
    }

    private static void spheresStatic1() {
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

        Renderer renderer = new Renderer(scene, true);
        renderer.update();
    }

    private static void spheresAnimated1() {
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

        Renderer renderer = new Renderer(scene, true);
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

    private static void spheresAnimated2() {
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

        Renderer renderer = new Renderer(scene, true);
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

    private static void spheresAnimated3() {
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

        Renderer renderer = new Renderer(scene, true);
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

    private static void trianglesManual1() {
        Vector corner1 = new Vector(-2, -1, -1);
        Vector corner2 = new Vector(-2, 1, -1);
        Vector corner3 = new Vector(-2, -1, 1);
        Viewport viewport = new Viewport(corner1, corner2, corner3, 1000, 1000);
        Vector viewpoint = new Vector(-3, 0, 0);

        Vector vector1 = new Vector(0, 0, 0);
        Vector vector2 = new Vector(0, 1, 0);
        Vector vector3 = new Vector(0, 0, 1);
        Vector vector4 = new Vector(1, 1, 1);

        Triangle triangle1 = new Triangle(vector1, vector2, vector3, 0.5);
        Triangle triangle2 = new Triangle(vector4, vector2, vector3, 0.5);
        Triangle triangle3 = new Triangle(vector1, vector4, vector3, 0.5);
        Triangle triangle4 = new Triangle(vector1, vector2, vector4, 0.5);

        Sphere sphere1 = new Sphere(vector1, 0.2, 0.5);
        Sphere sphere2 = new Sphere(vector2, 0.2, 0.5);
        Sphere sphere3 = new Sphere(vector3, 0.2, 0.5);
        Sphere sphere4 = new Sphere(vector4, 0.2, 0.5);

        Lightsource lightsource1 = new Lightsource(-2, 0, 4, 100,0,0);
        Lightsource lightsource2 = new Lightsource(-2, -2, -2, 0, 100, 0);
        Lightsource lightsource3 = new Lightsource(-2, 2, -2, 0, 0, 100);

        Scene scene = new Scene(lightsource1, sphere1, viewpoint, viewport);
        scene.addShape(triangle1);
        scene.addShape(triangle2);
        scene.addShape(triangle3);
        scene.addShape(triangle4);
        scene.addShape(sphere2);
        scene.addShape(sphere3);
        scene.addShape(sphere4);

        scene.addLightsource(lightsource2);
        scene.addLightsource(lightsource3);

        Renderer renderer = new Renderer(scene, true);
        CameraOperator cameraOperator = new CameraOperator(scene, renderer, Math.PI/8, 0.4);
        cameraOperator.run();
    }

    private static void polygonsManual1() throws InvalidGeometryException {
        Vector corner1 = new Vector(-2, -1, -1);
        Vector corner2 = new Vector(-2, 1, -1);
        Vector corner3 = new Vector(-2, -1, 1);
        Viewport viewport = new Viewport(corner1, corner2, corner3, 1000, 1000);
        Vector viewpoint = new Vector(-3, 0, 0);

        Vector vector1 = new Vector(0, 0, -2);
        Vector vector2 = new Vector(0, 0, 0);
        Vector vector3 = new Vector(0, 0, 2);
        Vector vector4 = new Vector(15, 0, 0);
        Vector firstCorner1 = new Vector(0, 1, 0);

        Vector normalVector1 = new Vector(-1, 0, 0);

        Polygon polygon1 = new Polygon(0.5, vector1, firstCorner1.add(vector1), normalVector1, 4);
        Polygon polygon2 = new Polygon(0.5, vector2, firstCorner1.add(vector2), normalVector1, 5);
        Polygon polygon3 = new Polygon(0.5, vector3, firstCorner1.add(vector3), normalVector1, 6);

        Sphere sphere1 = new Sphere(vector4, 10, 0.5);

        Lightsource lightsource1 = new Lightsource(-2, 0, 4, 100,0,0);
        Lightsource lightsource2 = new Lightsource(-2, -2, -2, 0, 100, 0);
        Lightsource lightsource3 = new Lightsource(-2, 2, -2, 0, 0, 100);

        Scene scene = new Scene(lightsource1, polygon1, viewpoint, viewport);
        scene.addShape(polygon2);
        scene.addShape(polygon3);
        scene.addShape(sphere1);

        scene.addLightsource(lightsource2);
        scene.addLightsource(lightsource3);

        Renderer renderer = new Renderer(scene, true);
        CameraOperator cameraOperator = new CameraOperator(scene, renderer, Math.PI/8, 0.4);
        cameraOperator.run();
    }

    private static void polyhedraManual1() throws InvalidGeometryException {
        Vector corner1 = new Vector(-2, -1, -1);
        Vector corner2 = new Vector(-2, 1, -1);
        Vector corner3 = new Vector(-2, -1, 1);
        Viewport viewport = new Viewport(corner1, corner2, corner3, 1000, 1000);
        Vector viewpoint = new Vector(-3, 0, 0);

        Vector topVector1 = new Vector(0, 2, -3);
        Vector topVector2 = new Vector(0, 2, 0);
        Vector topVector3 = new Vector(0, 2, 3);
        Vector baseCentre1 = new Vector(0, 0, -2);
        Vector baseCentre2 = new Vector(0, 0, 0);
        Vector baseCentre3 = new Vector(0, 0, 2);
        Vector firstBaseCorner1 = new Vector(0, 0, 1);
        Vector sphereCentre1 = new Vector(15, 0, 0);

        Vector normalVector1 = new Vector(0, 1, 0);

        float diffuseCoefficient = 0.5f;

        Polygon base1 = new Polygon(diffuseCoefficient, baseCentre1, firstBaseCorner1.add(baseCentre1), normalVector1, 4);
        Polygon base2 = new Polygon(diffuseCoefficient, baseCentre2, firstBaseCorner1.add(baseCentre2), normalVector1, 5);
        Polygon base3 = new Polygon(diffuseCoefficient, baseCentre3, firstBaseCorner1.add(baseCentre3), normalVector1, 6);

        Pyramid pyramid1 = new Pyramid(topVector1, base1, diffuseCoefficient);
        Pyramid pyramid2 = new Pyramid(topVector2, base2, diffuseCoefficient);
        Pyramid pyramid3 = new Pyramid(topVector3, base3, diffuseCoefficient);

        Vector firstCorner1 = new Vector(0, 1, 0);
        Vector testNormalVector1 = new Vector(-1, 0, 0);
        Vector vector1 = new Vector(0, 0, 0);
        Polygon polygon1 = new Polygon(0.5, vector1, firstCorner1.add(vector1), testNormalVector1, 4);
        Polyhedron testPolyhedron = new Polyhedron(new Polygon[] {polygon1}, diffuseCoefficient);

        Sphere sphere1 = new Sphere(sphereCentre1, 10, diffuseCoefficient);

        Lightsource lightsource1 = new Lightsource(-2, 0, 4, 100,0,0);
        Lightsource lightsource2 = new Lightsource(-2, -2, -2, 0, 100, 0);
        Lightsource lightsource3 = new Lightsource(-2, 2, -2, 0, 0, 100);

        Scene scene = new Scene(lightsource1, sphere1, viewpoint, viewport);
        scene.addShape(pyramid1);
        scene.addShape(pyramid2);
        scene.addShape(pyramid3);
        // scene.addShape(testPolyhedron);
        // scene.addShape(polygon1);

        scene.addLightsource(lightsource2);
        scene.addLightsource(lightsource3);

        Renderer renderer = new Renderer(scene, true);
        CameraOperator cameraOperator = new CameraOperator(scene, renderer, Math.PI/8, 0.4);
        cameraOperator.run();
    }


}
