package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.UserInfo;
import com.mobileserver.util.DB;

public class UserInfoDAO {

	public List<UserInfo> QueryUserInfo(String user_name,String realName,Timestamp birthday,String cardNumber,String city) {
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		DB db = new DB();
		String sql = "select * from UserInfo where 1=1";
		if (!user_name.equals(""))
			sql += " and user_name like '%" + user_name + "%'";
		if (!realName.equals(""))
			sql += " and realName like '%" + realName + "%'";
		if(birthday!=null)
			sql += " and birthday='" + birthday + "'";
		if (!cardNumber.equals(""))
			sql += " and cardNumber like '%" + cardNumber + "%'";
		if (!city.equals(""))
			sql += " and city like '%" + city + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setUser_name(rs.getString("user_name"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setRealName(rs.getString("realName"));
				userInfo.setSex(rs.getString("sex"));
				userInfo.setBirthday(rs.getTimestamp("birthday"));
				userInfo.setCardNumber(rs.getString("cardNumber"));
				userInfo.setCity(rs.getString("city"));
				userInfo.setPhoto(rs.getString("photo"));
				userInfo.setAddress(rs.getString("address"));
				userInfoList.add(userInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return userInfoList;
	}
	/* 传入用户信息对象，进行用户信息的添加业务 */
	public String AddUserInfo(UserInfo userInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新用户信息 */
			String sqlString = "insert into UserInfo(user_name,password,realName,sex,birthday,cardNumber,city,photo,address) values (";
			sqlString += "'" + userInfo.getUser_name() + "',";
			sqlString += "'" + userInfo.getPassword() + "',";
			sqlString += "'" + userInfo.getRealName() + "',";
			sqlString += "'" + userInfo.getSex() + "',";
			sqlString += "'" + userInfo.getBirthday() + "',";
			sqlString += "'" + userInfo.getCardNumber() + "',";
			sqlString += "'" + userInfo.getCity() + "',";
			sqlString += "'" + userInfo.getPhoto() + "',";
			sqlString += "'" + userInfo.getAddress() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "用户信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "用户信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除用户信息 */
	public String DeleteUserInfo(String user_name) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from UserInfo where user_name='" + user_name + "'";
			db.executeUpdate(sqlString);
			result = "用户信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "用户信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据用户名获取到用户信息 */
	public UserInfo GetUserInfo(String user_name) {
		UserInfo userInfo = null;
		DB db = new DB();
		String sql = "select * from UserInfo where user_name='" + user_name + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				userInfo = new UserInfo();
				userInfo.setUser_name(rs.getString("user_name"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setRealName(rs.getString("realName"));
				userInfo.setSex(rs.getString("sex"));
				userInfo.setBirthday(rs.getTimestamp("birthday"));
				userInfo.setCardNumber(rs.getString("cardNumber"));
				userInfo.setCity(rs.getString("city"));
				userInfo.setPhoto(rs.getString("photo"));
				userInfo.setAddress(rs.getString("address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return userInfo;
	}
	/* 更新用户信息 */
	public String UpdateUserInfo(UserInfo userInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update UserInfo set ";
			sql += "password='" + userInfo.getPassword() + "',";
			sql += "realName='" + userInfo.getRealName() + "',";
			sql += "sex='" + userInfo.getSex() + "',";
			sql += "birthday='" + userInfo.getBirthday() + "',";
			sql += "cardNumber='" + userInfo.getCardNumber() + "',";
			sql += "city='" + userInfo.getCity() + "',";
			sql += "photo='" + userInfo.getPhoto() + "',";
			sql += "address='" + userInfo.getAddress() + "'";
			sql += " where user_name='" + userInfo.getUser_name() + "'";
			db.executeUpdate(sql);
			result = "用户信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "用户信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
