package com.github.xmnathan.LBSBase.mapUtils.QuadTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 松散四叉树管理器
 * 管理四叉树的构建
 * 经纬度对应节点速查
 * 
 *	@author nathan
 */

/* 
****************************************************************** 
             象限划分:
		 top left   (0) | top right   (1) 
		----------------|------------------
		 bottom left(2) | bottom right(3) 
     
             对象限的划分编码方式如下:

		|————————|————————|—————————|—————————|
		|        |        |         |         |
		|   000  |   001  |   010   |   011   |
		|———————00————————|————————01—————————|
		|   002  |   003  |   012   |   013   |
		|        |        |         |         |
		|————————|————————0—————————|—————————|
		|        |        |         |         |
		|   020  |   021  |   030   |   031   |
		|———————02————————|————————03—————————|
		|   022  |   023  |   032   |   033   |
		|        |        |         |         |
		|————————|————————|—————————|—————————|
	
******************************************************************
*/
public class QuadTreeManager {
	
	public final static String ROOT_NODE_CODE = "0";
	public final static int LEVEL_DEFAULT = 15;
	public final static double EXTEND = 0.002;
	public final static double LAT_ROOT_MIN = -90L;
	public final static double LON_ROOT_MIN = -180L;
	public final static double LAT_ROOT_MAX = 90L;
	public final static double LON_ROOT_MAX = 180L;
	
	/**
	 * 通过经纬度获取所属区域四叉树编码
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static String getQuadTreeCodeByLatLon(double lat,double lon)
	{
		String code = "";
		code = findQuadTreeCode("0",lat,lon,LAT_ROOT_MIN, LAT_ROOT_MAX, LON_ROOT_MIN, LON_ROOT_MAX,0);
		return code;
	}
	
	/**
	 * 通过经纬度获取扩展之后，存储POI所属四叉树编码集合
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static List<String> getQuadTreeExtendCodeByLatLon(double lat,double lon)
	{
		return findQuadTreeExtendCode("0",lat,lon,LAT_ROOT_MIN, LAT_ROOT_MAX, LON_ROOT_MIN, LON_ROOT_MAX,0);
	}
	
	/**
	 * 递归查询扩展之后的四叉树，查找经纬度对应松散四叉树编码集合
	 * @param parentcode
	 * @param lat_current
	 * @param lon_current
	 * @param lat_min
	 * @param lat_max
	 * @param lon_min
	 * @param lon_max
	 * @param level
	 * @return
	 */
	private static List<String> findQuadTreeExtendCode(String parentcode,double lat_current,double lon_current,double lat_min,double lat_max,double lon_min,double lon_max,int level)
	{
		List<String> childcodes = new ArrayList<String>();
		if(level < QuadTreeManager.LEVEL_DEFAULT)
		{
			String codes = parentcode;
			//等级增加
			level++;
			//四叉树划分
			double lat_middle = (lat_min + lat_max)/2;
			double lon_middle = (lon_min + lon_max)/2;
			
			//判断是否在右上角区域
			if((lat_current >=lat_middle - EXTEND)&&(lon_current >= lon_middle - EXTEND)
					&&(lon_current <= lon_max + EXTEND)&&(lat_current <= lat_max + EXTEND))
			{
				
				String codes1 = codes + "1";
				List<String> codeall = findQuadTreeExtendCode(codes1,lat_current,lon_current,lat_middle,lat_max,lon_middle,lon_max,level);
				if(codeall!=null&&codeall.size() > 0)
				{
					for(int i = 0; i < codeall.size();i++)
					{
						childcodes.add(codeall.get(i));
					}
				}
			}
			
			//判断是否在左上角区域
			if((lat_current >=lat_middle - EXTEND)&&(lon_current <= lon_middle + EXTEND)
					&&(lon_current >= lon_min - EXTEND)&&(lat_current <= lat_max + EXTEND))
			{
				String codes2 = codes + "0";
				//递归获取子节点编码
				List<String> codeall = findQuadTreeExtendCode(codes2,lat_current,lon_current,lat_middle,lat_max,lon_min,lon_middle,level);
				if(codeall!=null&&codeall.size() > 0)
				{
					for(int i = 0; i < codeall.size();i++)
					{
						childcodes.add(codeall.get(i));
					}
				}
			}
			
			//判断是否在右下角区域
			if((lat_current <=lat_middle + EXTEND)&&(lon_current >= lon_middle - EXTEND)
					&&(lon_current <= lon_max + EXTEND)&&(lat_current >= lat_min - EXTEND))
			{
				String codes3 = codes + "3";
				//递归获取子节点编码
				List<String> codeall = findQuadTreeExtendCode(codes3,lat_current,lon_current,lat_min,lat_middle,lon_middle,lon_max,level);
				if(codeall!=null&&codeall.size() > 0)
				{
					for(int i = 0; i < codeall.size();i++)
					{
						childcodes.add(codeall.get(i));
					}
				}
			}
			
			//判断是否在左下角区域
			if((lat_current <=lat_middle + EXTEND)&&(lon_current <= lon_middle + EXTEND)
					&&(lon_current >= lon_min - EXTEND)&&(lat_current >= lat_min - EXTEND))
			{
				String codes4 = codes + "2";
				//递归获取子节点编码
				List<String> codeall = findQuadTreeExtendCode(codes4,lat_current,lon_current,lat_min,lat_middle,lon_min,lon_middle,level);
				if(codeall!=null&&codeall.size() > 0)
				{
					for(int i = 0; i < codeall.size();i++)
					{
						childcodes.add(codeall.get(i));
					}
				}
			}
		}else
		{
			childcodes.add(parentcode);
		}
		return childcodes;
		
	}
	/**
	 * 递归循环遍历四叉树,查找对应经纬度的四叉树编码值
	 * @param code
	 * @param lat_current
	 * @param lon_current
	 * @param lat_min
	 * @param lat_max
	 * @param lon_min
	 * @param lon_max
	 * @param level
	 * @return
	 */
	private static String findQuadTreeCode(String parentcode,double lat_current,double lon_current,double lat_min,double lat_max,double lon_min,double lon_max,int level)
	{
		String childcode = "";
		if(level < QuadTreeManager.LEVEL_DEFAULT)
		{
			String codes = parentcode;
			level++;
			double lat_middle = (lat_min + lat_max)/2;
			double lon_middle = (lon_min + lon_max)/2;
			if(lat_current >=lat_middle)
			{
				
				if(lon_current >= lon_middle)
				{
					//右上角区域，递归遍历
					codes += "1";
					childcode = findQuadTreeCode(codes,lat_current,lon_current,lat_middle,lat_max,lon_middle,lon_max,level);
				}else{
					//左上角区域，递归遍历
					codes += "0";
					childcode = findQuadTreeCode(codes,lat_current,lon_current,lat_middle,lat_max,lon_min,lon_middle,level);
				}
			}else{
				if(lon_current >= lon_middle)
				{
					//右下角区域，递归遍历
					codes += "3";
					childcode = findQuadTreeCode(codes,lat_current,lon_current,lat_min,lat_middle,lon_middle,lon_max,level);
				}else{
					//左下角区域，递归遍历
					codes += "2";
					childcode = findQuadTreeCode(codes,lat_current,lon_current,lat_min,lat_middle,lon_min,lon_middle,level);
				}
			}			
		}
		else
		{
			childcode = parentcode;
		}
		return childcode;
	}
	
}