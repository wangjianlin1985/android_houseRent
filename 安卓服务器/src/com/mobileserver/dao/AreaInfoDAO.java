package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.AreaInfo;
import com.mobileserver.util.DB;

public class AreaInfoDAO {

	public List<AreaInfo> QueryAreaInfo() {
		List<AreaInfo> areaInfoList = new ArrayList<AreaInfo>();
		DB db = new DB();
		String sql = "select * from AreaInfo where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				AreaInfo areaInfo = new AreaInfo();
				areaInfo.setAreaId(rs.getInt("areaId"));
				areaInfo.setAreaName(rs.getString("areaName"));
				areaInfoList.add(areaInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return areaInfoList;
	}
	/* 传入区域信息对象，进行区域信息的添加业务 */
	public String AddAreaInfo(AreaInfo areaInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新区域信息 */
			String sqlString = "insert into AreaInfo(areaName) values (";
			sqlString += "'" + areaInfo.getAreaName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "区域信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "区域信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除区域信息 */
	public String DeleteAreaInfo(int areaId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from AreaInfo where areaId=" + areaId;
			db.executeUpdate(sqlString);
			result = "区域信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "区域信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到区域信息 */
	public AreaInfo GetAreaInfo(int areaId) {
		AreaInfo areaInfo = null;
		DB db = new DB();
		String sql = "select * from AreaInfo where areaId=" + areaId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				areaInfo = new AreaInfo();
				areaInfo.setAreaId(rs.getInt("areaId"));
				areaInfo.setAreaName(rs.getString("areaName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return areaInfo;
	}
	/* 更新区域信息 */
	public String UpdateAreaInfo(AreaInfo areaInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update AreaInfo set ";
			sql += "areaName='" + areaInfo.getAreaName() + "'";
			sql += " where areaId=" + areaInfo.getAreaId();
			db.executeUpdate(sql);
			result = "区域信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "区域信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
