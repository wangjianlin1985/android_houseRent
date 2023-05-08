<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.BuildingInfo" %>
<%@ page import="com.chengxusheji.domain.AreaInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的AreaInfo信息
    List<AreaInfo> areaInfoList = (List<AreaInfo>)request.getAttribute("areaInfoList");
    BuildingInfo buildingInfo = (BuildingInfo)request.getAttribute("buildingInfo");

%>
<HTML><HEAD><TITLE>查看楼盘信息</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>楼盘编号:</td>
    <td width=70%><%=buildingInfo.getBuildingId() %></td>
  </tr>

  <tr>
    <td width=30%>所在区域:</td>
    <td width=70%>
      <%=buildingInfo.getAreaObj().getAreaName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>楼盘名称:</td>
    <td width=70%><%=buildingInfo.getBuildingName() %></td>
  </tr>

  <tr>
    <td width=30%>楼盘图片:</td>
    <td width=70%><img src="<%=basePath %><%=buildingInfo.getBuildingPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
