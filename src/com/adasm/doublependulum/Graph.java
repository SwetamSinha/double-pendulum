package com.adasm.doublependulum;

/* By Adam Michalowski (c) 2014 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

public class Graph extends JPanel {
    private static final long serialVersionUID = 1L;
    public BufferedImage canvas = null;
    public Vec4 last = null;
    public Color color = new Color(r.nextFloat(), r.nextFloat(), 1, 1);
    public int count = 0;
    public int mode = 0;
    public Graph(int m) { mode = m; }
    public static Random r = new Random();

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        int w = getWidth(), h = getHeight();

        if(canvas == null || canvas.getWidth() != w || canvas.getHeight() != h) {
            canvas = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = (Graphics2D)canvas.getGraphics();
            g2.setColor(Color.white);
            g2.clearRect(0, 0, w, h);
            count = 0;
        }

        Graphics2D g2 = (Graphics2D)canvas.getGraphics();
        g2.setColor(color);
        switch(mode) {
            default:
            case 0:
                g2.fillOval((int)(w/2 + w/2*(Simulation.state.x/(2*Math.PI))), (int)(h/2 + h/2*(Simulation.state.y/(2*Math.PI))), 2, 2);
                g2.setColor(Color.white);
                g2.drawString("A1 - A2", 0, 12);
                break;
            case 1:
                g2.fillOval((int)(w/2 + w/2*(Simulation.state.x/(2*Math.PI))), (int)(h/2 + h/2*(Simulation.state.z/(4*Math.PI))), 2, 2);
                g2.setColor(Color.white);
                g2.drawString("A1 - W1", 0, 12);
                break;
            case 2:
                g2.fillOval((int)(w/2 + w/2*(Simulation.state.y/(2*Math.PI))), (int)(h/2 + h/2*(Simulation.state.w/(4*Math.PI))), 2, 2);
                g2.setColor(Color.white);
                g2.drawString("A2 - W2", 0, 12);
                break;
            case 3:
                g2.fillOval((int)(w/2 + w/2*(Simulation.state.z/(4*Math.PI))), (int)(h/2 + h/2*(Simulation.state.w/(4*Math.PI))), 2, 2);
                g2.setColor(Color.white);
                g2.drawString("W1 - W2", 0, 12);
                break;
        }
        g2d.drawImage(canvas, null, 0, 0);
        count++;
    }
}
