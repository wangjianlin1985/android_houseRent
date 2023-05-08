package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.NewsInfo;
import com.mobileserver.util.DB;

public class NewsInfoDAO {

	public List<NewsInfo> QueryNewsInfo(String newsTitle,Timestamp newsDate) {
		List<NewsInfo> newsInfoList = new ArrayList<NewsInfo>();
		DB db = new DB();
		String sql = "select * from NewsInfo where 1=1";
		if (!newsTitle.equals(""))
			sql += " and newsTitle like '%" + newsTitle + "%'";
		if(newsDate!=null)
			sql += " and newsDate='" + newsDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				NewsInfo newsInfo = new NewsInfo();
				newsInfo.setNewsId(rs.getInt("newsId"));
				newsInfo.setNewsTitle(rs.getString("newsTitle"));
				newsInfo.setNewsContent(rs.getString("newsContent"));
				newsInfo.setNewsDate(rs.getTimestamp("newsDate"));
				newsInfoList.add(newsInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return newsInfoList;
	}
	/* �������Ź�����󣬽������Ź�������ҵ�� */
	public String AddNewsInfo(NewsInfo newsInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в��������Ź��� */
			String sqlString = "insert into NewsInfo(newsTitle,newsContent,newsDate) values (";
			sqlString += "'" + newsInfo.getNewsTitle() + "',";
			sqlString += "'" + newsInfo.getNewsContent() + "',";
			sqlString += "'" + newsInfo.getNewsDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���Ź�����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���Ź������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�����Ź��� */
	public String DeleteNewsInfo(int newsId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from NewsInfo where newsId=" + newsId;
			db.executeUpdate(sqlString);
			result = "���Ź���ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���Ź���ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ�����Ź��� */
	public NewsInfo GetNewsInfo(int newsId) {
		NewsInfo newsInfo = null;
		DB db = new DB();
		String sql = "select * from NewsInfo where newsId=" + newsId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				newsInfo = new NewsInfo();
				newsInfo.setNewsId(rs.getInt("newsId"));
				newsInfo.setNewsTitle(rs.getString("newsTitle"));
				newsInfo.setNewsContent(rs.getString("newsContent"));
				newsInfo.setNewsDate(rs.getTimestamp("newsDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return newsInfo;
	}
	/* �������Ź��� */
	public String UpdateNewsInfo(NewsInfo newsInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update NewsInfo set ";
			sql += "newsTitle='" + newsInfo.getNewsTitle() + "',";
			sql += "newsContent='" + newsInfo.getNewsContent() + "',";
			sql += "newsDate='" + newsInfo.getNewsDate() + "'";
			sql += " where newsId=" + newsInfo.getNewsId();
			db.executeUpdate(sql);
			result = "���Ź�����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���Ź������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
