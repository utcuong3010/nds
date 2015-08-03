package com.mbv.bp.common.model;

public class NotifTemplate {
	private String templateName;
	private String subject;
	private String content;
	private String to;
	private String from;
	private String cc;
	private String bcc;
	
	
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	@Override
	public String toString() {
		return "NotifEmailTemplate [\n" +
				"templateName=" + templateName + ", \n " +
				"from=" + from + ", \n " +
				"to=" + to + ", \n " +
				"cc=" + cc + ", \n " +
				"bcc=" + bcc + ", \n " +
				"subject=" + subject+ ", \n " +
				"content="+ content + ", \n " +
				"]\n";
	}
	
	
}
