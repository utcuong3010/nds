package com.mbv.bp.common.model;
import java.util.Date;


public class PduSms {
	private int index;
	private int msgStatus;
	private String smscAddress;
	private String senderAddress;
	private Date msgDate;
	private String msgType;
	private String seqId;
	private int totalPart;
	private int partId;
	private String txtMsg;
	private String pduMsg;
	
	public String getSmscAddress() {
		return smscAddress;
	}
	public void setSmscAddress(String smscAddress) {
		this.smscAddress = smscAddress;
	}
	public String getSenderAddress() {
		return senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	public Date getMsgDate() {
		return msgDate;
	}
	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	public int getTotalPart() {
		return totalPart;
	}
	public void setTotalPart(int totalPart) {
		this.totalPart = totalPart;
	}
	public int getPartId() {
		return partId;
	}
	public void setPartId(int partId) {
		this.partId = partId;
	}
	public String getTxtMsg() {
		return txtMsg;
	}
	public void setTxtMsg(String txtMsg) {
		this.txtMsg = txtMsg;
	}
	public String getPduMsg() {
		return pduMsg;
	}
	public void setPduMsg(String pduMsg) {
		this.pduMsg = pduMsg;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(int msgStatus) {
		this.msgStatus = msgStatus;
	}
	@Override
	public String toString() {
		return "PduSms [index=" + index + ", msgDate=" + msgDate
				+ ", msgStatus=" + msgStatus + ", msgType=" + msgType
				+ ", partId=" + partId + ", pduMsg=" + pduMsg
				+ ", senderAddress=" + senderAddress + ", seqId=" + seqId
				+ ", smscAddress=" + smscAddress + ", totalPart=" + totalPart
				+ ", txtMsg=" + txtMsg + "]";
	}
	
}
