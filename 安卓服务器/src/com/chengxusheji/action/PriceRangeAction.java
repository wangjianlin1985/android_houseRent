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

    private int rangeId;
    public void setRangeId(int rangeId) {
        this.rangeId = rangeId;
    }
    public int getRangeId() {
        return rangeId;
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
    @Resource PriceRangeDAO priceRangeDAO;

    /*��������PriceRange����*/
    private PriceRange priceRange;
    public void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }
    public PriceRange getPriceRange() {
        return this.priceRange;
    }

    /*��ת�����PriceRange��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���PriceRange��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddPriceRange() {
        ActionContext ctx = ActionContext.getContext();
        try {
            priceRangeDAO.AddPriceRange(priceRange);
            ctx.put("message",  java.net.URLEncoder.encode("PriceRange��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PriceRange���ʧ��!"));
            return "error";
        }
    }

    /*��ѯPriceRange��Ϣ*/
    public String QueryPriceRange() {
        if(currentPage == 0) currentPage = 1;
        List<PriceRange> priceRangeList = priceRangeDAO.QueryPriceRangeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        priceRangeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = priceRangeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = priceRangeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("priceRangeList",  priceRangeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryPriceRangeOutputToExcel() { 
        List<PriceRange> priceRangeList = priceRangeDAO.QueryPriceRangeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "PriceRange��Ϣ��¼"; 
        String[] headers = { "��¼���","�۸�����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"PriceRange.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯPriceRange��Ϣ*/
    public String FrontQueryPriceRange() {
        if(currentPage == 0) currentPage = 1;
        List<PriceRange> priceRangeList = priceRangeDAO.QueryPriceRangeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        priceRangeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = priceRangeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = priceRangeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("priceRangeList",  priceRangeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�PriceRange��Ϣ*/
    public String ModifyPriceRangeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������rangeId��ȡPriceRange����*/
        PriceRange priceRange = priceRangeDAO.GetPriceRangeByRangeId(rangeId);

        ctx.put("priceRange",  priceRange);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�PriceRange��Ϣ*/
    public String FrontShowPriceRangeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������rangeId��ȡPriceRange����*/
        PriceRange priceRange = priceRangeDAO.GetPriceRangeByRangeId(rangeId);

        ctx.put("priceRange",  priceRange);
        return "front_show_view";
    }

    /*�����޸�PriceRange��Ϣ*/
    public String ModifyPriceRange() {
        ActionContext ctx = ActionContext.getContext();
        try {
            priceRangeDAO.UpdatePriceRange(priceRange);
            ctx.put("message",  java.net.URLEncoder.encode("PriceRange��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PriceRange��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��PriceRange��Ϣ*/
    public String DeletePriceRange() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            priceRangeDAO.DeletePriceRange(rangeId);
            ctx.put("message",  java.net.URLEncoder.encode("PriceRangeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PriceRangeɾ��ʧ��!"));
            return "error";
        }
    }

}
