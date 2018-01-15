package be.scoutsronse.wafelbak.mvp.util;

import static java.lang.Math.sqrt;

/**
 * Vector stores and manipulates xyz triples representing 3 space
 * vectors.
 *
 * @author Anthony Steed
 * @version 1.0
 */

public class Vector {

    public double x;
    public double y;

    public Vector() {
        x =0d;
        y =0d;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y =y;
    }

    public Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * Find the length of this vector
     * @return the length
     */
    public double norm() {
        return  sqrt(x * x + y * y);
    }

    /**
     * Normalise this vector
     */
    public void normalise() {
        double d = norm();

        x /= d;
        y /= d;
    }

    /**
     * Create a new vector as the normalisation of this vector
     * @return the new vector with the normalised version
     */
    public Vector normalised() {
        Vector v = new Vector(this);
        v.normalise();
        return v;
    }

    public static Vector subtract(Vector v1, Vector v2) {
        return new Vector (v1.x - v2.x, v1.y - v2.y);
    }

    public static double dot(Vector v1, Vector v2) {
        return (v1.x * v2.x) + (v1.y * v2.y);
    }
    public void scale(double s) {
        x *=s;
        y *=s;
    }
    public static Vector scale(Vector v, double s) {
        return new Vector (v.x *s, v.y *s);
    }

    public static void scale(Vector vd, Vector v, double s) {
        vd.x = v.x *s;
        vd.y = v.y *s;
    }

    public void negate() {
        x = -x;
        y = -y;
    }

    public static Vector negate(Vector v) {
        return new Vector (-v.x,-v.y);
    }

    public String toString()  {
        return "[" + x + ", " + y + "]";
    }
}