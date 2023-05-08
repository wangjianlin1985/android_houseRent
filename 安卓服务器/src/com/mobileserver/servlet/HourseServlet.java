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

	/*���췿����Ϣҵ������*/
	private HourseDAO hourseDAO = new HourseDAO();

	/*Ĭ�Ϲ��캯��*/
	public HourseServlet() {
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

			/*����ҵ���߼���ִ�з�����Ϣ��ѯ*/
			List<Hourse> hourseList = hourseDAO.QueryHourse(hourseName,buildingObj,hourseTypeObj,priceRangeObj,madeYear,connectPerson,connectPhone);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӷ�����Ϣ����ȡ������Ϣ�������������浽�½��ķ�����Ϣ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = hourseDAO.AddHourse(hourse);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������Ϣ����ȡ������Ϣ�ķ��ݱ��*/
			int hourseId = Integer.parseInt(request.getParameter("hourseId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = hourseDAO.DeleteHourse(hourseId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���·�����Ϣ֮ǰ�ȸ���hourseId��ѯĳ��������Ϣ*/
			int hourseId = Integer.parseInt(request.getParameter("hourseId"));
			Hourse hourse = hourseDAO.GetHourse(hourseId);

			// �ͻ��˲�ѯ�ķ�����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���·�����Ϣ����ȡ������Ϣ�������������浽�½��ķ�����Ϣ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = hourseDAO.UpdateHourse(hourse);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
