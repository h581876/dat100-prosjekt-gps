package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.TODO;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double [] tabell = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			tabell [i] = gpspoints[i].getLatitude();
		}
		return tabell;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double [] tabell = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			tabell [i] = gpspoints[i].getLongitude();
		}
		return tabell;

	}

	private static final int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;

		latitude1 = Math.toRadians(gpspoint1.getLatitude());
		latitude2 = Math.toRadians(gpspoint2.getLatitude());
		longitude1 = Math.toRadians(gpspoint1.getLongitude());
		longitude2 = Math.toRadians(gpspoint2.getLongitude());
		
		double deltaLatitude = latitude2 - latitude1;
		double deltaLongitude = longitude2 - longitude1;
		
		double a = compute_a(latitude1, latitude2, deltaLatitude, deltaLongitude);
		
		double c = compute_c(a);
		
		return d = R * c;
	}
	
	private static double compute_a(double phi1, double phi2, double deltaphi, double deltadelta) {
	
		return Math.pow(Math.sin(deltaphi/2), 2) + Math.cos(phi1) * Math.cos(phi2) *
				Math.pow(Math.sin(deltadelta/2), 2);

	}

	private static double compute_c(double a) {

		return 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	}

	
	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		
		int deltaTime = gpspoint2.getTime() - gpspoint1.getTime();
		
		return speed = distance(gpspoint1, gpspoint2) / deltaTime;

	}

	public static String formatTime(int secs) {

		String timestr;

		int h = secs / 3600;
		int m = (secs % 3600) / 60;
		int s = secs % 60;
		
		timestr = String.format("%02d:%02d:%02d", h, m, s);
		
		return String.format("%10s", timestr);
		
		
		
	}
	
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;

		d = Math.round(d * 100.0) / 100.0;
		
		str = String.format("%" + TEXTWIDTH + ".2f", d);
		
		str = str.replace(',', '.');
		
		return str;
		
	}
}
