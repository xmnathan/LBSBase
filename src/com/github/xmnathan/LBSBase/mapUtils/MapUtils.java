package com.github.xmnathan.LBSBase.mapUtils;

/**
 * 坐标系互转
 *  
 * 国际GPS坐标系(WGS-84)
 * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的互转
 * 
 * @author nathan
 * 
 */
public class MapUtils {
	//计算过程常量
    public static double PI = 3.1415926535897932384626;
    public static double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;
    
    //GCJ坐标的有效范围
    private static double GCJ_LON_MAX = 137.8347;
    private static double GCJ_LON_MIN = 72.004;
    private static double GCJ_LAT_MAX = 55.8271;
    private static double GCJ_LAT_MIN = 0.8293;
 
    /**
     * 转换纬度
     * 
     * @param x
     * @param y
     * @return
     */
    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }
 
    /**
     * 转换经度
     * 
     * @param x
     * @param y
     * @return
     */
    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0
                * PI)) * 2.0 / 3.0;
        return ret;
    }
    
    /**
    * 计算经纬度差值
    * 
    * @param lat纬度
    * @param lon经度
    * @return delta[0] 是纬度差，delta[1]是经度差
    */
   public static double[] delta(double lat,double lon){
     double[] delta = new double[2];
     double dLat = transformLat(lon-105.0, lat-35.0);
     double dLon = transformLon(lon-105.0, lat-35.0);
     double radLat = lat / 180.0 * PI;
     double magic = Math.sin(radLat);
     magic = 1 - ee*magic*magic;
     double sqrtMagic = Math.sqrt(magic);
     delta[0] = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
     delta[1] = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);
     return delta;
   }
   
    
    /**
     * 中国大陆地区(不包括台湾地区)
     * 
     * @param lat
     * @param lon
     * 
     * @return 是否大陆地区
     */
    public static boolean outOfMainlandChina (double lat, double lon) {
        if (lon < GCJ_LON_MIN || lon > GCJ_LON_MAX)
            return true;
        if (lat < GCJ_LAT_MIN || lat > GCJ_LAT_MAX)
            return true;
        return false;
    }
    
    /**
     * 国际GPS坐标系 与 火星坐标系 的转换算法
     * 
     * WGS-84 to GCJ-02
     * 
     * @param lat 
     * @param lon 
     * 
     * @return
     * */
    public static double[] gps84_To_Gcj02(double lat, double lon) {
        if (outOfMainlandChina(lat, lon)) {
            return new double[]{lat,lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat, mgLon};
    }
 
    /**
     * 国际GPS坐标系 与 火星坐标系 的转换算法
     * 
     * GCJ-02 to WGS-84
     * 
     * @param lat 
     * @param lon 
     * 
     * @return
     * */
    public static double[] gcj02_To_Gps84(double lat, double lon) {
        double[] delta = delta(lat, lon);
        double latitude = lat - delta[0];
        double lontitude = lon - delta[1];
        return new double[]{latitude, lontitude};
    }
    
    /**
     * 火星坐标系 与 百度坐标系 的转换算法
     * 
     * GCJ-02 to BD-09
     *
     * @param lat
     * @param lon
     * 
     * @return
     */
    public static double[] gcj02_To_Bd09(double lat, double lon) {
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        double[] gps = {tempLat,tempLon};
        return gps;
    }
 
    /**
     * 火星坐标系 与百度坐标系 的转换算法
     * 
     * BD-09 to GCJ-02
     * 
     * * @param bd_lat
     * * @param bd_lon 
     * * @return
     */
    public static double[] bd09_To_Gcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat,tempLon};
        return gps;
    }
 
    /**
     * 国际GPS坐标系 与 百度坐标系 的转换算法
     * 
     * WGS-84 to BD-09
     * 
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gps84_To_bd09(double lat,double lon){
        double[] gcj02 = gps84_To_Gcj02(lat,lon);
        double[] bd09 = gcj02_To_Bd09(gcj02[0],gcj02[1]);
        return bd09;
    }
    
    /**
     * 国际GPS坐标系 与 百度坐标系 的转换算法
     *  
     * BD-09 to WGS-84
     * 
     * @param lat
     * @param lon
     * @return
     */
    public static double[] bd09_To_gps84(double lat,double lon){
        double[] gcj02 = bd09_To_Gcj02(lat, lon);
        double[] gps84 = gcj02_To_Gps84(gcj02[0], gcj02[1]);
        //保留小数点后六位
        gps84[0] = retain6(gps84[0]);
        gps84[1] = retain6(gps84[1]);
        return gps84;
    }
 
    /**保留小数点后六位
     * @param num
     * @return
     */
    private static double retain6(double num){
        String result = String .format("%.6f", num);
        return Double.valueOf(result);
    }
 
}