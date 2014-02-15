package com.kpu.plantid;

import java.io.InputStream;

/**
 * Store information about a car.
 */
public class Car {
	private String family;
	private String species;
	private String jon;
	private String common;
	private String id;
	
	
	public Car(String family, String species, String common, String id,String jon) {
		super();
		this.family = family;
		this.species = species;
		this.id = id;
		this.common = common;
		this.jon=jon;
		
	}
	
	public String getFamily() {
		return family;
	}
	public String getSpecies() {
		return species;
	}
	public String getIconID() {
		return jon;
	}
	public String getCommon() {
		return common;
	}
	public String getId() {
		return id;
	}
}
