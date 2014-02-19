package com.kpu.PlantidAdmin;

import java.io.InputStream;

import android.graphics.drawable.Drawable;

/**
 * Store information about a car.
 */
public class Car2 {
	private String family;
	private String species;
	private String jon;
	private String common;
	private String id;
	private String sdate;
	
	public Car2(String family, String species, String common, String id,String jon,String sdate) {
		super();
		this.family = family;
		this.species = species;
		this.id = id;
		this.common = common;
		this.jon=jon;
		this.sdate=sdate;
		
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
	public String getDate() {
		return sdate;
	}
}
