<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@page import="org.apache.commons.lang.StringUtils"%><html>
<LINK href="<%=basePath%>/css/style.css" type=text/css rel=stylesheet>
<SCRIPT language="JavaScript" src="<%=basePath%>/js/menu.js" type="text/javascript"></SCRIPT>
<script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.base64.js"></script>
<script type='text/javascript' src='js/jquery.js'></script>
<link type='text/css' href='<%=basePath%>css/basic.css' rel='stylesheet' media='screen' />
<script type='text/javascript' src='js/jquery.simplemodal.js'></script>
<script src="<%=basePath%>/js/jscal2.js"></script>
<script src="<%=basePath%>/js/lang/en.js"></script>
<script src="<%=basePath%>/js/jquery.number_format.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/jscal2.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/border-radius.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/steel/steel.css" />
<body>
<div align="center" >
	<% String errorCode=request.getParameter("error");%>
<table width="100%" height="100%" border="0" >
<%
	if (StringUtils.isNotBlank(errorCode)){
%>
<tr>
	<td colspan="2" align="center" valign="bottom">
			<SPAN class="error">
			<%
				if ("1".equalsIgnoreCase(errorCode)){
			%>
				&#272;&#259;ng nh&#7853;p kh&#244;ng th&#224;nh c&#244;ng
			<%	}else if ("2".equalsIgnoreCase(errorCode)){%>
				&#272;&#259;ng nh&#7853;p kh&#244;ng th&#224;nh c&#244;ng
			<%	}else if ("3".equalsIgnoreCase(errorCode)){%>
				Kh&#244;ng t&#236;m th&#7845;y t&#224;i kho&#7843;n
			<%	}else if ("4".equalsIgnoreCase(errorCode)){%>
				T&#224;i kho&#7843;n ch&#432;a &#273;&#432;&#7907;c k&#237;ch ho&#7841;t
			<%	}else if ("5".equalsIgnoreCase(errorCode)){%>
				L&#7895; h&#7879; th&#7889;ng
			<%	}else if ("500".equalsIgnoreCase(errorCode)){%>
				Access denied!
			<%} %>
			</SPAN>
	</td>
</tr>			
<%}%>

<tr>
	<td valign="middle" align="center">
			<form action="login.action" method="POST">
			<table width="400" class=tinyborder  bgcolor="#999999">
				<tr>
					<td align="center" colspan="2"> 
							<H3>Login</H3>
					</td>
				</tr>	
				<tr>
					<td>
						T&#234;n &#273;&#259;ng nh&#7853;p
					</td>
					<td>
						<input type="text" name="username"></input>
					</td>
				</tr>	
				<tr>	
					<td>
						M&#7853;t kh&#7849;u
					</td>
					<td>
						<input type="password" name="password"></input>
					</td>
				</tr>
				<tr>	
					<td colspan="2" align="center">
					<input type="submit" name="login" value="Login"/>
					</td>
				</tr>
			</table>
			</form>
	</td>
</tr>
</table>
</div>	
</body>

</html>