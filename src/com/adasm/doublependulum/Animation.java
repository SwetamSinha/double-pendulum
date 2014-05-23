package com.adasm.doublependulum;

/* By Adam Michalowski (c) 2014 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Animation extends JPanel {
    private static final long serialVersionUID = 1L;

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        int w = getWidth(), h = getHeight();

        g2d.setColor(Color.white);
        g2d.clearRect(0, 0, w, h);
        g2d.setColor(Color.red);
        g2d.fillOval((int)Simulation.x0-4, (int)Simulation.y0-4, 9, 9);
        g2d.drawLine((int)Simulation.x0, (int)Simulation.y0, (int)Simulation.positions.x, (int)Simulation.positions.y);
        g2d.fillOval((int)Simulation.positions.x-4, (int)Simulation.positions.y-4, 9, 9);

        g2d.setColor(Color.blue);
        g2d.drawLine((int)Simulation.positions.x, (int)Simulation.positions.y, (int)Simulation.positions.z, (int)Simulation.positions.w);
        g2d.fillOval((int)Simulation.positions.z-4, (int)Simulation.positions.w-4, 9, 9);

        g2d.setColor(Color.gray);
        g2d.drawString("h = " + Simulation.h , 0, 12);
        g2d.drawString("g = " + Simulation.g , 0, 24);
        g2d.drawString("m1 = " + Simulation.m1 , 0, 36);
        g2d.drawString("m2 = " + Simulation.m2 , 0, 48);
        g2d.drawString("l1 = " + Simulation.l1 , 0, 60);
        g2d.drawString("l2 = " + Simulation.l2 , 0, 72);
        g2d.drawString("x0 = " + Simulation.x0, 0, 84);
        g2d.drawString("y0 = " + Simulation.y0, 0, 96);
        g2d.drawString("scale = " + Simulation.scale, 0, 108);
        g2d.drawString("a1 = " + Simulation.state.x, 0, 120);
        g2d.drawString("a2 = " + Simulation.state.y, 0, 132);
    }
}

