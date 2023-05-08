package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.GuestBookDAO;
import com.mobileserver.domain.GuestBook;

import org.json.JSONStringer;

public class GuestBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����������Ϣҵ������*/
	private GuestBookDAO guestBookDAO = new GuestBookDAO();

	/*Ĭ�Ϲ��캯��*/
	public GuestBookServlet() {
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
			/*��ȡ��ѯ������Ϣ�Ĳ�����Ϣ*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");

			/*����ҵ���߼���ִ��������Ϣ��ѯ*/
			List<GuestBook> guestBookList = guestBookDAO.QueryGuestBook(title,userObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<GuestBooks>").append("\r\n");
			for (int i = 0; i < guestBookList.size(); i++) {
				sb.append("	<GuestBook>").append("\r\n")
				.append("		<guestBookId>")
				.append(guestBookList.get(i).getGuestBookId())
				.append("</guestBookId>").append("\r\n")
				.append("		<title>")
				.append(guestBookList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<content>")
				.append(guestBookList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<userObj>")
				.append(guestBookList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<addTime>")
				.append(guestBookList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("	</GuestBook>").append("\r\n");
			}
			sb.append("</GuestBooks>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(GuestBook guestBook: guestBookList) {
				  stringer.object();
			  stringer.key("guestBookId").value(guestBook.getGuestBookId());
			  stringer.key("title").value(guestBook.getTitle());
			  stringer.key("content").value(guestBook.getContent());
			  stringer.key("userObj").value(guestBook.getUserObj());
			  stringer.key("addTime").value(guestBook.getAddTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���������Ϣ����ȡ������Ϣ�������������浽�½���������Ϣ���� */ 
			GuestBook guestBook = new GuestBook();
			int guestBookId = Integer.parseInt(request.getParameter("guestBookId"));
			guestBook.setGuestBookId(guestBookId);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setContent(content);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setUserObj(userObj);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setAddTime(addTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = guestBookDAO.AddGuestBook(guestBook);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������Ϣ����ȡ������Ϣ�ļ�¼���*/
			int guestBookId = Integer.parseInt(request.getParameter("guestBookId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = guestBookDAO.DeleteGuestBook(guestBookId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����������Ϣ֮ǰ�ȸ���guestBookId��ѯĳ��������Ϣ*/
			int guestBookId = Integer.parseInt(request.getParameter("guestBookId"));
			GuestBook guestBook = guestBookDAO.GetGuestBook(guestBookId);

			// �ͻ��˲�ѯ��������Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("guestBookId").value(guestBook.getGuestBookId());
			  stringer.key("title").value(guestBook.getTitle());
			  stringer.key("content").value(guestBook.getContent());
			  stringer.key("userObj").value(guestBook.getUserObj());
			  stringer.key("addTime").value(guestBook.getAddTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����������Ϣ����ȡ������Ϣ�������������浽�½���������Ϣ���� */ 
			GuestBook guestBook = new GuestBook();
			int guestBookId = Integer.parseInt(request.getParameter("guestBookId"));
			guestBook.setGuestBookId(guestBookId);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setContent(content);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setUserObj(userObj);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setAddTime(addTime);

			/* ����ҵ���ִ�и��²��� */
			String result = guestBookDAO.UpdateGuestBook(guestBook);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
