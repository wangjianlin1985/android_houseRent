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
	/* ����¥����Ϣ���󣬽���¥����Ϣ�����ҵ�� */
	public String AddBuildingInfo(BuildingInfo buildingInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����¥����Ϣ */
			String sqlString = "insert into BuildingInfo(areaObj,buildingName,buildingPhoto) values (";
			sqlString += buildingInfo.getAreaObj() + ",";
			sqlString += "'" + buildingInfo.getBuildingName() + "',";
			sqlString += "'" + buildingInfo.getBuildingPhoto() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "¥����Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "¥����Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��¥����Ϣ */
	public String DeleteBuildingInfo(int buildingId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from BuildingInfo where buildingId=" + buildingId;
			db.executeUpdate(sqlString);
			result = "¥����Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "¥����Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����¥�̱�Ż�ȡ��¥����Ϣ */
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
	/* ����¥����Ϣ */
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
			result = "¥����Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "¥����Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
