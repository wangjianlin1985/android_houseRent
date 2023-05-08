package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.HourseTypeDAO;
import com.mobileserver.domain.HourseType;

import org.json.JSONStringer;

public class HourseTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���췿�����ҵ������*/
	private HourseTypeDAO hourseTypeDAO = new HourseTypeDAO();

	/*Ĭ�Ϲ��캯��*/
	public HourseTypeServlet() {
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
			/*��ȡ��ѯ�������Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ�з�������ѯ*/
			List<HourseType> hourseTypeList = hourseTypeDAO.QueryHourseType();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<HourseTypes>").append("\r\n");
			for (int i = 0; i < hourseTypeList.size(); i++) {
				sb.append("	<HourseType>").append("\r\n")
				.append("		<typeId>")
				.append(hourseTypeList.get(i).getTypeId())
				.append("</typeId>").append("\r\n")
				.append("		<typeName>")
				.append(hourseTypeList.get(i).getTypeName())
				.append("</typeName>").append("\r\n")
				.append("	</HourseType>").append("\r\n");
			}
			sb.append("</HourseTypes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(HourseType hourseType: hourseTypeList) {
				  stringer.object();
			  stringer.key("typeId").value(hourseType.getTypeId());
			  stringer.key("typeName").value(hourseType.getTypeName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӷ�����𣺻�ȡ�������������������浽�½��ķ��������� */ 
			HourseType hourseType = new HourseType();
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			hourseType.setTypeId(typeId);
			String typeName = new String(request.getParameter("typeName").getBytes("iso-8859-1"), "UTF-8");
			hourseType.setTypeName(typeName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = hourseTypeDAO.AddHourseType(hourseType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������𣺻�ȡ�������������*/
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = hourseTypeDAO.DeleteHourseType(typeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���·������֮ǰ�ȸ���typeId��ѯĳ���������*/
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			HourseType hourseType = hourseTypeDAO.GetHourseType(typeId);

			// �ͻ��˲�ѯ�ķ��������󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("typeId").value(hourseType.getTypeId());
			  stringer.key("typeName").value(hourseType.getTypeName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���·�����𣺻�ȡ�������������������浽�½��ķ��������� */ 
			HourseType hourseType = new HourseType();
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			hourseType.setTypeId(typeId);
			String typeName = new String(request.getParameter("typeName").getBytes("iso-8859-1"), "UTF-8");
			hourseType.setTypeName(typeName);

			/* ����ҵ���ִ�и��²��� */
			String result = hourseTypeDAO.UpdateHourseType(hourseType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
