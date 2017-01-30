package com.galaxy.weather.model.forecast;

public class Stable extends Weather {

	private final static String STATUS_STABLE = "ESTABLE";
	
	public Stable(int day) {
		super(day);
	}
	
	public String getStatus() {
		return STATUS_STABLE;
	}	
}
