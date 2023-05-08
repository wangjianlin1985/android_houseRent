package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.PriceRange;
import com.mobileserver.util.DB;

public class PriceRangeDAO {

	public List<PriceRange> QueryPriceRange() {
		List<PriceRange> priceRangeList = new ArrayList<PriceRange>();
		DB db = new DB();
		String sql = "select * from PriceRange where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				PriceRange priceRange = new PriceRange();
				priceRange.setRangeId(rs.getInt("rangeId"));
				priceRange.setPriceName(rs.getString("priceName"));
				priceRangeList.add(priceRange);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return priceRangeList;
	}
	/* �������Χ���󣬽������Χ�����ҵ�� */
	public String AddPriceRange(PriceRange priceRange) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в��������Χ */
			String sqlString = "insert into PriceRange(priceName) values (";
			sqlString += "'" + priceRange.getPriceName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���Χ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���Χ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�����Χ */
	public String DeletePriceRange(int rangeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from PriceRange where rangeId=" + rangeId;
			db.executeUpdate(sqlString);
			result = "���Χɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���Χɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ�����Χ */
	public PriceRange GetPriceRange(int rangeId) {
		PriceRange priceRange = null;
		DB db = new DB();
		String sql = "select * from PriceRange where rangeId=" + rangeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				priceRange = new PriceRange();
				priceRange.setRangeId(rs.getInt("rangeId"));
				priceRange.setPriceName(rs.getString("priceName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return priceRange;
	}
	/* �������Χ */
	public String UpdatePriceRange(PriceRange priceRange) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update PriceRange set ";
			sql += "priceName='" + priceRange.getPriceName() + "'";
			sql += " where rangeId=" + priceRange.getRangeId();
			db.executeUpdate(sql);
			result = "���Χ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���Χ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
