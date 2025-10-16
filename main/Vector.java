package main;

/**do you not get the point?. */
public class Vector {
    double x;
    double y;

    /**constructor. */
    Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**a + b. */
    public Vector add(Vector a) {
        return new Vector(
            this.x + a.x,
            this.y + a.y
           );
    }

    /**a - b. */
    public Vector subtract(Vector a) {
        return new Vector(
            this.x - a.x,
            this.y - a.y
           );
    }

    /**returns the lenght of the vector. */
    public double length() {
        return Math.sqrt(
            Math.pow(
                this.x,
                2
                )
            + Math.pow(
                this.y,
                2
                )
            );
    }

    /**multiply by a double. */
    public Vector multiply(double a) {
        return new Vector(
            this.x * a,
            this.y * a
           );
    }

    /**divide by a double. */
    public Vector divide(double a) {
        return new Vector(
            this.x / a,
            this.y / a
           );
    }
}
