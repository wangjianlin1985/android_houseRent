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
import com.chengxusheji.dao.AreaInfoDAO;
import com.chengxusheji.domain.AreaInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class AreaInfoAction extends BaseAction {

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

    private int areaId;
    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
    public int getAreaId() {
        return areaId;
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
    @Resource AreaInfoDAO areaInfoDAO;

    /*待操作的AreaInfo对象*/
    private AreaInfo areaInfo;
    public void setAreaInfo(AreaInfo areaInfo) {
        this.areaInfo = areaInfo;
    }
    public AreaInfo getAreaInfo() {
        return this.areaInfo;
    }

    /*跳转到添加AreaInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加AreaInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddAreaInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            areaInfoDAO.AddAreaInfo(areaInfo);
            ctx.put("message",  java.net.URLEncoder.encode("AreaInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("AreaInfo添加失败!"));
            return "error";
        }
    }

    /*查询AreaInfo信息*/
    public String QueryAreaInfo() {
        if(currentPage == 0) currentPage = 1;
        List<AreaInfo> areaInfoList = areaInfoDAO.QueryAreaInfoInfo(currentPage);
        /*计算总的页数和总的记录数*/
        areaInfoDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = areaInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = areaInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("areaInfoList",  areaInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryAreaInfoOutputToExcel() { 
        List<AreaInfo> areaInfoList = areaInfoDAO.QueryAreaInfoInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "AreaInfo信息记录"; 
        String[] headers = { "记录编号","区域名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<areaInfoList.size();i++) {
        	AreaInfo areaInfo = areaInfoList.get(i); 
        	dataset.add(new String[]{areaInfo.getAreaId() + "",areaInfo.getAreaName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"AreaInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询AreaInfo信息*/
    public String FrontQueryAreaInfo() {
        if(currentPage == 0) currentPage = 1;
        List<AreaInfo> areaInfoList = areaInfoDAO.QueryAreaInfoInfo(currentPage);
        /*计算总的页数和总的记录数*/
        areaInfoDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = areaInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = areaInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("areaInfoList",  areaInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的AreaInfo信息*/
    public String ModifyAreaInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键areaId获取AreaInfo对象*/
        AreaInfo areaInfo = areaInfoDAO.GetAreaInfoByAreaId(areaId);

        ctx.put("areaInfo",  areaInfo);
        return "modify_view";
    }

    /*查询要修改的AreaInfo信息*/
    public String FrontShowAreaInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键areaId获取AreaInfo对象*/
        AreaInfo areaInfo = areaInfoDAO.GetAreaInfoByAreaId(areaId);

        ctx.put("areaInfo",  areaInfo);
        return "front_show_view";
    }

    /*更新修改AreaInfo信息*/
    public String ModifyAreaInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            areaInfoDAO.UpdateAreaInfo(areaInfo);
            ctx.put("message",  java.net.URLEncoder.encode("AreaInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("AreaInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除AreaInfo信息*/
    public String DeleteAreaInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            areaInfoDAO.DeleteAreaInfo(areaId);
            ctx.put("message",  java.net.URLEncoder.encode("AreaInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("AreaInfo删除失败!"));
            return "error";
        }
    }

}
