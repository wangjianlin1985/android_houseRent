package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.BuildingInfo;
import com.mobileserver.util.DB;

public class BuildingInfoDAO {

	public List<BuildingInfo> QueryBuildingInfo(int areaObj,String buildingName) {
		List<BuildingInfo> buildingInfoList = new ArrayList<BuildingInfo>();
		DB db = new DB();
		String sql = "select * from BuildingInfo where 1=1";
		if (areaObj != 0)
			sql += " and areaObj=" + areaObj;
		if (!buildingName.equals(""))
			sql += " and buildingName like '%" + buildingName + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				BuildingInfo buildingInfo = new BuildingInfo();
				buildingInfo.setBuildingId(rs.getInt("buildingId"));
				buildingInfo.setAreaObj(rs.getInt("areaObj"));
				buildingInfo.setBuildingName(rs.getString("buildingName"));
				buildingInfo.setBuildingPhoto(rs.getString("buildingPhoto"));
				buildingInfoList.add(buildingInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return buildingInfoList;
	}
	/* 传入楼盘信息对象，进行楼盘信息的添加业务 */
	public String AddBuildingInfo(BuildingInfo buildingInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新楼盘信息 */
			String sqlString = "insert into BuildingInfo(areaObj,buildingName,buildingPhoto) values (";
			sqlString += buildingInfo.getAreaObj() + ",";
			sqlString += "'" + buildingInfo.getBuildingName() + "',";
			sqlString += "'" + buildingInfo.getBuildingPhoto() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "楼盘信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "楼盘信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除楼盘信息 */
	public String DeleteBuildingInfo(int buildingId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from BuildingInfo where buildingId=" + buildingId;
			db.executeUpdate(sqlString);
			result = "楼盘信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "楼盘信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据楼盘编号获取到楼盘信息 */
	public BuildingInfo GetBuildingInfo(int buildingId) {
		BuildingInfo buildingInfo = null;
		DB db = new DB();
		String sql = "select * from BuildingInfo where buildingId=" + buildingId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				buildingInfo = new BuildingInfo();
				buildingInfo.setBuildingId(rs.getInt("buildingId"));
				buildingInfo.setAreaObj(rs.getInt("areaObj"));
				buildingInfo.setBuildingName(rs.getString("buildingName"));
				buildingInfo.setBuildingPhoto(rs.getString("buildingPhoto"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return buildingInfo;
	}
	/* 更新楼盘信息 */
	public String UpdateBuildingInfo(BuildingInfo buildingInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update BuildingInfo set ";
			sql += "areaObj=" + buildingInfo.getAreaObj() + ",";
			sql += "buildingName='" + buildingInfo.getBuildingName() + "',";
			sql += "buildingPhoto='" + buildingInfo.getBuildingPhoto() + "'";
			sql += " where buildingId=" + buildingInfo.getBuildingId();
			db.executeUpdate(sql);
			result = "楼盘信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "楼盘信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
