package com.galaxy.weather.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxy.weather.model.Galaxy;
import com.galaxy.weather.model.forecast.Forecast;
import com.galaxy.weather.model.forecast.Weather;
import com.galaxy.weather.persistence.ForecastRepository;

@Service("weatherService")
public class WeatherService {

	@Autowired
	private ForecastRepository forecastRepository;

	private Galaxy galaxy;

	private int periodInfDays;

	public void setGalaxy(Galaxy galaxy) {
		this.galaxy = galaxy;
	}

	public void setPeriodInfDays(Integer days) {
		this.periodInfDays = days;
	}

	public List<Weather> processWeatherGalaxy() {
		List<Weather> weatherForecast = new ArrayList<Weather>();
		for (int day = 1; day <= this.periodInfDays; day++) {
			Weather weatherByDay = WeatherProcessor.predictGalaxyWeatherByDay(day, this.galaxy);
			weatherForecast.add(weatherByDay);
		}
		return weatherForecast;
	}

	@Transactional
	public void configDatabaseAPI(List<Weather> galaxyWeather) {
		List<Forecast> forecasts = galaxyWeather.stream().map(weather -> weather.getForecast())
				.collect(Collectors.toList());
		forecastRepository.save(forecasts);
	}
}
