package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.PriceRangeDAO;
import com.mobileserver.domain.PriceRange;

import org.json.JSONStringer;

public class PriceRangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�������Χҵ������*/
	private PriceRangeDAO priceRangeDAO = new PriceRangeDAO();

	/*Ĭ�Ϲ��캯��*/
	public PriceRangeServlet() {
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
			/*��ȡ��ѯ���Χ�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ�����Χ��ѯ*/
			List<PriceRange> priceRangeList = priceRangeDAO.QueryPriceRange();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<PriceRanges>").append("\r\n");
			for (int i = 0; i < priceRangeList.size(); i++) {
				sb.append("	<PriceRange>").append("\r\n")
				.append("		<rangeId>")
				.append(priceRangeList.get(i).getRangeId())
				.append("</rangeId>").append("\r\n")
				.append("		<priceName>")
				.append(priceRangeList.get(i).getPriceName())
				.append("</priceName>").append("\r\n")
				.append("	</PriceRange>").append("\r\n");
			}
			sb.append("</PriceRanges>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(PriceRange priceRange: priceRangeList) {
				  stringer.object();
			  stringer.key("rangeId").value(priceRange.getRangeId());
			  stringer.key("priceName").value(priceRange.getPriceName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ������Χ����ȡ���Χ�������������浽�½������Χ���� */ 
			PriceRange priceRange = new PriceRange();
			int rangeId = Integer.parseInt(request.getParameter("rangeId"));
			priceRange.setRangeId(rangeId);
			String priceName = new String(request.getParameter("priceName").getBytes("iso-8859-1"), "UTF-8");
			priceRange.setPriceName(priceName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = priceRangeDAO.AddPriceRange(priceRange);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����Χ����ȡ���Χ�ļ�¼���*/
			int rangeId = Integer.parseInt(request.getParameter("rangeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = priceRangeDAO.DeletePriceRange(rangeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�������Χ֮ǰ�ȸ���rangeId��ѯĳ�����Χ*/
			int rangeId = Integer.parseInt(request.getParameter("rangeId"));
			PriceRange priceRange = priceRangeDAO.GetPriceRange(rangeId);

			// �ͻ��˲�ѯ�����Χ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("rangeId").value(priceRange.getRangeId());
			  stringer.key("priceName").value(priceRange.getPriceName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �������Χ����ȡ���Χ�������������浽�½������Χ���� */ 
			PriceRange priceRange = new PriceRange();
			int rangeId = Integer.parseInt(request.getParameter("rangeId"));
			priceRange.setRangeId(rangeId);
			String priceName = new String(request.getParameter("priceName").getBytes("iso-8859-1"), "UTF-8");
			priceRange.setPriceName(priceName);

			/* ����ҵ���ִ�и��²��� */
			String result = priceRangeDAO.UpdatePriceRange(priceRange);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
