package com.adasm.doublependulum;

/* By Adam Michalowski (c) 2014 */

import javax.swing.JPanel;


public class Simulation implements Runnable {
    public static Vec4 startState = new Vec4(), state = new Vec4(), positions = new Vec4();
    public static boolean pause = true, exit = false;
    public static double h = 0.001, g = 9.81, m1 = 0.25, m2 = 0.25, l1 = 0.25, l2 = 0.25, x0 = 256, y0 = 64, scale = 500;
    public Simulation() {
        startState.x = Math.PI/2.5;
        startState.y = Math.PI/5;
    }
    @Override
    public void run() {
        state.set(startState);
        computePositions();
        repaint();

        while(!exit) {
            while(pause) { try { Thread.sleep(1); } catch (InterruptedException e) { } }
            simulate();
            computePositions();
            repaint();
            try { Thread.sleep((int)(h*1000)); } catch (InterruptedException e) { }
        }
    }

    public void f(Vec4 out, Vec4 in) {
        out.x = in.z;
        out.y = in.w;
        out.z = (-g*(2*m1 + m2)*Math.sin(in.x) - m2*g*Math.sin(in.x - 2*in.y) - 2*Math.sin(in.x - in.y)*m2*(in.z*in.z*l2 + in.w*in.w*l1*Math.cos(in.x - in.y))) /
                (l1*(2*m1 + m2 - m2*Math.cos(2*in.x - in.y)));
        out.w = (2*Math.sin(in.x - in.y)*(in.z*in.z*l1*(m1 + m2) +g*(m1 + m2)*Math.cos(in.x) + in.w*in.w*l2*m2*Math.cos(in.x - in.y))) /
                (l2*(2*m1 + m2 - m2*Math.cos(2*in.x - 2*in.y)));
    }

    Vec4 	s = new Vec4(), t = null,
            a = new Vec4(), b = new Vec4(), c = new Vec4(), d = new Vec4(),
            sb = new Vec4(), sc = new Vec4(), sd = new Vec4();

    public void simulate() {
        s.set(state);

        f(a, s);
        s.madd(sb, 0.5*h, a);
        f(b, sb);
        s.madd(sc, 0.5*h, b);
        f(c, sc);
        s.madd(sd, h, c);
        f(d, sd);

        a.madd(a, 2.0, b);
        a.madd(a, 2.0, c);
        a.madd(a, 1.0, d);
        s.madd(s, h/6.0, a);

        t = state;
        state = s;
        s = t;
    }

    public void computePositions() {
        positions.x = l1*scale*Math.sin(state.x) + x0;
        positions.y = l1*scale*Math.cos(state.x) + y0;
        positions.z = l2*scale*Math.sin(state.y) + positions.x;
        positions.w = l2*scale*Math.cos(state.y) + positions.y;

    }

    public void repaint() {
        Window.animation.repaint();
        for(JPanel p : Window.graphs)
            p.repaint();
    }
}
