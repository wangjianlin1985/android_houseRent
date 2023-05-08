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
	/* 传入租金范围对象，进行租金范围的添加业务 */
	public String AddPriceRange(PriceRange priceRange) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新租金范围 */
			String sqlString = "insert into PriceRange(priceName) values (";
			sqlString += "'" + priceRange.getPriceName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "租金范围添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "租金范围添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除租金范围 */
	public String DeletePriceRange(int rangeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from PriceRange where rangeId=" + rangeId;
			db.executeUpdate(sqlString);
			result = "租金范围删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "租金范围删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到租金范围 */
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
	/* 更新租金范围 */
	public String UpdatePriceRange(PriceRange priceRange) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update PriceRange set ";
			sql += "priceName='" + priceRange.getPriceName() + "'";
			sql += " where rangeId=" + priceRange.getRangeId();
			db.executeUpdate(sql);
			result = "租金范围更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "租金范围更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
