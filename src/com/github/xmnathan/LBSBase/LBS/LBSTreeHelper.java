package com.github.xmnathan.LBSBase.LBS;

import java.util.HashMap;
import java.util.Map;

import com.github.xmnathan.LBSBase.mapUtils.QuadTree.QuadTreeInfo;
import com.github.xmnathan.LBSBase.mapUtils.QuadTree.QuadTreeManager;

/**
 * 地图节点运用示例
 * 
 * @author nathan
 *
 */
public class LBSTreeHelper {

	public static Map<String, LBSNode> ALL_NODES = new HashMap<>();
	
	/**
	 * 获取指定地图节点唯一实例
	 * @param nodeCode
	 * @param deepLevel
	 * @param nodeInfo
	 * @return
	 */
	public static LBSNode getNodeInstance(String nodeCode, int deepLevel, QuadTreeInfo nodeInfo) {
		if(ALL_NODES.containsKey(nodeCode)) {
			return ALL_NODES.get(nodeCode);
		}
		LBSNode newNode = new LBSNode(deepLevel, nodeInfo);
		ALL_NODES.put(nodeCode, newNode);
		return newNode;
	}
	
	public static void addPoi(LBSPoi poi) {
		LBSNode root_node = ALL_NODES.get(QuadTreeManager.ROOT_NODE_CODE);
		root_node.addPoi(poi);
	}
	
	
	public static void main(String args[]) {
		
		LBSNode root_node = new LBSNode(0, new QuadTreeInfo(QuadTreeManager.ROOT_NODE_CODE,
								QuadTreeManager.LAT_ROOT_MIN, QuadTreeManager.LAT_ROOT_MAX,
								QuadTreeManager.LON_ROOT_MIN, QuadTreeManager.LON_ROOT_MAX));
		ALL_NODES.put(root_node.getInfo().code, root_node);
		
		LBSTreeHelper.addPoi(new LBSPoi("B025003X94",118.102555,24.436341,"厦门大学思明校区"));
		System.out.println(QuadTreeManager.getQuadTreeCodeByLatLon(118.102555,24.436341));
		
		LBSTreeHelper.addPoi(new LBSPoi("B0250019FA",118.094725,24.580073,"集美大学"));
		System.out.println(QuadTreeManager.getQuadTreeCodeByLatLon(118.094725,24.580073));
		
		LBSTreeHelper.addPoi(new LBSPoi("B025003LJD",118.08371,24.601965,"华侨大学(厦门校区)"));
		System.out.println(QuadTreeManager.getQuadTreeCodeByLatLon(118.08371,24.601965));
		
		LBSTreeHelper.addPoi(new LBSPoi("B0250042GR",118.087738,24.624113,"厦门理工学院"));
		System.out.println(QuadTreeManager.getQuadTreeCodeByLatLon(118.087738,24.624113));
		
		LBSTreeHelper.addPoi(new LBSPoi("B02500VK5M",118.30986,24.608698,"厦门大学翔安校区"));
		System.out.println(QuadTreeManager.getQuadTreeCodeByLatLon(118.30986,24.608698));
		
		LBSTreeHelper.addPoi(new LBSPoi("B025003KF9",118.165903,24.462229,"厦门城市职业学院"));
		System.out.println(QuadTreeManager.getQuadTreeCodeByLatLon(118.165903,24.462229));
		
		LBSTreeHelper.addPoi(new LBSPoi("B025003DLZ",118.094924,24.591499,"集美大学诚毅学院(北区)"));
		System.out.println(QuadTreeManager.getQuadTreeCodeByLatLon(118.094924,24.591499));
		
		LBSTreeHelper.addPoi(new LBSPoi("B025001KZ7",118.094463,24.565725,"华侨大学华文学院"));
		System.out.println(QuadTreeManager.getQuadTreeCodeByLatLon(118.094463,24.565725));
		
		LBSTreeHelper.addPoi(new LBSPoi("B0250047UP",118.037683,24.613757,"厦门兴才职业�?术学�?"));
		System.out.println(QuadTreeManager.getQuadTreeCodeByLatLon(118.037683,24.613757));
	}
	
	
	
}
