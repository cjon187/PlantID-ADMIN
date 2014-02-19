package com.kpu.PlantidAdmin;

public class car3 {

	private String family;
	private String species;
	private String species2;
	private String jon;
	private String common;
	private String id;
	private String type;
	private String zones;
	private String leafform;
	private String leafarrangement;
	private String leafshape;
	private String floweringtime;
	private String flowercolour;
	private String fruitcolour;
	private String pest;
	private String code;
	private String lat;
	private String lon;
	public car3(String family, String species, String common, String id,String jon,String type,String zones,String leafform,String leafarrangement,String leafshape, String floweringtime,String flowercolour,String fruitcolour,String pest,String code, String lat,String lon,String species2)
	{
		super();
		this.family = family;
		this.species = species;
		this.id = id;
		this.common = common;
		this.jon=jon;
		this.type=type;
		this.zones=zones;
		this.leafform=leafform;
		this.leafarrangement=leafarrangement;
		this.leafshape=leafshape;
		this.floweringtime=floweringtime;
		this.flowercolour=flowercolour;
		this.fruitcolour=fruitcolour;
		this.pest=pest;
		this.code=code;
		this.lat=lat;
		this.lon=lon;
		this.species2=species2;
	}
	
	public String getFamily() {
		return family;
	}
	public String getSpecies() {
		return species;
	}
	public String getID() {
		return id;
	}
	public String getCommon() {
		return common;
	}
	public String getType() {
		return type;
	}
	public String getZones() {
		return zones;		
	}
	public String getLeafform() {
		return leafform;
	}
	public String getLeafarrangement() {
		return leafarrangement;
	}
	public String getLeafshape() {
		return leafshape;
	}
	public String getFloweringtime() {
		return floweringtime;
	}
	public String getFlowercolour() {
		return flowercolour;
	}
	public String getFruitColour() {
		return fruitcolour;
	}
	public String getPest() {
		return pest;
	}
	public String getIconID() {
		
		return jon;
	}
	public String getCode(){
		return code;
	}
	public String getLat(){
		return lat;
	}
	public String getLon(){
		return lon;
	}
	public String getSpecies2(){
		return species2;
	}

}