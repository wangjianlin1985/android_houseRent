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
	/* ����������Ϣ���󣬽���������Ϣ�����ҵ�� */
	public String AddGuestBook(GuestBook guestBook) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����������Ϣ */
			String sqlString = "insert into GuestBook(title,content,userObj,addTime) values (";
			sqlString += "'" + guestBook.getTitle() + "',";
			sqlString += "'" + guestBook.getContent() + "',";
			sqlString += "'" + guestBook.getUserObj() + "',";
			sqlString += "'" + guestBook.getAddTime() + "'";
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
	public String DeleteGuestBook(int guestBookId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from GuestBook where guestBookId=" + guestBookId;
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
	/* ����������Ϣ */
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
