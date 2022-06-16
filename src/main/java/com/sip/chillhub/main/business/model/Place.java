package com.sip.chillhub.main.business.model;

public class Place {
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCoordinatesX() {
		return coordinatesX;
	}
	public void setCoordinatesX(double coordinatesX) {
		this.coordinatesX = coordinatesX;
	}
	public double getCoordinatesY() {
		return coordinatesY;
	}
	public void setCoordinatesY(double coordinatesY) {
		this.coordinatesY = coordinatesY;
	}
	public Place(Long id, String address, String name, double coordinatesX, double coordinatesY) {
		this.id = id;
		this.address = address;
		this.name = name;
		this.coordinatesX = coordinatesX;
		this.coordinatesY = coordinatesY;
	}
	public Place() {
		
	}
	private Long id;
	private String address;
	private String name;
	private double coordinatesX;
	private double coordinatesY;

}
