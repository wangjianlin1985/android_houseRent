package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.AreaInfoDAO;
import com.mobileserver.domain.AreaInfo;

import org.json.JSONStringer;

public class AreaInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����������Ϣҵ������*/
	private AreaInfoDAO areaInfoDAO = new AreaInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public AreaInfoServlet() {
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

			/*����ҵ���߼���ִ��������Ϣ��ѯ*/
			List<AreaInfo> areaInfoList = areaInfoDAO.QueryAreaInfo();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<AreaInfos>").append("\r\n");
			for (int i = 0; i < areaInfoList.size(); i++) {
				sb.append("	<AreaInfo>").append("\r\n")
				.append("		<areaId>")
				.append(areaInfoList.get(i).getAreaId())
				.append("</areaId>").append("\r\n")
				.append("		<areaName>")
				.append(areaInfoList.get(i).getAreaName())
				.append("</areaName>").append("\r\n")
				.append("	</AreaInfo>").append("\r\n");
			}
			sb.append("</AreaInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(AreaInfo areaInfo: areaInfoList) {
				  stringer.object();
			  stringer.key("areaId").value(areaInfo.getAreaId());
			  stringer.key("areaName").value(areaInfo.getAreaName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���������Ϣ����ȡ������Ϣ�������������浽�½���������Ϣ���� */ 
			AreaInfo areaInfo = new AreaInfo();
			int areaId = Integer.parseInt(request.getParameter("areaId"));
			areaInfo.setAreaId(areaId);
			String areaName = new String(request.getParameter("areaName").getBytes("iso-8859-1"), "UTF-8");
			areaInfo.setAreaName(areaName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = areaInfoDAO.AddAreaInfo(areaInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������Ϣ����ȡ������Ϣ�ļ�¼���*/
			int areaId = Integer.parseInt(request.getParameter("areaId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = areaInfoDAO.DeleteAreaInfo(areaId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����������Ϣ֮ǰ�ȸ���areaId��ѯĳ��������Ϣ*/
			int areaId = Integer.parseInt(request.getParameter("areaId"));
			AreaInfo areaInfo = areaInfoDAO.GetAreaInfo(areaId);

			// �ͻ��˲�ѯ��������Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("areaId").value(areaInfo.getAreaId());
			  stringer.key("areaName").value(areaInfo.getAreaName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����������Ϣ����ȡ������Ϣ�������������浽�½���������Ϣ���� */ 
			AreaInfo areaInfo = new AreaInfo();
			int areaId = Integer.parseInt(request.getParameter("areaId"));
			areaInfo.setAreaId(areaId);
			String areaName = new String(request.getParameter("areaName").getBytes("iso-8859-1"), "UTF-8");
			areaInfo.setAreaName(areaName);

			/* ����ҵ���ִ�и��²��� */
			String result = areaInfoDAO.UpdateAreaInfo(areaInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
