package com.galaxy.weather.model;

import java.util.ArrayList;
import java.util.List;

public class Galaxy {
	
	private final static int DAYS_PER_YEAR =72; // cantidad de dias por año = 360/ velocidad de Vulcano (vel maxima) = 72. Se toma referencia Vulcano para cantidad de dias por año
	
	private List<Planet> planets=new ArrayList<Planet>();
	
	public Galaxy(){		
		planets.add(new Ferengi());
		planets.add(new Vulcan());		
		planets.add(new Betazed());		
	}

	public List<Planet> getPlanets() {
		return planets;
	}	
	
	public int getDaysPerYear(){
		return DAYS_PER_YEAR;
	}
}
