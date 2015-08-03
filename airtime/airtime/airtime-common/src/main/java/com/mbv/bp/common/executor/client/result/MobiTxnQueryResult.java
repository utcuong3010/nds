package com.mbv.bp.common.executor.client.result;

import java.math.BigDecimal;

public class MobiTxnQueryResult {
private int transid;
private int result;
private String resultNamespace;
private String initiator;
private String creditor;
private String debtor;
private BigDecimal amount;
private int type;
private boolean external;
private long date;
private int state;
private int transResult;
private String transResultNamespace;
public int getTransid() {
	return transid;
}
public void setTransid(int transid) {
	this.transid = transid;
}
public int getResult() {
	return result;
}
public void setResult(int result) {
	this.result = result;
}
public String getResultNamespace() {
	return resultNamespace;
}
public void setResultNamespace(String resultNamespace) {
	this.resultNamespace = resultNamespace;
}
public String getInitiator() {
	return initiator;
}
public void setInitiator(String initiator) {
	this.initiator = initiator;
}

public String getCreditor() {
	return creditor;
}
public void setCreditor(String creditor) {
	this.creditor = creditor;
}
public String getDebtor() {
	return debtor;
}
public void setDebtor(String debtor) {
	this.debtor = debtor;
}
public BigDecimal getAmount() {
	return amount;
}
public void setAmount(BigDecimal amount) {
	this.amount = amount;
}
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public boolean isExternal() {
	return external;
}
public void setExternal(boolean external) {
	this.external = external;
}
public long getDate() {
	return date;
}
public void setDate(long date) {
	this.date = date;
}
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
}
public int getTransResult() {
	return transResult;
}
public void setTransResult(int transResult) {
	this.transResult = transResult;
}
public String getTransResultNamespace() {
	return transResultNamespace;
}
public void setTransResultNamespace(String transResultNamespace) {
	this.transResultNamespace = transResultNamespace;
}


}
