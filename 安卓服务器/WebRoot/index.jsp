<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>��׿Android��������ϵͳ-��ҳ</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">��ҳ</a></li>
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">�û���Ϣ</a></li> 
			<li><a href="<%=basePath %>AreaInfo/AreaInfo_FrontQueryAreaInfo.action" target="OfficeMain">������Ϣ</a></li> 
			<li><a href="<%=basePath %>BuildingInfo/BuildingInfo_FrontQueryBuildingInfo.action" target="OfficeMain">¥����Ϣ</a></li> 
			<li><a href="<%=basePath %>Hourse/Hourse_FrontQueryHourse.action" target="OfficeMain">������Ϣ</a></li> 
			<li><a href="<%=basePath %>HourseType/HourseType_FrontQueryHourseType.action" target="OfficeMain">�������</a></li> 
			<li><a href="<%=basePath %>WantHourseInfo/WantHourseInfo_FrontQueryWantHourseInfo.action" target="OfficeMain">������Ϣ</a></li> 
			<li><a href="<%=basePath %>GuestBook/GuestBook_FrontQueryGuestBook.action" target="OfficeMain">������Ϣ</a></li> 
			<li><a href="<%=basePath %>NewsInfo/NewsInfo_FrontQueryNewsInfo.action" target="OfficeMain">���Ź���</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>˫������� QQ:287307421��254540457 &copy;��Ȩ���� <a href="http://www.shuangyulin.com" target="_blank">˫���������</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>��̨��½</font></a></p>
	</div>
</div>
</body>
</html>
