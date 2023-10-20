package Clases;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class DibujoLineas extends JFrame {
    private Point puntoInicial, puntoFinal;
    private Linea lineaSeleccionada;
    private boolean handlerInicMov = false,handlerFinalMov = false;
    private ArrayList<Linea> lineas = new ArrayList<Linea>();
    static final int TAMANO_MANEJADOR = 10;

    public DibujoLineas() {
        super("Dibujo de Líneas");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Linea linea : lineas) {
                    if (linea == lineaSeleccionada) {
                        g.setColor(Color.RED);
                        g.fillRect(linea.getPuntoInic().x - TAMANO_MANEJADOR / 2, linea.getPuntoInic().y- TAMANO_MANEJADOR / 2, TAMANO_MANEJADOR, TAMANO_MANEJADOR);
                        g.fillRect(linea.getPuntoFin().x - TAMANO_MANEJADOR / 2, linea.getPuntoFin().y - TAMANO_MANEJADOR / 2, TAMANO_MANEJADOR, TAMANO_MANEJADOR);
                    } else {
                        g.setColor(Color.BLACK);
                    }
                    g.drawLine(linea.getPuntoInic().x, linea.getPuntoInic().y, linea.getPuntoFin().x, linea.getPuntoFin().y);
                    g.drawString("1", linea.getPuntoInic().x, linea.getPuntoInic().y - 10); // Dibuja el texto en el punto inicial
                    g.drawString("1", linea.getPuntoFin().x, linea.getPuntoFin().y - 10); // Dibuja el texto en el punto final
                }
                if (puntoInicial != null && puntoFinal != null) {
                    g.setColor(Color.GRAY);
                    g.drawLine(puntoInicial.x, puntoInicial.y, puntoFinal.x, puntoFinal.y);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    puntoInicial = e.getPoint();
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    for (Linea linea : lineas) {
                        if (linea.contains(e.getX(), e.getY())) {
                            lineaSeleccionada = linea;
                            panel.repaint();
                            return;
                        }
                    }
                    lineaSeleccionada = null;
                }
                panel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    puntoFinal = e.getPoint();
                    lineas.add(new Linea(puntoInicial, puntoFinal));
                    puntoInicial = null;
                    puntoFinal = null;
                    panel.repaint();
                    
                }
                else if(SwingUtilities.isLeftMouseButton(e)){
                	handlerFinalMov = false;
                    handlerInicMov = false;
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    puntoFinal = e.getPoint();
                    panel.repaint();
                } else if (SwingUtilities.isLeftMouseButton(e) && lineaSeleccionada != null) {
                    if (lineaSeleccionada.puntoCercanoInicial(e.getPoint()) || handlerInicMov) {
                    	handlerInicMov = true;
                        lineaSeleccionada.setPuntoInic(e.getPoint());
                    } else if (lineaSeleccionada.puntoCercanoFinal(e.getPoint()) || handlerFinalMov) {
                    	handlerFinalMov = true;
                        lineaSeleccionada.setPuntoFin(e.getPoint());
                    }
                    panel.repaint();
                }
            }
        });

        add(panel);
    }

    
}