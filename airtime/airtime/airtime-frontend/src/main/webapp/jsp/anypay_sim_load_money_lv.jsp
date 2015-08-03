<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>

<%@page import="com.mbv.frontend.model.PageView"%>

<%@page import="com.mbv.frontend.constant.FeConstant"%><html>
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
<!-- Page styles -->
<!--link type='text/css' href='<%=basePath%>css/demo.css' rel='stylesheet' media='screen' /-->
<!-- Contact Form CSS files -->

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
</head>
<body>
<div id="basic-modal-content">
</div>
<div align="center" id="body_content">

<div style="height:40px;"><span><b>Danh s&#225;ch n&#7841;p ti&#7873;n Sim Anypay
</b></span></div>
	
<table align="center" border="0" cellpadding="0" cellspacing="0" width="80%">
	<tr>
		<td width="70%">
<s:form action="/anypay_sim_load_money_lv.mbv" id="search_anypay_load_money_form">
	<table width="100%" class=tinyborder>
		<tr >
			<td>
				 Sim No.
			</td>
			<td>
				<s:select name="proAcc.simId"
						list="%{#request.simMap}" value="%{proAcc.simId}" />
						<s:fielderror cssClass="Error" fieldName="simId" />
			</td>
			<td>
				T&#7915; ng&#224;y
			</td>
			<td>
				<s:textfield name="proAcc.fromDate" value="%{proAcc.fromDate}" maxlength="10" size="10" id="t_fromdate" readonly="true"/>
				<a href="#"> <img src="<%=basePath%>images/calendar.gif" width="16" height="15" id="i_fromdate"></a>
				<s:fielderror cssClass="Error" fieldName="fromDate" />
			</td>
		</tr>
		<tr>
			<td>
				 M&#227; giao d&#7883;ch
			</td>
			<td>
				<s:textfield name="proAcc.txnId"
						cssStyle="Table_TR" maxlength="100" size="30" value="%{proAcc.txnId}"/>

			</td>
			<td>
				&#272;&#7871;n ng&#224;y
			</td>
			<td>

				<s:textfield name="proAcc.toDate" value="%{proAcc.toDate}"  maxlength="10" size="10" id="t_todate" readonly="true"/>
				<a href="#"> <img src="<%=basePath%>images/calendar.gif" width="16" height="15" id="i_todate"></a>
				<s:fielderror cssClass="Error" fieldName="toDate" />
			</td>
		</tr>
		<tr>
			<td>
				 Ng&#432;&#7901;i n&#7841;p
			</td>
			<td>
				<s:textfield name="proAcc.employee"
						cssStyle="Table_TR" maxlength="40" size="30"  value="%{proAcc.employee}"/>
			</td>
			<td>

			</td>
			<td>

			</td>
		</tr>
		<s:hidden name="proAcc.page" id="proAcc.page" value="1"/> 
		<tr>
			<td></td>
			<td>
				 <INPUT type="submit" value="Tim Kiem">
			</td>
			<td></td>
			<td>
				 
			</td>
		</tr>
		
	</table>
</s:form>
</td>
<td valign="top">
<TABLE class=tinyborder cellSpacing=0 cellPadding=0 width=100% 	bgColor=#f8f8f8 border=0 height="100%">
	
		<tr>
			<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">No.</span></td>
			<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Sim No.</span></td>
			<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">SimType</span></td>
			<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Com Port</span></td>
			<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Current Amount</span></td>
			<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Lock Amount</span></td>
			<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Status</span></td>
			<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Sent Sms</span></td>
		</tr>
	<s:iterator status="stat" value="%{#request.simInfoModels}">
		<tr>
			<td class="border_frame_right_bottom" align="center">
						<span	class="style19">
							<s:property value="#stat.index+1" />
						</span>
			</td>
			<td class="border_frame_right_bottom" align="center">
				<span	class="style19">
					<s:property value="msisdn"/>
				</span>
	
			</td>		
			
			<td class="border_frame_right_bottom" align="right">
						<span	class="style19">
							<s:property value="simType"/>
						</span>
	
			</td>
			<td class="border_frame_right_bottom" align="right">
						<span	class="style19">
							<s:property value="comId"/>
						</span>
	
			</td>
			<td class="border_frame_right_bottom" align="right">
						<span	class="style19">
							<s:property value="currentAmount"/>
						</span>
	
			</td>
			<td class="border_frame_right_bottom" align="right">
						<span	class="style19">
							<s:property value="lockAmount"/>
						</span>
	
			</td>
			<td class="border_frame_right_bottom" align="right">
						<span	class="style19">
							<s:property value="simStatus"/>
						</span>
	
			</td>
			<td class="border_frame_right_bottom" align="right">
						<span	class="style19">
							<s:property value="lastSentSms"/>
						</span>
	
			</td>
		</tr>	
	</s:iterator>
		
	</TABLE>
	</td>
	</tr>	
