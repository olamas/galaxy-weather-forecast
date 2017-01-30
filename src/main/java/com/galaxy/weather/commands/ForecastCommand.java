package com.galaxy.weather.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxy.weather.config.GalaxyFactory;
import com.galaxy.weather.model.Galaxy;
import com.galaxy.weather.services.WeatherService;
import com.galaxy.weather.utils.ForecastConstants;

@Component
public class ForecastCommand implements Command{
	
	@Autowired
	private WeatherService weatherService;
	
	@Override
	public void execute() {
		Galaxy galaxy = GalaxyFactory.getGalaxy();		
		weatherService.setGalaxy(galaxy);	
		weatherService.setPeriodInfDays(ForecastConstants.YEARS*galaxy.getDaysPerYear());
		weatherService.processWeatherGalaxy();		
	}
}
