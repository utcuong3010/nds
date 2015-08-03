package com.mbv.frontend.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.mbv.frontend.model.LoginModel;
import com.opensymphony.xwork2.ActionSupport;

public class AuthenticationController extends ActionSupport implements ServletRequestAware 
{
private static final long serialVersionUID = -2955649639253145886L;
private LoginModel loginModel;
private HttpServletRequest request;


public LoginModel getLoginModel() {
	return loginModel;
}


public void setLoginModel(LoginModel loginModel) {
	this.loginModel = loginModel;
}


public void setServletRequest(HttpServletRequest request) {
	this.request=request;
	
}


public String login() {
    System.out.println("loginModel:"+loginModel);
	request.setAttribute("userInfo", loginModel);
    return "success";
}

public String execute() {
	System.out.println("here loginModel:"+loginModel);
	request.setAttribute("userInfo", loginModel);
    return "success";
}
}
