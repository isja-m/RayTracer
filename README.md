To run the project, first build with
```console
C:...\RayTracer> ./gradlew build
```

Then run
```console
C:...\RayTracer\src\main\java> java main.java
```

The main class contains a number of methods that setup example scenes.
To view them, simply run one of the methods in the 'main' method.

To create your own scene, first create a Viewport object by supplying 3 of the corners as vectors and indixate the horizontal and vertical resulution.
```java
Viewport viewport = new Viewport(bottomLeftCorner, topLeftCorner, bottomRightCorner, screenWidth, screenHeight);
```
Make sure that the aspect ratio of the rectangle defined by the given corners matches the indicated resolution, otherwise the image will be stretched.
The first corner should be the bottom left corner, the second the top left and the third the bottom right.
We read the coordinates as positive y being the 'up' direction and x and z being horizontal as follows:
```text
     +x
     |
-z --+-- +z
     |
    -x
```
Next create a Vector object as viewpoint, a Shape object (currently only spheres are implemented) and a Lightsource object.
Use the Viewport, Vector, Shape and Lightsource objects to create a Scene object.
```java
Scene scene = new Scene(lightsource, shape, viewpoint, viewport);
```
You can add more shapes and lightsources as follows:
```java
scene.addShape(shape);
scene.addLightsource(lightsource);
```
Next you must create a Renderer object using the created Scene object.
The image can be manually updated as follows:
```java
scene.updateBrightness()
renderer.update();
```
You can also choose to create a CameraOperator object to handle image updating for you, as well as allowing you to manually move and rotate the camera.
```java
CameraOperator cameraOperator = new CameraOperator(scene, renderer, rotationSpeed, strafeSpeed);
```
If you want you can move the camera in code, by using the 'Scene.cameraStrafe', 'Scene.cameraRotate' and 'Scene.cameraPivotAroundPoint' methods.
