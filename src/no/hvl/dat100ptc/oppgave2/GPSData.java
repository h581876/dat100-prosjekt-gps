package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int antall) {

		gpspoints = new GPSPoint [antall];
		this.antall = 0;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	protected boolean insertGPS(GPSPoint gpspoint) {

		boolean inserted = false;
		
		if (antall < gpspoints.length) {
			gpspoints [antall] = gpspoint;
			antall++;
			return true;
		}
		return inserted;
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {

		GPSPoint gpspoint;

		int time1 = GPSDataConverter.toSeconds(time);
		double latitude1 = Double.parseDouble(latitude);
		double longitude1 = Double.parseDouble(longitude);
		double elevation1 = Double.parseDouble(elevation);
		
		gpspoint = new GPSPoint (time1, latitude1, longitude1, elevation1);
		
		return insertGPS(gpspoint);
		
	}

	public void print() {

		System.out.println("====== GPS Data - START ======");
		
		for (int i = 0; i < antall; i++) {
			System.out.println(gpspoints[i].toString());
		}
		
		System.out.println("====== GPS Data - SLUTT ======");
	}
}
