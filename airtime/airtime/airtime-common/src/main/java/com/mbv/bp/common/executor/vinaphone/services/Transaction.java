/**
 * Transaction.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.bp.common.executor.vinaphone.services;

public class Transaction  implements java.io.Serializable {
    private int channel;

    private java.util.Calendar createDate;

    private java.util.Calendar lastModify;

    private int processStatus;

    private double sourceCurrentBalance;

    private java.lang.String sourceIsdn;

    private double targetCurrentBalance;

    private java.lang.String targetIsdn;

    private double transAmount;

    private java.lang.String transType;

    private java.lang.String transactionError;

    public Transaction() {
    }

    public Transaction(
           int channel,
           java.util.Calendar createDate,
           java.util.Calendar lastModify,
           int processStatus,
           double sourceCurrentBalance,
           java.lang.String sourceIsdn,
           double targetCurrentBalance,
           java.lang.String targetIsdn,
           double transAmount,
           java.lang.String transType,
           java.lang.String transactionError) {
           this.channel = channel;
           this.createDate = createDate;
           this.lastModify = lastModify;
           this.processStatus = processStatus;
           this.sourceCurrentBalance = sourceCurrentBalance;
           this.sourceIsdn = sourceIsdn;
           this.targetCurrentBalance = targetCurrentBalance;
           this.targetIsdn = targetIsdn;
           this.transAmount = transAmount;
           this.transType = transType;
           this.transactionError = transactionError;
    }


    /**
     * Gets the channel value for this Transaction.
     * 
     * @return channel
     */
    public int getChannel() {
        return channel;
    }


    /**
     * Sets the channel value for this Transaction.
     * 
     * @param channel
     */
    public void setChannel(int channel) {
        this.channel = channel;
    }


    /**
     * Gets the createDate value for this Transaction.
     * 
     * @return createDate
     */
    public java.util.Calendar getCreateDate() {
        return createDate;
    }


    /**
     * Sets the createDate value for this Transaction.
     * 
     * @param createDate
     */
    public void setCreateDate(java.util.Calendar createDate) {
        this.createDate = createDate;
    }


    /**
     * Gets the lastModify value for this Transaction.
     * 
     * @return lastModify
     */
    public java.util.Calendar getLastModify() {
        return lastModify;
    }


    /**
     * Sets the lastModify value for this Transaction.
     * 
     * @param lastModify
     */
    public void setLastModify(java.util.Calendar lastModify) {
        this.lastModify = lastModify;
    }


    /**
     * Gets the processStatus value for this Transaction.
     * 
     * @return processStatus
     */
    public int getProcessStatus() {
        return processStatus;
    }


    /**
     * Sets the processStatus value for this Transaction.
     * 
     * @param processStatus
     */
    public void setProcessStatus(int processStatus) {
        this.processStatus = processStatus;
    }


    /**
     * Gets the sourceCurrentBalance value for this Transaction.
     * 
     * @return sourceCurrentBalance
     */
    public double getSourceCurrentBalance() {
        return sourceCurrentBalance;
    }


    /**
     * Sets the sourceCurrentBalance value for this Transaction.
     * 
     * @param sourceCurrentBalance
     */
    public void setSourceCurrentBalance(double sourceCurrentBalance) {
        this.sourceCurrentBalance = sourceCurrentBalance;
    }


    /**
     * Gets the sourceIsdn value for this Transaction.
     * 
     * @return sourceIsdn
     */
    public java.lang.String getSourceIsdn() {
        return sourceIsdn;
    }


    /**
     * Sets the sourceIsdn value for this Transaction.
     * 
     * @param sourceIsdn
     */
    public void setSourceIsdn(java.lang.String sourceIsdn) {
        this.sourceIsdn = sourceIsdn;
    }


    /**
     * Gets the targetCurrentBalance value for this Transaction.
     * 
     * @return targetCurrentBalance
     */
    public double getTargetCurrentBalance() {
        return targetCurrentBalance;
    }


    /**
     * Sets the targetCurrentBalance value for this Transaction.
     * 
     * @param targetCurrentBalance
     */
    public void setTargetCurrentBalance(double targetCurrentBalance) {
        this.targetCurrentBalance = targetCurrentBalance;
    }


    /**
     * Gets the targetIsdn value for this Transaction.
     * 
     * @return targetIsdn
     */
    public java.lang.String getTargetIsdn() {
        return targetIsdn;
    }


    /**
     * Sets the targetIsdn value for this Transaction.
     * 
     * @param targetIsdn
     */
    public void setTargetIsdn(java.lang.String targetIsdn) {
        this.targetIsdn = targetIsdn;
    }


    /**
     * Gets the transAmount value for this Transaction.
     * 
     * @return transAmount
     */
    public double getTransAmount() {
        return transAmount;
    }


    /**
     * Sets the transAmount value for this Transaction.
     * 
     * @param transAmount
     */
    public void setTransAmount(double transAmount) {
        this.transAmount = transAmount;
    }


    /**
     * Gets the transType value for this Transaction.
     * 
     * @return transType
     */
    public java.lang.String getTransType() {
        return transType;
    }


    /**
     * Sets the transType value for this Transaction.
     * 
     * @param transType
     */
    public void setTransType(java.lang.String transType) {
        this.transType = transType;
    }


    /**
     * Gets the transactionError value for this Transaction.
     * 
     * @return transactionError
     */
    public java.lang.String getTransactionError() {
        return transactionError;
    }


    /**
     * Sets the transactionError value for this Transaction.
     * 
     * @param transactionError
     */
    public void setTransactionError(java.lang.String transactionError) {
        this.transactionError = transactionError;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Transaction)) return false;
        Transaction other = (Transaction) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.channel == other.getChannel() &&
            ((this.createDate==null && other.getCreateDate()==null) || 
             (this.createDate!=null &&
              this.createDate.equals(other.getCreateDate()))) &&
            ((this.lastModify==null && other.getLastModify()==null) || 
             (this.lastModify!=null &&
              this.lastModify.equals(other.getLastModify()))) &&
            this.processStatus == other.getProcessStatus() &&
            this.sourceCurrentBalance == other.getSourceCurrentBalance() &&
            ((this.sourceIsdn==null && other.getSourceIsdn()==null) || 
             (this.sourceIsdn!=null &&
              this.sourceIsdn.equals(other.getSourceIsdn()))) &&
            this.targetCurrentBalance == other.getTargetCurrentBalance() &&
            ((this.targetIsdn==null && other.getTargetIsdn()==null) || 
             (this.targetIsdn!=null &&
              this.targetIsdn.equals(other.getTargetIsdn()))) &&
            this.transAmount == other.getTransAmount() &&
            ((this.transType==null && other.getTransType()==null) || 
             (this.transType!=null &&
              this.transType.equals(other.getTransType()))) &&
            ((this.transactionError==null && other.getTransactionError()==null) || 
             (this.transactionError!=null &&
              this.transactionError.equals(other.getTransactionError())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getChannel();
        if (getCreateDate() != null) {
            _hashCode += getCreateDate().hashCode();
        }
        if (getLastModify() != null) {
            _hashCode += getLastModify().hashCode();
        }
        _hashCode += getProcessStatus();
        _hashCode += new Double(getSourceCurrentBalance()).hashCode();
        if (getSourceIsdn() != null) {
            _hashCode += getSourceIsdn().hashCode();
        }
        _hashCode += new Double(getTargetCurrentBalance()).hashCode();
        if (getTargetIsdn() != null) {
            _hashCode += getTargetIsdn().hashCode();
        }
        _hashCode += new Double(getTransAmount()).hashCode();
        if (getTransType() != null) {
            _hashCode += getTransType().hashCode();
        }
        if (getTransactionError() != null) {
            _hashCode += getTransactionError().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Transaction.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://trans.mwallet.fss.com", "Transaction"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "channel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastModify");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastModify"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "processStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceCurrentBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sourceCurrentBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceIsdn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sourceIsdn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetCurrentBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "targetCurrentBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetIsdn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "targetIsdn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionError");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transactionError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
