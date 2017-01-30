package com.galaxy.weather.services;

import java.util.List;

import com.galaxy.weather.model.Galaxy;
import com.galaxy.weather.model.Planet;
import com.galaxy.weather.model.forecast.Drought;
import com.galaxy.weather.model.forecast.OptimalConditions;
import com.galaxy.weather.model.forecast.Stable;
import com.galaxy.weather.model.forecast.Weather;

public class WeatherProcessor {
		
	public final static int ORBIT_REFERENCE = 180;
	
	public static Weather predictGalaxyWeatherByDay(int day, Galaxy galaxy){
		
		List<Planet> planets = galaxy.getPlanets();
		planets.stream().forEach(planet->planet.setOrbitPosition(day));		
		
		if (isDroughtPeriod(planets)) {
			//System.out.println(day + " es sequia"+  planets.get(0).getOrbitPosition() +" - "+  planets.get(1).getOrbitPosition() +" - " +  planets.get(2).getOrbitPosition() );
			return new Drought(day);
		}  else if (isOptimalConditionsPeriod(planets)) { 
			System.out.println(day + " es optimal "+  planets.get(0).getOrbitPosition() +" - "+  planets.get(1).getOrbitPosition() +" - " +  planets.get(2).getOrbitPosition() );
			return new OptimalConditions(day);			
		}else			
			return new Stable(day);
	}
	
	
	private static boolean isOptimalConditionsPeriod(List<Planet> planets) {
		
		boolean isOptimal = false;		
		boolean checkSlope = false;

		int indexPlanet = 0;
		double planetSlope = 0;

		double initialPosX = 0;
		double initialPosY = 0;

		for (Planet planet : planets) {
			
			double auxilPosX = planet.getPositionX();
			double auxilPosY = planet.getPositionY();

			if (indexPlanet == 0) {
				initialPosX = auxilPosX;
				initialPosY = auxilPosY;
			} else {
				
				double abscissa = (auxilPosX - initialPosX);
				double auxSlope = (abscissa != 0) ? ((auxilPosY - initialPosY) / (auxilPosX - initialPosX))
						: Double.POSITIVE_INFINITY;

				if (!checkSlope) {
					planetSlope = (abscissa != 0) ? (Math.round(auxSlope * 100.0) / 100.0)
							: Double.POSITIVE_INFINITY;
					checkSlope = true;
				} else {
					auxSlope = (abscissa != 0) ? (Math.round(auxSlope * 100.0) / 100.0)
							: Double.POSITIVE_INFINITY;

					if ((Math.abs(auxSlope)) == (Math.abs(planetSlope))) {
						isOptimal = true;
					}

				}
			}
			indexPlanet++;
		}
		return isOptimal;
	}
		
	
	
	
	/**
	 * El metodo obtiene la orbita de todos los planestas y si se encuentran en
	 * el mismo angulo +/- 180 estan sobre el mismo eje con respecto al sol
	 * 
	 * @param planetas
	 * @return
	 */
	private static boolean isDroughtPeriod(List<Planet> planets) {
		boolean isDrough = true;
		int indexPlanet = 0;
		int orbit = 0;

		for (Planet planet : planets) {
			int auxilOrbit = planet.getOrbitPosition();
			
			if (auxilOrbit == ORBIT_REFERENCE*2){
				auxilOrbit = 0;
			}
			else if (auxilOrbit >= ORBIT_REFERENCE)
				auxilOrbit = auxilOrbit - ORBIT_REFERENCE;
					
			if (indexPlanet == 0) {
				orbit = auxilOrbit;
			} else {				
				if (orbit != auxilOrbit){		
					isDrough = false;
					break;
				}
			}
			indexPlanet++;
		}			
		return isDrough;
	}
	
	
}
