package com.galaxy.weather.model;

public class Planet {
	
    protected final String name;
	
	protected final int speed;

	protected final int rotation;

	protected final int orbit;

	protected double positionX;

	protected double positionY;
	
	protected Integer orbitPosition;

	protected final Integer ORBIT_DEGREES = 360;

	protected final Integer ORBIT_SET_POINT = 90; // seteo de posicion inicial
													// 90 grados - valor para
													// todos los planetas

	protected Planet(final String name,final Integer speed, final Integer rotation,final Integer orbit) {
		this.name = name;
		this.speed = speed;
		this.rotation = rotation;
		this.orbit = orbit;
	}

	public void setOrbitPosition(Integer day) {
		
		int orbitPositionValue = (this.speed * day * this.rotation ) + ORBIT_SET_POINT;	    
		
		//check nro de orbitas completadas 
		if(orbitPositionValue<0){			
			if(orbitPositionValue< (ORBIT_DEGREES*(-1)) ){
				int resto=orbitPositionValue % ORBIT_DEGREES;
				orbitPositionValue = resto;
			}
			this.orbitPosition=orbitPositionValue+ ORBIT_DEGREES; // Referencia con 360 grados positivos (sentido anti -horario)
		}
		else{
				if(orbitPositionValue>ORBIT_DEGREES){
					int resto=orbitPositionValue % ORBIT_DEGREES;
					orbitPositionValue=resto;
				}
				this.orbitPosition = orbitPositionValue;
		}
	}	
	
	public Integer getOrbitPosition() {			
		return this.orbitPosition;
	}
		
	public String getName() {
		return name;
	}

	public double getPositionX() {		
		double angle=Math.toRadians(this.getOrbitPosition());		
		 this.positionX=this.orbit*Math.cos(angle);	
		return this.positionX;
	}
	
	public double getPositionY() {
		double angle=Math.toRadians(this.getOrbitPosition());		
		 this.positionY=this.orbit*Math.sin(angle);	
		return this.positionY;
	}
	
	public int getSpeed() {
		return speed;
	}

	public int getRotation() {
		return rotation;
	}

	public int getOrbit() {
		return orbit;
	}
	
}
