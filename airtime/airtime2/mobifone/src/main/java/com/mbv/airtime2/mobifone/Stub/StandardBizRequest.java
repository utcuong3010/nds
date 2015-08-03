/**
 * StandardBizRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.airtime2.mobifone.Stub;

public class StandardBizRequest  implements java.io.Serializable {
    private java.lang.String ext_couponid;

    private java.lang.String ext_details;

    private java.lang.String ext_source;

    private java.lang.String ext_src_method;

    private java.lang.String ext_usedefault;

    private java.lang.Integer schedfreq;

    private java.lang.String schedule;

    private java.lang.String sessionid;

    private java.lang.Boolean suppress_confirm;

    private java.lang.Boolean suppress_notify_trans;

    private java.lang.Boolean wait;

    public StandardBizRequest() {
    }

    public StandardBizRequest(
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
           java.lang.Boolean wait) {
           this.ext_couponid = ext_couponid;
           this.ext_details = ext_details;
           this.ext_source = ext_source;
           this.ext_src_method = ext_src_method;
           this.ext_usedefault = ext_usedefault;
           this.schedfreq = schedfreq;
           this.schedule = schedule;
           this.sessionid = sessionid;
           this.suppress_confirm = suppress_confirm;
           this.suppress_notify_trans = suppress_notify_trans;
           this.wait = wait;
    }


    /**
     * Gets the ext_couponid value for this StandardBizRequest.
     * 
     * @return ext_couponid
     */
    public java.lang.String getExt_couponid() {
        return ext_couponid;
    }


    /**
     * Sets the ext_couponid value for this StandardBizRequest.
     * 
     * @param ext_couponid
     */
    public void setExt_couponid(java.lang.String ext_couponid) {
        this.ext_couponid = ext_couponid;
    }


    /**
     * Gets the ext_details value for this StandardBizRequest.
     * 
     * @return ext_details
     */
    public java.lang.String getExt_details() {
        return ext_details;
    }


    /**
     * Sets the ext_details value for this StandardBizRequest.
     * 
     * @param ext_details
     */
    public void setExt_details(java.lang.String ext_details) {
        this.ext_details = ext_details;
    }


    /**
     * Gets the ext_source value for this StandardBizRequest.
     * 
     * @return ext_source
     */
    public java.lang.String getExt_source() {
        return ext_source;
    }


    /**
     * Sets the ext_source value for this StandardBizRequest.
     * 
     * @param ext_source
     */
    public void setExt_source(java.lang.String ext_source) {
        this.ext_source = ext_source;
    }


    /**
     * Gets the ext_src_method value for this StandardBizRequest.
     * 
     * @return ext_src_method
     */
    public java.lang.String getExt_src_method() {
        return ext_src_method;
    }


    /**
     * Sets the ext_src_method value for this StandardBizRequest.
     * 
     * @param ext_src_method
     */
    public void setExt_src_method(java.lang.String ext_src_method) {
        this.ext_src_method = ext_src_method;
    }


    /**
     * Gets the ext_usedefault value for this StandardBizRequest.
     * 
     * @return ext_usedefault
     */
    public java.lang.String getExt_usedefault() {
        return ext_usedefault;
    }


    /**
     * Sets the ext_usedefault value for this StandardBizRequest.
     * 
     * @param ext_usedefault
     */
    public void setExt_usedefault(java.lang.String ext_usedefault) {
        this.ext_usedefault = ext_usedefault;
    }


    /**
     * Gets the schedfreq value for this StandardBizRequest.
     * 
     * @return schedfreq
     */
    public java.lang.Integer getSchedfreq() {
        return schedfreq;
    }


    /**
     * Sets the schedfreq value for this StandardBizRequest.
     * 
     * @param schedfreq
     */
    public void setSchedfreq(java.lang.Integer schedfreq) {
        this.schedfreq = schedfreq;
    }


    /**
     * Gets the schedule value for this StandardBizRequest.
     * 
     * @return schedule
     */
    public java.lang.String getSchedule() {
        return schedule;
    }


    /**
     * Sets the schedule value for this StandardBizRequest.
     * 
     * @param schedule
     */
    public void setSchedule(java.lang.String schedule) {
        this.schedule = schedule;
    }


    /**
     * Gets the sessionid value for this StandardBizRequest.
     * 
     * @return sessionid
     */
    public java.lang.String getSessionid() {
        return sessionid;
    }


    /**
     * Sets the sessionid value for this StandardBizRequest.
     * 
     * @param sessionid
     */
    public void setSessionid(java.lang.String sessionid) {
        this.sessionid = sessionid;
    }


    /**
     * Gets the suppress_confirm value for this StandardBizRequest.
     * 
     * @return suppress_confirm
     */
    public java.lang.Boolean getSuppress_confirm() {
        return suppress_confirm;
    }


    /**
     * Sets the suppress_confirm value for this StandardBizRequest.
     * 
     * @param suppress_confirm
     */
    public void setSuppress_confirm(java.lang.Boolean suppress_confirm) {
        this.suppress_confirm = suppress_confirm;
    }


    /**
     * Gets the suppress_notify_trans value for this StandardBizRequest.
     * 
     * @return suppress_notify_trans
     */
    public java.lang.Boolean getSuppress_notify_trans() {
        return suppress_notify_trans;
    }


    /**
     * Sets the suppress_notify_trans value for this StandardBizRequest.
     * 
     * @param suppress_notify_trans
     */
    public void setSuppress_notify_trans(java.lang.Boolean suppress_notify_trans) {
        this.suppress_notify_trans = suppress_notify_trans;
    }


    /**
     * Gets the wait value for this StandardBizRequest.
     * 
     * @return wait
     */
    public java.lang.Boolean getWait() {
        return wait;
    }


    /**
     * Sets the wait value for this StandardBizRequest.
     * 
     * @param wait
     */
    public void setWait(java.lang.Boolean wait) {
        this.wait = wait;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StandardBizRequest)) return false;
        StandardBizRequest other = (StandardBizRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ext_couponid==null && other.getExt_couponid()==null) || 
             (this.ext_couponid!=null &&
              this.ext_couponid.equals(other.getExt_couponid()))) &&
            ((this.ext_details==null && other.getExt_details()==null) || 
             (this.ext_details!=null &&
              this.ext_details.equals(other.getExt_details()))) &&
            ((this.ext_source==null && other.getExt_source()==null) || 
             (this.ext_source!=null &&
              this.ext_source.equals(other.getExt_source()))) &&
            ((this.ext_src_method==null && other.getExt_src_method()==null) || 
             (this.ext_src_method!=null &&
              this.ext_src_method.equals(other.getExt_src_method()))) &&
            ((this.ext_usedefault==null && other.getExt_usedefault()==null) || 
             (this.ext_usedefault!=null &&
              this.ext_usedefault.equals(other.getExt_usedefault()))) &&
            ((this.schedfreq==null && other.getSchedfreq()==null) || 
             (this.schedfreq!=null &&
              this.schedfreq.equals(other.getSchedfreq()))) &&
            ((this.schedule==null && other.getSchedule()==null) || 
             (this.schedule!=null &&
              this.schedule.equals(other.getSchedule()))) &&
            ((this.sessionid==null && other.getSessionid()==null) || 
             (this.sessionid!=null &&
              this.sessionid.equals(other.getSessionid()))) &&
            ((this.suppress_confirm==null && other.getSuppress_confirm()==null) || 
             (this.suppress_confirm!=null &&
              this.suppress_confirm.equals(other.getSuppress_confirm()))) &&
            ((this.suppress_notify_trans==null && other.getSuppress_notify_trans()==null) || 
             (this.suppress_notify_trans!=null &&
              this.suppress_notify_trans.equals(other.getSuppress_notify_trans()))) &&
            ((this.wait==null && other.getWait()==null) || 
             (this.wait!=null &&
              this.wait.equals(other.getWait())));
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
        if (getExt_couponid() != null) {
            _hashCode += getExt_couponid().hashCode();
        }
        if (getExt_details() != null) {
            _hashCode += getExt_details().hashCode();
        }
        if (getExt_source() != null) {
            _hashCode += getExt_source().hashCode();
        }
        if (getExt_src_method() != null) {
            _hashCode += getExt_src_method().hashCode();
        }
        if (getExt_usedefault() != null) {
            _hashCode += getExt_usedefault().hashCode();
        }
        if (getSchedfreq() != null) {
            _hashCode += getSchedfreq().hashCode();
        }
        if (getSchedule() != null) {
            _hashCode += getSchedule().hashCode();
        }
        if (getSessionid() != null) {
            _hashCode += getSessionid().hashCode();
        }
        if (getSuppress_confirm() != null) {
            _hashCode += getSuppress_confirm().hashCode();
        }
        if (getSuppress_notify_trans() != null) {
            _hashCode += getSuppress_notify_trans().hashCode();
        }
        if (getWait() != null) {
            _hashCode += getWait().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StandardBizRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://UMARKETSCWS", "StandardBizRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ext_couponid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ext_couponid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ext_details");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ext_details"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ext_source");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ext_source"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ext_src_method");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ext_src_method"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ext_usedefault");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ext_usedefault"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schedfreq");
        elemField.setXmlName(new javax.xml.namespace.QName("", "schedfreq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schedule");
        elemField.setXmlName(new javax.xml.namespace.QName("", "schedule"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sessionid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suppress_confirm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "suppress_confirm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suppress_notify_trans");
        elemField.setXmlName(new javax.xml.namespace.QName("", "suppress_notify_trans"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wait");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wait"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
