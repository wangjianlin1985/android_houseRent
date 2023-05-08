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
import com.chengxusheji.dao.WantHourseInfoDAO;
import com.chengxusheji.domain.WantHourseInfo;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.dao.AreaInfoDAO;
import com.chengxusheji.domain.AreaInfo;
import com.chengxusheji.dao.HourseTypeDAO;
import com.chengxusheji.domain.HourseType;
import com.chengxusheji.dao.PriceRangeDAO;
import com.chengxusheji.domain.PriceRange;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class WantHourseInfoAction extends BaseAction {

    /*�������Ҫ��ѯ������: �����û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private AreaInfo position;
    public void setPosition(AreaInfo position) {
        this.position = position;
    }
    public AreaInfo getPosition() {
        return this.position;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private HourseType hourseTypeObj;
    public void setHourseTypeObj(HourseType hourseTypeObj) {
        this.hourseTypeObj = hourseTypeObj;
    }
    public HourseType getHourseTypeObj() {
        return this.hourseTypeObj;
    }

    /*�������Ҫ��ѯ������: �۸�Χ*/
    private PriceRange priceRangeObj;
    public void setPriceRangeObj(PriceRange priceRangeObj) {
        this.priceRangeObj = priceRangeObj;
    }
    public PriceRange getPriceRangeObj() {
        return this.priceRangeObj;
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

    private int wantHourseId;
    public void setWantHourseId(int wantHourseId) {
        this.wantHourseId = wantHourseId;
    }
    public int getWantHourseId() {
        return wantHourseId;
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
    @Resource AreaInfoDAO areaInfoDAO;
    @Resource HourseTypeDAO hourseTypeDAO;
    @Resource PriceRangeDAO priceRangeDAO;
    @Resource WantHourseInfoDAO wantHourseInfoDAO;

    /*��������WantHourseInfo����*/
    private WantHourseInfo wantHourseInfo;
    public void setWantHourseInfo(WantHourseInfo wantHourseInfo) {
        this.wantHourseInfo = wantHourseInfo;
    }
    public WantHourseInfo getWantHourseInfo() {
        return this.wantHourseInfo;
    }

    /*��ת�����WantHourseInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*��ѯ���е�AreaInfo��Ϣ*/
        List<AreaInfo> areaInfoList = areaInfoDAO.QueryAllAreaInfoInfo();
        ctx.put("areaInfoList", areaInfoList);
        /*��ѯ���е�HourseType��Ϣ*/
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryAllHourseTypeInfo();
        ctx.put("hourseTypeList", hourseTypeList);
        /*��ѯ���е�PriceRange��Ϣ*/
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        return "add_view";
    }

    /*���WantHourseInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddWantHourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(wantHourseInfo.getUserObj().getUser_name());
            wantHourseInfo.setUserObj(userObj);
            AreaInfo position = areaInfoDAO.GetAreaInfoByAreaId(wantHourseInfo.getPosition().getAreaId());
            wantHourseInfo.setPosition(position);
            HourseType hourseTypeObj = hourseTypeDAO.GetHourseTypeByTypeId(wantHourseInfo.getHourseTypeObj().getTypeId());
            wantHourseInfo.setHourseTypeObj(hourseTypeObj);
            PriceRange priceRangeObj = priceRangeDAO.GetPriceRangeByRangeId(wantHourseInfo.getPriceRangeObj().getRangeId());
            wantHourseInfo.setPriceRangeObj(priceRangeObj);
            wantHourseInfoDAO.AddWantHourseInfo(wantHourseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("WantHourseInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("WantHourseInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯWantHourseInfo��Ϣ*/
    public String QueryWantHourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<WantHourseInfo> wantHourseInfoList = wantHourseInfoDAO.QueryWantHourseInfoInfo(userObj, title, position, hourseTypeObj, priceRangeObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        wantHourseInfoDAO.CalculateTotalPageAndRecordNumber(userObj, title, position, hourseTypeObj, priceRangeObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = wantHourseInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = wantHourseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("wantHourseInfoList",  wantHourseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("title", title);
        ctx.put("position", position);
        List<AreaInfo> areaInfoList = areaInfoDAO.QueryAllAreaInfoInfo();
        ctx.put("areaInfoList", areaInfoList);
        ctx.put("hourseTypeObj", hourseTypeObj);
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryAllHourseTypeInfo();
        ctx.put("hourseTypeList", hourseTypeList);
        ctx.put("priceRangeObj", priceRangeObj);
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryWantHourseInfoOutputToExcel() { 
        if(title == null) title = "";
        List<WantHourseInfo> wantHourseInfoList = wantHourseInfoDAO.QueryWantHourseInfoInfo(userObj,title,position,hourseTypeObj,priceRangeObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "WantHourseInfo��Ϣ��¼"; 
        String[] headers = { "�����û�","����","��������","��������","�۸�Χ","����ܳ����","��ϵ��","��ϵ�绰"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<wantHourseInfoList.size();i++) {
        	WantHourseInfo wantHourseInfo = wantHourseInfoList.get(i); 
        	dataset.add(new String[]{wantHourseInfo.getUserObj().getRealName(),
wantHourseInfo.getTitle(),wantHourseInfo.getPosition().getAreaName(),
wantHourseInfo.getHourseTypeObj().getTypeName(),
wantHourseInfo.getPriceRangeObj().getPriceName(),
wantHourseInfo.getPrice() + "",wantHourseInfo.getLianxiren(),wantHourseInfo.getTelephone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"WantHourseInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯWantHourseInfo��Ϣ*/
    public String FrontQueryWantHourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<WantHourseInfo> wantHourseInfoList = wantHourseInfoDAO.QueryWantHourseInfoInfo(userObj, title, position, hourseTypeObj, priceRangeObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        wantHourseInfoDAO.CalculateTotalPageAndRecordNumber(userObj, title, position, hourseTypeObj, priceRangeObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = wantHourseInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = wantHourseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("wantHourseInfoList",  wantHourseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("title", title);
        ctx.put("position", position);
        List<AreaInfo> areaInfoList = areaInfoDAO.QueryAllAreaInfoInfo();
        ctx.put("areaInfoList", areaInfoList);
        ctx.put("hourseTypeObj", hourseTypeObj);
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryAllHourseTypeInfo();
        ctx.put("hourseTypeList", hourseTypeList);
        ctx.put("priceRangeObj", priceRangeObj);
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�WantHourseInfo��Ϣ*/
    public String ModifyWantHourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������wantHourseId��ȡWantHourseInfo����*/
        WantHourseInfo wantHourseInfo = wantHourseInfoDAO.GetWantHourseInfoByWantHourseId(wantHourseId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<AreaInfo> areaInfoList = areaInfoDAO.QueryAllAreaInfoInfo();
        ctx.put("areaInfoList", areaInfoList);
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryAllHourseTypeInfo();
        ctx.put("hourseTypeList", hourseTypeList);
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        ctx.put("wantHourseInfo",  wantHourseInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�WantHourseInfo��Ϣ*/
    public String FrontShowWantHourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������wantHourseId��ȡWantHourseInfo����*/
        WantHourseInfo wantHourseInfo = wantHourseInfoDAO.GetWantHourseInfoByWantHourseId(wantHourseId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<AreaInfo> areaInfoList = areaInfoDAO.QueryAllAreaInfoInfo();
        ctx.put("areaInfoList", areaInfoList);
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryAllHourseTypeInfo();
        ctx.put("hourseTypeList", hourseTypeList);
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        ctx.put("wantHourseInfo",  wantHourseInfo);
        return "front_show_view";
    }

    /*�����޸�WantHourseInfo��Ϣ*/
    public String ModifyWantHourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(wantHourseInfo.getUserObj().getUser_name());
            wantHourseInfo.setUserObj(userObj);
            AreaInfo position = areaInfoDAO.GetAreaInfoByAreaId(wantHourseInfo.getPosition().getAreaId());
            wantHourseInfo.setPosition(position);
            HourseType hourseTypeObj = hourseTypeDAO.GetHourseTypeByTypeId(wantHourseInfo.getHourseTypeObj().getTypeId());
            wantHourseInfo.setHourseTypeObj(hourseTypeObj);
            PriceRange priceRangeObj = priceRangeDAO.GetPriceRangeByRangeId(wantHourseInfo.getPriceRangeObj().getRangeId());
            wantHourseInfo.setPriceRangeObj(priceRangeObj);
            wantHourseInfoDAO.UpdateWantHourseInfo(wantHourseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("WantHourseInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("WantHourseInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��WantHourseInfo��Ϣ*/
    public String DeleteWantHourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            wantHourseInfoDAO.DeleteWantHourseInfo(wantHourseId);
            ctx.put("message",  java.net.URLEncoder.encode("WantHourseInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("WantHourseInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
