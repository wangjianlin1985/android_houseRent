package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.HourseDAO;
import com.mobileserver.domain.Hourse;

import org.json.JSONStringer;

public class HourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造房屋信息业务层对象*/
	private HourseDAO hourseDAO = new HourseDAO();

	/*默认构造函数*/
	public HourseServlet() {
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
			/*获取查询房屋信息的参数信息*/
			String hourseName = request.getParameter("hourseName");
			hourseName = hourseName == null ? "" : new String(request.getParameter(
					"hourseName").getBytes("iso-8859-1"), "UTF-8");
			int buildingObj = 0;
			if (request.getParameter("buildingObj") != null)
				buildingObj = Integer.parseInt(request.getParameter("buildingObj"));
			int hourseTypeObj = 0;
			if (request.getParameter("hourseTypeObj") != null)
				hourseTypeObj = Integer.parseInt(request.getParameter("hourseTypeObj"));
			int priceRangeObj = 0;
			if (request.getParameter("priceRangeObj") != null)
				priceRangeObj = Integer.parseInt(request.getParameter("priceRangeObj"));
			String madeYear = request.getParameter("madeYear");
			madeYear = madeYear == null ? "" : new String(request.getParameter(
					"madeYear").getBytes("iso-8859-1"), "UTF-8");
			String connectPerson = request.getParameter("connectPerson");
			connectPerson = connectPerson == null ? "" : new String(request.getParameter(
					"connectPerson").getBytes("iso-8859-1"), "UTF-8");
			String connectPhone = request.getParameter("connectPhone");
			connectPhone = connectPhone == null ? "" : new String(request.getParameter(
					"connectPhone").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行房屋信息查询*/
			List<Hourse> hourseList = hourseDAO.QueryHourse(hourseName,buildingObj,hourseTypeObj,priceRangeObj,madeYear,connectPerson,connectPhone);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Hourses>").append("\r\n");
			for (int i = 0; i < hourseList.size(); i++) {
				sb.append("	<Hourse>").append("\r\n")
				.append("		<hourseId>")
				.append(hourseList.get(i).getHourseId())
				.append("</hourseId>").append("\r\n")
				.append("		<hourseName>")
				.append(hourseList.get(i).getHourseName())
				.append("</hourseName>").append("\r\n")
				.append("		<buildingObj>")
				.append(hourseList.get(i).getBuildingObj())
				.append("</buildingObj>").append("\r\n")
				.append("		<housePhoto>")
				.append(hourseList.get(i).getHousePhoto())
				.append("</housePhoto>").append("\r\n")
				.append("		<hourseTypeObj>")
				.append(hourseList.get(i).getHourseTypeObj())
				.append("</hourseTypeObj>").append("\r\n")
				.append("		<priceRangeObj>")
				.append(hourseList.get(i).getPriceRangeObj())
				.append("</priceRangeObj>").append("\r\n")
				.append("		<area>")
				.append(hourseList.get(i).getArea())
				.append("</area>").append("\r\n")
				.append("		<price>")
				.append(hourseList.get(i).getPrice())
				.append("</price>").append("\r\n")
				.append("		<louceng>")
				.append(hourseList.get(i).getLouceng())
				.append("</louceng>").append("\r\n")
				.append("		<zhuangxiu>")
				.append(hourseList.get(i).getZhuangxiu())
				.append("</zhuangxiu>").append("\r\n")
				.append("		<caoxiang>")
				.append(hourseList.get(i).getCaoxiang())
				.append("</caoxiang>").append("\r\n")
				.append("		<madeYear>")
				.append(hourseList.get(i).getMadeYear())
				.append("</madeYear>").append("\r\n")
				.append("		<connectPerson>")
				.append(hourseList.get(i).getConnectPerson())
				.append("</connectPerson>").append("\r\n")
				.append("		<connectPhone>")
				.append(hourseList.get(i).getConnectPhone())
				.append("</connectPhone>").append("\r\n")
				.append("		<detail>")
				.append(hourseList.get(i).getDetail())
				.append("</detail>").append("\r\n")
				.append("		<address>")
				.append(hourseList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("	</Hourse>").append("\r\n");
			}
			sb.append("</Hourses>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Hourse hourse: hourseList) {
				  stringer.object();
			  stringer.key("hourseId").value(hourse.getHourseId());
			  stringer.key("hourseName").value(hourse.getHourseName());
			  stringer.key("buildingObj").value(hourse.getBuildingObj());
			  stringer.key("housePhoto").value(hourse.getHousePhoto());
			  stringer.key("hourseTypeObj").value(hourse.getHourseTypeObj());
			  stringer.key("priceRangeObj").value(hourse.getPriceRangeObj());
			  stringer.key("area").value(hourse.getArea());
			  stringer.key("price").value(hourse.getPrice());
			  stringer.key("louceng").value(hourse.getLouceng());
			  stringer.key("zhuangxiu").value(hourse.getZhuangxiu());
			  stringer.key("caoxiang").value(hourse.getCaoxiang());
			  stringer.key("madeYear").value(hourse.getMadeYear());
			  stringer.key("connectPerson").value(hourse.getConnectPerson());
			  stringer.key("connectPhone").value(hourse.getConnectPhone());
			  stringer.key("detail").value(hourse.getDetail());
			  stringer.key("address").value(hourse.getAddress());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加房屋信息：获取房屋信息参数，参数保存到新建的房屋信息对象 */ 
			Hourse hourse = new Hourse();
			int hourseId = Integer.parseInt(request.getParameter("hourseId"));
			hourse.setHourseId(hourseId);
			String hourseName = new String(request.getParameter("hourseName").getBytes("iso-8859-1"), "UTF-8");
			hourse.setHourseName(hourseName);
			int buildingObj = Integer.parseInt(request.getParameter("buildingObj"));
			hourse.setBuildingObj(buildingObj);
			String housePhoto = new String(request.getParameter("housePhoto").getBytes("iso-8859-1"), "UTF-8");
			hourse.setHousePhoto(housePhoto);
			int hourseTypeObj = Integer.parseInt(request.getParameter("hourseTypeObj"));
			hourse.setHourseTypeObj(hourseTypeObj);
			int priceRangeObj = Integer.parseInt(request.getParameter("priceRangeObj"));
			hourse.setPriceRangeObj(priceRangeObj);
			String area = new String(request.getParameter("area").getBytes("iso-8859-1"), "UTF-8");
			hourse.setArea(area);
			float price = Float.parseFloat(request.getParameter("price"));
			hourse.setPrice(price);
			String louceng = new String(request.getParameter("louceng").getBytes("iso-8859-1"), "UTF-8");
			hourse.setLouceng(louceng);
			String zhuangxiu = new String(request.getParameter("zhuangxiu").getBytes("iso-8859-1"), "UTF-8");
			hourse.setZhuangxiu(zhuangxiu);
			String caoxiang = new String(request.getParameter("caoxiang").getBytes("iso-8859-1"), "UTF-8");
			hourse.setCaoxiang(caoxiang);
			String madeYear = new String(request.getParameter("madeYear").getBytes("iso-8859-1"), "UTF-8");
			hourse.setMadeYear(madeYear);
			String connectPerson = new String(request.getParameter("connectPerson").getBytes("iso-8859-1"), "UTF-8");
			hourse.setConnectPerson(connectPerson);
			String connectPhone = new String(request.getParameter("connectPhone").getBytes("iso-8859-1"), "UTF-8");
			hourse.setConnectPhone(connectPhone);
			String detail = new String(request.getParameter("detail").getBytes("iso-8859-1"), "UTF-8");
			hourse.setDetail(detail);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			hourse.setAddress(address);

			/* 调用业务层执行添加操作 */
			String result = hourseDAO.AddHourse(hourse);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除房屋信息：获取房屋信息的房屋编号*/
			int hourseId = Integer.parseInt(request.getParameter("hourseId"));
			/*调用业务逻辑层执行删除操作*/
			String result = hourseDAO.DeleteHourse(hourseId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新房屋信息之前先根据hourseId查询某个房屋信息*/
			int hourseId = Integer.parseInt(request.getParameter("hourseId"));
			Hourse hourse = hourseDAO.GetHourse(hourseId);

			// 客户端查询的房屋信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("hourseId").value(hourse.getHourseId());
			  stringer.key("hourseName").value(hourse.getHourseName());
			  stringer.key("buildingObj").value(hourse.getBuildingObj());
			  stringer.key("housePhoto").value(hourse.getHousePhoto());
			  stringer.key("hourseTypeObj").value(hourse.getHourseTypeObj());
			  stringer.key("priceRangeObj").value(hourse.getPriceRangeObj());
			  stringer.key("area").value(hourse.getArea());
			  stringer.key("price").value(hourse.getPrice());
			  stringer.key("louceng").value(hourse.getLouceng());
			  stringer.key("zhuangxiu").value(hourse.getZhuangxiu());
			  stringer.key("caoxiang").value(hourse.getCaoxiang());
			  stringer.key("madeYear").value(hourse.getMadeYear());
			  stringer.key("connectPerson").value(hourse.getConnectPerson());
			  stringer.key("connectPhone").value(hourse.getConnectPhone());
			  stringer.key("detail").value(hourse.getDetail());
			  stringer.key("address").value(hourse.getAddress());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新房屋信息：获取房屋信息参数，参数保存到新建的房屋信息对象 */ 
			Hourse hourse = new Hourse();
			int hourseId = Integer.parseInt(request.getParameter("hourseId"));
			hourse.setHourseId(hourseId);
			String hourseName = new String(request.getParameter("hourseName").getBytes("iso-8859-1"), "UTF-8");
			hourse.setHourseName(hourseName);
			int buildingObj = Integer.parseInt(request.getParameter("buildingObj"));
			hourse.setBuildingObj(buildingObj);
			String housePhoto = new String(request.getParameter("housePhoto").getBytes("iso-8859-1"), "UTF-8");
			hourse.setHousePhoto(housePhoto);
			int hourseTypeObj = Integer.parseInt(request.getParameter("hourseTypeObj"));
			hourse.setHourseTypeObj(hourseTypeObj);
			int priceRangeObj = Integer.parseInt(request.getParameter("priceRangeObj"));
			hourse.setPriceRangeObj(priceRangeObj);
			String area = new String(request.getParameter("area").getBytes("iso-8859-1"), "UTF-8");
			hourse.setArea(area);
			float price = Float.parseFloat(request.getParameter("price"));
			hourse.setPrice(price);
			String louceng = new String(request.getParameter("louceng").getBytes("iso-8859-1"), "UTF-8");
			hourse.setLouceng(louceng);
			String zhuangxiu = new String(request.getParameter("zhuangxiu").getBytes("iso-8859-1"), "UTF-8");
			hourse.setZhuangxiu(zhuangxiu);
			String caoxiang = new String(request.getParameter("caoxiang").getBytes("iso-8859-1"), "UTF-8");
			hourse.setCaoxiang(caoxiang);
			String madeYear = new String(request.getParameter("madeYear").getBytes("iso-8859-1"), "UTF-8");
			hourse.setMadeYear(madeYear);
			String connectPerson = new String(request.getParameter("connectPerson").getBytes("iso-8859-1"), "UTF-8");
			hourse.setConnectPerson(connectPerson);
			String connectPhone = new String(request.getParameter("connectPhone").getBytes("iso-8859-1"), "UTF-8");
			hourse.setConnectPhone(connectPhone);
			String detail = new String(request.getParameter("detail").getBytes("iso-8859-1"), "UTF-8");
			hourse.setDetail(detail);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			hourse.setAddress(address);

			/* 调用业务层执行更新操作 */
			String result = hourseDAO.UpdateHourse(hourse);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
