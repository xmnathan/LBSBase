# LBSBase 简介
LBS application basic library

地图类应用服务 基础库

# Purpose 用途
Transform coordinate between earth(WGS-84) and mars in china(GCJ-02) and baidu(BD-09).

在国际坐标系、火星坐标系、百度坐标系之间转换坐标。

Use a quadtree to manage objects scattered across the map.

用四叉树管理散布在地图上的对象。

# Description 说明
# 四叉树

四叉树索引的基本思想是将地理空间递归划分为不同层次的树结构。

将已知范围的空间等分成四个相等的子空间，如此递归下去，直至树的层次达到一定深度或者满足某种要求后停止分割。

四叉树的结构比较简单，并且当空间数据对象分布比较均匀时，具有比较高的空间数据插入和查询效率，因此四叉树是GIS中常用的空间索引之一。

四叉树所管理的地理空间对象都存储在叶子节点上，中间节点以及根节点不存储地理空间对象。

每个空间节点都有唯一的标签编码，可基于任意坐标快速查询到节点编码，进而获取到对应节点信息。

