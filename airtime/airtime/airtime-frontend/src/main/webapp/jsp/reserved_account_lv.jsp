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
<div style="height:40px;"><span><b>Truy v&#7845;n t&#224;i kho&#7843;n kh&#243;a ti&#7873;n
</b></span></div>
<s:form action="/reserved_account_lv.mbv" id="search_reserved_account_lv">
	<table width="70%" class=tinyborder>
		<tr>
			<td>
				 M&#227; t&#224;i kho&#7843;n
			</td>
			<td>
				<s:textfield name="accountModel.accountId"
						cssStyle="Table_TR" maxlength="100" size="30" value="%{accountModel.accountId}"/>
			</td>
			
			<td>
				 H&#7879; th&#7889;ng
			</td>
			<td>
				<s:textfield name="accountModel.systemId"
						cssStyle="Table_TR" maxlength="100" size="30" value="%{accountModel.systemId}"/>
			</td>
			
		</tr>
		<tr>
			<td>
				T&#7915; ng&#224;y
			</td>
			<td>
				<s:textfield name="accountModel.fromDate" value="%{accountModel.fromDate}" maxlength="10" size="10" id="t_fromdate" readonly="true"/>
				<a href="#"> <img src="<%=basePath%>images/calendar.gif" width="16" height="15" id="i_fromdate"></a>
				<s:fielderror cssClass="Error" fieldName="fromDate" />
			</td>
			
			<td>
				&#272;&#7871;n ng&#224;y
			</td>
			<td>

				<s:textfield name="accountModel.toDate" value="%{accountModel.toDate}"  maxlength="10" size="10" id="t_todate" readonly="true"/>
				<a href="#"> <img src="<%=basePath%>images/calendar.gif" width="16" height="15" id="i_todate"></a>
				<s:fielderror cssClass="Error" fieldName="toDate" />
			</td>
		</tr>
		<s:hidden name="accountModel.page" id="accountModel.page" value="1"/> 
		<tr>
			<td colspan="4" align="right">
				 <INPUT type="submit" value="Tim Kiem">
			</td>
		</tr>
	</table>
</s:form>

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
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Ng&#224;y t&#7841;o</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">M&#227; t&#224;i kho&#7843;n</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">H&#7879; th&#7889;ng</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">S&#7889; d&#432;</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Telcos</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Di&#7877;n gi&#7843;i</span></td>
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
						<s:property value="createdDate"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
						<s:property value="accountId"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="systemId"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
							<s:property value="amount"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="telcoIds"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="description"/>
					
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
    	  var x=document.getElementById("accountModel.page")
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
  	    	createReport("<%=basePath%>provider_txn_summary_rpt.mbv");
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
	            data:  $("#search_reserved_account_lv").serialize(),
	            success: function(data){
	    		  try
	    		  {
		    			var getData = $.parseJSON(data);
		        		if (getData.error=="SUCCESS"){
		        			retry=0;
		        			checkRequest("<%=basePath%>check_rpt.mbv",getData.reportId);
		  	        	}if(getData.error=="INVALID_INPUT"){
			  	        	alert("D\u1eef li\u1ec7u nh\u1eadp v\u00e0o kh\u00f4ng ch\u00ednh x\u00e1c. Kh\u00f4ng th\u1ec3 t\u1ea1o b\u00e1o c\u00e1o");
			  	        	$.modal.close();
		  	        	}
	    		  }
	    		  catch(e)
	    		  {
	    			alert("C\u00f3 l\u1ed7i x\u1ea3y ra. Kh\u00f4ng th\u1ec3 th\u1ef1c hi\u1ec7n b\u00e1o b\u00e1o");
	    		  	$.modal.close();
	    		  
	    		  } 
	            },
	            error: function (){
	            	alert('H\u1ec7 th\u1ed1ng l\u1ed7i. Kh\u00f4ng th\u1ec3 t\u1ea1o b\u00e1o c\u00e1o');
	            	$.modal.close();
	            }
		  });
    }	
   
    function checkRequest(url,reportId){
  	  $.ajax({
	            url: url,
	            type: 'POST',
	            cache: false,
	            data:  "reportId="+reportId+"&fileName=<%=FeConstant.REPORT_PROVIDER_TXN_SUMMARY_FILENAME%>",
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
	                alert('H\u1ec7 th\u1ed1ng l\u1ed7i. Kh\u00f4ng th\u1ec3 t\u1ea1o b\u00e1o c\u00e1o');
	              $.modal.close();
	            }
		  });
    }
  //report end      
   //]]></script>
</body>
</html>