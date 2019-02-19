package com.github.xmnathan.LBSBase.mapUtils;

/**
 * 火星坐标系 (GCJ-02)坐标
 * 
 * @author nathan
 *
 */
public class GCJPos extends LatLonPos {

	public GCJPos(double latitude, double longitude) {
		super(latitude, longitude);
	}
	
	/**
	 * 转换到WGS-84坐标系
	 * 粗略算法（精度为1米到2米之间）
	 * @return
	 */
	public WGSPos toWGSPos() {
		if (MapUtils.outOfMainlandChina(this.latitude, this.longitude)) {
		      return new WGSPos(this.latitude, this.longitude);
		    }
		    double[] delta = MapUtils.delta(this.latitude, this.longitude);
		    return new WGSPos(this.latitude - delta[0], this.longitude - delta[1]);
	}
	
	/**
	 * 转换到WGS-84坐标系
	 * 精确转换算法（精度为0.5米内）
	 * @return
	 */
	public WGSPos toExactWGSPos() {
	    final double initDelta = 0.01;
	    final double threshold = 0.000001;
	    double dLat = initDelta, dLng = initDelta;
	    double mLat = this.latitude - dLat, mLng = this.longitude - dLng;
	    double pLat = this.latitude + dLat, pLng = this.longitude + dLng;
	    double wgsLat, wgsLng;
	    WGSPos curWGSPos = null;
	    for (int i = 0; i < 30; i++) {
	      wgsLat = (mLat + pLat) / 2;
	      wgsLng = (mLng + pLng) / 2;
	      curWGSPos = new WGSPos(wgsLat, wgsLng);
	      GCJPos tmp = curWGSPos.toGCJPos();
	      dLat = tmp.Lat() - this.Lat();
	      dLng = tmp.Lon() - this.Lon();
	      
	      if ((Math.abs(dLat) < threshold) && (Math.abs(dLng) < threshold)) {
	        return curWGSPos;
	      } 
	      
	      if (dLat > 0) {
	        pLat = wgsLat;
	      } else {
	        mLat = wgsLat;
	      }
	      if (dLng > 0) {
	        pLng = wgsLng;
	      } else {
	        mLng = wgsLng;
	      }
	    }
	    return curWGSPos;
	  }
}
