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
<div style="height:40px;"><span><b>Chuy&#7875;n ti&#7873;n t&#7915; Sim Anypay t&#7899;i Sim Anypay
</b></span></div>
<br>

<table class=tinyborder cellSpacing=0 cellPadding=0 width=360
	bgColor=#f8f8f8 border=0>
	<TBODY>

		<SPAN class="error">
			<s:fielderror cssClass="Error" fieldName="sys_message" />

		</SPAN>
		<s:form action="/anypay_transfer_s.mbv">
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD>From Sim No.</TD>
				<TD><s:select name="txnModel.simId"
					list="%{#request.simMap}" value="%{txnModel.simId}" />
					<s:fielderror cssClass="Error" fieldName="simId" />
					</TD>
			</TR>
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD>To Sim No.</TD>
				<TD><s:select name="txnModel.msisdn"
					list="%{#request.simMap}" value="%{txnModel.msisdn}" />
					<s:fielderror cssClass="Error" fieldName="msisdn" />
					</TD>
			</TR>
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD>S&#7889; ti&#7873;n</TD>
				<TD><s:textfield name="txnModel.amount"
					cssStyle="Table_TR" maxlength="40" size="30" value="%{txnModel.amount}"
					onblur="formatTextInputNumber(this);"
					/>
						<s:fielderror cssClass="Error" fieldName="amount" />
					</TD>
			</TR>
			
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR" align=center >
				<TD colspan=2 align="center">
					<input type="submit" value="L&#432;u"></input>
					<input type="button" value="H&#7911;y b&#7887;" onclick="backToList()"></input>
			</TR>
			
		</s:form>

	</TBODY>
</TABLE>
</div>
<script type="text/javascript">//<![CDATA[
      Calendar.setup({
        inputField : "t_ngaynap",
        trigger    : "i_ngaynap",
        onSelect   : function() { this.hide() },
        showTime   : 24,
        dateFormat : "%d/%m/%Y %H:%M"
      });

      function formatTextInputNumber(input, options) {
                if ($==null || $.number_format==null) return;
                var locale = "vi";
                var MONEY_AMOUNT_OPTIONS = {
                        allow_negative:true,
                        precision:0,
                        decimal:locale=="en"?'.':',',
                        thousands:locale=="en"?',':'.',
                        defaultValue:'0'
                };

                input.value = $.number_format(input.value, options || MONEY_AMOUNT_OPTIONS);
            }
      function backToList(){
      	window.location="<%=basePath%>anypay_transaction_lv.mbv";
      }
    //]]></script>


</body>
</html>
<script type="text/javascript">
    function saveClick(){
    	$(document).ready(function() {
        $cityName = document.getElementById("cityName").value;
            $.post("http://localhost:8080/airtime-frontend/msisdn_prefix_uv.mbv?providerId=VTEL&shortCode=0167", function(data) {
                alert(data);
         });
    	});
    }
</script>

