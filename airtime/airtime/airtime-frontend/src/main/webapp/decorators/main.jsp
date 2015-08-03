<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>

<%@page import="com.mbv.frontend.constant.FeConstant"%>
<%@page import="com.mbv.frontend.util.AppUtils"%>
<%@page import="org.apache.commons.lang.StringUtils"%><head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<title>Mobivi Airtime Admin</title>
<style type="text/css">
@import "css/global.css";
</style>
<decorator:head />
<body>
<table align="center" width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class=tinyborder>
	<tr align="left" valign="top" height="80">
		<td colspan="1" valign="top" align="left"><img src="<%=basePath%>images/mobivi_logo_vi.png" alt="MobiVí" /></td>
		<td colspan="1" valign="middle" align="right">
			<%
				if (StringUtils.isNotBlank(AppUtils.getUserNameLogin())){
			%>
				Xin ch&#224;o : <%=AppUtils.getUserNameLogin()%> | <a href="<%=basePath%>logout.jsp"> &#272;&#259;ng xu&#7845;t </a>
			<%}else{ %>
				<a href="<%=basePath%>login.jsp"> &#272;&#259;ng nh&#7853;p </a>
			<%} %>
		</td>
	</tr>
	<tr>
		<td valign="top" width="160">
			<a href="#"><b>Qu&#7843;n l&#253; h&#7879; th&#7889;ng  </b></a> <br>
			<a href="<%=FeConstant.ACCESS_CONTROL_ACCESS_TARGET_URL%>">+Ph&#226;n quy&#7873;n</a> <br>
			<a href="<%=basePath%>recache_v.mbv">+C&#7853;p nh&#7853;t H&#7879; th&#7889;ng</a> <br>
			<a href="#"><b>T&#224;i kho&#7843;n Mobifone  </b></a> <br>
			<a href="<%=basePath%>mobi_change_pwd_v.mbv">+&#272;&#7893;i m&#7853;t kh&#7849;u Mobifone</a> <br>
			<a href="<%=basePath%>mobi_balance_lv.mbv">+Truy v&#7845;n s&#7889; d&#432; tk Mobifone</a> <br>
			<%--  Quan ly Tai khoan Tong --%>
			<a href="#"><b>Qu&#7843;n l&#253; t&#224;i kho&#7843;n t&#7893;ng</b></a> <br>
			<a href="<%=basePath%>reserved_account_lv.mbv">+Truy v&#7845;n t&#224;i kho&#7843;n kh&#243;a ti&#7873;n</a> <br>
			<a href="<%=basePath%>reserved_txn_lv.mbv">+Truy v&#7845;n giao d&#7883;ch kh&#243;a ti&#7873;n</a> <br>
			<%--  Quan ly Dat Hang --%>
			<a href="#"><b>Qu&#7843;n l&#253; &#272;&#7863;t h&#224;ng</b></a> <br>
			<a href="<%=basePath%>provider_account_load_money_nv.mbv">+ N&#7841;p ti&#7873;n</a> <br>
			<a href="<%=basePath%>provider_account_load_money_lv.mbv">+ Danh s&#225;ch n&#7841;p ti&#7873;n</a> <br>
			<%--  Quan ly giao dich --%>
			<a href="#"><b>Qu&#7843;n l&#253; giao d&#7883;ch</b></a> <br>
			<a href="<%=basePath%>airtime_transaction_lv.mbv">+ Truy v&#7845;n giao d&#7883;ch</a> <br>
			<a href="<%=basePath%>vietpay_view.mbv">+ Truy v&#7845;n giao d&#7883;ch VietPay</a> <br>
			<a href="<%=basePath%>comparison_cdr_lv.mbv">+Truy v&#7845;n &#273;&#7889;i so&#225;t</a> <br>
			<%-- a href="<%=basePath%>provider_txn_summary_lv.mbv">+ Truy v&#7845;n giao d&#7883;ch v&#7899;i nh&#224; cung c&#7845;p</a> <br>
			<a href="<%=basePath%>provider_account_summary_lv.mbv">+ Th&#244;ng tin t&#224;i kho&#7843;n</a> <br--%>
			<a href="#"><b>ANYPAY</b></a> <br>
			<a href="<%=basePath%>anypay_sim_load_money_lv.mbv">+ Truy v&#7845;n GD n&#7841;p ti&#7873;n Anypay</a> <br>
			<a href="<%=basePath%>anypay_sim_load_money_nv.mbv">+ N&#7841;p ti&#7873;n v&#224;o Sim Anypay</a> <br>
			<a href="<%=basePath%>anypay_transfer_nv.mbv">+ Chuy&#7875;n ti&#7873;n t&#7915; Sim qua Sim</a> <br>
			<a href="<%=basePath%>anypay_transaction_lv.mbv">+ Truy v&#7845;n GD AnyPay</a> <br>
			<a href="<%=basePath%>sms_content_lv.mbv">+ Truy v&#7845;n SMS</a> <br>
		</td>
		
		<td valign="top"><div id="content" ><decorator:body /></div></td>
	</tr>
	<tr height="30">
		<td colspan="2" valign="middle" align="center"> Mobiv&#237; 2011</td>
	</tr>
</table>
</body>
</html>