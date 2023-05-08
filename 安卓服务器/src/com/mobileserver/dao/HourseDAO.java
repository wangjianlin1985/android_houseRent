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
	/* ���뷿����Ϣ���󣬽��з�����Ϣ�����ҵ�� */
	public String AddHourse(Hourse hourse) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����·�����Ϣ */
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
			result = "������Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��������Ϣ */
	public String DeleteHourse(int hourseId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Hourse where hourseId=" + hourseId;
			db.executeUpdate(sqlString);
			result = "������Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݷ��ݱ�Ż�ȡ��������Ϣ */
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
	/* ���·�����Ϣ */
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
			result = "������Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
