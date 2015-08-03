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
<div style="height:40px;"><span><b>N&#7841;p ti&#7873;n v&#224;o t&#224;i kho&#7843;n t&#7893;ng
</b></span></div>
<br>

<table class=tinyborder cellSpacing=0 cellPadding=0 width=360
	bgColor=#f8f8f8 border=0>
	<TBODY>

		<SPAN class="error">
			<s:fielderror cssClass="Error" fieldName="sys_message" />

		</SPAN>
		<s:form action="/provider_account_load_money_s.mbv">
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD>T&#224;i kho&#7843;n t&#7893;ng</TD>
				<TD><s:select name="proAcc.providerId"
					list="%{#request.providerAccountMap}" value="%{proAcc.providerId}" />
					<s:fielderror cssClass="Error" fieldName="providerId" />
					</TD>
			</TR>
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD>Gi&#225; v&#7889;n</TD>
				<TD><s:textfield name="proAcc.inputAmount"
					cssStyle="Table_TR" maxlength="40" size="30" value="%{proAcc.inputAmount}"
					onblur="formatTextInputNumber(this);"
					/>
						<s:fielderror cssClass="Error" fieldName="inputAmount" />
					</TD>
			</TR>
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD>Chi&#7871;t kh&#7845;u</TD>
				<TD><s:textfield name="proAcc.discount"
					cssStyle="Table_TR" maxlength="5" size="30" value="%{proAcc.discount}"
					/>(%)
					<s:fielderror cssClass="Error" fieldName="discount" />
					</TD>
			</TR>
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD>Th&#7921;c c&#243;(VN&#272;)</TD>
				<TD><s:textfield name="proAcc.totalAmount"
					cssStyle="Table_TR" maxlength="40" size="30" value="%{proAcc.totalAmount}"
					onblur="formatTextInputNumber(this);"
					/>
					<s:fielderror cssClass="Error" fieldName="totalAmount" />
					</TD>
			</TR>
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD>M&#227; giao d&#7883;ch</TD>
				<TD><s:textfield name="proAcc.txnId"
					cssStyle="Table_TR" maxlength="100" size="30" value="%{proAcc.txnId}"/>
					<s:fielderror cssClass="Error" fieldName="txnId" />
					</TD>
			</TR>
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD>Ng&#224;y n&#7841;p</TD>
				<TD><s:textfield name="proAcc.txnDate" id="t_ngaynap"
					cssStyle="Table_TR" maxlength="40" size="30" value="%{proAcc.txnDate}" readonly="true"/>
					<a href="#"> <img src="<%=basePath%>images/calendar.gif" width="16" height="15" id="i_ngaynap"></a>
						<s:fielderror cssClass="Error" fieldName="txnDate"  />
					</TD>
			</TR>
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
				<TD>Ng&#432;&#7901;i n&#7841;p</TD>
				<TD><s:textfield name="proAcc.employee"
					cssStyle="Table_TR" maxlength="40" size="30"  value="%{proAcc.employee}"/>
					<s:fielderror cssClass="Error" fieldName="employee"  />
					</TD>
			</TR>
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR" valign="top">
				<TD>Ghi ch&#250;</TD>
				<TD><s:textarea name="proAcc.description" cols="23" rows="5"
					cssStyle="Table_TR" maxlength="100" value="%{proAcc.description}"/>
					<s:fielderror cssClass="Error" fieldName="description"  />
					</TD>
			</TR>
			<TR bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR" align=center >
				<TD colspan=2 align="center">
					<input type="submit" value="L&#432;u"></input>
					<input type="button" value="H&#7911;y b&#7887;" onclick="backToList()"></input>
				<!-- INPUT type=image height=20
					hspace=8 width=82 src="<%=basePath%>images/capnhat1.gif"--></TD>

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
      	window.location="<%=basePath%>provider_account_load_money_lv.mbv";
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

