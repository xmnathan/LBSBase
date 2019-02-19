package com.github.xmnathan.LBSBase.mapUtils;


/**
 * 国际GPS坐标系(WGS-84)坐标
 * 
 * @author nathan
 *
 */
public class WGSPos extends LatLonPos{

	public WGSPos(double latitude, double longitude) {
		super(latitude, longitude);
	}
	
	/**
	 * 转换到GCJ-02坐标系
	 * @return
	 */
	public GCJPos toGCJPos() {
	    if (MapUtils.outOfMainlandChina(this.latitude, this.longitude)) {
	      return new GCJPos(this.latitude, this.longitude);
	    }
	    double[] delta = MapUtils.delta(this.latitude, this.longitude);
	    return new GCJPos(this.latitude + delta[0], this.longitude + delta[1]);
	}
}

