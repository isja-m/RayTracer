class Intersection {
    final ParametricLine line1;
    final ParametricLine line2;
    final Vector intersectionPoint;
    final double angle;

    Intersection(double angle, ParametricLine line1, ParametricLine line2, Vector intersectionPoint) {
        this.angle = angle;
        this.line1 = line1;
        this.line2 = line2;
        this.intersectionPoint = intersectionPoint;
    }
}