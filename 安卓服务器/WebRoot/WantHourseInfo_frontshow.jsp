<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.WantHourseInfo" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.AreaInfo" %>
<%@ page import="com.chengxusheji.domain.HourseType" %>
<%@ page import="com.chengxusheji.domain.PriceRange" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //获取所有的AreaInfo信息
    List<AreaInfo> areaInfoList = (List<AreaInfo>)request.getAttribute("areaInfoList");
    //获取所有的HourseType信息
    List<HourseType> hourseTypeList = (List<HourseType>)request.getAttribute("hourseTypeList");
    //获取所有的PriceRange信息
    List<PriceRange> priceRangeList = (List<PriceRange>)request.getAttribute("priceRangeList");
    WantHourseInfo wantHourseInfo = (WantHourseInfo)request.getAttribute("wantHourseInfo");

%>
<HTML><HEAD><TITLE>查看求租信息</TITLE>
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
    <td width=30%>记录编号:</td>
    <td width=70%><%=wantHourseInfo.getWantHourseId() %></td>
  </tr>

  <tr>
    <td width=30%>求租用户:</td>
    <td width=70%>
      <%=wantHourseInfo.getUserObj().getRealName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>标题:</td>
    <td width=70%><%=wantHourseInfo.getTitle() %></td>
  </tr>

  <tr>
    <td width=30%>求租区域:</td>
    <td width=70%>
      <%=wantHourseInfo.getPosition().getAreaName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>房屋类型:</td>
    <td width=70%>
      <%=wantHourseInfo.getHourseTypeObj().getTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>价格范围:</td>
    <td width=70%>
      <%=wantHourseInfo.getPriceRangeObj().getPriceName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>最高能出租金:</td>
    <td width=70%><%=wantHourseInfo.getPrice() %></td>
  </tr>

  <tr>
    <td width=30%>联系人:</td>
    <td width=70%><%=wantHourseInfo.getLianxiren() %></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><%=wantHourseInfo.getTelephone() %></td>
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
