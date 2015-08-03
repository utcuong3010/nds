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
<div style="height:40px;"><span><b>Th&#244;ng tin t&#224;i kho&#7843;n t&#7893;ng
</b></span></div>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="70%">
	<tr>
		<td width="70%">

	<s:form action="/provider_account_summary_lv.mbv" id="search_provider_account_summary_lv">
		<table width="100%" class=tinyborder>
			<tr>
				<td>
					 T&#224;i kho&#7843;n t&#7893;ng
				</td>
				<td>
					<s:select name="proAcc.providerId"
							list="%{#request.providerAccountMap}" value="%{proAcc.providerId}" />
							<s:fielderror cssClass="Error" fieldName="providerId" />
				</td>
				
			</tr>
			<tr>
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
					&#272;&#7871;n ng&#224;y
				</td>
				<td>
	
					<s:textfield name="proAcc.toDate" value="%{proAcc.toDate}"  maxlength="10" size="10" id="t_todate" readonly="true"/>
					<a href="#"> <img src="<%=basePath%>images/calendar.gif" width="16" height="15" id="i_todate"></a>
					<s:fielderror cssClass="Error" fieldName="toDate" />
				</td>
			</tr>
			<s:hidden name="proAcc.page" id="proAcc.page" value="1"/> 
			<tr>
				
				
				<td>
					 <INPUT type="submit" value="Tim Kiem">
				</td>
				
				<td align="left">
					 <INPUT type="button" id="exportExcelBtn" value="Xuat Excel">
				</td>
			
			</tr>
		</table>
	</s:form>
		</td>
		<td valign="top" >
	<TABLE class=tinyborder cellSpacing=0 cellPadding=0 width=100% 	bgColor=#f8f8f8 border=0 height="100%">
	
		<tr>
			<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Stt</span></td>
			<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">TK T&#7893;ng</span></td>
			<!--td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Ng&#224;y GD cu&#7889;i</span></td-->
			<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">S&#7889; d&#432;</span></td>
		</tr>
	<s:iterator status="stat" value="%{#request.listAmountModel}">
		<tr>
			<td class="border_frame_right_bottom" align="center">
						<span	class="style19">
							<s:property value="#stat.index+1" />
						</span>
			</td>
			<td class="border_frame_right_bottom" align="center">
				<span	class="style19">
					<s:property value="providerId"/>
				</span>
	
			</td>		
			<!--td class="border_frame_right_bottom" align="center">
						<span	class="style19">
							<s:property value="txnDate"/>
						</span>
	
			</td-->
			<td class="border_frame_right_bottom" align="right">
						<span	class="style19">
							<s:property value="endAmount"/>
						</span>
	
			</td>
		</tr>	
	</s:iterator>
		
	</TABLE>	
		</td>
	</tr>
</table>	
<table width="70%">
	<tr>
		<td width="50%" align="left"><span><b>K&#7871;t Qu&#7843;</b></span></td>
		<td width="50%" align="right"><span><b>T&#7893;ng s&#7889;: <s:property value="%{#request.pageView.totalItems}"/> giao d&#7883;ch</b></span></td>
	</tr>
</table>
<br>


<SPAN class="error"> <s:property value="%{#request.message}" /> </SPAN>

