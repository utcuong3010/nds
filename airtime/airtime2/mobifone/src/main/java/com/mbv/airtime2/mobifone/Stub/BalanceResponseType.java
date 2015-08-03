/**
 * BalanceResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.airtime2.mobifone.Stub;

public class BalanceResponseType  extends com.mbv.airtime2.mobifone.Stub.StandardBizResponse  implements java.io.Serializable {
    private java.math.BigDecimal avail;

    private java.math.BigDecimal avail_1;

    private java.math.BigDecimal avail_2;

    private java.math.BigDecimal avail_3;

    private java.math.BigDecimal current;

    private java.math.BigDecimal current_1;

    private java.math.BigDecimal current_2;

    private java.math.BigDecimal current_3;

    private java.math.BigDecimal pending;

    private java.math.BigDecimal pending_1;

    private java.math.BigDecimal pending_2;

    private java.math.BigDecimal pending_3;

    public BalanceResponseType() {
    }

    public BalanceResponseType(
           java.math.BigDecimal fee,
           int result,
           java.lang.String result_namespace,
           java.lang.Long schedule_id,
           java.lang.Integer transid,
           java.math.BigDecimal avail,
           java.math.BigDecimal avail_1,
           java.math.BigDecimal avail_2,
           java.math.BigDecimal avail_3,
           java.math.BigDecimal current,
           java.math.BigDecimal current_1,
           java.math.BigDecimal current_2,
           java.math.BigDecimal current_3,
           java.math.BigDecimal pending,
           java.math.BigDecimal pending_1,
           java.math.BigDecimal pending_2,
           java.math.BigDecimal pending_3) {
        super(
            fee,
            result,
            result_namespace,
            schedule_id,
            transid);
        this.avail = avail;
        this.avail_1 = avail_1;
        this.avail_2 = avail_2;
        this.avail_3 = avail_3;
        this.current = current;
        this.current_1 = current_1;
        this.current_2 = current_2;
        this.current_3 = current_3;
        this.pending = pending;
        this.pending_1 = pending_1;
        this.pending_2 = pending_2;
        this.pending_3 = pending_3;
    }


    /**
     * Gets the avail value for this BalanceResponseType.
     * 
     * @return avail
     */
    public java.math.BigDecimal getAvail() {
        return avail;
    }


    /**
     * Sets the avail value for this BalanceResponseType.
     * 
     * @param avail
     */
    public void setAvail(java.math.BigDecimal avail) {
        this.avail = avail;
    }


    /**
     * Gets the avail_1 value for this BalanceResponseType.
     * 
     * @return avail_1
     */
    public java.math.BigDecimal getAvail_1() {
        return avail_1;
    }


    /**
     * Sets the avail_1 value for this BalanceResponseType.
     * 
     * @param avail_1
     */
    public void setAvail_1(java.math.BigDecimal avail_1) {
        this.avail_1 = avail_1;
    }


    /**
     * Gets the avail_2 value for this BalanceResponseType.
     * 
     * @return avail_2
     */
    public java.math.BigDecimal getAvail_2() {
        return avail_2;
    }


    /**
     * Sets the avail_2 value for this BalanceResponseType.
     * 
     * @param avail_2
     */
    public void setAvail_2(java.math.BigDecimal avail_2) {
        this.avail_2 = avail_2;
    }


    /**
     * Gets the avail_3 value for this BalanceResponseType.
     * 
     * @return avail_3
     */
    public java.math.BigDecimal getAvail_3() {
        return avail_3;
    }


    /**
     * Sets the avail_3 value for this BalanceResponseType.
     * 
     * @param avail_3
     */
    public void setAvail_3(java.math.BigDecimal avail_3) {
        this.avail_3 = avail_3;
    }


    /**
     * Gets the current value for this BalanceResponseType.
     * 
     * @return current
     */
    public java.math.BigDecimal getCurrent() {
        return current;
    }


    /**
     * Sets the current value for this BalanceResponseType.
     * 
     * @param current
     */
    public void setCurrent(java.math.BigDecimal current) {
        this.current = current;
    }


    /**
     * Gets the current_1 value for this BalanceResponseType.
     * 
     * @return current_1
     */
    public java.math.BigDecimal getCurrent_1() {
        return current_1;
    }


    /**
     * Sets the current_1 value for this BalanceResponseType.
     * 
     * @param current_1
     */
    public void setCurrent_1(java.math.BigDecimal current_1) {
        this.current_1 = current_1;
    }


    /**
     * Gets the current_2 value for this BalanceResponseType.
     * 
     * @return current_2
     */
    public java.math.BigDecimal getCurrent_2() {
        return current_2;
    }


    /**
     * Sets the current_2 value for this BalanceResponseType.
     * 
     * @param current_2
     */
    public void setCurrent_2(java.math.BigDecimal current_2) {
        this.current_2 = current_2;
    }


    /**
     * Gets the current_3 value for this BalanceResponseType.
     * 
     * @return current_3
     */
    public java.math.BigDecimal getCurrent_3() {
        return current_3;
    }


    /**
     * Sets the current_3 value for this BalanceResponseType.
     * 
     * @param current_3
     */
    public void setCurrent_3(java.math.BigDecimal current_3) {
        this.current_3 = current_3;
    }


    /**
     * Gets the pending value for this BalanceResponseType.
     * 
     * @return pending
     */
    public java.math.BigDecimal getPending() {
        return pending;
    }


    /**
     * Sets the pending value for this BalanceResponseType.
     * 
     * @param pending
     */
    public void setPending(java.math.BigDecimal pending) {
        this.pending = pending;
    }


    /**
     * Gets the pending_1 value for this BalanceResponseType.
     * 
     * @return pending_1
     */
    public java.math.BigDecimal getPending_1() {
        return pending_1;
    }


    /**
     * Sets the pending_1 value for this BalanceResponseType.
     * 
     * @param pending_1
     */
    public void setPending_1(java.math.BigDecimal pending_1) {
        this.pending_1 = pending_1;
    }


    /**
     * Gets the pending_2 value for this BalanceResponseType.
     * 
     * @return pending_2
     */
    public java.math.BigDecimal getPending_2() {
        return pending_2;
    }


    /**
     * Sets the pending_2 value for this BalanceResponseType.
     * 
     * @param pending_2
     */
    public void setPending_2(java.math.BigDecimal pending_2) {
        this.pending_2 = pending_2;
    }


    /**
     * Gets the pending_3 value for this BalanceResponseType.
     * 
     * @return pending_3
     */
    public java.math.BigDecimal getPending_3() {
        return pending_3;
    }


    /**
     * Sets the pending_3 value for this BalanceResponseType.
     * 
     * @param pending_3
     */
    public void setPending_3(java.math.BigDecimal pending_3) {
        this.pending_3 = pending_3;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BalanceResponseType)) return false;
        BalanceResponseType other = (BalanceResponseType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.avail==null && other.getAvail()==null) || 
             (this.avail!=null &&
              this.avail.equals(other.getAvail()))) &&
            ((this.avail_1==null && other.getAvail_1()==null) || 
             (this.avail_1!=null &&
              this.avail_1.equals(other.getAvail_1()))) &&
            ((this.avail_2==null && other.getAvail_2()==null) || 
             (this.avail_2!=null &&
              this.avail_2.equals(other.getAvail_2()))) &&
            ((this.avail_3==null && other.getAvail_3()==null) || 
             (this.avail_3!=null &&
              this.avail_3.equals(other.getAvail_3()))) &&
            ((this.current==null && other.getCurrent()==null) || 
             (this.current!=null &&
              this.current.equals(other.getCurrent()))) &&
            ((this.current_1==null && other.getCurrent_1()==null) || 
             (this.current_1!=null &&
              this.current_1.equals(other.getCurrent_1()))) &&
            ((this.current_2==null && other.getCurrent_2()==null) || 
             (this.current_2!=null &&
              this.current_2.equals(other.getCurrent_2()))) &&
            ((this.current_3==null && other.getCurrent_3()==null) || 
             (this.current_3!=null &&
              this.current_3.equals(other.getCurrent_3()))) &&
            ((this.pending==null && other.getPending()==null) || 
             (this.pending!=null &&
              this.pending.equals(other.getPending()))) &&
            ((this.pending_1==null && other.getPending_1()==null) || 
             (this.pending_1!=null &&
              this.pending_1.equals(other.getPending_1()))) &&
            ((this.pending_2==null && other.getPending_2()==null) || 
             (this.pending_2!=null &&
              this.pending_2.equals(other.getPending_2()))) &&
            ((this.pending_3==null && other.getPending_3()==null) || 
             (this.pending_3!=null &&
              this.pending_3.equals(other.getPending_3())));
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
        if (getAvail() != null) {
            _hashCode += getAvail().hashCode();
        }
        if (getAvail_1() != null) {
            _hashCode += getAvail_1().hashCode();
        }
        if (getAvail_2() != null) {
            _hashCode += getAvail_2().hashCode();
        }
        if (getAvail_3() != null) {
            _hashCode += getAvail_3().hashCode();
        }
        if (getCurrent() != null) {
            _hashCode += getCurrent().hashCode();
        }
        if (getCurrent_1() != null) {
            _hashCode += getCurrent_1().hashCode();
        }
        if (getCurrent_2() != null) {
            _hashCode += getCurrent_2().hashCode();
        }
        if (getCurrent_3() != null) {
            _hashCode += getCurrent_3().hashCode();
        }
        if (getPending() != null) {
            _hashCode += getPending().hashCode();
        }
        if (getPending_1() != null) {
            _hashCode += getPending_1().hashCode();
        }
        if (getPending_2() != null) {
            _hashCode += getPending_2().hashCode();
        }
        if (getPending_3() != null) {
            _hashCode += getPending_3().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BalanceResponseType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://UMARKETSCWS", "BalanceResponseType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("avail");
        elemField.setXmlName(new javax.xml.namespace.QName("", "avail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("avail_1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "avail_1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("avail_2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "avail_2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("avail_3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "avail_3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("current");
        elemField.setXmlName(new javax.xml.namespace.QName("", "current"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("current_1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "current_1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("current_2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "current_2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("current_3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "current_3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pending");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pending"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pending_1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pending_1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pending_2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pending_2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pending_3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pending_3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
