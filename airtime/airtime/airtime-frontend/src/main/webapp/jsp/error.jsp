<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<LINK href="<%=basePath%>/css/style.css" type=text/css rel=stylesheet>
<title>Insert title here</title>
</head>
<body>
<table width="100%" height="100%" align="center">
	<tr>
		<td valign="middle" align="center">
			<SPAN class="error">
				<s:fielderror cssClass="Error"/>
			</SPAN>
		</td>
	</tr>
</table>
</body>
</html>