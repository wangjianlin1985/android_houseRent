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
import com.chengxusheji.dao.HourseDAO;
import com.chengxusheji.domain.Hourse;
import com.chengxusheji.dao.BuildingInfoDAO;
import com.chengxusheji.domain.BuildingInfo;
import com.chengxusheji.dao.HourseTypeDAO;
import com.chengxusheji.domain.HourseType;
import com.chengxusheji.dao.PriceRangeDAO;
import com.chengxusheji.domain.PriceRange;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class HourseAction extends BaseAction {

	/*ͼƬ���ļ��ֶ�housePhoto��������*/
	private File housePhotoFile;
	private String housePhotoFileFileName;
	private String housePhotoFileContentType;
	public File getHousePhotoFile() {
		return housePhotoFile;
	}
	public void setHousePhotoFile(File housePhotoFile) {
		this.housePhotoFile = housePhotoFile;
	}
	public String getHousePhotoFileFileName() {
		return housePhotoFileFileName;
	}
	public void setHousePhotoFileFileName(String housePhotoFileFileName) {
		this.housePhotoFileFileName = housePhotoFileFileName;
	}
	public String getHousePhotoFileContentType() {
		return housePhotoFileContentType;
	}
	public void setHousePhotoFileContentType(String housePhotoFileContentType) {
		this.housePhotoFileContentType = housePhotoFileContentType;
	}
    /*�������Ҫ��ѯ������: ��������*/
    private String hourseName;
    public void setHourseName(String hourseName) {
        this.hourseName = hourseName;
    }
    public String getHourseName() {
        return this.hourseName;
    }

    /*�������Ҫ��ѯ������: ����¥��*/
    private BuildingInfo buildingObj;
    public void setBuildingObj(BuildingInfo buildingObj) {
        this.buildingObj = buildingObj;
    }
    public BuildingInfo getBuildingObj() {
        return this.buildingObj;
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

    /*�������Ҫ��ѯ������: �������*/
    private String madeYear;
    public void setMadeYear(String madeYear) {
        this.madeYear = madeYear;
    }
    public String getMadeYear() {
        return this.madeYear;
    }

    /*�������Ҫ��ѯ������: ��ϵ��*/
    private String connectPerson;
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }
    public String getConnectPerson() {
        return this.connectPerson;
    }

    /*�������Ҫ��ѯ������: ��ϵ�绰*/
    private String connectPhone;
    public void setConnectPhone(String connectPhone) {
        this.connectPhone = connectPhone;
    }
    public String getConnectPhone() {
        return this.connectPhone;
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

    private int hourseId;
    public void setHourseId(int hourseId) {
        this.hourseId = hourseId;
    }
    public int getHourseId() {
        return hourseId;
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
    @Resource BuildingInfoDAO buildingInfoDAO;
    @Resource HourseTypeDAO hourseTypeDAO;
    @Resource PriceRangeDAO priceRangeDAO;
    @Resource HourseDAO hourseDAO;

    /*��������Hourse����*/
    private Hourse hourse;
    public void setHourse(Hourse hourse) {
        this.hourse = hourse;
    }
    public Hourse getHourse() {
        return this.hourse;
    }

    /*��ת�����Hourse��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�BuildingInfo��Ϣ*/
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        /*��ѯ���е�HourseType��Ϣ*/
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryAllHourseTypeInfo();
        ctx.put("hourseTypeList", hourseTypeList);
        /*��ѯ���е�PriceRange��Ϣ*/
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        return "add_view";
    }

    /*���Hourse��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddHourse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BuildingInfo buildingObj = buildingInfoDAO.GetBuildingInfoByBuildingId(hourse.getBuildingObj().getBuildingId());
            hourse.setBuildingObj(buildingObj);
            HourseType hourseTypeObj = hourseTypeDAO.GetHourseTypeByTypeId(hourse.getHourseTypeObj().getTypeId());
            hourse.setHourseTypeObj(hourseTypeObj);
            PriceRange priceRangeObj = priceRangeDAO.GetPriceRangeByRangeId(hourse.getPriceRangeObj().getRangeId());
            hourse.setPriceRangeObj(priceRangeObj);
            /*������ͼƬ�ϴ�*/
            String housePhotoPath = "upload/noimage.jpg"; 
       	 	if(housePhotoFile != null)
       	 		housePhotoPath = photoUpload(housePhotoFile,housePhotoFileContentType);
       	 	hourse.setHousePhoto(housePhotoPath);
            hourseDAO.AddHourse(hourse);
            ctx.put("message",  java.net.URLEncoder.encode("Hourse��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Hourse���ʧ��!"));
            return "error";
        }
    }

    /*��ѯHourse��Ϣ*/
    public String QueryHourse() {
        if(currentPage == 0) currentPage = 1;
        if(hourseName == null) hourseName = "";
        if(madeYear == null) madeYear = "";
        if(connectPerson == null) connectPerson = "";
        if(connectPhone == null) connectPhone = "";
        List<Hourse> hourseList = hourseDAO.QueryHourseInfo(hourseName, buildingObj, hourseTypeObj, priceRangeObj, madeYear, connectPerson, connectPhone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        hourseDAO.CalculateTotalPageAndRecordNumber(hourseName, buildingObj, hourseTypeObj, priceRangeObj, madeYear, connectPerson, connectPhone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = hourseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = hourseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("hourseList",  hourseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("hourseName", hourseName);
        ctx.put("buildingObj", buildingObj);
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        ctx.put("hourseTypeObj", hourseTypeObj);
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryAllHourseTypeInfo();
        ctx.put("hourseTypeList", hourseTypeList);
        ctx.put("priceRangeObj", priceRangeObj);
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        ctx.put("madeYear", madeYear);
        ctx.put("connectPerson", connectPerson);
        ctx.put("connectPhone", connectPhone);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryHourseOutputToExcel() { 
        if(hourseName == null) hourseName = "";
        if(madeYear == null) madeYear = "";
        if(connectPerson == null) connectPerson = "";
        if(connectPhone == null) connectPhone = "";
        List<Hourse> hourseList = hourseDAO.QueryHourseInfo(hourseName,buildingObj,hourseTypeObj,priceRangeObj,madeYear,connectPerson,connectPhone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Hourse��Ϣ��¼"; 
        String[] headers = { "��������","����¥��","����ͼƬ","��������","�۸�Χ","���","���(Ԫ/��)","¥��/��¥��","װ��","����","�������","��ϵ��","��ϵ�绰"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<hourseList.size();i++) {
        	Hourse hourse = hourseList.get(i); 
        	dataset.add(new String[]{hourse.getHourseName(),hourse.getBuildingObj().getBuildingName(),
hourse.getHousePhoto(),hourse.getHourseTypeObj().getTypeName(),
hourse.getPriceRangeObj().getPriceName(),
hourse.getArea(),hourse.getPrice() + "",hourse.getLouceng(),hourse.getZhuangxiu(),hourse.getCaoxiang(),hourse.getMadeYear(),hourse.getConnectPerson(),hourse.getConnectPhone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Hourse.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯHourse��Ϣ*/
    public String FrontQueryHourse() {
        if(currentPage == 0) currentPage = 1;
        if(hourseName == null) hourseName = "";
        if(madeYear == null) madeYear = "";
        if(connectPerson == null) connectPerson = "";
        if(connectPhone == null) connectPhone = "";
        List<Hourse> hourseList = hourseDAO.QueryHourseInfo(hourseName, buildingObj, hourseTypeObj, priceRangeObj, madeYear, connectPerson, connectPhone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        hourseDAO.CalculateTotalPageAndRecordNumber(hourseName, buildingObj, hourseTypeObj, priceRangeObj, madeYear, connectPerson, connectPhone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = hourseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = hourseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("hourseList",  hourseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("hourseName", hourseName);
        ctx.put("buildingObj", buildingObj);
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        ctx.put("hourseTypeObj", hourseTypeObj);
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryAllHourseTypeInfo();
        ctx.put("hourseTypeList", hourseTypeList);
        ctx.put("priceRangeObj", priceRangeObj);
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        ctx.put("madeYear", madeYear);
        ctx.put("connectPerson", connectPerson);
        ctx.put("connectPhone", connectPhone);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Hourse��Ϣ*/
    public String ModifyHourseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������hourseId��ȡHourse����*/
        Hourse hourse = hourseDAO.GetHourseByHourseId(hourseId);

        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryAllHourseTypeInfo();
        ctx.put("hourseTypeList", hourseTypeList);
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        ctx.put("hourse",  hourse);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Hourse��Ϣ*/
    public String FrontShowHourseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������hourseId��ȡHourse����*/
        Hourse hourse = hourseDAO.GetHourseByHourseId(hourseId);

        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryAllHourseTypeInfo();
        ctx.put("hourseTypeList", hourseTypeList);
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        ctx.put("hourse",  hourse);
        return "front_show_view";
    }

    /*�����޸�Hourse��Ϣ*/
    public String ModifyHourse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BuildingInfo buildingObj = buildingInfoDAO.GetBuildingInfoByBuildingId(hourse.getBuildingObj().getBuildingId());
            hourse.setBuildingObj(buildingObj);
            HourseType hourseTypeObj = hourseTypeDAO.GetHourseTypeByTypeId(hourse.getHourseTypeObj().getTypeId());
            hourse.setHourseTypeObj(hourseTypeObj);
            PriceRange priceRangeObj = priceRangeDAO.GetPriceRangeByRangeId(hourse.getPriceRangeObj().getRangeId());
            hourse.setPriceRangeObj(priceRangeObj);
            /*������ͼƬ�ϴ�*/
            if(housePhotoFile != null) {
            	String housePhotoPath = photoUpload(housePhotoFile,housePhotoFileContentType);
            	hourse.setHousePhoto(housePhotoPath);
            }
            hourseDAO.UpdateHourse(hourse);
            ctx.put("message",  java.net.URLEncoder.encode("Hourse��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Hourse��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Hourse��Ϣ*/
    public String DeleteHourse() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            hourseDAO.DeleteHourse(hourseId);
            ctx.put("message",  java.net.URLEncoder.encode("Hourseɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Hourseɾ��ʧ��!"));
            return "error";
        }
    }

}
