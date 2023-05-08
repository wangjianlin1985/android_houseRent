package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Hourse;
import com.mobileserver.util.DB;

public class HourseDAO {

	public List<Hourse> QueryHourse(String hourseName,int buildingObj,int hourseTypeObj,int priceRangeObj,String madeYear,String connectPerson,String connectPhone) {
		List<Hourse> hourseList = new ArrayList<Hourse>();
		DB db = new DB();
		String sql = "select * from Hourse where 1=1";
		if (!hourseName.equals(""))
			sql += " and hourseName like '%" + hourseName + "%'";
		if (buildingObj != 0)
			sql += " and buildingObj=" + buildingObj;
		if (hourseTypeObj != 0)
			sql += " and hourseTypeObj=" + hourseTypeObj;
		if (priceRangeObj != 0)
			sql += " and priceRangeObj=" + priceRangeObj;
		if (!madeYear.equals(""))
			sql += " and madeYear like '%" + madeYear + "%'";
		if (!connectPerson.equals(""))
			sql += " and connectPerson like '%" + connectPerson + "%'";
		if (!connectPhone.equals(""))
			sql += " and connectPhone like '%" + connectPhone + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Hourse hourse = new Hourse();
				hourse.setHourseId(rs.getInt("hourseId"));
				hourse.setHourseName(rs.getString("hourseName"));
				hourse.setBuildingObj(rs.getInt("buildingObj"));
				hourse.setHousePhoto(rs.getString("housePhoto"));
				hourse.setHourseTypeObj(rs.getInt("hourseTypeObj"));
				hourse.setPriceRangeObj(rs.getInt("priceRangeObj"));
				hourse.setArea(rs.getString("area"));
				hourse.setPrice(rs.getFloat("price"));
				hourse.setLouceng(rs.getString("louceng"));
				hourse.setZhuangxiu(rs.getString("zhuangxiu"));
				hourse.setCaoxiang(rs.getString("caoxiang"));
				hourse.setMadeYear(rs.getString("madeYear"));
				hourse.setConnectPerson(rs.getString("connectPerson"));
				hourse.setConnectPhone(rs.getString("connectPhone"));
				hourse.setDetail(rs.getString("detail"));
				hourse.setAddress(rs.getString("address"));
				hourseList.add(hourse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return hourseList;
	}
	/* 传入房屋信息对象，进行房屋信息的添加业务 */
	public String AddHourse(Hourse hourse) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新房屋信息 */
			String sqlString = "insert into Hourse(hourseName,buildingObj,housePhoto,hourseTypeObj,priceRangeObj,area,price,louceng,zhuangxiu,caoxiang,madeYear,connectPerson,connectPhone,detail,address) values (";
			sqlString += "'" + hourse.getHourseName() + "',";
			sqlString += hourse.getBuildingObj() + ",";
			sqlString += "'" + hourse.getHousePhoto() + "',";
			sqlString += hourse.getHourseTypeObj() + ",";
			sqlString += hourse.getPriceRangeObj() + ",";
			sqlString += "'" + hourse.getArea() + "',";
			sqlString += hourse.getPrice() + ",";
			sqlString += "'" + hourse.getLouceng() + "',";
			sqlString += "'" + hourse.getZhuangxiu() + "',";
			sqlString += "'" + hourse.getCaoxiang() + "',";
			sqlString += "'" + hourse.getMadeYear() + "',";
			sqlString += "'" + hourse.getConnectPerson() + "',";
			sqlString += "'" + hourse.getConnectPhone() + "',";
			sqlString += "'" + hourse.getDetail() + "',";
			sqlString += "'" + hourse.getAddress() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "房屋信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房屋信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除房屋信息 */
	public String DeleteHourse(int hourseId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Hourse where hourseId=" + hourseId;
			db.executeUpdate(sqlString);
			result = "房屋信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房屋信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据房屋编号获取到房屋信息 */
	public Hourse GetHourse(int hourseId) {
		Hourse hourse = null;
		DB db = new DB();
		String sql = "select * from Hourse where hourseId=" + hourseId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				hourse = new Hourse();
				hourse.setHourseId(rs.getInt("hourseId"));
				hourse.setHourseName(rs.getString("hourseName"));
				hourse.setBuildingObj(rs.getInt("buildingObj"));
				hourse.setHousePhoto(rs.getString("housePhoto"));
				hourse.setHourseTypeObj(rs.getInt("hourseTypeObj"));
				hourse.setPriceRangeObj(rs.getInt("priceRangeObj"));
				hourse.setArea(rs.getString("area"));
				hourse.setPrice(rs.getFloat("price"));
				hourse.setLouceng(rs.getString("louceng"));
				hourse.setZhuangxiu(rs.getString("zhuangxiu"));
				hourse.setCaoxiang(rs.getString("caoxiang"));
				hourse.setMadeYear(rs.getString("madeYear"));
				hourse.setConnectPerson(rs.getString("connectPerson"));
				hourse.setConnectPhone(rs.getString("connectPhone"));
				hourse.setDetail(rs.getString("detail"));
				hourse.setAddress(rs.getString("address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return hourse;
	}
	/* 更新房屋信息 */
	public String UpdateHourse(Hourse hourse) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Hourse set ";
			sql += "hourseName='" + hourse.getHourseName() + "',";
			sql += "buildingObj=" + hourse.getBuildingObj() + ",";
			sql += "housePhoto='" + hourse.getHousePhoto() + "',";
			sql += "hourseTypeObj=" + hourse.getHourseTypeObj() + ",";
			sql += "priceRangeObj=" + hourse.getPriceRangeObj() + ",";
			sql += "area='" + hourse.getArea() + "',";
			sql += "price=" + hourse.getPrice() + ",";
			sql += "louceng='" + hourse.getLouceng() + "',";
			sql += "zhuangxiu='" + hourse.getZhuangxiu() + "',";
			sql += "caoxiang='" + hourse.getCaoxiang() + "',";
			sql += "madeYear='" + hourse.getMadeYear() + "',";
			sql += "connectPerson='" + hourse.getConnectPerson() + "',";
			sql += "connectPhone='" + hourse.getConnectPhone() + "',";
			sql += "detail='" + hourse.getDetail() + "',";
			sql += "address='" + hourse.getAddress() + "'";
			sql += " where hourseId=" + hourse.getHourseId();
			db.executeUpdate(sql);
			result = "房屋信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房屋信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
