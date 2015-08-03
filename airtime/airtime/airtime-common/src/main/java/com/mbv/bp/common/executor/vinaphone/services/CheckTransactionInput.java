/**
 * CheckTransactionInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.bp.common.executor.vinaphone.services;

public class CheckTransactionInput  implements java.io.Serializable {
    private java.lang.String agentMsIsdn;

    private java.lang.String password;

    private java.lang.String transIdCheck;

    private java.lang.String username;

    public CheckTransactionInput() {
    }

    public CheckTransactionInput(
           java.lang.String agentMsIsdn,
           java.lang.String password,
           java.lang.String transIdCheck,
           java.lang.String username) {
           this.agentMsIsdn = agentMsIsdn;
           this.password = password;
           this.transIdCheck = transIdCheck;
           this.username = username;
    }


    /**
     * Gets the agentMsIsdn value for this CheckTransactionInput.
     * 
     * @return agentMsIsdn
     */
    public java.lang.String getAgentMsIsdn() {
        return agentMsIsdn;
    }


    /**
     * Sets the agentMsIsdn value for this CheckTransactionInput.
     * 
     * @param agentMsIsdn
     */
    public void setAgentMsIsdn(java.lang.String agentMsIsdn) {
        this.agentMsIsdn = agentMsIsdn;
    }


    /**
     * Gets the password value for this CheckTransactionInput.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this CheckTransactionInput.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the transIdCheck value for this CheckTransactionInput.
     * 
     * @return transIdCheck
     */
    public java.lang.String getTransIdCheck() {
        return transIdCheck;
    }


    /**
     * Sets the transIdCheck value for this CheckTransactionInput.
     * 
     * @param transIdCheck
     */
    public void setTransIdCheck(java.lang.String transIdCheck) {
        this.transIdCheck = transIdCheck;
    }


    /**
     * Gets the username value for this CheckTransactionInput.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this CheckTransactionInput.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CheckTransactionInput)) return false;
        CheckTransactionInput other = (CheckTransactionInput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.agentMsIsdn==null && other.getAgentMsIsdn()==null) || 
             (this.agentMsIsdn!=null &&
              this.agentMsIsdn.equals(other.getAgentMsIsdn()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.transIdCheck==null && other.getTransIdCheck()==null) || 
             (this.transIdCheck!=null &&
              this.transIdCheck.equals(other.getTransIdCheck()))) &&
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername())));
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
        if (getAgentMsIsdn() != null) {
            _hashCode += getAgentMsIsdn().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getTransIdCheck() != null) {
            _hashCode += getTransIdCheck().hashCode();
        }
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CheckTransactionInput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mwallet.fss.com", "CheckTransactionInput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agentMsIsdn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agentMsIsdn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transIdCheck");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transIdCheck"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("", "username"));
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
