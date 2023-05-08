<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Hourse" %>
<%@ page import="com.chengxusheji.domain.BuildingInfo" %>
<%@ page import="com.chengxusheji.domain.HourseType" %>
<%@ page import="com.chengxusheji.domain.PriceRange" %>
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

%>
<HTML><HEAD><TITLE>�鿴������Ϣ</TITLE>
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
    <td width=30%>���ݱ��:</td>
    <td width=70%><%=hourse.getHourseId() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><%=hourse.getHourseName() %></td>
  </tr>

  <tr>
    <td width=30%>����¥��:</td>
    <td width=70%>
      <%=hourse.getBuildingObj().getBuildingName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ͼƬ:</td>
    <td width=70%><img src="<%=basePath %><%=hourse.getHousePhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>��������:</td>
    <td width=70%>
      <%=hourse.getHourseTypeObj().getTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�۸�Χ:</td>
    <td width=70%>
      <%=hourse.getPriceRangeObj().getPriceName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>���:</td>
    <td width=70%><%=hourse.getArea() %></td>
  </tr>

  <tr>
    <td width=30%>���(Ԫ/��):</td>
    <td width=70%><%=hourse.getPrice() %></td>
  </tr>

  <tr>
    <td width=30%>¥��/��¥��:</td>
    <td width=70%><%=hourse.getLouceng() %></td>
  </tr>

  <tr>
    <td width=30%>װ��:</td>
    <td width=70%><%=hourse.getZhuangxiu() %></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><%=hourse.getCaoxiang() %></td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><%=hourse.getMadeYear() %></td>
  </tr>

  <tr>
    <td width=30%>��ϵ��:</td>
    <td width=70%><%=hourse.getConnectPerson() %></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><%=hourse.getConnectPhone() %></td>
  </tr>

  <tr>
    <td width=30%>��ϸ��Ϣ:</td>
    <td width=70%><%=hourse.getDetail() %></td>
  </tr>

  <tr>
    <td width=30%>��ַ:</td>
    <td width=70%><%=hourse.getAddress() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
