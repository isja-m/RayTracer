public class Vector {
    final double xCoord;
    final double yCoord;
    final double zCoord;

    Vector(double xCoord, double yCoord, double zCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
    }

    double norm() {
        return Math.sqrt(squaredNorm());
    }

    double squaredNorm() {
        return this.dotProduct(this);
    }

    Vector normalize() {
        return this.scalarMultiple(1/this.norm());
    }

    double dotProduct(Vector vector2) {
        return this.xCoord*vector2.xCoord + this.yCoord*vector2.yCoord + this.zCoord*vector2.zCoord;
    }

    Vector crossProduct(Vector vector2) {
        double newXCoord = this.yCoord*vector2.zCoord - this.zCoord*vector2.yCoord;
        double newYCoord = this.zCoord*vector2.xCoord - this.xCoord*vector2.zCoord;
        double newZCoord = this.xCoord*vector2.yCoord - this.yCoord*vector2.xCoord;
        return new Vector(newXCoord, newYCoord, newZCoord);
    }
    
    boolean equals(Vector vector2) {
        return equals(vector2, 1e-13);
    }
    
    boolean equals(Vector vector2, double errorMargin) {
        return Math.abs(this.xCoord - vector2.xCoord) < errorMargin && Math.abs(this.yCoord - vector2.yCoord) < errorMargin 
            && Math.abs(this.zCoord - vector2.zCoord) < errorMargin;
    }

    Vector add(Vector vector2) {
        return new Vector(this.xCoord + vector2.xCoord, this.yCoord + vector2.yCoord, this.zCoord + vector2.zCoord);
    }

    Vector subtract(Vector vector2) {
        return this.add(vector2.scalarMultiple(-1));
    }

    Vector scalarMultiple(double lambda) {
        return new Vector(this.xCoord * lambda, this.yCoord * lambda, this.zCoord * lambda);
    }

    double distance(Vector vector2) {
        return this.subtract(vector2).norm();
    }

    Vector projectionToPlane(Vector perpendicularToPlane) {
        double scalar = this.dotProduct(perpendicularToPlane)/perpendicularToPlane.squaredNorm();
        return this.subtract(perpendicularToPlane.scalarMultiple(scalar));
    }

    Vector horizontalPivotAround(Vector pivotPoint, double angle) {
        double distanceToNewLocation = 2 * Math.sin(-angle/2);
        Vector direction = new Vector(pivotPoint.xCoord - xCoord, 0, pivotPoint.zCoord - zCoord).horizontalRotate(Math.PI/2 + angle/2);
        return this.add(direction.scalarMultiple(distanceToNewLocation));
    }

    Vector horizontalRotate(double angle) {
        double newXCoord = Math.cos(angle) * xCoord - Math.sin(angle) * zCoord;
        double newZCoord = Math.sin(angle) * xCoord + Math.cos(angle) * zCoord;
        return new Vector(newXCoord, yCoord, newZCoord);
    }

    Vector verticalPivotAround(Vector pivotPoint, Vector rotationAxis, double angle) {
        double distanceToNewLocation = 2 * Math.sin(-angle/2);
        Vector direction = pivotPoint.subtract(this).projectionToPlane(rotationAxis).verticalRotate(rotationAxis, Math.PI/2 + angle/2);
        return this.add(direction.scalarMultiple(distanceToNewLocation));
    }

    Vector verticalRotate(Vector rotationAxis, double angle) {
        Vector term1 = this.scalarMultiple(Math.cos(-angle));
        Vector term2 = rotationAxis.crossProduct(this).scalarMultiple(Math.sin(-angle));
        Vector term3 = rotationAxis.scalarMultiple(rotationAxis.dotProduct(this) * (1 - Math.cos(-angle)));
        return term1.add(term2).add(term3);
    }

    double determinant(Vector vector2, Vector vector3) {
        // Computes the determinant of the 3x3 matrix with this vector as first collumn and vector2 and vector3 as second and third columns respectively.
        return dotProduct(vector2.crossProduct(vector3));
    }
}
