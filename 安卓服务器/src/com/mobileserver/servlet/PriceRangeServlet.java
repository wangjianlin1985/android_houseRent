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

	/*构造租金范围业务层对象*/
	private PriceRangeDAO priceRangeDAO = new PriceRangeDAO();

	/*默认构造函数*/
	public PriceRangeServlet() {
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
			/*获取查询租金范围的参数信息*/

			/*调用业务逻辑层执行租金范围查询*/
			List<PriceRange> priceRangeList = priceRangeDAO.QueryPriceRange();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加租金范围：获取租金范围参数，参数保存到新建的租金范围对象 */ 
			PriceRange priceRange = new PriceRange();
			int rangeId = Integer.parseInt(request.getParameter("rangeId"));
			priceRange.setRangeId(rangeId);
			String priceName = new String(request.getParameter("priceName").getBytes("iso-8859-1"), "UTF-8");
			priceRange.setPriceName(priceName);

			/* 调用业务层执行添加操作 */
			String result = priceRangeDAO.AddPriceRange(priceRange);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除租金范围：获取租金范围的记录编号*/
			int rangeId = Integer.parseInt(request.getParameter("rangeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = priceRangeDAO.DeletePriceRange(rangeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新租金范围之前先根据rangeId查询某个租金范围*/
			int rangeId = Integer.parseInt(request.getParameter("rangeId"));
			PriceRange priceRange = priceRangeDAO.GetPriceRange(rangeId);

			// 客户端查询的租金范围对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新租金范围：获取租金范围参数，参数保存到新建的租金范围对象 */ 
			PriceRange priceRange = new PriceRange();
			int rangeId = Integer.parseInt(request.getParameter("rangeId"));
			priceRange.setRangeId(rangeId);
			String priceName = new String(request.getParameter("priceName").getBytes("iso-8859-1"), "UTF-8");
			priceRange.setPriceName(priceName);

			/* 调用业务层执行更新操作 */
			String result = priceRangeDAO.UpdatePriceRange(priceRange);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
