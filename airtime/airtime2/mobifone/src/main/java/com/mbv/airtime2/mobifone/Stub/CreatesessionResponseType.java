/**
 * CreatesessionResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.airtime2.mobifone.Stub;

public class CreatesessionResponseType  implements java.io.Serializable {
    private int result;

    private java.lang.String result_namespace;

    private java.lang.String sessionid;

    public CreatesessionResponseType() {
    }

    public CreatesessionResponseType(
           int result,
           java.lang.String result_namespace,
           java.lang.String sessionid) {
           this.result = result;
           this.result_namespace = result_namespace;
           this.sessionid = sessionid;
    }


    /**
     * Gets the result value for this CreatesessionResponseType.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this CreatesessionResponseType.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the result_namespace value for this CreatesessionResponseType.
     * 
     * @return result_namespace
     */
    public java.lang.String getResult_namespace() {
        return result_namespace;
    }


    /**
     * Sets the result_namespace value for this CreatesessionResponseType.
     * 
     * @param result_namespace
     */
    public void setResult_namespace(java.lang.String result_namespace) {
        this.result_namespace = result_namespace;
    }


    /**
     * Gets the sessionid value for this CreatesessionResponseType.
     * 
     * @return sessionid
     */
    public java.lang.String getSessionid() {
        return sessionid;
    }


    /**
     * Sets the sessionid value for this CreatesessionResponseType.
     * 
     * @param sessionid
     */
    public void setSessionid(java.lang.String sessionid) {
        this.sessionid = sessionid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreatesessionResponseType)) return false;
        CreatesessionResponseType other = (CreatesessionResponseType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.result == other.getResult() &&
            ((this.result_namespace==null && other.getResult_namespace()==null) || 
             (this.result_namespace!=null &&
              this.result_namespace.equals(other.getResult_namespace()))) &&
            ((this.sessionid==null && other.getSessionid()==null) || 
             (this.sessionid!=null &&
              this.sessionid.equals(other.getSessionid())));
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
        _hashCode += getResult();
        if (getResult_namespace() != null) {
            _hashCode += getResult_namespace().hashCode();
        }
        if (getSessionid() != null) {
            _hashCode += getSessionid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreatesessionResponseType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://UMARKETSCWS", "CreatesessionResponseType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result_namespace");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result_namespace"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sessionid"));
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
