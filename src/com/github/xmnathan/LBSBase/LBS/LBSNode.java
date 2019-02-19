package com.github.xmnathan.LBSBase.LBS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.xmnathan.LBSBase.mapUtils.QuadTree.QuadTreeInfo;
import com.github.xmnathan.LBSBase.mapUtils.QuadTree.QuadTreeManager;

/**
 * LBS消息节点
 * 
 * @author nathan
 *
 */
public class LBSNode {
						
	private List<LBSPoi> poiList;			//当前节点  POI点列表
	private Map<String, LBSPoi> poiMap;		//当前节点  POI点映射
	
	private int deepLevel;					//节点深度等级			
	private QuadTreeInfo nodeInfo;			//节点信息
	
	LBSNode(int deepLevel, QuadTreeInfo nodeInfo) {
		this.deepLevel = deepLevel;
		this.nodeInfo = nodeInfo;

		if(isLeaf()) {
			poiList = new ArrayList<>();
			poiMap = new HashMap<>();
		}
	}
	/**
	 * 是否是叶子节点
	 * @return
	 */
	public boolean isLeaf() {
		return deepLevel >= QuadTreeManager.LEVEL_DEFAULT;
	}
	
	public int getDeepLevel() {
		return deepLevel;
	}
	
	public QuadTreeInfo getInfo() {
		return nodeInfo;
	}
	
	public List<LBSPoi> getPoiList(){
		return poiList;
	}
	
	public LBSPoi findPoi(String poiSign){
		if(!isLeaf()) {
			return null;
		}
		return poiMap.get(poiSign);
	}
	
	public void addPoi(LBSPoi poi) {
		System.out.println(nodeInfo.code);
		if(isLeaf()) {
			poiList.add(poi);
			poiMap.put(poi.getSign(), poi);
		}else {
			//继续更深的层�?
			double lat = poi.getLat();
			double lon = poi.getLon();
			String childcode = nodeInfo.code;
			double lat_middle = (nodeInfo.lat_min + nodeInfo.lat_max)/2;
			double lon_middle = (nodeInfo.lon_min + nodeInfo.lon_max)/2;
			if(lat >=lat_middle)
			{
				if(lon >= lon_middle)
				{
					//右上角区域，递归遍历
					childcode += "1";
					LBSNode childNode = LBSTreeHelper.getNodeInstance(childcode, deepLevel+1,
							new QuadTreeInfo(childcode, lat_middle, nodeInfo.lat_max, lon_middle, nodeInfo.lon_max,
									nodeInfo.lat_min,nodeInfo.lat_max,nodeInfo.lon_min,nodeInfo.lon_max)
							);
					childNode.addPoi(poi);
				}else{
					//左上角区域，递归遍历
					childcode += "0";			
					LBSNode childNode = LBSTreeHelper.getNodeInstance(childcode,deepLevel+1,
							new QuadTreeInfo(childcode, lat_middle, nodeInfo.lat_max, nodeInfo.lon_min, lon_middle,
									nodeInfo.lat_min,nodeInfo.lat_max,nodeInfo.lon_min,nodeInfo.lon_max)
							);
					childNode.addPoi(poi);
				}
			}else{
				if(lon >= lon_middle)
				{
					//右下角区域，递归遍历
					childcode += "3";
					LBSNode childNode = LBSTreeHelper.getNodeInstance(childcode,deepLevel+1,
							new QuadTreeInfo(childcode, nodeInfo.lat_min, lat_middle, lon_middle, nodeInfo.lon_max,
									nodeInfo.lat_min,nodeInfo.lat_max,nodeInfo.lon_min,nodeInfo.lon_max)
							);
					childNode.addPoi(poi);
				}else{
					//左下角区域，递归遍历
					childcode += "2";
					LBSNode childNode = LBSTreeHelper.getNodeInstance(childcode,deepLevel+1,
							new QuadTreeInfo(childcode, nodeInfo.lat_min, lat_middle, nodeInfo.lon_min, lon_middle,
									nodeInfo.lat_min,nodeInfo.lat_max,nodeInfo.lon_min,nodeInfo.lon_max)
							);
					childNode.addPoi(poi);
				}
			}	
			
		}
	}
	
	public void dumpPoi() {
		if(!isLeaf()) return;
		
		for(LBSPoi poi:poiList) {
			poi.dump();
		}
	}
}
