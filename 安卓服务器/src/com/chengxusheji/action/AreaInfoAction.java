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

    private int areaId;
    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
    public int getAreaId() {
        return areaId;
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
    @Resource AreaInfoDAO areaInfoDAO;

    /*��������AreaInfo����*/
    private AreaInfo areaInfo;
    public void setAreaInfo(AreaInfo areaInfo) {
        this.areaInfo = areaInfo;
    }
    public AreaInfo getAreaInfo() {
        return this.areaInfo;
    }

    /*��ת�����AreaInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���AreaInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddAreaInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            areaInfoDAO.AddAreaInfo(areaInfo);
            ctx.put("message",  java.net.URLEncoder.encode("AreaInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("AreaInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯAreaInfo��Ϣ*/
    public String QueryAreaInfo() {
        if(currentPage == 0) currentPage = 1;
        List<AreaInfo> areaInfoList = areaInfoDAO.QueryAreaInfoInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        areaInfoDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = areaInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = areaInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("areaInfoList",  areaInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryAreaInfoOutputToExcel() { 
        List<AreaInfo> areaInfoList = areaInfoDAO.QueryAreaInfoInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "AreaInfo��Ϣ��¼"; 
        String[] headers = { "��¼���","��������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"AreaInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯAreaInfo��Ϣ*/
    public String FrontQueryAreaInfo() {
        if(currentPage == 0) currentPage = 1;
        List<AreaInfo> areaInfoList = areaInfoDAO.QueryAreaInfoInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        areaInfoDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = areaInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = areaInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("areaInfoList",  areaInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�AreaInfo��Ϣ*/
    public String ModifyAreaInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������areaId��ȡAreaInfo����*/
        AreaInfo areaInfo = areaInfoDAO.GetAreaInfoByAreaId(areaId);

        ctx.put("areaInfo",  areaInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�AreaInfo��Ϣ*/
    public String FrontShowAreaInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������areaId��ȡAreaInfo����*/
        AreaInfo areaInfo = areaInfoDAO.GetAreaInfoByAreaId(areaId);

        ctx.put("areaInfo",  areaInfo);
        return "front_show_view";
    }

    /*�����޸�AreaInfo��Ϣ*/
    public String ModifyAreaInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            areaInfoDAO.UpdateAreaInfo(areaInfo);
            ctx.put("message",  java.net.URLEncoder.encode("AreaInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("AreaInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��AreaInfo��Ϣ*/
    public String DeleteAreaInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            areaInfoDAO.DeleteAreaInfo(areaId);
            ctx.put("message",  java.net.URLEncoder.encode("AreaInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("AreaInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
