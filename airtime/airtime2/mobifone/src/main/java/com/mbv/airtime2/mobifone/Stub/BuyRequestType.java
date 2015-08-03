/**
 * BuyRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.airtime2.mobifone.Stub;

public class BuyRequestType  extends com.mbv.airtime2.mobifone.Stub.StandardBizRequest  implements java.io.Serializable {
    private java.math.BigDecimal amount;

    private java.lang.String notify_alternate;

    private java.lang.String product;

    private java.lang.String recipient;

    private java.lang.String reference1;

    private java.lang.String reference2;

    private java.lang.String target;

    private java.lang.Integer type;

    public BuyRequestType() {
    }

    public BuyRequestType(
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
           java.math.BigDecimal amount,
           java.lang.String notify_alternate,
           java.lang.String product,
           java.lang.String recipient,
           java.lang.String reference1,
           java.lang.String reference2,
           java.lang.String target,
           java.lang.Integer type) {
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
        this.amount = amount;
        this.notify_alternate = notify_alternate;
        this.product = product;
        this.recipient = recipient;
        this.reference1 = reference1;
        this.reference2 = reference2;
        this.target = target;
        this.type = type;
    }


    /**
     * Gets the amount value for this BuyRequestType.
     * 
     * @return amount
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this BuyRequestType.
     * 
     * @param amount
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the notify_alternate value for this BuyRequestType.
     * 
     * @return notify_alternate
     */
    public java.lang.String getNotify_alternate() {
        return notify_alternate;
    }


    /**
     * Sets the notify_alternate value for this BuyRequestType.
     * 
     * @param notify_alternate
     */
    public void setNotify_alternate(java.lang.String notify_alternate) {
        this.notify_alternate = notify_alternate;
    }


    /**
     * Gets the product value for this BuyRequestType.
     * 
     * @return product
     */
    public java.lang.String getProduct() {
        return product;
    }


    /**
     * Sets the product value for this BuyRequestType.
     * 
     * @param product
     */
    public void setProduct(java.lang.String product) {
        this.product = product;
    }


    /**
     * Gets the recipient value for this BuyRequestType.
     * 
     * @return recipient
     */
    public java.lang.String getRecipient() {
        return recipient;
    }


    /**
     * Sets the recipient value for this BuyRequestType.
     * 
     * @param recipient
     */
    public void setRecipient(java.lang.String recipient) {
        this.recipient = recipient;
    }


    /**
     * Gets the reference1 value for this BuyRequestType.
     * 
     * @return reference1
     */
    public java.lang.String getReference1() {
        return reference1;
    }


    /**
     * Sets the reference1 value for this BuyRequestType.
     * 
     * @param reference1
     */
    public void setReference1(java.lang.String reference1) {
        this.reference1 = reference1;
    }


    /**
     * Gets the reference2 value for this BuyRequestType.
     * 
     * @return reference2
     */
    public java.lang.String getReference2() {
        return reference2;
    }


    /**
     * Sets the reference2 value for this BuyRequestType.
     * 
     * @param reference2
     */
    public void setReference2(java.lang.String reference2) {
        this.reference2 = reference2;
    }


    /**
     * Gets the target value for this BuyRequestType.
     * 
     * @return target
     */
    public java.lang.String getTarget() {
        return target;
    }


    /**
     * Sets the target value for this BuyRequestType.
     * 
     * @param target
     */
    public void setTarget(java.lang.String target) {
        this.target = target;
    }


    /**
     * Gets the type value for this BuyRequestType.
     * 
     * @return type
     */
    public java.lang.Integer getType() {
        return type;
    }


    /**
     * Sets the type value for this BuyRequestType.
     * 
     * @param type
     */
    public void setType(java.lang.Integer type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BuyRequestType)) return false;
        BuyRequestType other = (BuyRequestType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.notify_alternate==null && other.getNotify_alternate()==null) || 
             (this.notify_alternate!=null &&
              this.notify_alternate.equals(other.getNotify_alternate()))) &&
            ((this.product==null && other.getProduct()==null) || 
             (this.product!=null &&
              this.product.equals(other.getProduct()))) &&
            ((this.recipient==null && other.getRecipient()==null) || 
             (this.recipient!=null &&
              this.recipient.equals(other.getRecipient()))) &&
            ((this.reference1==null && other.getReference1()==null) || 
             (this.reference1!=null &&
              this.reference1.equals(other.getReference1()))) &&
            ((this.reference2==null && other.getReference2()==null) || 
             (this.reference2!=null &&
              this.reference2.equals(other.getReference2()))) &&
            ((this.target==null && other.getTarget()==null) || 
             (this.target!=null &&
              this.target.equals(other.getTarget()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType())));
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
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getNotify_alternate() != null) {
            _hashCode += getNotify_alternate().hashCode();
        }
        if (getProduct() != null) {
            _hashCode += getProduct().hashCode();
        }
        if (getRecipient() != null) {
            _hashCode += getRecipient().hashCode();
        }
        if (getReference1() != null) {
            _hashCode += getReference1().hashCode();
        }
        if (getReference2() != null) {
            _hashCode += getReference2().hashCode();
        }
        if (getTarget() != null) {
            _hashCode += getTarget().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BuyRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://UMARKETSCWS", "BuyRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notify_alternate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notify_alternate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("product");
        elemField.setXmlName(new javax.xml.namespace.QName("", "product"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipient");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recipient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reference1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reference2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("target");
        elemField.setXmlName(new javax.xml.namespace.QName("", "target"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
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
