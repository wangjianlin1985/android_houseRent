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

	/*构造留言信息业务层对象*/
	private GuestBookDAO guestBookDAO = new GuestBookDAO();

	/*默认构造函数*/
	public GuestBookServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询留言信息的参数信息*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");

			/*调用业务逻辑层执行留言信息查询*/
			List<GuestBook> guestBookList = guestBookDAO.QueryGuestBook(title,userObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加留言信息：获取留言信息参数，参数保存到新建的留言信息对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = guestBookDAO.AddGuestBook(guestBook);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除留言信息：获取留言信息的记录编号*/
			int guestBookId = Integer.parseInt(request.getParameter("guestBookId"));
			/*调用业务逻辑层执行删除操作*/
			String result = guestBookDAO.DeleteGuestBook(guestBookId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新留言信息之前先根据guestBookId查询某个留言信息*/
			int guestBookId = Integer.parseInt(request.getParameter("guestBookId"));
			GuestBook guestBook = guestBookDAO.GetGuestBook(guestBookId);

			// 客户端查询的留言信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新留言信息：获取留言信息参数，参数保存到新建的留言信息对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = guestBookDAO.UpdateGuestBook(guestBook);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
