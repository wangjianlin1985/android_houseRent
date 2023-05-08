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
import com.chengxusheji.dao.NewsInfoDAO;
import com.chengxusheji.domain.NewsInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class NewsInfoAction extends BaseAction {

    /*�������Ҫ��ѯ������: ����*/
    private String newsTitle;
    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
    public String getNewsTitle() {
        return this.newsTitle;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String newsDate;
    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }
    public String getNewsDate() {
        return this.newsDate;
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

    private int newsId;
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }
    public int getNewsId() {
        return newsId;
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
    @Resource NewsInfoDAO newsInfoDAO;

    /*��������NewsInfo����*/
    private NewsInfo newsInfo;
    public void setNewsInfo(NewsInfo newsInfo) {
        this.newsInfo = newsInfo;
    }
    public NewsInfo getNewsInfo() {
        return this.newsInfo;
    }

    /*��ת�����NewsInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���NewsInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddNewsInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            newsInfoDAO.AddNewsInfo(newsInfo);
            ctx.put("message",  java.net.URLEncoder.encode("NewsInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NewsInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯNewsInfo��Ϣ*/
    public String QueryNewsInfo() {
        if(currentPage == 0) currentPage = 1;
        if(newsTitle == null) newsTitle = "";
        if(newsDate == null) newsDate = "";
        List<NewsInfo> newsInfoList = newsInfoDAO.QueryNewsInfoInfo(newsTitle, newsDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        newsInfoDAO.CalculateTotalPageAndRecordNumber(newsTitle, newsDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = newsInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = newsInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("newsInfoList",  newsInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("newsTitle", newsTitle);
        ctx.put("newsDate", newsDate);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryNewsInfoOutputToExcel() { 
        if(newsTitle == null) newsTitle = "";
        if(newsDate == null) newsDate = "";
        List<NewsInfo> newsInfoList = newsInfoDAO.QueryNewsInfoInfo(newsTitle,newsDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "NewsInfo��Ϣ��¼"; 
        String[] headers = { "����","��������","��������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<newsInfoList.size();i++) {
        	NewsInfo newsInfo = newsInfoList.get(i); 
        	dataset.add(new String[]{newsInfo.getNewsTitle(),newsInfo.getNewsContent(),new SimpleDateFormat("yyyy-MM-dd").format(newsInfo.getNewsDate())});
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
			response.setHeader("Content-disposition","attachment; filename="+"NewsInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯNewsInfo��Ϣ*/
    public String FrontQueryNewsInfo() {
        if(currentPage == 0) currentPage = 1;
        if(newsTitle == null) newsTitle = "";
        if(newsDate == null) newsDate = "";
        List<NewsInfo> newsInfoList = newsInfoDAO.QueryNewsInfoInfo(newsTitle, newsDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        newsInfoDAO.CalculateTotalPageAndRecordNumber(newsTitle, newsDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = newsInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = newsInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("newsInfoList",  newsInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("newsTitle", newsTitle);
        ctx.put("newsDate", newsDate);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�NewsInfo��Ϣ*/
    public String ModifyNewsInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������newsId��ȡNewsInfo����*/
        NewsInfo newsInfo = newsInfoDAO.GetNewsInfoByNewsId(newsId);

        ctx.put("newsInfo",  newsInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�NewsInfo��Ϣ*/
    public String FrontShowNewsInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������newsId��ȡNewsInfo����*/
        NewsInfo newsInfo = newsInfoDAO.GetNewsInfoByNewsId(newsId);

        ctx.put("newsInfo",  newsInfo);
        return "front_show_view";
    }

    /*�����޸�NewsInfo��Ϣ*/
    public String ModifyNewsInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            newsInfoDAO.UpdateNewsInfo(newsInfo);
            ctx.put("message",  java.net.URLEncoder.encode("NewsInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NewsInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��NewsInfo��Ϣ*/
    public String DeleteNewsInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            newsInfoDAO.DeleteNewsInfo(newsId);
            ctx.put("message",  java.net.URLEncoder.encode("NewsInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NewsInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
