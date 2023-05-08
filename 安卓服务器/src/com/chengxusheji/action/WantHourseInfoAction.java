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

    /*界面层需要查询的属性: 求租用户*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 标题*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*界面层需要查询的属性: 求租区域*/
    private AreaInfo position;
    public void setPosition(AreaInfo position) {
        this.position = position;
    }
    public AreaInfo getPosition() {
        return this.position;
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

    private int wantHourseId;
    public void setWantHourseId(int wantHourseId) {
        this.wantHourseId = wantHourseId;
    }
    public int getWantHourseId() {
        return wantHourseId;
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
    @Resource AreaInfoDAO areaInfoDAO;
    @Resource HourseTypeDAO hourseTypeDAO;
    @Resource PriceRangeDAO priceRangeDAO;
    @Resource WantHourseInfoDAO wantHourseInfoDAO;

    /*待操作的WantHourseInfo对象*/
    private WantHourseInfo wantHourseInfo;
    public void setWantHourseInfo(WantHourseInfo wantHourseInfo) {
        this.wantHourseInfo = wantHourseInfo;
    }
    public WantHourseInfo getWantHourseInfo() {
        return this.wantHourseInfo;
    }

    /*跳转到添加WantHourseInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*查询所有的AreaInfo信息*/
        List<AreaInfo> areaInfoList = areaInfoDAO.QueryAllAreaInfoInfo();
        ctx.put("areaInfoList", areaInfoList);
        /*查询所有的HourseType信息*/
        List<HourseType> hourseTypeList = hourseTypeDAO.QueryAllHourseTypeInfo();
        ctx.put("hourseTypeList", hourseTypeList);
        /*查询所有的PriceRange信息*/
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        return "add_view";
    }

    /*添加WantHourseInfo信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("WantHourseInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("WantHourseInfo添加失败!"));
            return "error";
        }
    }

    /*查询WantHourseInfo信息*/
    public String QueryWantHourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<WantHourseInfo> wantHourseInfoList = wantHourseInfoDAO.QueryWantHourseInfoInfo(userObj, title, position, hourseTypeObj, priceRangeObj, currentPage);
        /*计算总的页数和总的记录数*/
        wantHourseInfoDAO.CalculateTotalPageAndRecordNumber(userObj, title, position, hourseTypeObj, priceRangeObj);
        /*获取到总的页码数目*/
        totalPage = wantHourseInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryWantHourseInfoOutputToExcel() { 
        if(title == null) title = "";
        List<WantHourseInfo> wantHourseInfoList = wantHourseInfoDAO.QueryWantHourseInfoInfo(userObj,title,position,hourseTypeObj,priceRangeObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "WantHourseInfo信息记录"; 
        String[] headers = { "求租用户","标题","求租区域","房屋类型","价格范围","最高能出租金","联系人","联系电话"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"WantHourseInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询WantHourseInfo信息*/
    public String FrontQueryWantHourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<WantHourseInfo> wantHourseInfoList = wantHourseInfoDAO.QueryWantHourseInfoInfo(userObj, title, position, hourseTypeObj, priceRangeObj, currentPage);
        /*计算总的页数和总的记录数*/
        wantHourseInfoDAO.CalculateTotalPageAndRecordNumber(userObj, title, position, hourseTypeObj, priceRangeObj);
        /*获取到总的页码数目*/
        totalPage = wantHourseInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的WantHourseInfo信息*/
    public String ModifyWantHourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键wantHourseId获取WantHourseInfo对象*/
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

    /*查询要修改的WantHourseInfo信息*/
    public String FrontShowWantHourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键wantHourseId获取WantHourseInfo对象*/
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

    /*更新修改WantHourseInfo信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("WantHourseInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("WantHourseInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除WantHourseInfo信息*/
    public String DeleteWantHourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            wantHourseInfoDAO.DeleteWantHourseInfo(wantHourseId);
            ctx.put("message",  java.net.URLEncoder.encode("WantHourseInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("WantHourseInfo删除失败!"));
            return "error";
        }
    }

}
