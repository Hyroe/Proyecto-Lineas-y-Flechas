package Clases;

import java.awt.Point;
import java.awt.geom.Line2D;

public class Linea {
	private Point puntoFin;
	private Point puntoInic;
	private DibujoLineas dib;
	
	public Linea(Point i, Point f){
		this.puntoInic = i;
		this.puntoFin = f;
	}

	public boolean contains(int x, int y) {
        double distancia = Line2D.ptSegDist(puntoInic.getX(), puntoInic.getY(), puntoFin.getX(), puntoFin.getY(), x, y);
        return distancia < 5.0;
    }

    public boolean puntoCercanoInicial(Point p) {
        return p.distance(puntoInic.getX(), puntoInic.getY()) < dib.TAMANO_MANEJADOR;
    }
    public boolean puntoCercanoFinal(Point p) {
        return p.distance(puntoFin.getX(), puntoFin.getY()) < dib.TAMANO_MANEJADOR;
    }

	public Point getPuntoFin() {
		return puntoFin;
	}

	public void setPuntoFin(Point puntoFin) {
		this.puntoFin = puntoFin;
	}

	public Point getPuntoInic() {
		return puntoInic;
	}

	public void setPuntoInic(Point puntoInic) {
		this.puntoInic = puntoInic;
	}
}