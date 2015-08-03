/**
 * TransactionInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.bp.common.executor.vinaphone.services;

public class TransactionInput  implements java.io.Serializable {
    private java.lang.String username;

    private java.lang.String password;

    private java.lang.String agentMsIsdn;

    private java.lang.String targetMsIsdn;

    private double amount;

    private int counter;

    public TransactionInput() {
    }

    public TransactionInput(
           java.lang.String username,
           java.lang.String password,
           java.lang.String agentMsIsdn,
           java.lang.String targetMsIsdn,
           double amount,
           int counter) {
           this.username = username;
           this.password = password;
           this.agentMsIsdn = agentMsIsdn;
           this.targetMsIsdn = targetMsIsdn;
           this.amount = amount;
           this.counter = counter;
    }


    /**
     * Gets the username value for this TransactionInput.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this TransactionInput.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the password value for this TransactionInput.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this TransactionInput.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the agentMsIsdn value for this TransactionInput.
     * 
     * @return agentMsIsdn
     */
    public java.lang.String getAgentMsIsdn() {
        return agentMsIsdn;
    }


    /**
     * Sets the agentMsIsdn value for this TransactionInput.
     * 
     * @param agentMsIsdn
     */
    public void setAgentMsIsdn(java.lang.String agentMsIsdn) {
        this.agentMsIsdn = agentMsIsdn;
    }


    /**
     * Gets the targetMsIsdn value for this TransactionInput.
     * 
     * @return targetMsIsdn
     */
    public java.lang.String getTargetMsIsdn() {
        return targetMsIsdn;
    }


    /**
     * Sets the targetMsIsdn value for this TransactionInput.
     * 
     * @param targetMsIsdn
     */
    public void setTargetMsIsdn(java.lang.String targetMsIsdn) {
        this.targetMsIsdn = targetMsIsdn;
    }


    /**
     * Gets the amount value for this TransactionInput.
     * 
     * @return amount
     */
    public double getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this TransactionInput.
     * 
     * @param amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }


    /**
     * Gets the counter value for this TransactionInput.
     * 
     * @return counter
     */
    public int getCounter() {
        return counter;
    }


    /**
     * Sets the counter value for this TransactionInput.
     * 
     * @param counter
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransactionInput)) return false;
        TransactionInput other = (TransactionInput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.agentMsIsdn==null && other.getAgentMsIsdn()==null) || 
             (this.agentMsIsdn!=null &&
              this.agentMsIsdn.equals(other.getAgentMsIsdn()))) &&
            ((this.targetMsIsdn==null && other.getTargetMsIsdn()==null) || 
             (this.targetMsIsdn!=null &&
              this.targetMsIsdn.equals(other.getTargetMsIsdn()))) &&
            this.amount == other.getAmount() &&
            this.counter == other.getCounter();
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
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getAgentMsIsdn() != null) {
            _hashCode += getAgentMsIsdn().hashCode();
        }
        if (getTargetMsIsdn() != null) {
            _hashCode += getTargetMsIsdn().hashCode();
        }
        _hashCode += new Double(getAmount()).hashCode();
        _hashCode += getCounter();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TransactionInput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mwallet.fss.com", "TransactionInput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("", "username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agentMsIsdn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agentMsIsdn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetMsIsdn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "targetMsIsdn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("counter");
        elemField.setXmlName(new javax.xml.namespace.QName("", "counter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
