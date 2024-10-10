package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

import no.hvl.dat100ptc.TODO;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	public double totalDistance() {

		double distance = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i+1]);
			
		}
		return distance;

	}

	public double totalElevation() {

		double elevation = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			if (gpspoints[i+1].getElevation() > gpspoints[i].getElevation()) {
				elevation += gpspoints[i+1].getElevation() - gpspoints[i].getElevation();
			}
		}
		return elevation;
		
	}

	public int totalTime() {

		 int time = 0;
		 
		 for (int i = 0; i < gpspoints.length - 1; i++) {
			 time += gpspoints[i + 1].getTime() - gpspoints[i].getTime();
		 }
		return time;
	}
		

	public double[] speeds() {

		double[] speeds = new double[gpspoints.length-1];
		
		for (int i = 0; i < gpspoints.length - 1; i++) {
			double distance = GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
			int time = gpspoints[i + 1].getTime() - gpspoints [i].getTime();
			
			speeds [i] = distance / time;
		}
		return speeds;
	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		
		for (int i = 0; i < gpspoints.length - 1; i++) {
			 double speed = GPSUtils.speed(gpspoints [i], gpspoints [i + 1]);
			 if (speed > maxspeed){
				 maxspeed = speed;
			 }
		}
		return maxspeed;
	}

	public double averageSpeed() {
		
		return totalDistance () / totalTime ();
		
	}


	// conversion factor m/s to miles per hour (mps)
	public static final double MS = 2.23;

	public double kcal(double weight, int secs, double speed) {

		double kcal;

		double met = 0;		
		double speedmph = speed * MS;
		
		double t = secs / 3600.0;
		
		if (speedmph < 10) {
			met = 4.0;
			kcal = met * weight * t;
		}
		else if (speedmph >= 10 && speedmph < 12) {
			met = 6.0;
		}
		else if (speedmph >= 12 && speedmph < 14) {
			met = 8.0;
		}
		else if (speedmph >= 14 && speedmph < 16) {
			met = 10.0;
		}
		else if (speedmph >= 16 && speedmph < 20) {
			met = 12.0;
		}
		else {
			met = 16.0;
		}
		
		kcal = met * weight * t;
		
		return kcal;
		
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			int sec = gpspoints [i+1].getTime() - gpspoints [i].getTime();
			double speed = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
			totalkcal += kcal(weight, sec, speed);
		}
		return totalkcal;
		
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {
		
		 double distance = totalDistance() /1000;
		 int time = totalTime();
		 double elevation = totalElevation();
		 double maxSpeed = maxSpeed() * 3.6;
		 double avgSpeed = averageSpeed() * 3.6;
		 double kcal = totalKcal(WEIGHT);

		System.out.println("==============================================");
		System.out.println("Total time     :" + GPSUtils.formatTime(time));
		System.out.println("Total distance :" + GPSUtils.formatDouble(distance) + " km");
		System.out.println("Total elevation:" + GPSUtils.formatDouble(elevation) + " m");
		System.out.println("Max speed      :" + GPSUtils.formatDouble(maxSpeed) + " km/t");
		System.out.println("Average speed  :" + GPSUtils.formatDouble(avgSpeed) + " km/t");
		System.out.println("Energy         :" + GPSUtils.formatDouble(kcal) + " kcal");
		System.out.println("==============================================");
		
	}

}
