package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.BuildingInfoDAO;
import com.mobileserver.domain.BuildingInfo;

import org.json.JSONStringer;

public class BuildingInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����¥����Ϣҵ������*/
	private BuildingInfoDAO buildingInfoDAO = new BuildingInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public BuildingInfoServlet() {
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
			/*��ȡ��ѯ¥����Ϣ�Ĳ�����Ϣ*/
			int areaObj = 0;
			if (request.getParameter("areaObj") != null)
				areaObj = Integer.parseInt(request.getParameter("areaObj"));
			String buildingName = request.getParameter("buildingName");
			buildingName = buildingName == null ? "" : new String(request.getParameter(
					"buildingName").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ��¥����Ϣ��ѯ*/
			List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryBuildingInfo(areaObj,buildingName);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<BuildingInfos>").append("\r\n");
			for (int i = 0; i < buildingInfoList.size(); i++) {
				sb.append("	<BuildingInfo>").append("\r\n")
				.append("		<buildingId>")
				.append(buildingInfoList.get(i).getBuildingId())
				.append("</buildingId>").append("\r\n")
				.append("		<areaObj>")
				.append(buildingInfoList.get(i).getAreaObj())
				.append("</areaObj>").append("\r\n")
				.append("		<buildingName>")
				.append(buildingInfoList.get(i).getBuildingName())
				.append("</buildingName>").append("\r\n")
				.append("		<buildingPhoto>")
				.append(buildingInfoList.get(i).getBuildingPhoto())
				.append("</buildingPhoto>").append("\r\n")
				.append("	</BuildingInfo>").append("\r\n");
			}
			sb.append("</BuildingInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(BuildingInfo buildingInfo: buildingInfoList) {
				  stringer.object();
			  stringer.key("buildingId").value(buildingInfo.getBuildingId());
			  stringer.key("areaObj").value(buildingInfo.getAreaObj());
			  stringer.key("buildingName").value(buildingInfo.getBuildingName());
			  stringer.key("buildingPhoto").value(buildingInfo.getBuildingPhoto());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���¥����Ϣ����ȡ¥����Ϣ�������������浽�½���¥����Ϣ���� */ 
			BuildingInfo buildingInfo = new BuildingInfo();
			int buildingId = Integer.parseInt(request.getParameter("buildingId"));
			buildingInfo.setBuildingId(buildingId);
			int areaObj = Integer.parseInt(request.getParameter("areaObj"));
			buildingInfo.setAreaObj(areaObj);
			String buildingName = new String(request.getParameter("buildingName").getBytes("iso-8859-1"), "UTF-8");
			buildingInfo.setBuildingName(buildingName);
			String buildingPhoto = new String(request.getParameter("buildingPhoto").getBytes("iso-8859-1"), "UTF-8");
			buildingInfo.setBuildingPhoto(buildingPhoto);

			/* ����ҵ���ִ����Ӳ��� */
			String result = buildingInfoDAO.AddBuildingInfo(buildingInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��¥����Ϣ����ȡ¥����Ϣ��¥�̱��*/
			int buildingId = Integer.parseInt(request.getParameter("buildingId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = buildingInfoDAO.DeleteBuildingInfo(buildingId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����¥����Ϣ֮ǰ�ȸ���buildingId��ѯĳ��¥����Ϣ*/
			int buildingId = Integer.parseInt(request.getParameter("buildingId"));
			BuildingInfo buildingInfo = buildingInfoDAO.GetBuildingInfo(buildingId);

			// �ͻ��˲�ѯ��¥����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("buildingId").value(buildingInfo.getBuildingId());
			  stringer.key("areaObj").value(buildingInfo.getAreaObj());
			  stringer.key("buildingName").value(buildingInfo.getBuildingName());
			  stringer.key("buildingPhoto").value(buildingInfo.getBuildingPhoto());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����¥����Ϣ����ȡ¥����Ϣ�������������浽�½���¥����Ϣ���� */ 
			BuildingInfo buildingInfo = new BuildingInfo();
			int buildingId = Integer.parseInt(request.getParameter("buildingId"));
			buildingInfo.setBuildingId(buildingId);
			int areaObj = Integer.parseInt(request.getParameter("areaObj"));
			buildingInfo.setAreaObj(areaObj);
			String buildingName = new String(request.getParameter("buildingName").getBytes("iso-8859-1"), "UTF-8");
			buildingInfo.setBuildingName(buildingName);
			String buildingPhoto = new String(request.getParameter("buildingPhoto").getBytes("iso-8859-1"), "UTF-8");
			buildingInfo.setBuildingPhoto(buildingPhoto);

			/* ����ҵ���ִ�и��²��� */
			String result = buildingInfoDAO.UpdateBuildingInfo(buildingInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
