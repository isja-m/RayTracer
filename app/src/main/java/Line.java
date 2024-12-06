class Line {
    final Vector point1;
    final Vector point2;

    Line(Vector point1, Vector point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public ParametricLine getParametricLine() {
        Vector direction = point2.subtract(point1);
        double normalizationFactor = 1/direction.norm();
        Vector normalizedDirection = direction.scalarMultiple(normalizationFactor);
        return new ParametricLine(point1, normalizedDirection);
    }

    public Intersection findIntersection(Line line2) {
        return this.getParametricLine().findIntersection(line2.getParametricLine());
    }
}
