/**
 * PinRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.airtime2.mobifone.Stub;

public class PinRequestType  extends com.mbv.airtime2.mobifone.Stub.StandardBizRequest  implements java.io.Serializable {
    private java.lang.String initiator;

    private java.lang.String new_pin;

    private java.lang.String pin;

    public PinRequestType() {
    }

    public PinRequestType(
           java.lang.String ext_couponid,
           java.lang.String ext_details,
           java.lang.String ext_source,
           java.lang.String ext_src_method,
           java.lang.String ext_usedefault,
           java.lang.Integer schedfreq,
           java.lang.String schedule,
           java.lang.String sessionid,
           java.lang.Boolean suppress_confirm,
           java.lang.Boolean suppress_notify_trans,
           java.lang.Boolean wait,
           java.lang.String initiator,
           java.lang.String new_pin,
           java.lang.String pin) {
        super(
            ext_couponid,
            ext_details,
            ext_source,
            ext_src_method,
            ext_usedefault,
            schedfreq,
            schedule,
            sessionid,
            suppress_confirm,
            suppress_notify_trans,
            wait);
        this.initiator = initiator;
        this.new_pin = new_pin;
        this.pin = pin;
    }


    /**
     * Gets the initiator value for this PinRequestType.
     * 
     * @return initiator
     */
    public java.lang.String getInitiator() {
        return initiator;
    }


    /**
     * Sets the initiator value for this PinRequestType.
     * 
     * @param initiator
     */
    public void setInitiator(java.lang.String initiator) {
        this.initiator = initiator;
    }


    /**
     * Gets the new_pin value for this PinRequestType.
     * 
     * @return new_pin
     */
    public java.lang.String getNew_pin() {
        return new_pin;
    }


    /**
     * Sets the new_pin value for this PinRequestType.
     * 
     * @param new_pin
     */
    public void setNew_pin(java.lang.String new_pin) {
        this.new_pin = new_pin;
    }


    /**
     * Gets the pin value for this PinRequestType.
     * 
     * @return pin
     */
    public java.lang.String getPin() {
        return pin;
    }


    /**
     * Sets the pin value for this PinRequestType.
     * 
     * @param pin
     */
    public void setPin(java.lang.String pin) {
        this.pin = pin;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PinRequestType)) return false;
        PinRequestType other = (PinRequestType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.initiator==null && other.getInitiator()==null) || 
             (this.initiator!=null &&
              this.initiator.equals(other.getInitiator()))) &&
            ((this.new_pin==null && other.getNew_pin()==null) || 
             (this.new_pin!=null &&
              this.new_pin.equals(other.getNew_pin()))) &&
            ((this.pin==null && other.getPin()==null) || 
             (this.pin!=null &&
              this.pin.equals(other.getPin())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getInitiator() != null) {
            _hashCode += getInitiator().hashCode();
        }
        if (getNew_pin() != null) {
            _hashCode += getNew_pin().hashCode();
        }
        if (getPin() != null) {
            _hashCode += getPin().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PinRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://UMARKETSCWS", "PinRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("initiator");
        elemField.setXmlName(new javax.xml.namespace.QName("", "initiator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("new_pin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "new_pin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pin"));
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
