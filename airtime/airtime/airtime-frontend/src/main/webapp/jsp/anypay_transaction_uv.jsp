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
<div style="height:40px;"><span><b>Ch&#7881;nh s&#7917;a tr&#7841;ng th&#225;i giao d&#7883;ch AnyPay
</b></span></div>
<s:form action="/anypay_transaction_u.mbv">
	<table width="70%" class=tinyborder border="0">
		<tr align="left">
			<td colspan="2" align="left">
				 <span><b>Thay &#273;&#7893;i tr&#7841;ng th&#225;i c&#7911;a giao d&#7883;ch<b></span>
				 
			</td>
	</tr>
	
	<tr>
		<td colspan="2">
			M&#227; giao d&#7883;ch :<b> <s:property value="%{txnModel.txnId}"/> </b>
			<s:hidden name="txnModel.txnId" value="%{txnModel.txnId}"/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			Tr&#7841;ng th&#225;i giao d&#7883;ch hi&#7879;n t&#7841;i :<b>
			<s:if test="%{txnModel.txnStatus=='SUCCESS'}">
				Th&#224;nh c&#244;ng
			</s:if>
			<s:if test="%{txnModel.txnStatus=='ERROR'}">
				Th&#7845;t b&#7841;i
			</s:if>
			<s:if test="%{txnModel.txnStatus=='PENDING'}">
				Ch&#7901; x&#7917; l&#253;
			</s:if>
			<s:if test="%{txnModel.txnStatus=='DELIVERING'}">
				&#272;ang x&#7917; l&#253;
			</s:if>
			</b>
		</td>
	</tr>
	<tr>
		<td colspan="1">
			Tr&#7841;ng th&#225;i giao d&#7883;ch : 
			<s:select name="txnModel.txnStatus" list="%{#request.statusMap}" value="%{txnModel.txnStatus}" /> 
			<input type="submit" value="Change"></input>
		</td>
		<td colspan="1">
			 <input type="button" value="Cancel" onclick="javascript:backToList();" />
		</td>			
	</tr>

	</table>
</s:form>	
		
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
      	window.location="<%=basePath%>anypay_transaction_lv.mbv";
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