package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.WantHourseInfo;
import com.mobileserver.util.DB;

public class WantHourseInfoDAO {

	public List<WantHourseInfo> QueryWantHourseInfo(String userObj,String title,int position,int hourseTypeObj,int priceRangeObj) {
		List<WantHourseInfo> wantHourseInfoList = new ArrayList<WantHourseInfo>();
		DB db = new DB();
		String sql = "select * from WantHourseInfo where 1=1";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if (position != 0)
			sql += " and position=" + position;
		if (hourseTypeObj != 0)
			sql += " and hourseTypeObj=" + hourseTypeObj;
		if (priceRangeObj != 0)
			sql += " and priceRangeObj=" + priceRangeObj;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				WantHourseInfo wantHourseInfo = new WantHourseInfo();
				wantHourseInfo.setWantHourseId(rs.getInt("wantHourseId"));
				wantHourseInfo.setUserObj(rs.getString("userObj"));
				wantHourseInfo.setTitle(rs.getString("title"));
				wantHourseInfo.setPosition(rs.getInt("position"));
				wantHourseInfo.setHourseTypeObj(rs.getInt("hourseTypeObj"));
				wantHourseInfo.setPriceRangeObj(rs.getInt("priceRangeObj"));
				wantHourseInfo.setPrice(rs.getFloat("price"));
				wantHourseInfo.setLianxiren(rs.getString("lianxiren"));
				wantHourseInfo.setTelephone(rs.getString("telephone"));
				wantHourseInfoList.add(wantHourseInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return wantHourseInfoList;
	}
	/* ����������Ϣ���󣬽���������Ϣ�����ҵ�� */
	public String AddWantHourseInfo(WantHourseInfo wantHourseInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����������Ϣ */
			String sqlString = "insert into WantHourseInfo(userObj,title,position,hourseTypeObj,priceRangeObj,price,lianxiren,telephone) values (";
			sqlString += "'" + wantHourseInfo.getUserObj() + "',";
			sqlString += "'" + wantHourseInfo.getTitle() + "',";
			sqlString += wantHourseInfo.getPosition() + ",";
			sqlString += wantHourseInfo.getHourseTypeObj() + ",";
			sqlString += wantHourseInfo.getPriceRangeObj() + ",";
			sqlString += wantHourseInfo.getPrice() + ",";
			sqlString += "'" + wantHourseInfo.getLianxiren() + "',";
			sqlString += "'" + wantHourseInfo.getTelephone() + "'";
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
	public String DeleteWantHourseInfo(int wantHourseId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from WantHourseInfo where wantHourseId=" + wantHourseId;
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

	/* ���ݼ�¼��Ż�ȡ��������Ϣ */
	public WantHourseInfo GetWantHourseInfo(int wantHourseId) {
		WantHourseInfo wantHourseInfo = null;
		DB db = new DB();
		String sql = "select * from WantHourseInfo where wantHourseId=" + wantHourseId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				wantHourseInfo = new WantHourseInfo();
				wantHourseInfo.setWantHourseId(rs.getInt("wantHourseId"));
				wantHourseInfo.setUserObj(rs.getString("userObj"));
				wantHourseInfo.setTitle(rs.getString("title"));
				wantHourseInfo.setPosition(rs.getInt("position"));
				wantHourseInfo.setHourseTypeObj(rs.getInt("hourseTypeObj"));
				wantHourseInfo.setPriceRangeObj(rs.getInt("priceRangeObj"));
				wantHourseInfo.setPrice(rs.getFloat("price"));
				wantHourseInfo.setLianxiren(rs.getString("lianxiren"));
				wantHourseInfo.setTelephone(rs.getString("telephone"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return wantHourseInfo;
	}
	/* ����������Ϣ */
	public String UpdateWantHourseInfo(WantHourseInfo wantHourseInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update WantHourseInfo set ";
			sql += "userObj='" + wantHourseInfo.getUserObj() + "',";
			sql += "title='" + wantHourseInfo.getTitle() + "',";
			sql += "position=" + wantHourseInfo.getPosition() + ",";
			sql += "hourseTypeObj=" + wantHourseInfo.getHourseTypeObj() + ",";
			sql += "priceRangeObj=" + wantHourseInfo.getPriceRangeObj() + ",";
			sql += "price=" + wantHourseInfo.getPrice() + ",";
			sql += "lianxiren='" + wantHourseInfo.getLianxiren() + "',";
			sql += "telephone='" + wantHourseInfo.getTelephone() + "'";
			sql += " where wantHourseId=" + wantHourseInfo.getWantHourseId();
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
