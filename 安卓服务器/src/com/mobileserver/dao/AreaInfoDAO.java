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
	/* ����������Ϣ���󣬽���������Ϣ�����ҵ�� */
	public String AddAreaInfo(AreaInfo areaInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����������Ϣ */
			String sqlString = "insert into AreaInfo(areaName) values (";
			sqlString += "'" + areaInfo.getAreaName() + "'";
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
	public String DeleteAreaInfo(int areaId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from AreaInfo where areaId=" + areaId;
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
	/* ����������Ϣ */
	public String UpdateAreaInfo(AreaInfo areaInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update AreaInfo set ";
			sql += "areaName='" + areaInfo.getAreaName() + "'";
			sql += " where areaId=" + areaInfo.getAreaId();
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
