package com.galaxy.weather.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxy.weather.config.GalaxyFactory;
import com.galaxy.weather.model.Galaxy;
import com.galaxy.weather.model.forecast.Weather;
import com.galaxy.weather.services.WeatherService;
import com.galaxy.weather.utils.ForecastConstants;

@Component
public class ForecastCommand implements Command{
	
	@Autowired
	private WeatherService weatherService;
	
	@Override
	public String execute() {
		Galaxy galaxy = GalaxyFactory.getGalaxy();		
		weatherService.setGalaxy(galaxy);	
		weatherService.setPeriodInfDays(ForecastConstants.YEARS*galaxy.getDaysPerYear());
		 List<Weather> galaxyWeather = weatherService.processWeatherGalaxy();
		 weatherService.configDatabaseAPI(galaxyWeather);		 
		 String status = "Forecast was succesfully processed!";
		 
		 return status;
		 
	}
}
