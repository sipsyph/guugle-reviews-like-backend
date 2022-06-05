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
	public double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}
	public Integer getMemoirsAmount() {
		return memoirsAmount;
	}
	public void setMemoirsAmount(Integer memoirsAmount) {
		this.memoirsAmount = memoirsAmount;
	}
	public Integer getEngagesAmount() {
		return engagesAmount;
	}
	public void setEngagesAmount(Integer engagesAmount) {
		this.engagesAmount = engagesAmount;
	}
	@Override
	public String toString() {
		return "Place [id=" + id + ", displayName=" + displayName + ", name=" + name + ", coordinatesX=" + coordinatesX
				+ ", coordinatesY=" + coordinatesY + ", avgRating=" + avgRating + ", memoirsAmount=" + memoirsAmount
				+ ", engagesAmount=" + engagesAmount + "]";
	}
	public Place(Long id, String displayName, String name, double coordinatesX, double coordinatesY, double avgRating,
			Integer memoirsAmount, Integer engagesAmount) {
		this.id = id;
		this.displayName = displayName;
		this.name = name;
		this.coordinatesX = coordinatesX;
		this.coordinatesY = coordinatesY;
		this.avgRating = avgRating;
		this.memoirsAmount = memoirsAmount;
		this.engagesAmount = engagesAmount;
	}
	
	private Long id;
	private String displayName;
	private String name;
	private double coordinatesX;
	private double coordinatesY;
	private double avgRating;
	private Integer memoirsAmount;
	private Integer engagesAmount;

}
