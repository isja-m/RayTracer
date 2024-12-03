public class Vector {
    final double xCoord;
    final double yCoord;
    final double zCoord;

    Vector(double xCoord, double yCoord, double zCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
    }

    public double norm() {
        return Math.sqrt(squaredNorm());
    }

    public double squaredNorm() {
        return xCoord*xCoord + yCoord*yCoord + zCoord*zCoord;
    }

    public double dotProduct(Vector vector2) {
        return this.xCoord*vector2.xCoord + this.yCoord*vector2.yCoord + this.zCoord*vector2.zCoord;
    }
    
    public Boolean equals(Vector vector2) {
        return Math.abs(this.xCoord - vector2.xCoord) < 1e-13 && Math.abs(this.yCoord - vector2.yCoord) < 1e-13 
            && Math.abs(this.zCoord - vector2.zCoord) < 1e-13;
    }

    public Vector add(Vector vector2) {
        return new Vector(this.xCoord + vector2.xCoord, this.yCoord + vector2.yCoord, this.zCoord + vector2.zCoord);
    }

    public Vector scalarMultiple(double lambda) {
        return new Vector(this.xCoord * lambda, this.yCoord * lambda, this.zCoord * lambda);
    }

    public double distance(Vector vector2) {
        return this.add(vector2.scalarMultiple(-1)).norm();
    }
}
