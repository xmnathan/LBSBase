package com.github.xmnathan.LBSBase.LBS;

import java.util.ArrayList;
import java.util.List;
import com.github.xmnathan.LBSBase.mapUtils.GCJPos;

/**
 * 地图POI  
 * 兴趣点信息   保存具体POI坐标的消息列表
 * 
 * @author nathan
 *
 */
public class LBSPoi {
	private String poiName;
	private String poiSign;			//POI标记
	private GCJPos pos;				//POI坐标
	private List<LBSMsg> msgList;	//当前 POI的消息列表
	
	LBSPoi(String poiSign, double lat, double lon){
		this.poiSign = poiSign;
		this.pos = new GCJPos(lat, lon);
		msgList = new ArrayList<>();
	}
	LBSPoi(String poiSign, double lat, double lon, String name){
		this.poiName = name;
		this.poiSign = poiSign;
		this.pos = new GCJPos(lat, lon);
		msgList = new ArrayList<>();
	}
	public String getName() {
		return poiName;
	}
	public void setName(String name) {
		poiName = name;
	}
	public String getSign() {
		return poiSign;
	}
	public GCJPos getPos() {
		return pos;
	}
	public double getLat() {
		return pos.Lat();
	}
	public double getLon() {
		return pos.Lon();
	}
	public void addNewMsg(LBSMsg msg) {
		msgList.add(msg);
	}
	
	public void dump() {
		System.out.format("%s %s (%fl,%fl)\n", poiName, poiSign, pos.Lat(),pos.Lon());
	}
}
