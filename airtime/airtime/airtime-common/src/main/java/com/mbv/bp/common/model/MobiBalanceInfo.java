package com.mbv.bp.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class MobiBalanceInfo {
	private BigDecimal avail1;
	private BigDecimal current1;
	private BigDecimal pending1;
	private BigDecimal avail2;
	private BigDecimal current2;
	private BigDecimal pending2;
	private BigDecimal avail3;
	private BigDecimal current3;
	private BigDecimal pending3;
	private Date txnDate;
	private String strDate;
	private Integer txnId;
	private String status;
	private String errorCode;
	private String nameSpace;
	private Long mobiviBalance; 
	public BigDecimal getAvail1() {
		return avail1;
	}
	public void setAvail1(BigDecimal avail1) {
		this.avail1 = avail1;
	}
	public BigDecimal getCurrent1() {
		return current1;
	}
	public void setCurrent1(BigDecimal current1) {
		this.current1 = current1;
	}
	public BigDecimal getPending1() {
		return pending1;
	}
	public void setPending1(BigDecimal pending1) {
		this.pending1 = pending1;
	}
	public BigDecimal getAvail2() {
		return avail2;
	}
	public void setAvail2(BigDecimal avail2) {
		this.avail2 = avail2;
	}
	public BigDecimal getCurrent2() {
		return current2;
	}
	public void setCurrent2(BigDecimal current2) {
		this.current2 = current2;
	}
	public BigDecimal getPending2() {
		return pending2;
	}
	public void setPending2(BigDecimal pending2) {
		this.pending2 = pending2;
	}
	public BigDecimal getAvail3() {
		return avail3;
	}
	public void setAvail3(BigDecimal avail3) {
		this.avail3 = avail3;
	}
	public BigDecimal getCurrent3() {
		return current3;
	}
	public void setCurrent3(BigDecimal current3) {
		this.current3 = current3;
	}
	public BigDecimal getPending3() {
		return pending3;
	}
	public void setPending3(BigDecimal pending3) {
		this.pending3 = pending3;
	}
	public Date getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	public Integer getTxnId() {
		return txnId;
	}
	public void setTxnId(Integer txnId) {
		this.txnId = txnId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	
	public String getStrDate() {
		return strDate;
	}
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}
	
	public Long getMobiviBalance() {
		return mobiviBalance;
	}
	public void setMobiviBalance(Long mobiviBalance) {
		this.mobiviBalance = mobiviBalance;
	}
	@Override
	public String toString() {
		return "MobiBalanceInfo [avail1=" + avail1 + ", avail2=" + avail2
				+ ", avail3=" + avail3 + ", current1=" + current1
				+ ", current2=" + current2 + ", current3=" + current3
				+ ", errorCode=" + errorCode + ", nameSpace=" + nameSpace
				+ ", pending1=" + pending1 + ", pending2=" + pending2
				+ ", pending3=" + pending3 + ", status=" + status
				+ ", txnDate=" + txnDate + ", txnId=" + txnId + "]";
	}
}
