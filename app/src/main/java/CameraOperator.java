import java.util.Scanner;

public class CameraOperator {
    Scanner scanner;
    Scene scene;
    Renderer renderer;
    double strafeSpeed;
    double rotateSpeed;

    public CameraOperator(Scene scene, Renderer renderer, double rotateSpeed, double strafeSpeed) {
        this.scanner = new Scanner(System.in);
        this.renderer = renderer;
        this.rotateSpeed = rotateSpeed;
        this.scene = scene;
        this.strafeSpeed = strafeSpeed;
    }

    public void run() {
        scene.updateBrightness();
        renderer.update();

        Boolean running = true;
        String input;

        while (running) {
            input = scanner.nextLine();
            if (input.equals("a")) {
                strafeLeft();
            } else if (input.equals("d")) {
                strafeRight();
            } else if (input.equals("q")) {
                strafeDown();
            } else if (input.equals("e")) {
                strafeUp();
            } else if (input.equals("w")) {
                strafeForward();
            } else if (input.equals("s")) {
                strafeBackward();
            } else if (input.equals("j")) {
                rotateLeft();
            } else if (input.equals("l")) {
                rotateRight();
            } else if (input.equals("k")) {
                rotateDown();
            } else if (input.equals("i")) {
                rotateUp();
            } else if (input.equals("quit")) {
                System.out.println("Goodbye!");
                running = false;
            }
        }
    }

    private void updateScene() {
        scene.updateBrightness();
        renderer.update();
    }

    private void strafeLeft() {
        Vector strafeDirection = scene.viewport.horizontalDirection.normalize();
        scene.cameraStrafe(strafeDirection.scalarMultiple(-strafeSpeed));
        updateScene();
    }

    private void strafeRight() {
        Vector strafeDirection = scene.viewport.horizontalDirection.normalize();
        scene.cameraStrafe(strafeDirection.scalarMultiple(strafeSpeed));
        updateScene();
    }

    private void strafeDown() {
        Vector strafeDirection = scene.viewport.verticalDirection.normalize();
        scene.cameraStrafe(strafeDirection.scalarMultiple(strafeSpeed));
        updateScene();
    }

    private void strafeUp() {
        Vector strafeDirection = scene.viewport.verticalDirection.normalize();
        scene.cameraStrafe(strafeDirection.scalarMultiple(-strafeSpeed));
        updateScene();
    }

    private void strafeForward() {
        Vector strafeDirection = scene.viewport.verticalDirection.crossProduct(scene.viewport.horizontalDirection).normalize();
        scene.cameraStrafe(strafeDirection.scalarMultiple(strafeSpeed));
        updateScene();
    }

    private void strafeBackward() {
        Vector strafeDirection = scene.viewport.verticalDirection.crossProduct(scene.viewport.horizontalDirection).normalize();
        scene.cameraStrafe(strafeDirection.scalarMultiple(-strafeSpeed));
        updateScene();
    }

    private void rotateLeft() {
        scene.cameraRotate(-rotateSpeed, 0);
        updateScene();
    }

    private void rotateRight() {
        scene.cameraRotate(rotateSpeed, 0);
        updateScene();
    }

    private void rotateDown() {
        scene.cameraRotate(0, -rotateSpeed);
        updateScene();
    }

    private void rotateUp() {
        scene.cameraRotate(0, rotateSpeed);
        updateScene();
    }
}
