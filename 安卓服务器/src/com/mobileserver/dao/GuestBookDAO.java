package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.GuestBook;
import com.mobileserver.util.DB;

public class GuestBookDAO {

	public List<GuestBook> QueryGuestBook(String title,String userObj) {
		List<GuestBook> guestBookList = new ArrayList<GuestBook>();
		DB db = new DB();
		String sql = "select * from GuestBook where 1=1";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				GuestBook guestBook = new GuestBook();
				guestBook.setGuestBookId(rs.getInt("guestBookId"));
				guestBook.setTitle(rs.getString("title"));
				guestBook.setContent(rs.getString("content"));
				guestBook.setUserObj(rs.getString("userObj"));
				guestBook.setAddTime(rs.getString("addTime"));
				guestBookList.add(guestBook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return guestBookList;
	}
	/* 传入留言信息对象，进行留言信息的添加业务 */
	public String AddGuestBook(GuestBook guestBook) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新留言信息 */
			String sqlString = "insert into GuestBook(title,content,userObj,addTime) values (";
			sqlString += "'" + guestBook.getTitle() + "',";
			sqlString += "'" + guestBook.getContent() + "',";
			sqlString += "'" + guestBook.getUserObj() + "',";
			sqlString += "'" + guestBook.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "留言信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "留言信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除留言信息 */
	public String DeleteGuestBook(int guestBookId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from GuestBook where guestBookId=" + guestBookId;
			db.executeUpdate(sqlString);
			result = "留言信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "留言信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到留言信息 */
	public GuestBook GetGuestBook(int guestBookId) {
		GuestBook guestBook = null;
		DB db = new DB();
		String sql = "select * from GuestBook where guestBookId=" + guestBookId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				guestBook = new GuestBook();
				guestBook.setGuestBookId(rs.getInt("guestBookId"));
				guestBook.setTitle(rs.getString("title"));
				guestBook.setContent(rs.getString("content"));
				guestBook.setUserObj(rs.getString("userObj"));
				guestBook.setAddTime(rs.getString("addTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return guestBook;
	}
	/* 更新留言信息 */
	public String UpdateGuestBook(GuestBook guestBook) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update GuestBook set ";
			sql += "title='" + guestBook.getTitle() + "',";
			sql += "content='" + guestBook.getContent() + "',";
			sql += "userObj='" + guestBook.getUserObj() + "',";
			sql += "addTime='" + guestBook.getAddTime() + "'";
			sql += " where guestBookId=" + guestBook.getGuestBookId();
			db.executeUpdate(sql);
			result = "留言信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "留言信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
