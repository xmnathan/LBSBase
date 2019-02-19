package com.github.xmnathan.LBSBase.LBS;

import com.github.xmnathan.LBSBase.mapUtils.GCJPos;

/**
 * LBS消息  
 * 存储带有坐标信息的用户消息
 * 每个消息都有一个绑定的兴趣点POI
 * 
 * @author nathan
 *
 */
public class LBSMsg{
	
	private long msgSign;	//消息唯一标志
	private long humanId;	//消息发送者
	private int hotRat;		//消息热度
	
	private GCJPos pos;		//发送原始坐标位置
	private String poiSign;	//归属的POI标签
	
	
	public LBSMsg(){
		
	}
	
	public LBSMsg(long msgSign, long humanId, GCJPos pos, String poiSign){
		this.msgSign = msgSign;
		this.humanId = humanId;
		this.pos = pos;
		this.poiSign = poiSign;
	}
	
	public long getMsgSign() {
		return msgSign;
	}
	
	public long getHumanId() {
		return humanId;
	}
	
	public int getHotRat() {
		return hotRat;
	}
	
	public GCJPos getPos() {
		return pos;
	}
	
	public String getPoiSign() {
		return poiSign;
	}
}
