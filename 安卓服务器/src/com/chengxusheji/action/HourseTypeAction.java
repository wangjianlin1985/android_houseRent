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

    private int typeId;
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public int getTypeId() {
        return typeId;
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
    @Resource HourseTypeDAO hourseTypeDAO;

    /*��������HourseType����*/
    private HourseType hourseType;
    public void setHourseType(HourseType hourseType) {
        this.hourseType = hourseType;
    }
    public HourseType getHourseType() {
        return this.hourseType;
    }

    /*��ת�����HourseType��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���HourseType��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddHourseType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            hourseTypeDAO.AddHourseType(hourseType);
            ctx.put("message",  java.net.URLEncoder.encode("HourseType��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HourseType���ʧ��!"));
            return "error";
        }
    }

    /*��ѯHourseType��Ϣ*/
    public String QueryHourseType() {
        if(currentPage == 0) currentPage = 1;
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryHourseTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        hourseTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = hourseTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = hourseTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("hourseTypeList",  hourseTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryHourseTypeOutputToExcel() { 
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryHourseTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "HourseType��Ϣ��¼"; 
        String[] headers = { "�����","��������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"HourseType.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯHourseType��Ϣ*/
    public String FrontQueryHourseType() {
        if(currentPage == 0) currentPage = 1;
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryHourseTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        hourseTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = hourseTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = hourseTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("hourseTypeList",  hourseTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�HourseType��Ϣ*/
    public String ModifyHourseTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������typeId��ȡHourseType����*/
        HourseType hourseType = hourseTypeDAO.GetHourseTypeByTypeId(typeId);

        ctx.put("hourseType",  hourseType);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�HourseType��Ϣ*/
    public String FrontShowHourseTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������typeId��ȡHourseType����*/
        HourseType hourseType = hourseTypeDAO.GetHourseTypeByTypeId(typeId);

        ctx.put("hourseType",  hourseType);
        return "front_show_view";
    }

    /*�����޸�HourseType��Ϣ*/
    public String ModifyHourseType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            hourseTypeDAO.UpdateHourseType(hourseType);
            ctx.put("message",  java.net.URLEncoder.encode("HourseType��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HourseType��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��HourseType��Ϣ*/
    public String DeleteHourseType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            hourseTypeDAO.DeleteHourseType(typeId);
            ctx.put("message",  java.net.URLEncoder.encode("HourseTypeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HourseTypeɾ��ʧ��!"));
            return "error";
        }
    }

}
