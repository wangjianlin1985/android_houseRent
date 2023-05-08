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

	/*����������Ϣҵ������*/
	private WantHourseInfoDAO wantHourseInfoDAO = new WantHourseInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public WantHourseInfoServlet() {
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

			/*����ҵ���߼���ִ��������Ϣ��ѯ*/
			List<WantHourseInfo> wantHourseInfoList = wantHourseInfoDAO.QueryWantHourseInfo(userObj,title,position,hourseTypeObj,priceRangeObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���������Ϣ����ȡ������Ϣ�������������浽�½���������Ϣ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = wantHourseInfoDAO.AddWantHourseInfo(wantHourseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������Ϣ����ȡ������Ϣ�ļ�¼���*/
			int wantHourseId = Integer.parseInt(request.getParameter("wantHourseId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = wantHourseInfoDAO.DeleteWantHourseInfo(wantHourseId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����������Ϣ֮ǰ�ȸ���wantHourseId��ѯĳ��������Ϣ*/
			int wantHourseId = Integer.parseInt(request.getParameter("wantHourseId"));
			WantHourseInfo wantHourseInfo = wantHourseInfoDAO.GetWantHourseInfo(wantHourseId);

			// �ͻ��˲�ѯ��������Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����������Ϣ����ȡ������Ϣ�������������浽�½���������Ϣ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = wantHourseInfoDAO.UpdateWantHourseInfo(wantHourseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
