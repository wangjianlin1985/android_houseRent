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
	/* ���뷿�������󣬽��з����������ҵ�� */
	public String AddHourseType(HourseType hourseType) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����·������ */
			String sqlString = "insert into HourseType(typeName) values (";
			sqlString += "'" + hourseType.getTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��������� */
	public String DeleteHourseType(int typeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from HourseType where typeId=" + typeId;
			db.executeUpdate(sqlString);
			result = "�������ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�������ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ��������Ż�ȡ��������� */
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
	/* ���·������ */
	public String UpdateHourseType(HourseType hourseType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update HourseType set ";
			sql += "typeName='" + hourseType.getTypeName() + "'";
			sql += " where typeId=" + hourseType.getTypeId();
			db.executeUpdate(sql);
			result = "���������³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
