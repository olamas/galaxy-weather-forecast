package com.galaxy.weather.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.galaxy.weather.model.Galaxy;
import com.galaxy.weather.model.forecast.Weather;

@Service("weatherService")
public class WeatherService {
	
	private Galaxy galaxy ;
	
	private int periodInfDays;
		
	public void setGalaxy(Galaxy galaxy){
		this.galaxy = galaxy;
	}
	
	public void setPeriodInfDays(Integer days){
		this.periodInfDays = days;
	}
	
	public List<Weather> processWeatherGalaxy(){		
		List<Weather> weatherForecast = new ArrayList<Weather>();		
		for (int day = 1; day <= this.periodInfDays; day++) {
			Weather weatherByDay=WeatherProcessor.predictGalaxyWeatherByDay(day, this.galaxy);
			weatherForecast.add(weatherByDay);		
		}
		return weatherForecast;
	}

	
}
