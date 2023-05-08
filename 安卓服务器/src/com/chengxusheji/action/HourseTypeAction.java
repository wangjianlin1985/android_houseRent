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
import com.chengxusheji.dao.HourseTypeDAO;
import com.chengxusheji.domain.HourseType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class HourseTypeAction extends BaseAction {

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

    private int typeId;
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public int getTypeId() {
        return typeId;
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
    @Resource HourseTypeDAO hourseTypeDAO;

    /*待操作的HourseType对象*/
    private HourseType hourseType;
    public void setHourseType(HourseType hourseType) {
        this.hourseType = hourseType;
    }
    public HourseType getHourseType() {
        return this.hourseType;
    }

    /*跳转到添加HourseType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加HourseType信息*/
    @SuppressWarnings("deprecation")
    public String AddHourseType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            hourseTypeDAO.AddHourseType(hourseType);
            ctx.put("message",  java.net.URLEncoder.encode("HourseType添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HourseType添加失败!"));
            return "error";
        }
    }

    /*查询HourseType信息*/
    public String QueryHourseType() {
        if(currentPage == 0) currentPage = 1;
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryHourseTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        hourseTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = hourseTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = hourseTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("hourseTypeList",  hourseTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryHourseTypeOutputToExcel() { 
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryHourseTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "HourseType信息记录"; 
        String[] headers = { "类别编号","房屋类型"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<hourseTypeList.size();i++) {
        	HourseType hourseType = hourseTypeList.get(i); 
        	dataset.add(new String[]{hourseType.getTypeId() + "",hourseType.getTypeName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"HourseType.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询HourseType信息*/
    public String FrontQueryHourseType() {
        if(currentPage == 0) currentPage = 1;
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryHourseTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        hourseTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = hourseTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = hourseTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("hourseTypeList",  hourseTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的HourseType信息*/
    public String ModifyHourseTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键typeId获取HourseType对象*/
        HourseType hourseType = hourseTypeDAO.GetHourseTypeByTypeId(typeId);

        ctx.put("hourseType",  hourseType);
        return "modify_view";
    }

    /*查询要修改的HourseType信息*/
    public String FrontShowHourseTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键typeId获取HourseType对象*/
        HourseType hourseType = hourseTypeDAO.GetHourseTypeByTypeId(typeId);

        ctx.put("hourseType",  hourseType);
        return "front_show_view";
    }

    /*更新修改HourseType信息*/
    public String ModifyHourseType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            hourseTypeDAO.UpdateHourseType(hourseType);
            ctx.put("message",  java.net.URLEncoder.encode("HourseType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HourseType信息更新失败!"));
            return "error";
       }
   }

    /*删除HourseType信息*/
    public String DeleteHourseType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            hourseTypeDAO.DeleteHourseType(typeId);
            ctx.put("message",  java.net.URLEncoder.encode("HourseType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HourseType删除失败!"));
            return "error";
        }
    }

}
