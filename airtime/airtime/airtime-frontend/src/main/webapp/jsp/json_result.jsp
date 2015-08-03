<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String result=(String)request.getAttribute("jsonResult");
out.print(result);
%>