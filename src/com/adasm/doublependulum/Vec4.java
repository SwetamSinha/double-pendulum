package com.adasm.doublependulum;

/* By Adam Michalowski (c) 2014 */

public class Vec4 {
    public double x = 0, y = 0, z = 0, w = 0;
    public Vec4() { }
    public void set(Vec4 v) {
        x = v.x; y = v.y; z = v.z; w = v.w;
    }
    public void madd(Vec4 out, double a, Vec4 v) {
        out.x = x + a * v.x; out.y = y + a * v.y; out.z = z + a * v.z; out.w = w + a * v.w;
    }
    public void mul(double a) {
        x *= a; y *= a; z *= a; w *= a;
    }
}

