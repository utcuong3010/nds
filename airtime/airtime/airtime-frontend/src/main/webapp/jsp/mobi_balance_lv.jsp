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
<SCRIPT language="JavaScript" src="<%=basePath%>/js/menu.js" type="text/javascript"></SCRIPT>
<SCRIPT language="JavaScript" src="<%=basePath%>/js/jquery-1.6.js" type="text/javascript"></SCRIPT>
<script src="<%=basePath%>/js/jscal2.js"></script>
<script src="<%=basePath%>/js/lang/en.js"></script>
<script src="<%=basePath%>/js/jquery.number_format.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/jscal2.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/border-radius.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/steel/steel.css" />
<link type='text/css' href='<%=basePath%>css/basic.css' rel='stylesheet' media='screen' />
<script type='text/javascript' src='js/jquery.simplemodal.js'></script>
</head>
<body>
<div id="basic-modal-content">
</div>
<div align="center">
	<div style="height:40px;"><span><b>Truy v&#7845;n t&#224;i kho&#7843;n Mobifone</b></span></div>
	<s:form action="/mobi_balance_lv.mbv" id="mobi_balance_lv">
		<table width="50%" class=tinyborder border="0">
			<tr align="left">
				<td>
					T&#7915; ng&#224;y
				</td>
				<td>
					<s:textfield name="mobifoneModel.fromDate" value="%{mobifoneModel.fromDate}" maxlength="10" size="10" id="t_fromdate" readonly="true"/>
					<a href="#"> <img src="<%=basePath%>images/calendar.gif" width="16" height="15" id="i_fromdate"></a>
					<s:fielderror cssClass="Error" fieldName="fromDate" />
				</td>
				<td>
					&#272;&#7871;n ng&#224;y
				</td>
				<td>
					<s:textfield name="mobifoneModel.toDate" value="%{mobifoneModel.toDate}"  maxlength="10" size="10" id="t_todate" readonly="true"/>
					<a href="#"> <img src="<%=basePath%>images/calendar.gif" width="16" height="15" id="i_todate"></a>
					<s:fielderror cssClass="Error" fieldName="toDate" />
				</td>
				
			</tr>
			<tr>
				<td align="left" colspan="2">
					<INPUT type="button" value="Cap nhat so du" id="updateBalance">
				</td>
				
				<td align="left" colspan="2">
					 <INPUT type="submit" value="Tim Kiem">
				</td>
				
				<s:hidden name="mobifoneModel.page" id="mobifoneModel.page" value="1"/> 
			</tr>
		</table>
	</s:form>
<table width="70%">
	<tr>
		<td width="50%" align="left"><span><b>K&#7871;t Qu&#7843;</b></span></td>
		<td width="50%" align="right"><span><b>T&#7893;ng s&#7889;: <s:property value="%{#request.pageView.totalItems}"/> </b></span></td>
	</tr>
</table>
<br>
<SPAN class="error"> <s:property value="%{#request.message}" /> </SPAN>