<TABLE class=tinyborder cellSpacing=0 cellPadding=0 width=70%
	bgColor=#f8f8f8 border=0>

	<tr>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Stt</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Ng&#224;y</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">TK T&#7893;ng</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">S&#7889; d&#432; &#273;&#7847;u</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">S&#7889; d&#432; t&#259;ng</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">S&#7889; d&#432; gi&#7843;m</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">S&#7889; d&#432; cu&#7889;i</span></td>
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
						<s:property value="providerId"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
						<s:property value="beginAmount"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
					<s:if test="%{totalAmount==0}">
						-
					</s:if>
					<s:else>
						+<s:property value="totalAmount"/>
					</s:else>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
					<s:if test="%{usedAmount==0}">
						-
					</s:if>
					<s:else>
						-<s:property value="usedAmount"/>
					</s:else>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="endAmount"/>
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
      	window.location="<%=basePath%>provider_account_load_money_lv.mbv";
      }
	  
      function search(input){
    	  var x=document.getElementById("proAcc.page")
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

    //report start      
	   function download(){
			$.modal.close();
	   }
	
	
     var retry=0;	
     $(document).ready(function(){  
   	    $('#exportExcelBtn').click(function(){ 
   	    	createReport("<%=basePath%>provider_account_summary_rpt.mbv");
   	        return false;  
   	    });  
   	}); 
	  
	  
     function createReport(url){
    	  $('#basic-modal-content').modal();
		  $('#basic-modal-content').html("&#272;ang t&#7841;o b&#225;o c&#225;o...<br><img src='<%=basePath%>images/waiting.gif'>");
   	  $.ajax({
	            url: url,
	            type: 'POST',
	            cache: false,
	            data:  $("#search_provider_account_summary_lv").serialize(),
	            success: function(data){
	    		  try
	    		  {
	    			  var getData = $.parseJSON(data);
	        		  if (getData.error=="SUCCESS"){
	        			retry=0;
	        			checkRequest("<%=basePath%>check_rpt.mbv",getData.reportId);
	  	        	  }if(getData.error=="INVALID_INPUT"){
	  	        		$.modal.close();  
		  	        	alert("D\u1eef li\u1ec7u nh\u1eadp v\u00e0o kh\u00f4ng ch\u00ednh x\u00e1c. Kh\u00f4ng th\u1ec3 t\u1ea1o b\u00e1o c\u00e1o");
		  	        	
	  	        	  }
		  	        	
	    		  }
	    		  catch(e)
	    		  {
	    			$.modal.close();  
	    			alert("C\u00f3 l\u1ed7i x\u1ea3y ra. Kh\u00f4ng th\u1ec3 th\u1ef1c hi\u1ec7n b\u00e1o b\u00e1o");
	    		  	
	    		  } 
	            },
	            error: function (){
	            	$.modal.close();
	            	alert('H\u1ec7 th\u1ed1ng l\u1ed7i. Kh\u00f4ng th\u1ec3 t\u1ea1o b\u00e1o c\u00e1o');
	            	
	            }
		  });
     }	
    
     function checkRequest(url,reportId){
   	  $.ajax({
 	            url: url,
 	            type: 'POST',
 	            cache: false,
 	            data:  "reportId="+reportId+"&fileName=<%=FeConstant.REPORT_PROVIDER_ACCOUNT_SUMMARY_FILENAME%>",
 	            success: function(data){
	            
 	            var getData = $.parseJSON(data);
	  	          try
	    		  {
	  	             if (getData.error=="DONE"){
	  	            		$('#basic-modal-content').html("B\u1ea5m v\u00e0o \u0111\u00e2y \u0111\u1ec3 l\u1ea5y b\u00e1o c\u00e1o <a href='<%=basePath+FeConstant.REPORT_TEMP_PATH%>/"+getData.fileName+"' id='downloadReport' onclick='javascript:download();'>"+getData.fileName+"</a>");
	  	  	         }else{ 
			  	  	         if(getData.error=="IN_PROGRESS"){
		  	  	        		retry=retry+1;
		  	  	        		$('#basic-modal-content').html("B\u00e1o c\u00e1o \u0111ang \u0111\u01b0\u1ee3c x\u1eed l\u00fd..."+retry+"<br><img src='<%=basePath%>images/waiting.gif'/>");
		  	  	        	    window.setTimeout("checkRequest('"+url+"',"+reportId+")", 5000);  
			  	  	     		}
			  	  	     	else{
			  	  	     		$.modal.close();
			  	  	     		alert("C\u00f3 l\u1ed7i x\u1ea3y ra. Kh\u00f4ng th\u1ec3 th\u1ef1c hi\u1ec7n b\u00e1o b\u00e1o");
				  	  	     	}
	  	  	         }  	 
	    		  }catch(e)
		    		  {
	    			  $.modal.close();
	    			  alert("C\u00f3 l\u1ed7i x\u1ea3y ra. Kh\u00f4ng th\u1ec3 th\u1ef1c hi\u1ec7n b\u00e1o b\u00e1o");
		    		  }   
		    	 	
 	            },
 	            error: function (){
 	            	$.modal.close();
 	                alert('H\u1ec7 th\u1ed1ng l\u1ed7i. Kh\u00f4ng th\u1ec3 t\u1ea1o b\u00e1o c\u00e1o');
 	              
 	            }
		  });
     }
   //report end      
    //]]></script>
</body>
</html>