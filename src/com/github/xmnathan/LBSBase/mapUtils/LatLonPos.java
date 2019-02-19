package com.github.xmnathan.LBSBase.mapUtils;

/**
 * 地图经纬度坐标数据
 *  
 * @author nathan
 *
 */
public class LatLonPos {
	
	protected double latitude;	//纬度	在-90 与90 之间的double 型数值
	protected double longitude;	//经度	在-180 与180 之间的double 型数值
	

	public LatLonPos(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double Lat() {
		return latitude;
	}
	
	public double Lon() {
		return longitude;
	}
	
	public void setLat(double lat) {
		this.latitude = lat;
	}
	
	public void setLon(double lon) {
		this.longitude = lon;
	}
}
