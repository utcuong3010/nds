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
<div style="height:40px;"><span><b>Truy v&#7845;n &#273;&#7889;i so&#225;t giao d&#7883;ch
</b></span></div>
<s:form action="/comparison_cdr_lv.mbv" id="search_comparison_cdr_lv">
	<table width="70%" class=tinyborder border="0">
		<tr align="left">
			<td>
				 Nh&#224; cung c&#7845;p
			</td>
			<td width="30%">
				<s:select name="cdrDataModel.providerId"
						list="%{#request.providerAccountMap}" value="%{cdrDataModel.providerId}" disabled="false"/>
						<s:fielderror cssClass="Error" fieldName="providerId" />
			</td>
			<td>
				M&#227; GD (K&#7871;t n&#7889;i)
			</td>
			<td>

				<s:textfield name="cdrDataModel.ptxnId"
						cssStyle="Table_TR" maxlength="100" size="30" value="%{cdrDataModel.ptxnId}"/>
			</td>
			
		</tr>
		
		
		<tr>
			<td>
				 M&#227; GD (Mobivi)
			</td>
			<td>
				<s:textfield name="cdrDataModel.atTxnId"
						cssStyle="Table_TR" maxlength="100" size="30" value="%{cdrDataModel.atTxnId}"/>

			</td>
			
			<td>
				 S&#272;T &#273;&#432;&#7907;c n&#7841;p
			</td>
			<td>
				<s:textfield name="cdrDataModel.msisdn"
						cssStyle="Table_TR" maxlength="100" size="14" value="%{cdrDataModel.msisdn}"/>

			</td>
		
		<tr>
			
			<td>
				K&#7871;t qu&#7843; &#273;&#7889;i so&#225;t
			</td>
			<td>

				<s:select name="cdrDataModel.result"
						list="%{#request.resultMap}" value="%{cdrDataModel.result}" />
			</td>
			
			
			<td>
				T&#7915; ng&#224;y
			</td>
			<td>
				<s:textfield name="cdrDataModel.fromDate" value="%{cdrDataModel.fromDate}" maxlength="10" size="10" id="t_fromdate" readonly="true"/>
				<a href="#"> <img src="<%=basePath%>images/calendar.gif" width="16" height="15" id="i_fromdate"></a>
				<s:fielderror cssClass="Error" fieldName="fromDate" />
			</td>
		</tr>
		
		<tr>
			<td>
				
			</td>
			<td>
				
			</td>
			
			<td>
				&#272;&#7871;n ng&#224;y
			</td>
			<td>
				<s:textfield name="cdrDataModel.toDate" value="%{cdrDataModel.toDate}"  maxlength="10" size="10" id="t_todate" readonly="true"/>
				<a href="#"> <img src="<%=basePath%>images/calendar.gif" width="16" height="15" id="i_todate"></a>
				<s:fielderror cssClass="Error" fieldName="toDate" />
			</td>
			
		</tr>
		<tr>
			
			<td>
				
			</td>
			<td>
				
			</td>
			
		</tr>
		<tr>
			
			<td colspan="2" align="center">
				<INPUT type="submit" value="Tim Kiem">
			</td>
			
			<td colspan="2" align="center">
				 <INPUT type="button" id="exportCdr" value="Xuat File">
				  
			</td>
		</tr>
		<s:hidden name="cdrDataModel.page" id="cdrDataModel.page" value="1"/> 
		
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

