public class ParametricLine {
    final Vector start;
    final Vector direction;

    ParametricLine(Vector start, Vector direction) {
        this.start = start;
        this.direction = direction;
    }
    public double angle(ParametricLine line2) {
        double dotProd = this.direction.dotProduct(line2.direction);
        double norm1 = this.direction.norm(); 
        double norm2 = line2.direction.norm(); 
        return Math.acos(dotProd/(norm1 * norm2));
    }

    public Intersection findIntersection(ParametricLine line2) {
        /* Calculate the scalar lambda that gives the intersection as 'this.origin + lambda * this.direction'.
         * The calculations are obtained by rearanging the the expression 'intersection = this.start + lambda * this.direction
         * = line2.start + mu * line2.direction' and eliminating mu.
         */
        double calculationPart1 = this.start.xCoord*line2.direction.yCoord - this.start.yCoord*line2.direction.xCoord;
        double calculationPart2 = line2.start.xCoord*line2.direction.yCoord - line2.start.yCoord*line2.direction.xCoord;
        double calculationPart3 = this.direction.yCoord*line2.direction.xCoord - this.direction.xCoord*line2.direction.yCoord;
        double lambda = (calculationPart1 - calculationPart2)/calculationPart3;

        // Calculate the intersectionpoint, using lambda.
        double intersectionXCoord = this.start.xCoord + lambda*this.direction.xCoord;
        double intersectionYCoord = this.start.yCoord + lambda*this.direction.yCoord;
        double intersectionZCoord = this.start.zCoord + lambda*this.direction.zCoord;
        Vector intersectionPoint = new Vector(intersectionXCoord, intersectionYCoord, intersectionZCoord);

        // Calculate the angle, by anchoring both lines at the intersectionpoint.
        ParametricLine thisAtIntersection = new ParametricLine(intersectionPoint, this.direction);
        ParametricLine line2AtIntersection = new ParametricLine(intersectionPoint, line2.direction);
        double intersectionAngle = thisAtIntersection.angle(line2AtIntersection);
        return new Intersection(intersectionAngle, this, line2, intersectionPoint);
    }
}
