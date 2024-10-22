package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import no.hvl.dat100ptc.TODO;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;

	private double minlon, minlat, maxlon, maxlat;

	private double xstep, ystep;

	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));

		xstep = scale(MAPXSIZE, minlon, maxlon);
		ystep = scale(MAPYSIZE, minlat, maxlat);

		showRouteMap(MARGIN + MAPYSIZE);

		replayRoute(MARGIN + MAPYSIZE);

		showStatistics();
	}

	public double scale(int maxsize, double minval, double maxval) {

		double step = maxsize / (Math.abs(maxval - minval));

		return step;
	}

	public void showRouteMap(int ybase) {
		
		 setColor(0, 255, 0);

		for (int i = 0; i < gpspoints.length - 1; i++) {
			int x1 = MARGIN + (int) ((gpspoints[i].getLongitude() - minlon) * xstep);
			int y1 = ybase - (int) ((gpspoints[i].getLatitude() - minlat) * ystep);

			int x2 = MARGIN + (int) ((gpspoints[i + 1].getLongitude() - minlon) * xstep);
			int y2 = ybase - (int) ((gpspoints[i + 1].getLatitude() - minlat) * ystep);

			drawLine(x1, y1, x2, y2);
		}

	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;
		int x = MARGIN; 
		int y = MARGIN; 

		setColor(0, 0, 0);
		setFont("Courier", 12);

		double distance = gpscomputer.totalDistance() / 1000; // i km
		int time = gpscomputer.totalTime();
		double elevation = gpscomputer.totalElevation(); // i meter
		double maxSpeed = gpscomputer.maxSpeed() * 3.6; // i km/t
		double avgSpeed = gpscomputer.averageSpeed() * 3.6; // i km/t
		double kcal = gpscomputer.totalKcal(80);

		y += TEXTDISTANCE;
		drawString("Total time     : " + GPSUtils.formatTime(time), x, y);
		y += TEXTDISTANCE;
		drawString("Total distance : " + GPSUtils.formatDouble(distance) + " km", x, y);
		y += TEXTDISTANCE;
		drawString("Total elevation: " + GPSUtils.formatDouble(elevation) + " m", x, y);
		y += TEXTDISTANCE;
		drawString("Max speed      : " + GPSUtils.formatDouble(maxSpeed) + " km/t", x, y);
		y += TEXTDISTANCE;
		drawString("Average speed  : " + GPSUtils.formatDouble(avgSpeed) + " km/t", x, y);
		y += TEXTDISTANCE;
		drawString("Energy         : " + GPSUtils.formatDouble(kcal) + " kcal", x, y);
		y += TEXTDISTANCE;
		

	}

	public void replayRoute(int ybase) {
		
		 setColor(0, 0, 255);
		 
		 int radius = 8;

		int circle = fillCircle(MARGIN + (int) ((gpspoints[0].getLongitude() - minlon) * xstep),
				ybase - (int) ((gpspoints[0].getLatitude() - minlat) * ystep), radius);

		for (int i = 1; i < gpspoints.length; i++) {
			int x = MARGIN + (int) ((gpspoints[i].getLongitude() - minlon) * xstep);
			int y = ybase - (int) ((gpspoints[i].getLatitude() - minlat) * ystep);

			moveCircle(circle, x, y);
			pause(100);
		}

	}

}
