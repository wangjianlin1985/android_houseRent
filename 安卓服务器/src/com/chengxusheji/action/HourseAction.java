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

	/*图片或文件字段housePhoto参数接收*/
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
    /*界面层需要查询的属性: 房屋名称*/
    private String hourseName;
    public void setHourseName(String hourseName) {
        this.hourseName = hourseName;
    }
    public String getHourseName() {
        return this.hourseName;
    }

    /*界面层需要查询的属性: 所在楼盘*/
    private BuildingInfo buildingObj;
    public void setBuildingObj(BuildingInfo buildingObj) {
        this.buildingObj = buildingObj;
    }
    public BuildingInfo getBuildingObj() {
        return this.buildingObj;
    }

    /*界面层需要查询的属性: 房屋类型*/
    private HourseType hourseTypeObj;
    public void setHourseTypeObj(HourseType hourseTypeObj) {
        this.hourseTypeObj = hourseTypeObj;
    }
    public HourseType getHourseTypeObj() {
        return this.hourseTypeObj;
    }

    /*界面层需要查询的属性: 价格范围*/
    private PriceRange priceRangeObj;
    public void setPriceRangeObj(PriceRange priceRangeObj) {
        this.priceRangeObj = priceRangeObj;
    }
    public PriceRange getPriceRangeObj() {
        return this.priceRangeObj;
    }

    /*界面层需要查询的属性: 建筑年代*/
    private String madeYear;
    public void setMadeYear(String madeYear) {
        this.madeYear = madeYear;
    }
    public String getMadeYear() {
        return this.madeYear;
    }

    /*界面层需要查询的属性: 联系人*/
    private String connectPerson;
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }
    public String getConnectPerson() {
        return this.connectPerson;
    }

    /*界面层需要查询的属性: 联系电话*/
    private String connectPhone;
    public void setConnectPhone(String connectPhone) {
        this.connectPhone = connectPhone;
    }
    public String getConnectPhone() {
        return this.connectPhone;
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

    private int hourseId;
    public void setHourseId(int hourseId) {
        this.hourseId = hourseId;
    }
    public int getHourseId() {
        return hourseId;
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
    @Resource BuildingInfoDAO buildingInfoDAO;
    @Resource HourseTypeDAO hourseTypeDAO;
    @Resource PriceRangeDAO priceRangeDAO;
    @Resource HourseDAO hourseDAO;

    /*待操作的Hourse对象*/
    private Hourse hourse;
    public void setHourse(Hourse hourse) {
        this.hourse = hourse;
    }
    public Hourse getHourse() {
        return this.hourse;
    }

    /*跳转到添加Hourse视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的BuildingInfo信息*/
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        /*查询所有的HourseType信息*/
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryAllHourseTypeInfo();
        ctx.put("hourseTypeList", hourseTypeList);
        /*查询所有的PriceRange信息*/
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        return "add_view";
    }

    /*添加Hourse信息*/
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
            /*处理房屋图片上传*/
            String housePhotoPath = "upload/noimage.jpg"; 
       	 	if(housePhotoFile != null)
       	 		housePhotoPath = photoUpload(housePhotoFile,housePhotoFileContentType);
       	 	hourse.setHousePhoto(housePhotoPath);
            hourseDAO.AddHourse(hourse);
            ctx.put("message",  java.net.URLEncoder.encode("Hourse添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Hourse添加失败!"));
            return "error";
        }
    }

    /*查询Hourse信息*/
    public String QueryHourse() {
        if(currentPage == 0) currentPage = 1;
        if(hourseName == null) hourseName = "";
        if(madeYear == null) madeYear = "";
        if(connectPerson == null) connectPerson = "";
        if(connectPhone == null) connectPhone = "";
        List<Hourse> hourseList = hourseDAO.QueryHourseInfo(hourseName, buildingObj, hourseTypeObj, priceRangeObj, madeYear, connectPerson, connectPhone, currentPage);
        /*计算总的页数和总的记录数*/
        hourseDAO.CalculateTotalPageAndRecordNumber(hourseName, buildingObj, hourseTypeObj, priceRangeObj, madeYear, connectPerson, connectPhone);
        /*获取到总的页码数目*/
        totalPage = hourseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryHourseOutputToExcel() { 
        if(hourseName == null) hourseName = "";
        if(madeYear == null) madeYear = "";
        if(connectPerson == null) connectPerson = "";
        if(connectPhone == null) connectPhone = "";
        List<Hourse> hourseList = hourseDAO.QueryHourseInfo(hourseName,buildingObj,hourseTypeObj,priceRangeObj,madeYear,connectPerson,connectPhone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Hourse信息记录"; 
        String[] headers = { "房屋名称","所在楼盘","房屋图片","房屋类型","价格范围","面积","租金(元/月)","楼层/总楼层","装修","朝向","建筑年代","联系人","联系电话"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Hourse.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Hourse信息*/
    public String FrontQueryHourse() {
        if(currentPage == 0) currentPage = 1;
        if(hourseName == null) hourseName = "";
        if(madeYear == null) madeYear = "";
        if(connectPerson == null) connectPerson = "";
        if(connectPhone == null) connectPhone = "";
        List<Hourse> hourseList = hourseDAO.QueryHourseInfo(hourseName, buildingObj, hourseTypeObj, priceRangeObj, madeYear, connectPerson, connectPhone, currentPage);
        /*计算总的页数和总的记录数*/
        hourseDAO.CalculateTotalPageAndRecordNumber(hourseName, buildingObj, hourseTypeObj, priceRangeObj, madeYear, connectPerson, connectPhone);
        /*获取到总的页码数目*/
        totalPage = hourseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Hourse信息*/
    public String ModifyHourseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键hourseId获取Hourse对象*/
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

    /*查询要修改的Hourse信息*/
    public String FrontShowHourseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键hourseId获取Hourse对象*/
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

    /*更新修改Hourse信息*/
    public String ModifyHourse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BuildingInfo buildingObj = buildingInfoDAO.GetBuildingInfoByBuildingId(hourse.getBuildingObj().getBuildingId());
            hourse.setBuildingObj(buildingObj);
            HourseType hourseTypeObj = hourseTypeDAO.GetHourseTypeByTypeId(hourse.getHourseTypeObj().getTypeId());
            hourse.setHourseTypeObj(hourseTypeObj);
            PriceRange priceRangeObj = priceRangeDAO.GetPriceRangeByRangeId(hourse.getPriceRangeObj().getRangeId());
            hourse.setPriceRangeObj(priceRangeObj);
            /*处理房屋图片上传*/
            if(housePhotoFile != null) {
            	String housePhotoPath = photoUpload(housePhotoFile,housePhotoFileContentType);
            	hourse.setHousePhoto(housePhotoPath);
            }
            hourseDAO.UpdateHourse(hourse);
            ctx.put("message",  java.net.URLEncoder.encode("Hourse信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Hourse信息更新失败!"));
            return "error";
       }
   }

    /*删除Hourse信息*/
    public String DeleteHourse() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            hourseDAO.DeleteHourse(hourseId);
            ctx.put("message",  java.net.URLEncoder.encode("Hourse删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Hourse删除失败!"));
            return "error";
        }
    }

}
