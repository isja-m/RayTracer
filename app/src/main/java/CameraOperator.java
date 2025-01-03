import java.util.Scanner;

public class CameraOperator {
    private Scanner scanner;
    private Scene scene;
    private Renderer renderer;
    private double strafeSpeed;
    private double rotateSpeed;

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

        boolean running = true;
        String input;

        System.out.println("Please enter 1 character at a time.");
        System.out.println("Use 'a' and 'd' to strafe left and right.");
        System.out.println("Use 'w' and 's' to strafe forward and backward.");
        System.out.println("Use 'q' and 'e' to strafe down and up.");
        System.out.println("Use 'j' and 'l' to rotate left and right.");
        System.out.println("Use 'i' and 'k' to rotate up and down.");
        System.out.println("Close the window to stop the program.");

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
        Vector strafeDirection = scene.getHorizontalDirection().normalize();
        scene.cameraStrafe(strafeDirection.scalarMultiple(-strafeSpeed));
        updateScene();
    }

    private void strafeRight() {
        Vector strafeDirection = scene.getHorizontalDirection().normalize();
        scene.cameraStrafe(strafeDirection.scalarMultiple(strafeSpeed));
        updateScene();
    }

    private void strafeDown() {
        Vector strafeDirection = scene.getVerticalDirection().normalize();
        scene.cameraStrafe(strafeDirection.scalarMultiple(strafeSpeed));
        updateScene();
    }

    private void strafeUp() {
        Vector strafeDirection = scene.getVerticalDirection().normalize();
        scene.cameraStrafe(strafeDirection.scalarMultiple(-strafeSpeed));
        updateScene();
    }

    private void strafeForward() {
        Vector strafeDirection = scene.getVerticalDirection().crossProduct(scene.getHorizontalDirection()).normalize();
        scene.cameraStrafe(strafeDirection.scalarMultiple(strafeSpeed));
        updateScene();
    }

    private void strafeBackward() {
        Vector strafeDirection = scene.getVerticalDirection().crossProduct(scene.getHorizontalDirection()).normalize();
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
