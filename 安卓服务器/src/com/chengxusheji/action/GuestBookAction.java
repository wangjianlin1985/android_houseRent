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
import com.chengxusheji.dao.GuestBookDAO;
import com.chengxusheji.domain.GuestBook;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class GuestBookAction extends BaseAction {

    /*界面层需要查询的属性: 留言标题*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*界面层需要查询的属性: 留言人*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

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

    private int guestBookId;
    public void setGuestBookId(int guestBookId) {
        this.guestBookId = guestBookId;
    }
    public int getGuestBookId() {
        return guestBookId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource GuestBookDAO guestBookDAO;

    /*待操作的GuestBook对象*/
    private GuestBook guestBook;
    public void setGuestBook(GuestBook guestBook) {
        this.guestBook = guestBook;
    }
    public GuestBook getGuestBook() {
        return this.guestBook;
    }

    /*跳转到添加GuestBook视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加GuestBook信息*/
    @SuppressWarnings("deprecation")
    public String AddGuestBook() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(guestBook.getUserObj().getUser_name());
            guestBook.setUserObj(userObj);
            guestBookDAO.AddGuestBook(guestBook);
            ctx.put("message",  java.net.URLEncoder.encode("GuestBook添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GuestBook添加失败!"));
            return "error";
        }
    }

    /*查询GuestBook信息*/
    public String QueryGuestBook() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<GuestBook> guestBookList = guestBookDAO.QueryGuestBookInfo(title, userObj, currentPage);
        /*计算总的页数和总的记录数*/
        guestBookDAO.CalculateTotalPageAndRecordNumber(title, userObj);
        /*获取到总的页码数目*/
        totalPage = guestBookDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = guestBookDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("guestBookList",  guestBookList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryGuestBookOutputToExcel() { 
        if(title == null) title = "";
        List<GuestBook> guestBookList = guestBookDAO.QueryGuestBookInfo(title,userObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "GuestBook信息记录"; 
        String[] headers = { "留言标题","留言内容","留言人","留言时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<guestBookList.size();i++) {
        	GuestBook guestBook = guestBookList.get(i); 
        	dataset.add(new String[]{guestBook.getTitle(),guestBook.getContent(),guestBook.getUserObj().getRealName(),
guestBook.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"GuestBook.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询GuestBook信息*/
    public String FrontQueryGuestBook() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<GuestBook> guestBookList = guestBookDAO.QueryGuestBookInfo(title, userObj, currentPage);
        /*计算总的页数和总的记录数*/
        guestBookDAO.CalculateTotalPageAndRecordNumber(title, userObj);
        /*获取到总的页码数目*/
        totalPage = guestBookDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = guestBookDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("guestBookList",  guestBookList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "front_query_view";
    }

    /*查询要修改的GuestBook信息*/
    public String ModifyGuestBookQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键guestBookId获取GuestBook对象*/
        GuestBook guestBook = guestBookDAO.GetGuestBookByGuestBookId(guestBookId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("guestBook",  guestBook);
        return "modify_view";
    }

    /*查询要修改的GuestBook信息*/
    public String FrontShowGuestBookQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键guestBookId获取GuestBook对象*/
        GuestBook guestBook = guestBookDAO.GetGuestBookByGuestBookId(guestBookId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("guestBook",  guestBook);
        return "front_show_view";
    }

    /*更新修改GuestBook信息*/
    public String ModifyGuestBook() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(guestBook.getUserObj().getUser_name());
            guestBook.setUserObj(userObj);
            guestBookDAO.UpdateGuestBook(guestBook);
            ctx.put("message",  java.net.URLEncoder.encode("GuestBook信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GuestBook信息更新失败!"));
            return "error";
       }
   }

    /*删除GuestBook信息*/
    public String DeleteGuestBook() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            guestBookDAO.DeleteGuestBook(guestBookId);
            ctx.put("message",  java.net.URLEncoder.encode("GuestBook删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GuestBook删除失败!"));
            return "error";
        }
    }

}
