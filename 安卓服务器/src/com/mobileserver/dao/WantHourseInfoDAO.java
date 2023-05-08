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
	/* 传入求租信息对象，进行求租信息的添加业务 */
	public String AddWantHourseInfo(WantHourseInfo wantHourseInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新求租信息 */
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
			result = "求租信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "求租信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除求租信息 */
	public String DeleteWantHourseInfo(int wantHourseId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from WantHourseInfo where wantHourseId=" + wantHourseId;
			db.executeUpdate(sqlString);
			result = "求租信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "求租信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到求租信息 */
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
	/* 更新求租信息 */
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
			result = "求租信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "求租信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
