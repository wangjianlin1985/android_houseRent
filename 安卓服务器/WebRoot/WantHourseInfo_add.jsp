<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.AreaInfo" %>
<%@ page import="com.chengxusheji.domain.HourseType" %>
<%@ page import="com.chengxusheji.domain.PriceRange" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //��ȡ���е�AreaInfo��Ϣ
    List<AreaInfo> areaInfoList = (List<AreaInfo>)request.getAttribute("areaInfoList");
    //��ȡ���е�HourseType��Ϣ
    List<HourseType> hourseTypeList = (List<HourseType>)request.getAttribute("hourseTypeList");
    //��ȡ���е�PriceRange��Ϣ
    List<PriceRange> priceRangeList = (List<PriceRange>)request.getAttribute("priceRangeList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>���������Ϣ</TITLE> 
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
/*��֤��*/
function checkForm() {
    var title = document.getElementById("wantHourseInfo.title").value;
    if(title=="") {
        alert('���������!');
        return false;
    }
    var lianxiren = document.getElementById("wantHourseInfo.lianxiren").value;
    if(lianxiren=="") {
        alert('��������ϵ��!');
        return false;
    }
    var telephone = document.getElementById("wantHourseInfo.telephone").value;
    if(telephone=="") {
        alert('��������ϵ�绰!');
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
    <TD align="left" vAlign=top >
    <s:form action="WantHourseInfo/WantHourseInfo_AddWantHourseInfo.action" method="post" id="wantHourseInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>�����û�:</td>
    <td width=70%>
      <select name="wantHourseInfo.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
      %>
          <option value='<%=userInfo.getUser_name() %>'><%=userInfo.getRealName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="wantHourseInfo.title" name="wantHourseInfo.title" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%>
      <select name="wantHourseInfo.position.areaId">
      <%
        for(AreaInfo areaInfo:areaInfoList) {
      %>
          <option value='<%=areaInfo.getAreaId() %>'><%=areaInfo.getAreaName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%>
      <select name="wantHourseInfo.hourseTypeObj.typeId">
      <%
        for(HourseType hourseType:hourseTypeList) {
      %>
          <option value='<%=hourseType.getTypeId() %>'><%=hourseType.getTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�۸�Χ:</td>
    <td width=70%>
      <select name="wantHourseInfo.priceRangeObj.rangeId">
      <%
        for(PriceRange priceRange:priceRangeList) {
      %>
          <option value='<%=priceRange.getRangeId() %>'><%=priceRange.getPriceName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ܳ����:</td>
    <td width=70%><input id="wantHourseInfo.price" name="wantHourseInfo.price" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>��ϵ��:</td>
    <td width=70%><input id="wantHourseInfo.lianxiren" name="wantHourseInfo.lianxiren" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="wantHourseInfo.telephone" name="wantHourseInfo.telephone" type="text" size="20" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
