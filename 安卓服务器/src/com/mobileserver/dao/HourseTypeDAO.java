package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.HourseType;
import com.mobileserver.util.DB;

public class HourseTypeDAO {

	public List<HourseType> QueryHourseType() {
		List<HourseType> hourseTypeList = new ArrayList<HourseType>();
		DB db = new DB();
		String sql = "select * from HourseType where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				HourseType hourseType = new HourseType();
				hourseType.setTypeId(rs.getInt("typeId"));
				hourseType.setTypeName(rs.getString("typeName"));
				hourseTypeList.add(hourseType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return hourseTypeList;
	}
	/* 传入房屋类别对象，进行房屋类别的添加业务 */
	public String AddHourseType(HourseType hourseType) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新房屋类别 */
			String sqlString = "insert into HourseType(typeName) values (";
			sqlString += "'" + hourseType.getTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "房屋类别添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房屋类别添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除房屋类别 */
	public String DeleteHourseType(int typeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from HourseType where typeId=" + typeId;
			db.executeUpdate(sqlString);
			result = "房屋类别删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房屋类别删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据类别编号获取到房屋类别 */
	public HourseType GetHourseType(int typeId) {
		HourseType hourseType = null;
		DB db = new DB();
		String sql = "select * from HourseType where typeId=" + typeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				hourseType = new HourseType();
				hourseType.setTypeId(rs.getInt("typeId"));
				hourseType.setTypeName(rs.getString("typeName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return hourseType;
	}
	/* 更新房屋类别 */
	public String UpdateHourseType(HourseType hourseType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update HourseType set ";
			sql += "typeName='" + hourseType.getTypeName() + "'";
			sql += " where typeId=" + hourseType.getTypeId();
			db.executeUpdate(sql);
			result = "房屋类别更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房屋类别更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
