<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>安卓Android房屋租赁系统-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">用户信息</a></li> 
			<li><a href="<%=basePath %>AreaInfo/AreaInfo_FrontQueryAreaInfo.action" target="OfficeMain">区域信息</a></li> 
			<li><a href="<%=basePath %>BuildingInfo/BuildingInfo_FrontQueryBuildingInfo.action" target="OfficeMain">楼盘信息</a></li> 
			<li><a href="<%=basePath %>Hourse/Hourse_FrontQueryHourse.action" target="OfficeMain">房屋信息</a></li> 
			<li><a href="<%=basePath %>HourseType/HourseType_FrontQueryHourseType.action" target="OfficeMain">房屋类别</a></li> 
			<li><a href="<%=basePath %>WantHourseInfo/WantHourseInfo_FrontQueryWantHourseInfo.action" target="OfficeMain">求租信息</a></li> 
			<li><a href="<%=basePath %>GuestBook/GuestBook_FrontQueryGuestBook.action" target="OfficeMain">留言信息</a></li> 
			<li><a href="<%=basePath %>NewsInfo/NewsInfo_FrontQueryNewsInfo.action" target="OfficeMain">新闻公告</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>双鱼林设计 QQ:287307421或254540457 &copy;版权所有 <a href="http://www.shuangyulin.com" target="_blank">双鱼林设计网</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
