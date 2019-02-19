package com.github.xmnathan.LBSBase.mapUtils.QuadTree;

public class QuadTreeInfo {
	public String code = "";
	public double lat_min = 0L;
	public double lat_max = 0L;
	public double lon_min = 0L;
	public double lon_max = 0L;
	//上层节点坐标信息
	public double lat_parent_min = 0L;
	public double lat_parent_max = 0L;
	public double lon_parent_min = 0L;
	public double lon_parent_max = 0L;
	
	/**
	 * 
	 * @param code
	 * @param latMin
	 * @param latMax
	 * @param lonMin
	 * @param lonMax
	 */
	public QuadTreeInfo(String code, double latMin, double latMax, double lonMin, double lonMax){
		this.code = code;
		lat_min = latMin;
		lat_max = latMax;
		lon_min = lonMin;
		lon_max = lonMax;
	}
	
	public QuadTreeInfo(String code, double latMin, double latMax, double lonMin, double lonMax, 
								  double latMinExt, double latMaxExt, double lonMinExt, double lonMaxExt){
		this.code = code;
		lat_min = latMin;
		lat_max = latMax;
		lon_min = lonMin;
		lon_max = lonMax;
		
		lat_parent_min = latMinExt;
		lat_parent_max = latMaxExt;
		lon_parent_min = lonMinExt;
		lon_parent_max = lonMaxExt;
	}
}
