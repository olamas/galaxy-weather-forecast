package com.galaxy.weather.commands;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxy.weather.config.GalaxyFactory;
import com.galaxy.weather.model.Galaxy;
import com.galaxy.weather.model.forecast.Drought;
import com.galaxy.weather.model.forecast.OptimalConditions;
import com.galaxy.weather.model.forecast.Rain;
import com.galaxy.weather.model.forecast.Weather;
import com.galaxy.weather.services.WeatherService;
import com.galaxy.weather.utils.ForecastConstants;

@Component
public class ReportCommand implements Command{
	
	@Autowired
	private WeatherService weatherService;
	
	@Override
	public String execute() {
		Galaxy galaxy = GalaxyFactory.getGalaxy();		
		weatherService.setGalaxy(galaxy);	
		weatherService.setPeriodInfDays(ForecastConstants.YEARS*galaxy.getDaysPerYear());
		List<Weather> galaxyWeather = weatherService.processWeatherGalaxy();
				 
		StringBuffer reportBuffer = new StringBuffer();
		reportBuffer.append("\n Reporte: Periodos de Sequia \n\n ");
		galaxyWeather.stream().filter(weather -> weather.getStatus().equals(Drought.STATUS_DROUGHT)).forEach(
				weather -> reportBuffer.append("Dia:" + weather.getDay() + " -  Estado:" + weather.getStatus() + "\n"));
		
		reportBuffer.append("\n Reporte: Periodos de Lluvia \n\n ");
		galaxyWeather.stream().filter(weather -> weather.getStatus().equals(Rain.STATUS_RAIN)).forEach(
				weather -> reportBuffer.append("Dia:" + weather.getDay() + " -  Estado:" + weather.getStatus() + " valor: "+weather.getValue() + "\n"));
		
		
		Optional<Weather> maxRainOptional = galaxyWeather.stream().filter(weather -> weather.getStatus().equals(Rain.STATUS_RAIN)).max((w1,w2)->Double.compare(w1.getValue(), w2.getValue()));
			if(maxRainOptional.isPresent()){
				reportBuffer.append("\n Reporte: Periodos de Lluvia  - Maximo valor de LLuvia \n\n ");				
				Weather maxRain = maxRainOptional.get();
				reportBuffer.append("Dia:" + maxRain.getDay() + " -  Estado:" + maxRain.getStatus() + " valor: "+maxRain.getValue() + "\n");
			}
						
		reportBuffer.append("\n Reporte: Periodos de Condiciones Optimas \n\n ");
		galaxyWeather.stream().filter(weather -> weather.getStatus().equals(OptimalConditions.STATUS_OPTIMAL_CONDITIONS)).forEach(
				weather -> reportBuffer.append("Dia:" + weather.getDay() + " -  Estado:" + weather.getStatus() + "\n"));
		
		
		String status = reportBuffer.toString();
		return status;
		 
	}
}
