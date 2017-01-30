package com.galaxy.weather.model.forecast;

public class Rain extends Weather {

	public final static String STATUS_RAIN = "LLUVIA";
	
	public Rain(int day) {
		super(day);		
	}

	public String getStatus() {
		return STATUS_RAIN;
	}	
}
