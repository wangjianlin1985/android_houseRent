package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.WantHourseInfoDAO;
import com.mobileserver.domain.WantHourseInfo;

import org.json.JSONStringer;

public class WantHourseInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造求租信息业务层对象*/
	private WantHourseInfoDAO wantHourseInfoDAO = new WantHourseInfoDAO();

	/*默认构造函数*/
	public WantHourseInfoServlet() {
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
			/*获取查询求租信息的参数信息*/
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			int position = 0;
			if (request.getParameter("position") != null)
				position = Integer.parseInt(request.getParameter("position"));
			int hourseTypeObj = 0;
			if (request.getParameter("hourseTypeObj") != null)
				hourseTypeObj = Integer.parseInt(request.getParameter("hourseTypeObj"));
			int priceRangeObj = 0;
			if (request.getParameter("priceRangeObj") != null)
				priceRangeObj = Integer.parseInt(request.getParameter("priceRangeObj"));

			/*调用业务逻辑层执行求租信息查询*/
			List<WantHourseInfo> wantHourseInfoList = wantHourseInfoDAO.QueryWantHourseInfo(userObj,title,position,hourseTypeObj,priceRangeObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<WantHourseInfos>").append("\r\n");
			for (int i = 0; i < wantHourseInfoList.size(); i++) {
				sb.append("	<WantHourseInfo>").append("\r\n")
				.append("		<wantHourseId>")
				.append(wantHourseInfoList.get(i).getWantHourseId())
				.append("</wantHourseId>").append("\r\n")
				.append("		<userObj>")
				.append(wantHourseInfoList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<title>")
				.append(wantHourseInfoList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<position>")
				.append(wantHourseInfoList.get(i).getPosition())
				.append("</position>").append("\r\n")
				.append("		<hourseTypeObj>")
				.append(wantHourseInfoList.get(i).getHourseTypeObj())
				.append("</hourseTypeObj>").append("\r\n")
				.append("		<priceRangeObj>")
				.append(wantHourseInfoList.get(i).getPriceRangeObj())
				.append("</priceRangeObj>").append("\r\n")
				.append("		<price>")
				.append(wantHourseInfoList.get(i).getPrice())
				.append("</price>").append("\r\n")
				.append("		<lianxiren>")
				.append(wantHourseInfoList.get(i).getLianxiren())
				.append("</lianxiren>").append("\r\n")
				.append("		<telephone>")
				.append(wantHourseInfoList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("	</WantHourseInfo>").append("\r\n");
			}
			sb.append("</WantHourseInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(WantHourseInfo wantHourseInfo: wantHourseInfoList) {
				  stringer.object();
			  stringer.key("wantHourseId").value(wantHourseInfo.getWantHourseId());
			  stringer.key("userObj").value(wantHourseInfo.getUserObj());
			  stringer.key("title").value(wantHourseInfo.getTitle());
			  stringer.key("position").value(wantHourseInfo.getPosition());
			  stringer.key("hourseTypeObj").value(wantHourseInfo.getHourseTypeObj());
			  stringer.key("priceRangeObj").value(wantHourseInfo.getPriceRangeObj());
			  stringer.key("price").value(wantHourseInfo.getPrice());
			  stringer.key("lianxiren").value(wantHourseInfo.getLianxiren());
			  stringer.key("telephone").value(wantHourseInfo.getTelephone());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加求租信息：获取求租信息参数，参数保存到新建的求租信息对象 */ 
			WantHourseInfo wantHourseInfo = new WantHourseInfo();
			int wantHourseId = Integer.parseInt(request.getParameter("wantHourseId"));
			wantHourseInfo.setWantHourseId(wantHourseId);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			wantHourseInfo.setUserObj(userObj);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			wantHourseInfo.setTitle(title);
			int position = Integer.parseInt(request.getParameter("position"));
			wantHourseInfo.setPosition(position);
			int hourseTypeObj = Integer.parseInt(request.getParameter("hourseTypeObj"));
			wantHourseInfo.setHourseTypeObj(hourseTypeObj);
			int priceRangeObj = Integer.parseInt(request.getParameter("priceRangeObj"));
			wantHourseInfo.setPriceRangeObj(priceRangeObj);
			float price = Float.parseFloat(request.getParameter("price"));
			wantHourseInfo.setPrice(price);
			String lianxiren = new String(request.getParameter("lianxiren").getBytes("iso-8859-1"), "UTF-8");
			wantHourseInfo.setLianxiren(lianxiren);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			wantHourseInfo.setTelephone(telephone);

			/* 调用业务层执行添加操作 */
			String result = wantHourseInfoDAO.AddWantHourseInfo(wantHourseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除求租信息：获取求租信息的记录编号*/
			int wantHourseId = Integer.parseInt(request.getParameter("wantHourseId"));
			/*调用业务逻辑层执行删除操作*/
			String result = wantHourseInfoDAO.DeleteWantHourseInfo(wantHourseId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新求租信息之前先根据wantHourseId查询某个求租信息*/
			int wantHourseId = Integer.parseInt(request.getParameter("wantHourseId"));
			WantHourseInfo wantHourseInfo = wantHourseInfoDAO.GetWantHourseInfo(wantHourseId);

			// 客户端查询的求租信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("wantHourseId").value(wantHourseInfo.getWantHourseId());
			  stringer.key("userObj").value(wantHourseInfo.getUserObj());
			  stringer.key("title").value(wantHourseInfo.getTitle());
			  stringer.key("position").value(wantHourseInfo.getPosition());
			  stringer.key("hourseTypeObj").value(wantHourseInfo.getHourseTypeObj());
			  stringer.key("priceRangeObj").value(wantHourseInfo.getPriceRangeObj());
			  stringer.key("price").value(wantHourseInfo.getPrice());
			  stringer.key("lianxiren").value(wantHourseInfo.getLianxiren());
			  stringer.key("telephone").value(wantHourseInfo.getTelephone());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新求租信息：获取求租信息参数，参数保存到新建的求租信息对象 */ 
			WantHourseInfo wantHourseInfo = new WantHourseInfo();
			int wantHourseId = Integer.parseInt(request.getParameter("wantHourseId"));
			wantHourseInfo.setWantHourseId(wantHourseId);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			wantHourseInfo.setUserObj(userObj);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			wantHourseInfo.setTitle(title);
			int position = Integer.parseInt(request.getParameter("position"));
			wantHourseInfo.setPosition(position);
			int hourseTypeObj = Integer.parseInt(request.getParameter("hourseTypeObj"));
			wantHourseInfo.setHourseTypeObj(hourseTypeObj);
			int priceRangeObj = Integer.parseInt(request.getParameter("priceRangeObj"));
			wantHourseInfo.setPriceRangeObj(priceRangeObj);
			float price = Float.parseFloat(request.getParameter("price"));
			wantHourseInfo.setPrice(price);
			String lianxiren = new String(request.getParameter("lianxiren").getBytes("iso-8859-1"), "UTF-8");
			wantHourseInfo.setLianxiren(lianxiren);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			wantHourseInfo.setTelephone(telephone);

			/* 调用业务层执行更新操作 */
			String result = wantHourseInfoDAO.UpdateWantHourseInfo(wantHourseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
