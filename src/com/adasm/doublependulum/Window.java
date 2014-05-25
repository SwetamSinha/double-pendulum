package com.adasm.doublependulum;

/* By Adam Michalowski (c) 2014 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Window extends JFrame implements ActionListener  {
    private static final long serialVersionUID = 1L;
    public static JTextField params = new JTextField();
    public static JPanel graphs[] = new JPanel[] { new Graph(1), new Graph(2), new Graph(3), new Graph(4) };
    public static Animation animation = new Animation();
    public static Simulation simulation = new Simulation();
    public static Thread simulationThread = null;
    public static Window mainWindow = new Window();
    public static JButton buttonPause;
    public Window() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(animation);

        JPanel panel = new JPanel(new BorderLayout());
        params.setActionCommand("params");
        params.addActionListener(this);
        panel.add(params);

        buttonPause = new JButton(Simulation.pause ? "Resume" : "Pause");
        buttonPause.setActionCommand("pause");
        buttonPause.addActionListener(this);
        panel.add(buttonPause, BorderLayout.WEST);
        JButton button = new JButton("Restart");
        button.setActionCommand("restart");
        button.addActionListener(this);
        panel.add(button, BorderLayout.EAST);

        JFrame frame = new JFrame();
        frame.setSize(256, 256);
        frame.setVisible(true);
        int n = (int)Math.sqrt(graphs.length);
        JPanel pan = new JPanel(new GridLayout(n, n+1));
        for(JPanel p : graphs)pan.add(p);
        frame.add(pan);

        add(panel, BorderLayout.SOUTH);
        setSize(512, 512);
        setVisible(true);
    }
    public static void main(String[] args) {
        simulationThread = new Thread(simulation);
        simulationThread.start();
    }
    @Override
    public void actionPerformed(ActionEvent a) {
        switch(a.getActionCommand()) {
            case "pause": {
                Simulation.pause = !Simulation.pause;
                buttonPause.setText(Simulation.pause ? "Resume" : "Pause");
            } break;
            case "restart": {
                if(simulationThread != null) {
                    Simulation.pause = false;
                    Simulation.exit = true;
                    try { simulationThread.join(); } catch (InterruptedException e) { e.printStackTrace(); }
                }
                Simulation.exit = false;
                Simulation.pause = true;
                buttonPause.setText(Simulation.pause ? "Resume" : "Pause");
                simulationThread = new Thread(simulation);
                simulationThread.start();

            } break;
            case "params": {
                String str = params.getText();
                if(str.length() < 1) {
                    params.setText(
                            "h = " + Simulation.h + "; " +
                                    "g = " + Simulation.g + "; " +
                                    "m1 = " + Simulation.m1 + "; " +
                                    "m2 = " + Simulation.m2 + "; " +
                                    "l1 = " + Simulation.l1 + "; " +
                                    "l2 = " + Simulation.l2 + "; " +
                                    "x0 = " + Simulation.x0 + "; " +
                                    "y0 = " + Simulation.y0 + "; " +
                                    "a1 = " + (int)(Simulation.startState.x * 180.0 / Math.PI) + "; " +
                                    "a2 = " + (int)(Simulation.startState.y * 180.0 / Math.PI) + "; "

                    );
                }
                else {
                    for(String s : str.split(";")) {
                        String tokens[] = s.split("=");
                        if(tokens.length < 2) continue;
                        String name =  tokens[0].trim().toLowerCase();
                        double value =  Double.parseDouble(tokens[1].trim());
                        if(name.equals("h")) Simulation.h = value;
                        else if(name.equals("g")) Simulation.g = value;
                        else if(name.equals("m1")) Simulation.m1 = value;
                        else if(name.equals("m2")) Simulation.m2 = value;
                        else if(name.equals("l1")) Simulation.l1 = value;
                        else if(name.equals("l2")) Simulation.l2 = value;
                        else if(name.equals("x0")) Simulation.x0 = value;
                        else if(name.equals("y0")) Simulation.y0 = value;
                        else if(name.equals("scale")) Simulation.scale = value;
                        else if(name.equals("a1")) simulation.startState.x = (value * Math.PI / 180.0);
                        else if(name.equals("a2")) simulation.startState.y = (value * Math.PI / 180.0);
                    }
                    animation.repaint();
                }
            } break;
        }
    }

}
