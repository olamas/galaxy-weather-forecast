package com.galaxy.weather.services;

import java.util.List;

import com.galaxy.weather.model.Galaxy;
import com.galaxy.weather.model.Planet;
import com.galaxy.weather.model.forecast.Drought;
import com.galaxy.weather.model.forecast.OptimalConditions;
import com.galaxy.weather.model.forecast.Rain;
import com.galaxy.weather.model.forecast.Stable;
import com.galaxy.weather.model.forecast.Weather;

public class WeatherProcessor {

	public final static int ORBIT_REFERENCE = 180;

	public static Weather predictGalaxyWeatherByDay(int day, Galaxy galaxy) {

		List<Planet> planets = galaxy.getPlanets();
		planets.stream().forEach(planet -> planet.setOrbitPosition(day));

		if (isDroughtPeriod(planets)) {		
			return new Drought(day);
		} else if (isOptimalConditionsPeriod(planets)) {			
			return new OptimalConditions(day);
		} else if (isRainPeriod(planets)) {			
			Rain rain = new Rain(day);
			double amount = amountCalculate(planets);
			//System.out.println(day + " es lluvia " + planets.get(0).getOrbitPosition() + " - "+ planets.get(1).getOrbitPosition() + " - " + planets.get(2).getOrbitPosition());
			rain.setValue(amount);
			return rain;
		} else {
			return new Stable(day);
		}
	}

	private static double amountCalculate(List<Planet> planets) {
		double periodAmount = 0;
		boolean isFirstPlanet = false;
		boolean isAllPlanetsChecked = false;
		String planetaInicial = "";

		for (Planet planet : planets) {
			int auxilOrbita = planet.getOrbitPosition();

			if (!isFirstPlanet) {
				planetaInicial = planet.getName();
				for (Planet auxilPlanet : planets) {
					if (!planet.getName().equals(auxilPlanet.getName())) {
						double side = calculateSize(planet.getOrbit(), auxilPlanet.getOrbit(), auxilOrbita,
								auxilPlanet.getOrbitPosition());
						periodAmount = periodAmount + side;
					}
				}
				isFirstPlanet = true;
			} else {
				if (!isAllPlanetsChecked) {
					for (Planet auxilPlanet : planets) {
						if (!((planet.getName().equals(auxilPlanet.getName()))
								&& (auxilPlanet.getName().equals(planetaInicial)))) {
							double size = calculateSize(planet.getOrbit(), auxilPlanet.getOrbit(), auxilOrbita,
									auxilPlanet.getOrbitPosition());
							periodAmount = periodAmount + size;
							isAllPlanetsChecked = true;
						}
					}
				}
			}
		}
		return periodAmount;
	}

	private static double calculateSize(int orbit, int orbit2, int orbitPosition, int orbitPosition2) {
		double side = 0;
		double anguleBetweenSides = Math.toRadians(
				(orbitPosition > orbitPosition2) ? (orbitPosition - orbitPosition2) : (orbitPosition2 - orbitPosition));

		side = Math.round((Math.sqrt(
				(Math.pow(orbit, 2)) + (Math.pow(orbit2, 2)) - (2 * orbit * orbit2 * (Math.cos(anguleBetweenSides)))))
				* 100.0 / 100.0);

		return side;
	}

	private static boolean isRainPeriod(List<Planet> planets) {
		boolean isRain = false;

		boolean checkOrbitPosition = false;
		int indexPlanet = 0;
		int orbitRefVertice1 = 0;
		int orbitRefVertice2 = 0;

		boolean checkMinorOrbitPosition = false;

		for (Planet planet : planets) {
			int auxilOrbit = planet.getOrbitPosition();
			if (indexPlanet == 0) {
				orbitRefVertice1 = auxilOrbit;
			} else {
				if (!checkOrbitPosition) {
					if ((auxilOrbit > (orbitRefVertice1 + ORBIT_REFERENCE)) || (auxilOrbit < orbitRefVertice1)) {
						checkMinorOrbitPosition = true;
					}
					orbitRefVertice2 = auxilOrbit;
					checkOrbitPosition = true;
				} else {
					if (checkMinorOrbitPosition) {
						if ((auxilOrbit > (orbitRefVertice2 + ORBIT_REFERENCE))
								&& (auxilOrbit < (orbitRefVertice1 + ORBIT_REFERENCE))) {
							isRain = true;
						}
					} else {
						// comparar mayor referencia 1
						if ((auxilOrbit < (orbitRefVertice2 + ORBIT_REFERENCE))
								&& (auxilOrbit > (orbitRefVertice1 + ORBIT_REFERENCE))) {
							isRain = true;
						}
					}
				}
			}
			indexPlanet++;
		}
		return isRain;
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
					planetSlope = (abscissa != 0) ? (Math.round(auxSlope * 100.0) / 100.0) : Double.POSITIVE_INFINITY;
					checkSlope = true;
				} else {
					auxSlope = (abscissa != 0) ? (Math.round(auxSlope * 100.0) / 100.0) : Double.POSITIVE_INFINITY;

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

			if (auxilOrbit == ORBIT_REFERENCE * 2) {
				auxilOrbit = 0;
			} else if (auxilOrbit >= ORBIT_REFERENCE)
				auxilOrbit = auxilOrbit - ORBIT_REFERENCE;

			if (indexPlanet == 0) {
				orbit = auxilOrbit;
			} else {
				if (orbit != auxilOrbit) {
					isDrough = false;
					break;
				}
			}
			indexPlanet++;
		}
		return isDrough;
	}

}
