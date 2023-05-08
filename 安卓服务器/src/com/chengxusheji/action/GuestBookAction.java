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

    /*�������Ҫ��ѯ������: ���Ա���*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: ������*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource UserInfoDAO userInfoDAO;
    @Resource GuestBookDAO guestBookDAO;

    /*��������GuestBook����*/
    private GuestBook guestBook;
    public void setGuestBook(GuestBook guestBook) {
        this.guestBook = guestBook;
    }
    public GuestBook getGuestBook() {
        return this.guestBook;
    }

    /*��ת�����GuestBook��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���GuestBook��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddGuestBook() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(guestBook.getUserObj().getUser_name());
            guestBook.setUserObj(userObj);
            guestBookDAO.AddGuestBook(guestBook);
            ctx.put("message",  java.net.URLEncoder.encode("GuestBook��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GuestBook���ʧ��!"));
            return "error";
        }
    }

    /*��ѯGuestBook��Ϣ*/
    public String QueryGuestBook() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<GuestBook> guestBookList = guestBookDAO.QueryGuestBookInfo(title, userObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        guestBookDAO.CalculateTotalPageAndRecordNumber(title, userObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = guestBookDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryGuestBookOutputToExcel() { 
        if(title == null) title = "";
        List<GuestBook> guestBookList = guestBookDAO.QueryGuestBookInfo(title,userObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "GuestBook��Ϣ��¼"; 
        String[] headers = { "���Ա���","��������","������","����ʱ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"GuestBook.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯGuestBook��Ϣ*/
    public String FrontQueryGuestBook() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<GuestBook> guestBookList = guestBookDAO.QueryGuestBookInfo(title, userObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        guestBookDAO.CalculateTotalPageAndRecordNumber(title, userObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = guestBookDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�GuestBook��Ϣ*/
    public String ModifyGuestBookQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������guestBookId��ȡGuestBook����*/
        GuestBook guestBook = guestBookDAO.GetGuestBookByGuestBookId(guestBookId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("guestBook",  guestBook);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�GuestBook��Ϣ*/
    public String FrontShowGuestBookQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������guestBookId��ȡGuestBook����*/
        GuestBook guestBook = guestBookDAO.GetGuestBookByGuestBookId(guestBookId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("guestBook",  guestBook);
        return "front_show_view";
    }

    /*�����޸�GuestBook��Ϣ*/
    public String ModifyGuestBook() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(guestBook.getUserObj().getUser_name());
            guestBook.setUserObj(userObj);
            guestBookDAO.UpdateGuestBook(guestBook);
            ctx.put("message",  java.net.URLEncoder.encode("GuestBook��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GuestBook��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��GuestBook��Ϣ*/
    public String DeleteGuestBook() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            guestBookDAO.DeleteGuestBook(guestBookId);
            ctx.put("message",  java.net.URLEncoder.encode("GuestBookɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GuestBookɾ��ʧ��!"));
            return "error";
        }
    }

}