<TABLE class=tinyborder cellSpacing=0 cellPadding=0 width=85%
	bgColor=#f8f8f8 border=0>

	<tr>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Stt</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Ng&#224;y</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">M&#227; GD (Mobivi)</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">S&#272;T &#273;&#432;&#7907;c n&#7841;p</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">M&#7879;nh gi&#225;</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Th&#224;nh ti&#7873;n</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">Nh&#224; cung c&#7845;p</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">MobiVi ErrorCode</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">MobiVi Tr&#7841;ng th&#225;i</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style20">M&#227; GD (Provider)</span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style20">Provider ErrorCode </span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style20">Provider Tr&#7841;ng th&#225;i </span></td>
		<td class="tinyborder_td" bgcolor ="#3991C3" align="center"><span class="style18">KQ &#272;&#7889;i xo&#225;t</span></td>
	</tr>
	<s:iterator status="stats" value="%{#request.pageView.items}">
		<s:if test="%{#stats.index%2==1}">
				<tr onmouseout="td_onmouseout(this,color_out1)"
				onmouseover="td_onmouseover(this,color_over1)" class="Table_TR">
		</s:if>
		<s:else>
			<tr bgcolor="#E3EFFF" onmouseout="td_onmouseout(this,color_out2)"
				onmouseover="td_onmouseover(this,color_over2)" class="Table_TR">
		</s:else>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
						<s:property value="#stats.index+1 +(#request.pageView.currentPage-1)*#request.pageView.pageSize" />
					</span>

				</td>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
						<s:property value="txnDate"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
							<s:property value="atTxnId"/>
					</span>
				</td>
				
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="msisdn"/>
					</span>
				</td>
				
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="amount"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="price"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
						<s:property value="providerId"/>
					</span>

				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="errorCode"/>
					</span>
				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style19">
						<s:property value="status"/>
					</span>
				</td>
				<td class="border_frame_right_bottom" align="right">
					<span	class="style21">
						<s:property value="ptxnId"/>
					</span>
				</td>
				
				<td class="border_frame_right_bottom" align="right">
					<span	class="style21">
						<s:property value="perrorCode"/>
					</span>

				</td>		
				<td class="border_frame_right_bottom" align="right">
					<span	class="style21">
						<s:property value="pstatus"/>
					</span>

				</td>		
				<td class="border_frame_right_bottom" align="center">
					<span	class="style19">
						<s:property value="result"/>
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
    	  var x=document.getElementById("cdrDataModel.page")
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
 	    $('#exportCdr').click(function(){ 
 	    	createFile("<%=basePath%>export_cdr_comparison.mbv");
 	        return false;  
 	    });  
 	}); 
	  
	  
   function createFile(url){
	   $('#basic-modal-content').modal();
	   $('#basic-modal-content').html("&#272;ang t&#7841;o file...<br><img src='<%=basePath%>images/waiting.gif'>");
		
 	  $.ajax({
	            url: url,
	            type: 'POST',
	            cache: false,
	            data:  $("#search_comparison_cdr_lv").serialize(),
	            success: function(data){
	    		  try
	    		  {
	    			  var getData = $.parseJSON(data);
		        		if (getData.error=="SUCCESS"){
		        			retry=0;
		        			$('#basic-modal-content').html("<a href='<%=basePath+FeConstant.CDR_TEMP_PATH%>/"+getData.fileName+"' onclick='javascript:download();'>"+getData.fileName+"</a>");
		  	        	}else if(getData.error=="INVALID_INPUT"){
		  	        		$.modal.close();
			  	        	alert("D\u1eef li\u1ec7u nh\u1eadp v\u00e0o kh\u00f4ng ch\u00ednh x\u00e1c. Kh\u00f4ng th\u1ec3 t\u1ea1o b\u00e1o c\u00e1o");
			  	        	
		  	        	}else if(getData.error=="ERROR"){
		  	        		$.modal.close();
			  	        	alert("Khong the tao file. ErrMsg - " +getData.errorMsg);
			  	        	
		  	        	}
	    		  }
	    		  catch(e)
	    		  {
	    			  $.modal.close();  
	    			  alert("H\u1ec7 th\u1ed1ng l\u1ed7i..");
	    		  	
	    		  
	    		  } 
	            },
	            error: function (){
	            	 $.modal.close();  
	            	 alert('H\u1ec7 th\u1ed1ng l\u1ed7i.');
	            }
		  });
   }	
 //report start      
   function download(){
		$.modal.close();
   }
    //]]></script>
</body>
</html>