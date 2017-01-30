package com.galaxy.weather.model.forecast;

import java.util.List;

public abstract class Weather {

    private int day;
	
	private double value;
	
	private List<Integer> planetsPositions;
	
	public Weather(int day){
		this.day = day;
	}	
	
	public abstract String getStatus();
	
	public int getDay() {
		return this.day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setPPlanetsPosition(List<Integer> planetsPositions) {
		this.planetsPositions = planetsPositions;
	}

	
	public List<Integer> getPlanetsPosition() {
		return this.planetsPositions;
	}

	
	public void setValue(double value) {
		this.value = value;

	}
	
	public double getValue() {
		return this.value;
	}
	
}
