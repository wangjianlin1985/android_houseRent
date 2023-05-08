package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.NewsInfoDAO;
import com.mobileserver.domain.NewsInfo;

import org.json.JSONStringer;

public class NewsInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�������Ź���ҵ������*/
	private NewsInfoDAO newsInfoDAO = new NewsInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public NewsInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ���Ź���Ĳ�����Ϣ*/
			String newsTitle = request.getParameter("newsTitle");
			newsTitle = newsTitle == null ? "" : new String(request.getParameter(
					"newsTitle").getBytes("iso-8859-1"), "UTF-8");
			Timestamp newsDate = null;
			if (request.getParameter("newsDate") != null)
				newsDate = Timestamp.valueOf(request.getParameter("newsDate"));

			/*����ҵ���߼���ִ�����Ź����ѯ*/
			List<NewsInfo> newsInfoList = newsInfoDAO.QueryNewsInfo(newsTitle,newsDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<NewsInfos>").append("\r\n");
			for (int i = 0; i < newsInfoList.size(); i++) {
				sb.append("	<NewsInfo>").append("\r\n")
				.append("		<newsId>")
				.append(newsInfoList.get(i).getNewsId())
				.append("</newsId>").append("\r\n")
				.append("		<newsTitle>")
				.append(newsInfoList.get(i).getNewsTitle())
				.append("</newsTitle>").append("\r\n")
				.append("		<newsContent>")
				.append(newsInfoList.get(i).getNewsContent())
				.append("</newsContent>").append("\r\n")
				.append("		<newsDate>")
				.append(newsInfoList.get(i).getNewsDate())
				.append("</newsDate>").append("\r\n")
				.append("	</NewsInfo>").append("\r\n");
			}
			sb.append("</NewsInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(NewsInfo newsInfo: newsInfoList) {
				  stringer.object();
			  stringer.key("newsId").value(newsInfo.getNewsId());
			  stringer.key("newsTitle").value(newsInfo.getNewsTitle());
			  stringer.key("newsContent").value(newsInfo.getNewsContent());
			  stringer.key("newsDate").value(newsInfo.getNewsDate());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ������Ź��棺��ȡ���Ź���������������浽�½������Ź������ */ 
			NewsInfo newsInfo = new NewsInfo();
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			newsInfo.setNewsId(newsId);
			String newsTitle = new String(request.getParameter("newsTitle").getBytes("iso-8859-1"), "UTF-8");
			newsInfo.setNewsTitle(newsTitle);
			String newsContent = new String(request.getParameter("newsContent").getBytes("iso-8859-1"), "UTF-8");
			newsInfo.setNewsContent(newsContent);
			Timestamp newsDate = Timestamp.valueOf(request.getParameter("newsDate"));
			newsInfo.setNewsDate(newsDate);

			/* ����ҵ���ִ����Ӳ��� */
			String result = newsInfoDAO.AddNewsInfo(newsInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����Ź��棺��ȡ���Ź���ļ�¼���*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = newsInfoDAO.DeleteNewsInfo(newsId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�������Ź���֮ǰ�ȸ���newsId��ѯĳ�����Ź���*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			NewsInfo newsInfo = newsInfoDAO.GetNewsInfo(newsId);

			// �ͻ��˲�ѯ�����Ź�����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("newsId").value(newsInfo.getNewsId());
			  stringer.key("newsTitle").value(newsInfo.getNewsTitle());
			  stringer.key("newsContent").value(newsInfo.getNewsContent());
			  stringer.key("newsDate").value(newsInfo.getNewsDate());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �������Ź��棺��ȡ���Ź���������������浽�½������Ź������ */ 
			NewsInfo newsInfo = new NewsInfo();
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			newsInfo.setNewsId(newsId);
			String newsTitle = new String(request.getParameter("newsTitle").getBytes("iso-8859-1"), "UTF-8");
			newsInfo.setNewsTitle(newsTitle);
			String newsContent = new String(request.getParameter("newsContent").getBytes("iso-8859-1"), "UTF-8");
			newsInfo.setNewsContent(newsContent);
			Timestamp newsDate = Timestamp.valueOf(request.getParameter("newsDate"));
			newsInfo.setNewsDate(newsDate);

			/* ����ҵ���ִ�и��²��� */
			String result = newsInfoDAO.UpdateNewsInfo(newsInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
