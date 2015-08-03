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
<div style="height:40px;"><span><b>Thi&#7871;t l&#7853;p th&#244;ng s&#7889; c&#7843;nh b&#225;o
</b></span></div>
<SPAN class="error">
	<s:fielderror />
</SPAN>
<s:form action="/airtime_alert_update_notif_type.mbv">
	<table width="60%" class=tinyborder border="0">
		<tr align="left">
			<td>
				 <SPAN><b><s:property value="%{alertModel.alertName}"/></b></span>
				 <s:hidden name="alertModel.alertId" value="%{alertModel.alertId}"/> 
			</td>
		</tr>
		<tr align="left">
			<td>
				  Ki&#7875;u c&#7843;nh b&#225;o:
				  <s:checkbox name="alertModel.smsEnable" value="%{alertModel.smsEnable}">SMS</s:checkbox>
				  <s:checkbox name="alertModel.emailEnable" value="%{alertModel.emailEnable}">EMAIL</s:checkbox>
			</td>
		</tr>
		<tr align="left">
			<td>
				 <s:submit key="button.submit.Change"></s:submit>
			</td>
		</tr>
	</table>
</s:form>
	
<s:if test="%{alertModel.smsEnable}">
<Span><b>SMS contact</b></Span>
	<s:form action="/airtime_alert_add_contact.mbv">
		<TABLE class=tinyborder cellSpacing=0 cellPadding=0 width=60%
	bgColor=#f8f8f8 border=0>
		<s:hidden name="alertModel.alertId" value="%{alertModel.alertId}"/>
		<s:hidden name="alertModel.contactType" value="SMS"/>
	<tr>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Stt</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">T&#234;n</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">S&#7889; &#273;i&#7879;n tho&#7841;i</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">X&#243;a</span></td>
	<s:iterator status="status" value="%{alertModel.listSmsContact}">
		<s:if test="%{#status.index%2==1}">
				<tr onmouseout="td_onmouseout(this,color_out1)"
				onmouseover="td_onmouseover(this,color_over1)" class="Table_TR">
		</s:if>
		<s:else>
			<tr bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
		</s:else>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
						<s:property value="#status.index+1" />
					</span>

				</td>
				<td class="border_frame_right_bottom" align="left">
					<span	class="style19">
						<s:property value="contactName"/>
					</span>
				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="contactAddress"/>
					</span>

				</td>
				<td align="center">
					<a href="<%=basePath%>airtime_alert_remove_contact.mbv?alertModel.alertId=<s:property value='%{alertModel.alertId}'/>&alertModel.contactType=SMS&alertModel.contactAddress=<s:property value='contactAddress'/>">X&#243;a</a>
				</td>
			</tr>
	</s:iterator>
			<tr>
				<td>
					
				</td>
				<td>
					<s:if test="%{alertModel.contactType=='SMS'}"> 
						<s:textfield name="alertModel.contactName" value="%{alertModel.contactName}" maxlength="100" size="40"/>
					</s:if>
					<s:else>
						<s:textfield name="alertModel.contactName" value="" maxlength="100" size="40"/>
					</s:else>
				</td>
				<td>
					<s:if test="%{alertModel.contactType=='SMS'}"> 
						<s:textfield name="alertModel.contactAddress" value="%{alertModel.contactAddress}" maxlength="100" size="40"/>
					</s:if>
					<s:else>
						<s:textfield name="alertModel.contactAddress" value="" maxlength="100" size="40"/>
					</s:else>
				    
				</td>
				<td align="center"><s:submit key="button.addNew"/></td>
			</tr>
</table>
	</s:form>	
</s:if>

<s:if test="%{alertModel.emailEnable}">
<Span><b>Email contact</b></Span>
	<s:form action="/airtime_alert_add_contact.mbv">
		<TABLE class=tinyborder cellSpacing=0 cellPadding=0 width=60%
	bgColor=#f8f8f8 border=0>
	<s:hidden name="alertModel.alertId" value="%{alertModel.alertId}"/>
	<s:hidden name="alertModel.contactType" value="EMAIL"/>
	<tr>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Stt</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">T&#234;n</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">&#272;&#7883;a ch&#7881; email</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">X&#243;a</span></td>
	<s:iterator status="status" value="%{alertModel.listEmailContact}">
		<s:if test="%{#status.index%2==1}">
				<tr onmouseout="td_onmouseout(this,color_out1)"
				onmouseover="td_onmouseover(this,color_over1)" class="Table_TR">
		</s:if>
		<s:else>
			<tr bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
		</s:else>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
						<s:property value="#status.index+1" />
					</span>

				</td>
				<td class="border_frame_right_bottom" align="left">
					<span	class="style19">
						<s:property value="contactName"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="contactAddress"/>
					</span>

				</td>
				<td align="center">
					<a href="<%=basePath%>airtime_alert_remove_contact.mbv?alertModel.alertId=<s:property value='%{alertModel.alertId}'/>&alertModel.contactType=EMAIL&alertModel.contactAddress=<s:property value='contactAddress'/>">X&#243;a</a>
				</td>
			</tr>
	</s:iterator>
			<tr>
				<td>
					
				</td>
				<td>
					<s:if test="%{alertModel.contactType=='EMAIL'}"> 
						<s:textfield name="alertModel.contactName" value="%{alertModel.contactName}" maxlength="100" size="40"/>
					</s:if>
					<s:else>
						<s:textfield name="alertModel.contactName" value="" maxlength="100" size="40"/>
					</s:else>
				</td>
				<td>
					<s:if test="%{alertModel.contactType=='EMAIL'}"> 
						<s:textfield name="alertModel.contactAddress" value="%{alertModel.contactAddress}" maxlength="100" size="40"/>
					</s:if>
					<s:else>
						<s:textfield name="alertModel.contactAddress" value="" maxlength="100" size="40"/>
					</s:else>
				</td>
				<td align="center"><s:submit key="button.addNew"/></td>
			</tr>
</table>
	</s:form>	
</s:if>
</div>

</body>
</html>