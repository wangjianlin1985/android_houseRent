<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Hourse" %>
<%@ page import="com.chengxusheji.domain.BuildingInfo" %>
<%@ page import="com.chengxusheji.domain.HourseType" %>
<%@ page import="com.chengxusheji.domain.PriceRange" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的BuildingInfo信息
    List<BuildingInfo> buildingInfoList = (List<BuildingInfo>)request.getAttribute("buildingInfoList");
    //获取所有的HourseType信息
    List<HourseType> hourseTypeList = (List<HourseType>)request.getAttribute("hourseTypeList");
    //获取所有的PriceRange信息
    List<PriceRange> priceRangeList = (List<PriceRange>)request.getAttribute("priceRangeList");
    Hourse hourse = (Hourse)request.getAttribute("hourse");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改房屋信息</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var hourseName = document.getElementById("hourse.hourseName").value;
    if(hourseName=="") {
        alert('请输入房屋名称!');
        return false;
    }
    var area = document.getElementById("hourse.area").value;
    if(area=="") {
        alert('请输入面积!');
        return false;
    }
    var louceng = document.getElementById("hourse.louceng").value;
    if(louceng=="") {
        alert('请输入楼层/总楼层!');
        return false;
    }
    var zhuangxiu = document.getElementById("hourse.zhuangxiu").value;
    if(zhuangxiu=="") {
        alert('请输入装修!');
        return false;
    }
    var connectPerson = document.getElementById("hourse.connectPerson").value;
    if(connectPerson=="") {
        alert('请输入联系人!');
        return false;
    }
    var connectPhone = document.getElementById("hourse.connectPhone").value;
    if(connectPhone=="") {
        alert('请输入联系电话!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="Hourse/Hourse_ModifyHourse.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>房屋编号:</td>
    <td width=70%><input id="hourse.hourseId" name="hourse.hourseId" type="text" value="<%=hourse.getHourseId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>房屋名称:</td>
    <td width=70%><input id="hourse.hourseName" name="hourse.hourseName" type="text" size="20" value='<%=hourse.getHourseName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>所在楼盘:</td>
    <td width=70%>
      <select name="hourse.buildingObj.buildingId">
      <%
        for(BuildingInfo buildingInfo:buildingInfoList) {
          String selected = "";
          if(buildingInfo.getBuildingId() == hourse.getBuildingObj().getBuildingId())
            selected = "selected";
      %>
          <option value='<%=buildingInfo.getBuildingId() %>' <%=selected %>><%=buildingInfo.getBuildingName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>房屋图片:</td>
    <td width=70%><img src="<%=basePath %><%=hourse.getHousePhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="hourse.housePhoto" value="<%=hourse.getHousePhoto() %>" />
    <input id="housePhotoFile" name="housePhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>房屋类型:</td>
    <td width=70%>
      <select name="hourse.hourseTypeObj.typeId">
      <%
        for(HourseType hourseType:hourseTypeList) {
          String selected = "";
          if(hourseType.getTypeId() == hourse.getHourseTypeObj().getTypeId())
            selected = "selected";
      %>
          <option value='<%=hourseType.getTypeId() %>' <%=selected %>><%=hourseType.getTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>价格范围:</td>
    <td width=70%>
      <select name="hourse.priceRangeObj.rangeId">
      <%
        for(PriceRange priceRange:priceRangeList) {
          String selected = "";
          if(priceRange.getRangeId() == hourse.getPriceRangeObj().getRangeId())
            selected = "selected";
      %>
          <option value='<%=priceRange.getRangeId() %>' <%=selected %>><%=priceRange.getPriceName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>面积:</td>
    <td width=70%><input id="hourse.area" name="hourse.area" type="text" size="20" value='<%=hourse.getArea() %>'/></td>
  </tr>

  <tr>
    <td width=30%>租金(元/月):</td>
    <td width=70%><input id="hourse.price" name="hourse.price" type="text" size="8" value='<%=hourse.getPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>楼层/总楼层:</td>
    <td width=70%><input id="hourse.louceng" name="hourse.louceng" type="text" size="20" value='<%=hourse.getLouceng() %>'/></td>
  </tr>

  <tr>
    <td width=30%>装修:</td>
    <td width=70%><input id="hourse.zhuangxiu" name="hourse.zhuangxiu" type="text" size="20" value='<%=hourse.getZhuangxiu() %>'/></td>
  </tr>

  <tr>
    <td width=30%>朝向:</td>
    <td width=70%><input id="hourse.caoxiang" name="hourse.caoxiang" type="text" size="20" value='<%=hourse.getCaoxiang() %>'/></td>
  </tr>

  <tr>
    <td width=30%>建筑年代:</td>
    <td width=70%><input id="hourse.madeYear" name="hourse.madeYear" type="text" size="20" value='<%=hourse.getMadeYear() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系人:</td>
    <td width=70%><input id="hourse.connectPerson" name="hourse.connectPerson" type="text" size="20" value='<%=hourse.getConnectPerson() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="hourse.connectPhone" name="hourse.connectPhone" type="text" size="20" value='<%=hourse.getConnectPhone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>详细信息:</td>
    <td width=70%><textarea id="hourse.detail" name="hourse.detail" rows=5 cols=50><%=hourse.getDetail() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>地址:</td>
    <td width=70%><input id="hourse.address" name="hourse.address" type="text" size="50" value='<%=hourse.getAddress() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
