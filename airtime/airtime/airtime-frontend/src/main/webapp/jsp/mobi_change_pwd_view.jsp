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
	<s:form action="/mobi_change_pwd.mbv" id="mobi_change_pwd">
		<table width="70%" class=tinyborder border="0">
		
			<tr align="left">
				<td>
					M&#7853;t kh&#7849;u m&#7899;i
				</td>
				<td>
					<s:textfield name="mobifoneModel.password" value="%{mobifoneModel.password}" maxlength="50" size="20"/>
				</td>
				<td>
					 <INPUT type="button" value="Doi Mat Khau" id="changePwdButton">
				</td>
				
				
			</tr>
		</table>
	</s:form>
</div>
<script type="text/javascript">//<![CDATA[
  	
   var retry=0;	
   $(document).ready(function(){  
 	    $('#changePwdButton').click(function(){
 	    	createInquiry("<%=basePath%>mobi_change_pwd.mbv");
 	        return false;  
 	    });  
 	}); 
	  
   
   function createInquiry(url){
	   $('#basic-modal-content').modal();
	   $('#basic-modal-content').html("Dang x&#7917; l&#253; &#273;&#7893;i m&#7853;t kh&#7843;u Mobifone...<br><img src='<%=basePath%>images/waiting.gif'>");
		
 	  $.ajax({
	            url: url,
	            type: 'POST',
	            cache: false,
	            data:  $("#mobi_change_pwd").serialize(),
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
 	  	        				$('#basic-modal-content').html("Dang x&#7917; l&#253; y&#234;u c&#7847;u...<br><img src='<%=basePath%>images/waiting.gif'/>");
 			             	}
			             	if (getData.txn_status=="SUCCESS"){
			             		clearInterval(alertTimerId);
			             		$.modal.close();
			             		alert("Doi mat khau Mobifone thanh cong!");
			             	}
			             	if (getData.txn_status=="ERROR"){
			             		clearInterval(alertTimerId);
			             		$.modal.close();
			             		alert("Co loi trong xu ly doi mat khau Mobifone, ErrorCode:"+getData.txn_errorcode);
			             	}   
  	  	     		}else{
  	  	     				clearInterval(alertTimerId);
  	  	     				$.modal.close();
  	  	     				alert("C\u00f3 l\u1ed7i x\u1ea3y ra.");
		  	  	     	}
	  	
	    		  }catch(e)
		    		  {
	    			  	clearInterval(alertTimerId);
		    		  	$.modal.close();
		    		  	alert("C\u00f3 l\u1ed7i x\u1ea3y ra.");
		    		  
		    		  }   
		    	 	
	            },
	            error: function (){
	              $.modal.close();
	              alert('H\u1ec7 th\u1ed1ng l\u1ed7i. ');
	            }
		  });
   }
   	  

 //report end        
    //]]></script>
</body>
</html>