<TABLE class=tinyborder cellSpacing=0 cellPadding=0 width=70%
	bgColor=#f8f8f8 border=0>

	<tr>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Stt</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Ng&#224;y</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">M&#227; GD</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Pending</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Availabe</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Current</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Mobivi Amount</span></td>
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
						<s:property value="strDate"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
							<s:property value="txnId"/>
					</span>
				</td>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
						<s:property value="pending2"/></a>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="avail2"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="current2"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="mobiviBalance"/>
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
	
   var retry=0;
   var alertTimerId = 0;	
   $(document).ready(function(){  
 	    $('#updateBalance').click(function(){
 	        createInquiry("<%=basePath%>mobi_update_balance.mbv");
 	        return false;  
 	    });  
 	}); 
   function createInquiry(url){
	   $('#basic-modal-content').modal();
	   $('#basic-modal-content').html("&#272;ang truy v&#7845;n s&#7889; d&#432; tk Mobifone...<br><img src='<%=basePath%>images/waiting.gif'>");
		
 	  $.ajax({
	            url: url,
	            type: 'POST',
	            cache: false,
	            data:  $("#mobi_balance_lv").serialize(),
	            success: function(data){
	    		  try
	    		  {
	    			  var getData = $.parseJSON(data);
		        		if (getData.error=="SUCCESS"){
		        			retry=0;
		        			alertTimerId=setInterval("checkRequest('<%=basePath%>check_txn.mbv','"+getData.transaction_id+"')",5000);
		  	        	}else if(getData.error=="INVALID_INPUT"){
		  	        		$.modal.close();
			  	        	alert("D\u1eef li\u1ec7u nh\u1eadp v\u00e0o kh\u00f4ng ch\u00ednh x\u00e1c.");
		  	        	}else {
		  	        		$.modal.close();
			  	        	alert("He thong loi ");
			  	        }
	    		  }
	    		  catch(e)
	    		  {
	    			  $.modal.close();  
	    			  alert("C\u00f3 l\u1ed7i x\u1ea3y ra. H\u1ec7 th\u1ed1ng l\u1ed7i");
	    		  	
	    		  
	    		  } 
	            },
	            error: function (){
	            	 $.modal.close();  
	            	 alert('H\u1ec7 th\u1ed1ng l\u1ed7i');
	            }
		  });
   }	

   function checkRequest(url,transaction_id){
 	  $.ajax({
	            url: url,
	            type: 'POST',
	            cache: false,
	            data:  "mobifoneModel.txnId="+transaction_id+"",
	            success: function(data){
 		 		var getData = $.parseJSON(data);
	              try
	    		  {
	            	 $('#basic-modal-content').modal();
		    		 if(getData.error=="SUCCESS"){
			             	if (getData.txn_status=="PENDING"){
 	  	        				$('#basic-modal-content').html("&#272;ang x&#7917; l&#253; y&#234;u c&#7847;u...<br><img src='<%=basePath%>images/waiting.gif'/>");
 			             	}
			             	if (getData.txn_status=="SUCCESS"){
			             		clearInterval(alertTimerId);
			             		$.modal.close();
			             		alert("Cap nhat so du tai khoan tai Mobifone thanh cong!");
			             		var now = new Date();
			             		var curdate=DateFormat(now,"dd/mm/yyyy");	  
			             		$("#t_fromdate").val(curdate);
			             	   	$("#t_todate").val(curdate);
			             		$("#mobi_balance_lv").submit();
			             	}
			             	if (getData.txn_status=="ERROR"){
			             		clearInterval(alertTimerId);
			             		$.modal.close();
			             		alert("Khong the cap nhat so du tai khoan tai Mobifone, ErrorCode:"+getData.txn_errorcode);
			             	}   
  	  	     		}else{
  	  	     				clearInterval(alertTimerId);
  	  	     				$.modal.close();
  	  	     				alert("C\u00f3 l\u1ed7i x\u1ea3y ra.");
		  	  	     	}
	  	
	    		  }catch(e)
		    		  {
		    			  alert("C\u00f3 l\u1ed7i x\u1ea3y ra.");
		    			  $.modal.close();
		    			  clearInterval(alertTimerId);
		    		  }   
		    	 	
	            },
	            error: function (){
	              $.modal.close();
	              alert('H\u1ec7 th\u1ed1ng l\u1ed7i. ');
	            }
		  });
   }

   function search(input){
 	  var x=document.getElementById("mobifoneModel.page")
 	  x.value=input	
 	  document.forms[0].submit();
   }

   function DateFormat(formatDate, formatString) {
		if(formatDate instanceof Date) {
			var months = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
			var yyyy = formatDate.getFullYear();
			var yy = yyyy.toString().substring(2);
			var m = formatDate.getMonth()+1;
			var mm = m < 10 ? "0" + m : m;
			var mmm = months[m];
			var d = formatDate.getDate();
			var dd = d < 10 ? "0" + d : d;
			
			var h = formatDate.getHours();
			var hh = h < 10 ? "0" + h : h;
			var n = formatDate.getMinutes();
			var nn = n < 10 ? "0" + n : n;
			var s = formatDate.getSeconds();
			var ss = s < 10 ? "0" + s : s;

			formatString = formatString.replace(/yyyy/i, yyyy);
			formatString = formatString.replace(/yy/i, yy);
			formatString = formatString.replace(/mmm/i, mmm);
			formatString = formatString.replace(/mm/i, mm);
			formatString = formatString.replace(/m/i, m);
			formatString = formatString.replace(/dd/i, dd);
			formatString = formatString.replace(/d/i, d);
			formatString = formatString.replace(/hh/i, hh);
			formatString = formatString.replace(/h/i, h);
			formatString = formatString.replace(/nn/i, nn);
			formatString = formatString.replace(/n/i, n);
			formatString = formatString.replace(/ss/i, ss);
			formatString = formatString.replace(/s/i, s);

			return formatString;
		} else {
			return "";
		}
	} 
 //report end        
    //]]></script>
</body>
</html>