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
<div style="height:40px;">
<span><b>Thi&#7871;t l&#7853;p s&#7889; d&#432; c&#7843;nh b&#225;o<b></span>
</div>
<SPAN class="error">
	<s:fielderror />
</SPAN>
	<table width="70%" class=tinyborder border="0">
		
	<s:iterator status="status" value="%{#request.listProviderMinAmount}">

	<s:form action="/airtime_alert_provider_min_amount_uv.mbv">
	<tr>
		<td colspan="1" align="left">
			<b>T&#224;i kho&#7843;n t&#7893;ng: <s:property value="type"/></b>
		</td>
	</tr>
	<tr>		
		<td align="left">
			<b>S&#7889; d&#432; c&#7843;nh b&#225;o hi&#7879;n t&#7841;i: <s:property value="paramValue"/></b>
		</td>
	</tr>
	<tr>		
		<td align="left">
			S&#7889; d&#432; c&#7843;nh b&#225;o m&#7899;i:
			<s:if test="%{providerMinAmount.paramValue==paramValue}"> 
				<s:textfield name="providerMinAmount.paramValue" value="%{providerMinAmount.paramValue}"/>
			</s:if>
			<s:else>
				<s:textfield name="providerMinAmount.paramValue" value=""/>
			</s:else>
			<s:hidden name="providerMinAmount.type" value="%{type}"/>
			<s:hidden name="providerMinAmount.module" value="%{module}"/>
			<s:hidden name="providerMinAmount.paramKey" value="%{paramKey}"/>
			<s:submit key="button.submit.select"/>
		</td>
	</tr>
	</s:form>
</s:iterator>	
	</table>

		
</div>
<script type="text/javascript">//<![CDATA[
      Calendar.setup({
        inputField : "t_fromdate",
        trigger    : "i_fromdate",
        onSelect   : function() { this.hide() },
        showTime   : 0,
        dateFormat : "%d/%m/%Y"
      });

 	Calendar.setup({
        inputField : "t_todate",
        trigger    : "i_todate",
        onSelect   : function() { this.hide() },
        showTime   : 0,
        dateFormat : "%d/%m/%Y"
      });

      function formatTextInputNumber(input, options) {
                if ($==null || $.number_format==null) return;
                var locale = "vi";
                var MONEY_AMOUNT_OPTIONS = {
                        allow_negative:false,
                        precision:0,
                        decimal:locale=="en"?'.':',',
                        thousands:locale=="en"?',':'.',
                        defaultValue:'0'
                };

                input.value = $.number_format(input.value, options || MONEY_AMOUNT_OPTIONS);
            }
      function backToList(){
      	window.location="<%=basePath%>provider_account_load_money_lv.mbv";
      }
	  
      function search(input){
    	  var x=document.getElementById("txnModel.page")
    	  x.value=input	
    	  document.forms[0].submit();
      }

      function confirmDelete(input){
    	 var agree=confirm("<B>hello<B>");
    	  if (agree){
    		  search('1');
    	  }else{
    	 	return false ;
    	  }
    	  return false;
    	
      }
    //]]></script>
</body>
</html>