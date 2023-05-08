<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.GuestBook" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    GuestBook guestBook = (GuestBook)request.getAttribute("guestBook");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改留言信息</TITLE>
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
    var title = document.getElementById("guestBook.title").value;
    if(title=="") {
        alert('请输入留言标题!');
        return false;
    }
    var content = document.getElementById("guestBook.content").value;
    if(content=="") {
        alert('请输入留言内容!');
        return false;
    }
    var addTime = document.getElementById("guestBook.addTime").value;
    if(addTime=="") {
        alert('请输入留言时间!');
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
    <TD align="left" vAlign=top ><s:form action="GuestBook/GuestBook_ModifyGuestBook.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>记录编号:</td>
    <td width=70%><input id="guestBook.guestBookId" name="guestBook.guestBookId" type="text" value="<%=guestBook.getGuestBookId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>留言标题:</td>
    <td width=70%><input id="guestBook.title" name="guestBook.title" type="text" size="40" value='<%=guestBook.getTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>留言内容:</td>
    <td width=70%><textarea id="guestBook.content" name="guestBook.content" rows=5 cols=50><%=guestBook.getContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>留言人:</td>
    <td width=70%>
      <select name="guestBook.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(guestBook.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getRealName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>留言时间:</td>
    <td width=70%><input id="guestBook.addTime" name="guestBook.addTime" type="text" size="20" value='<%=guestBook.getAddTime() %>'/></td>
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
