<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>

<%@page import="com.mbv.frontend.model.PageView"%>
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Qu&#7843;n l&#253; c&#7843;nh b&#225;o</title>
<LINK href="<%=basePath%>/css/style.css" type=text/css rel=stylesheet>

</head>
<body>
<div align="center">
<div style="height:40px;"><span><b>Thi&#7871;t l&#7853;p th&#244;ng s&#7889; c&#7843;nh b&#225;o
</b></span></div>
<SPAN class="error">
			<s:fielderror />
</SPAN>
<s:form action="/airtime_alert_dv.mbv">
	<table width="70%" class=tinyborder border="0">
		<tr align="left">
			<td>
				 Ch&#7885;n lo&#7841;i c&#7843;nh b&#225;o
			</td>
			<td width="30%">
				<s:select name="alertModel.alertId"
						list="%{#request.alertMap}" value="%{alertModel.alertId}" />
						<s:fielderror cssClass="Error" fieldName="providerId" />
				<s:submit key="button.submit.select"/>		
			</td>
		</tr>
	</table>
</s:form>
</div>

</body>
</html>