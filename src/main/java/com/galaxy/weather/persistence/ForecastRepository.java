package com.galaxy.weather.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.galaxy.weather.model.forecast.Forecast;


public interface ForecastRepository extends JpaRepository<Forecast, Integer> {

	public Forecast findByDay(Integer day);	
	
	public List<Forecast> findByDayGreaterThanAndDayLessThan(Integer startOffset,Integer endOffset);

}