</table>	
<table width="80%">
	<tr>
		<td width="50%" align="left"><span><b>K&#7871;t Qu&#7843;</b></span></td>
		<td width="50%" align="right"><span><b>T&#7893;ng s&#7889;: <s:property value="%{#request.pageView.totalItems}"/> giao d&#7883;ch</b></span></td>
	</tr>
</table>
<br>
<SPAN class="error"> <s:property value="%{#request.message}" /> </SPAN>

<TABLE class=tinyborder cellSpacing=0 cellPadding=0 width=80%
	bgColor=#f8f8f8 border=0>

	<tr>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Stt</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Ng&#224;y</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Sim No.</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">M&#227; GD</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">S&#7889; ti&#7873;n</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Ng&#432;&#7901;i n&#7841;p</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Ghi ch&#250;</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">X&#243;a</span></td>
	</tr>
	<s:iterator status="status" value="%{#request.pageView.items}">
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
						<s:property value="#status.index+1 +(#request.pageView.currentPage-1)*#request.pageView.pageSize" />
					</span>

				</td>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
						<s:property value="txnDate"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
						<s:property value="simId"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
						<s:property value="txnId"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="amount"/>
					</span>

				</td>
				
				<td class="border_frame_right_bottom" align="left">
					<span	class="style19">
						<s:property value="employee"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="left">
					<span	class="style19">
						<s:property value="description"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
						<a href="#" onclick="javascript:confirmDelete('<s:property value='txnId'/>');">X&#243;a</a>
					</span>

				</td>
			</tr>
	</s:iterator>
</table>
<br>
<TABLE cellSpacing=0 cellPadding=0 width=70% border=0>
	<tr>
		<td align="center">
			<s:if test="%{#request.pageView.hasPre}">	
			
				<span><a href="javascript:search('<s:property value='%{#request.pageView.currentPage-1}'/>');" >Tr&#432;&#7899;c</a></span>
			</s:if>
			<s:else>
			</s:else>
			
			<s:iterator value="%{#request.pageView.pageRange}" status="stat">
				<s:if test="%{#request.pageView.pageRange[#stat.index]==#request.pageView.currentPage}">
					<s:property/>
				</s:if>
				<s:else>
					<span><a href="javascript:search('<s:property/>');" ><s:property/></a></span>
				</s:else>
				
			</s:iterator>
				
			<s:if test="%{#request.pageView.hasNext}">	
				<span><a href="javascript:search('<s:property value='%{#request.pageView.currentPage+1}'/>');" >Ti&#7871;p</a></span>
			</s:if>
			<s:else>
			
			</s:else>		
		</td>
	</tr>
</TABLE>	
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
      	window.location="<%=basePath%>anypay_sim_load_money_lv.mbv";
      }
	  
      function search(input){
    	  var x=document.getElementById("proAcc.page")
    	  x.value=input	
    	  document.forms[0].submit();
      }

      function confirmDelete(input){
    	 var agree=confirm("Ban muon xoa giao dich nap tien sim anypay co ma giao dich:"+input +" ?");
    	  if (agree){
    		  window.location="<%=basePath%>anypay_sim_load_money_d.mbv?proAcc.txnId="+input;
    	  }else{
    	 	return false ;
    	  }
    	  return false;
    	
      }
    //]]></script>
</body>
</html>