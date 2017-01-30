package com.galaxy.weather.model.forecast;

public class Drought extends Weather {

	public Drought(int day) {
		super(day);
	}

	public final static String STATUS_DROUGHT = "SEQUIA";

	public String getStatus() {
		return STATUS_DROUGHT;
	}	
}
