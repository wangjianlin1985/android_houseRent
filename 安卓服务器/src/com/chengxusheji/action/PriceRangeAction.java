package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.PriceRangeDAO;
import com.chengxusheji.domain.PriceRange;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class PriceRangeAction extends BaseAction {

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int rangeId;
    public void setRangeId(int rangeId) {
        this.rangeId = rangeId;
    }
    public int getRangeId() {
        return rangeId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource PriceRangeDAO priceRangeDAO;

    /*待操作的PriceRange对象*/
    private PriceRange priceRange;
    public void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }
    public PriceRange getPriceRange() {
        return this.priceRange;
    }

    /*跳转到添加PriceRange视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加PriceRange信息*/
    @SuppressWarnings("deprecation")
    public String AddPriceRange() {
        ActionContext ctx = ActionContext.getContext();
        try {
            priceRangeDAO.AddPriceRange(priceRange);
            ctx.put("message",  java.net.URLEncoder.encode("PriceRange添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PriceRange添加失败!"));
            return "error";
        }
    }

    /*查询PriceRange信息*/
    public String QueryPriceRange() {
        if(currentPage == 0) currentPage = 1;
        List<PriceRange> priceRangeList = priceRangeDAO.QueryPriceRangeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        priceRangeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = priceRangeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = priceRangeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("priceRangeList",  priceRangeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryPriceRangeOutputToExcel() { 
        List<PriceRange> priceRangeList = priceRangeDAO.QueryPriceRangeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "PriceRange信息记录"; 
        String[] headers = { "记录编号","价格区间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<priceRangeList.size();i++) {
        	PriceRange priceRange = priceRangeList.get(i); 
        	dataset.add(new String[]{priceRange.getRangeId() + "",priceRange.getPriceName()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"PriceRange.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询PriceRange信息*/
    public String FrontQueryPriceRange() {
        if(currentPage == 0) currentPage = 1;
        List<PriceRange> priceRangeList = priceRangeDAO.QueryPriceRangeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        priceRangeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = priceRangeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = priceRangeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("priceRangeList",  priceRangeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的PriceRange信息*/
    public String ModifyPriceRangeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键rangeId获取PriceRange对象*/
        PriceRange priceRange = priceRangeDAO.GetPriceRangeByRangeId(rangeId);

        ctx.put("priceRange",  priceRange);
        return "modify_view";
    }

    /*查询要修改的PriceRange信息*/
    public String FrontShowPriceRangeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键rangeId获取PriceRange对象*/
        PriceRange priceRange = priceRangeDAO.GetPriceRangeByRangeId(rangeId);

        ctx.put("priceRange",  priceRange);
        return "front_show_view";
    }

    /*更新修改PriceRange信息*/
    public String ModifyPriceRange() {
        ActionContext ctx = ActionContext.getContext();
        try {
            priceRangeDAO.UpdatePriceRange(priceRange);
            ctx.put("message",  java.net.URLEncoder.encode("PriceRange信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PriceRange信息更新失败!"));
            return "error";
       }
   }

    /*删除PriceRange信息*/
    public String DeletePriceRange() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            priceRangeDAO.DeletePriceRange(rangeId);
            ctx.put("message",  java.net.URLEncoder.encode("PriceRange删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PriceRange删除失败!"));
            return "error";
        }
    }

}
