package com.galaxy.weather.model.forecast;

public class OptimalConditions extends Weather {

	public final static String STATUS_OPTIMAL_CONDITIONS = "CO";
		
	public OptimalConditions(int day) {
		super(day);
	}

	public String getStatus() {
		return STATUS_OPTIMAL_CONDITIONS;
	}	
}
