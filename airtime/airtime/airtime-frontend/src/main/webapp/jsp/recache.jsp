<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Qu&#7843;n l&#253; &#273;&#7847;u s&#7889;</title>
<LINK href="<%=basePath%>/css/style.css" type=text/css rel=stylesheet>
<SCRIPT language="JavaScript" src="<%=basePath%>/js/menu.js" type="text/javascript"></SCRIPT>
<SCRIPT language="JavaScript" src="<%=basePath%>/js/jquery-1.6.js" type="text/javascript"></SCRIPT>
<script src="<%=basePath%>/js/jscal2.js"></script>
<script src="<%=basePath%>/js/lang/en.js"></script>
<script src="<%=basePath%>/js/jquery.number_format.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/jscal2.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/border-radius.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/steel/steel.css" />
</head>
<body>
<div align="center">
<div style="height:40px;"><span><b>MOBIFONE URL 
</b></span></div>
<table class=tinyborder cellSpacing=0 cellPadding=0 width=50%
	bgColor=#f8f8f8 border=0>
	<TBODY>

		<SPAN class="error">
			<s:fielderror cssClass="Error" fieldName="sys_message" />

		</SPAN>
		<br>
		<br>
		<s:form action="/recache_mobi_property.mbv">
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD>Mobifone URL</TD>
				<TD>
				<s:textfield name="recacheModel.url"
					cssStyle="Table_TR" maxlength="200" size="50" value="%{recacheModel.url}"/>
				<s:fielderror cssClass="Error" fieldName="url" />
				</TD>
				<TD>
					<s:submit value="Thay doi"/>
				</TD>
			</TR>
		</s:form>
	</TBODY>
</TABLE>
<br>
<br>
<div style="height:40px;"><span><b>VIETTEL HOST  
</b></span></div>
<table class=tinyborder cellSpacing=0 cellPadding=0 width=50%
	bgColor=#f8f8f8 border=0>
	<TBODY>
		<s:form action="/recache_viettel_property.mbv">
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD>Viettel Host</TD>
				<TD>
				<s:textfield name="recacheModel.host"
					cssStyle="Table_TR" maxlength="100" size="50" value="%{recacheModel.host}"/>
				<s:fielderror cssClass="Error" fieldName="host" />
				</TD>
				<TD>
					
				</TD>
			</TR>
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD>Viettel Port</TD>
				<TD>
				<s:textfield name="recacheModel.port"
					cssStyle="Table_TR" maxlength="10" size="10" value="%{recacheModel.port}"/>
				<s:fielderror cssClass="Error" fieldName="port" />
				</TD>
				<TD>
					<s:submit value="Thay doi"/>
				</TD>
			</TR>
			
		</s:form>
	</TBODY>
</TABLE>

<br>
<br>

<table class=tinyborder cellSpacing=0 cellPadding=0 width=50%
	bgColor=#f8f8f8 border=0>
	<TBODY>
		<s:form action="/recache_notif_template.mbv">
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD><div ><span><b>C&#7853;p nh&#7853;t NOTIFICATION TEMPLATE t&#7915; database  
</b></span></div></TD>
				<TD>
					<s:submit value="Cap nhat"/>
				</TD>
			</TR>
		</s:form>
	</TBODY>
</TABLE>
<br>
<br>

<table class=tinyborder cellSpacing=0 cellPadding=0 width=50%
	bgColor=#f8f8f8 border=0>
	<TBODY>
		<s:form action="/recache_telco_provider.mbv">
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD><div><span><b>C&#7853;p nh&#7853;t TELCO PROVIDER</b></span></div></TD>
				<TD rowspan="1" valign="top">
					<s:submit value="Cap nhat"/>
				</TD>
			</TR>
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD colspan="2">
				<s:textarea name="recacheModel.paramValue" value="%{recacheModel.paramValue}" cols="80" rows="30"></s:textarea>	
				</TD>
				
			</TR>
		</s:form>
	</TBODY>
</TABLE>
</div>

</body>
</html>


