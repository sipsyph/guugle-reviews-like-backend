package com.sip.chillhub.main.business.model;

public class Place {
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
	public Integer getUpvotes() {
		return upvotes;
	}
	public void setUpvotes(Integer upvotes) {
		this.upvotes = upvotes;
	}
	public Place(Long id, String displayName, String name, double coordinatesX, double coordinatesY, Integer upvotes) {
		this.id = id;
		this.displayName = displayName;
		this.name = name;
		this.coordinatesX = coordinatesX;
		this.coordinatesY = coordinatesY;
		this.upvotes = upvotes;
	}
	private Long id;
	private String displayName;
	private String name;
	private double coordinatesX;
	private double coordinatesY;
	private Integer upvotes;

}
