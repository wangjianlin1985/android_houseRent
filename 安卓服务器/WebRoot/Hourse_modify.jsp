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
    //��ȡ���е�BuildingInfo��Ϣ
    List<BuildingInfo> buildingInfoList = (List<BuildingInfo>)request.getAttribute("buildingInfoList");
    //��ȡ���е�HourseType��Ϣ
    List<HourseType> hourseTypeList = (List<HourseType>)request.getAttribute("hourseTypeList");
    //��ȡ���е�PriceRange��Ϣ
    List<PriceRange> priceRangeList = (List<PriceRange>)request.getAttribute("priceRangeList");
    Hourse hourse = (Hourse)request.getAttribute("hourse");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸ķ�����Ϣ</TITLE>
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
    var hourseName = document.getElementById("hourse.hourseName").value;
    if(hourseName=="") {
        alert('�����뷿������!');
        return false;
    }
    var area = document.getElementById("hourse.area").value;
    if(area=="") {
        alert('���������!');
        return false;
    }
    var louceng = document.getElementById("hourse.louceng").value;
    if(louceng=="") {
        alert('������¥��/��¥��!');
        return false;
    }
    var zhuangxiu = document.getElementById("hourse.zhuangxiu").value;
    if(zhuangxiu=="") {
        alert('������װ��!');
        return false;
    }
    var connectPerson = document.getElementById("hourse.connectPerson").value;
    if(connectPerson=="") {
        alert('��������ϵ��!');
        return false;
    }
    var connectPhone = document.getElementById("hourse.connectPhone").value;
    if(connectPhone=="") {
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
    <TD align="left" vAlign=top ><s:form action="Hourse/Hourse_ModifyHourse.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>���ݱ��:</td>
    <td width=70%><input id="hourse.hourseId" name="hourse.hourseId" type="text" value="<%=hourse.getHourseId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="hourse.hourseName" name="hourse.hourseName" type="text" size="20" value='<%=hourse.getHourseName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����¥��:</td>
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
    <td width=30%>����ͼƬ:</td>
    <td width=70%><img src="<%=basePath %><%=hourse.getHousePhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="hourse.housePhoto" value="<%=hourse.getHousePhoto() %>" />
    <input id="housePhotoFile" name="housePhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>��������:</td>
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
    <td width=30%>�۸�Χ:</td>
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
    <td width=30%>���:</td>
    <td width=70%><input id="hourse.area" name="hourse.area" type="text" size="20" value='<%=hourse.getArea() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���(Ԫ/��):</td>
    <td width=70%><input id="hourse.price" name="hourse.price" type="text" size="8" value='<%=hourse.getPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>¥��/��¥��:</td>
    <td width=70%><input id="hourse.louceng" name="hourse.louceng" type="text" size="20" value='<%=hourse.getLouceng() %>'/></td>
  </tr>

  <tr>
    <td width=30%>װ��:</td>
    <td width=70%><input id="hourse.zhuangxiu" name="hourse.zhuangxiu" type="text" size="20" value='<%=hourse.getZhuangxiu() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="hourse.caoxiang" name="hourse.caoxiang" type="text" size="20" value='<%=hourse.getCaoxiang() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><input id="hourse.madeYear" name="hourse.madeYear" type="text" size="20" value='<%=hourse.getMadeYear() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ��:</td>
    <td width=70%><input id="hourse.connectPerson" name="hourse.connectPerson" type="text" size="20" value='<%=hourse.getConnectPerson() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="hourse.connectPhone" name="hourse.connectPhone" type="text" size="20" value='<%=hourse.getConnectPhone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϸ��Ϣ:</td>
    <td width=70%><textarea id="hourse.detail" name="hourse.detail" rows=5 cols=50><%=hourse.getDetail() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>��ַ:</td>
    <td width=70%><input id="hourse.address" name="hourse.address" type="text" size="50" value='<%=hourse.getAddress() %>'/></td>
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
