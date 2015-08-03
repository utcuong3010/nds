/**
 * StandardBizResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.airtime2.mobifone.Stub;

public class StandardBizResponse  implements java.io.Serializable {
    private java.math.BigDecimal fee;

    private int result;

    private java.lang.String result_namespace;

    private java.lang.Long schedule_id;

    private java.lang.Integer transid;

    public StandardBizResponse() {
    }

    public StandardBizResponse(
           java.math.BigDecimal fee,
           int result,
           java.lang.String result_namespace,
           java.lang.Long schedule_id,
           java.lang.Integer transid) {
           this.fee = fee;
           this.result = result;
           this.result_namespace = result_namespace;
           this.schedule_id = schedule_id;
           this.transid = transid;
    }


    /**
     * Gets the fee value for this StandardBizResponse.
     * 
     * @return fee
     */
    public java.math.BigDecimal getFee() {
        return fee;
    }


    /**
     * Sets the fee value for this StandardBizResponse.
     * 
     * @param fee
     */
    public void setFee(java.math.BigDecimal fee) {
        this.fee = fee;
    }


    /**
     * Gets the result value for this StandardBizResponse.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this StandardBizResponse.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the result_namespace value for this StandardBizResponse.
     * 
     * @return result_namespace
     */
    public java.lang.String getResult_namespace() {
        return result_namespace;
    }


    /**
     * Sets the result_namespace value for this StandardBizResponse.
     * 
     * @param result_namespace
     */
    public void setResult_namespace(java.lang.String result_namespace) {
        this.result_namespace = result_namespace;
    }


    /**
     * Gets the schedule_id value for this StandardBizResponse.
     * 
     * @return schedule_id
     */
    public java.lang.Long getSchedule_id() {
        return schedule_id;
    }


    /**
     * Sets the schedule_id value for this StandardBizResponse.
     * 
     * @param schedule_id
     */
    public void setSchedule_id(java.lang.Long schedule_id) {
        this.schedule_id = schedule_id;
    }


    /**
     * Gets the transid value for this StandardBizResponse.
     * 
     * @return transid
     */
    public java.lang.Integer getTransid() {
        return transid;
    }


    /**
     * Sets the transid value for this StandardBizResponse.
     * 
     * @param transid
     */
    public void setTransid(java.lang.Integer transid) {
        this.transid = transid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StandardBizResponse)) return false;
        StandardBizResponse other = (StandardBizResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fee==null && other.getFee()==null) || 
             (this.fee!=null &&
              this.fee.equals(other.getFee()))) &&
            this.result == other.getResult() &&
            ((this.result_namespace==null && other.getResult_namespace()==null) || 
             (this.result_namespace!=null &&
              this.result_namespace.equals(other.getResult_namespace()))) &&
            ((this.schedule_id==null && other.getSchedule_id()==null) || 
             (this.schedule_id!=null &&
              this.schedule_id.equals(other.getSchedule_id()))) &&
            ((this.transid==null && other.getTransid()==null) || 
             (this.transid!=null &&
              this.transid.equals(other.getTransid())));
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
        if (getFee() != null) {
            _hashCode += getFee().hashCode();
        }
        _hashCode += getResult();
        if (getResult_namespace() != null) {
            _hashCode += getResult_namespace().hashCode();
        }
        if (getSchedule_id() != null) {
            _hashCode += getSchedule_id().hashCode();
        }
        if (getTransid() != null) {
            _hashCode += getTransid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StandardBizResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://UMARKETSCWS", "StandardBizResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fee");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("schedule_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "schedule_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
