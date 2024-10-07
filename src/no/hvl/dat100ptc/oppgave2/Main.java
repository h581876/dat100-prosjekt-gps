package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class Main {

	
	public static void main(String[] args) {

		GPSPoint gpspoint1 = new GPSPoint (4, 10.2, 12.4, 50.2);
		GPSPoint gpspoint2 = new GPSPoint (9, 14.12, 23.56, 100.4);
		
		GPSData gpsdata = new GPSData (2);
		
		gpsdata.insertGPS(gpspoint1);
		gpsdata.insertGPS(gpspoint2);
		
		gpsdata.print();
		
	}
}
