<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.BuildingInfo" %>
<%@ page import="com.chengxusheji.domain.AreaInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的AreaInfo信息
    List<AreaInfo> areaInfoList = (List<AreaInfo>)request.getAttribute("areaInfoList");
    BuildingInfo buildingInfo = (BuildingInfo)request.getAttribute("buildingInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改楼盘信息</TITLE>
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
    var buildingName = document.getElementById("buildingInfo.buildingName").value;
    if(buildingName=="") {
        alert('请输入楼盘名称!');
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
    <TD align="left" vAlign=top ><s:form action="BuildingInfo/BuildingInfo_ModifyBuildingInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>楼盘编号:</td>
    <td width=70%><input id="buildingInfo.buildingId" name="buildingInfo.buildingId" type="text" value="<%=buildingInfo.getBuildingId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>所在区域:</td>
    <td width=70%>
      <select name="buildingInfo.areaObj.areaId">
      <%
        for(AreaInfo areaInfo:areaInfoList) {
          String selected = "";
          if(areaInfo.getAreaId() == buildingInfo.getAreaObj().getAreaId())
            selected = "selected";
      %>
          <option value='<%=areaInfo.getAreaId() %>' <%=selected %>><%=areaInfo.getAreaName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>楼盘名称:</td>
    <td width=70%><input id="buildingInfo.buildingName" name="buildingInfo.buildingName" type="text" size="20" value='<%=buildingInfo.getBuildingName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>楼盘图片:</td>
    <td width=70%><img src="<%=basePath %><%=buildingInfo.getBuildingPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="buildingInfo.buildingPhoto" value="<%=buildingInfo.getBuildingPhoto() %>" />
    <input id="buildingPhotoFile" name="buildingPhotoFile" type="file" size="50" /></td>
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
