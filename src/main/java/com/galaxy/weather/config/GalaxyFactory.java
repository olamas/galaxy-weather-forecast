package com.galaxy.weather.config;

import com.galaxy.weather.model.Galaxy;

public class GalaxyFactory {
		
	// se puede agregar mas logica para obtener una galaxia en base a configuracion del sistema o que se detecte los planetas que la componen
	public static Galaxy getGalaxy(){
			return new Galaxy();
	}
	
}
