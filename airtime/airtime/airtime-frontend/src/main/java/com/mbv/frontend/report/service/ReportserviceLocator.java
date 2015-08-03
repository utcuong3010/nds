/**
 * ReportserviceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.frontend.report.service;

import com.mbv.frontend.constant.FeConstant;

public class ReportserviceLocator extends org.apache.axis.client.Service implements com.mbv.frontend.report.service.Reportservice {

/**
 * gSOAP 2.7.12 generated service definition
 */

    public ReportserviceLocator() {
    }


    public ReportserviceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ReportserviceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for reportservice
//    private java.lang.String reportservice_address = "http://10.120.8.11:16510";
    private java.lang.String reportservice_address = FeConstant.REPORT_SERVICE_URL;

    public java.lang.String getreportserviceAddress() {
        return reportservice_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String reportserviceWSDDServiceName = "reportservice";

    public java.lang.String getreportserviceWSDDServiceName() {
        return reportserviceWSDDServiceName;
    }

    public void setreportserviceWSDDServiceName(java.lang.String name) {
        reportserviceWSDDServiceName = name;
    }

    public com.mbv.frontend.report.service.ReportservicePortType getreportservice() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(reportservice_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getreportservice(endpoint);
    }

    public com.mbv.frontend.report.service.ReportservicePortType getreportservice(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.mbv.frontend.report.service.ReportserviceStub _stub = new com.mbv.frontend.report.service.ReportserviceStub(portAddress, this);
            _stub.setPortName(getreportserviceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setreportserviceEndpointAddress(java.lang.String address) {
        reportservice_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.mbv.frontend.report.service.ReportservicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.mbv.frontend.report.service.ReportserviceStub _stub = new com.mbv.frontend.report.service.ReportserviceStub(new java.net.URL(reportservice_address), this);
                _stub.setPortName(getreportserviceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("reportservice".equals(inputPortName)) {
            return getreportservice();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:reportservice", "reportservice");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:reportservice", "reportservice"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("reportservice".equals(portName)) {
            setreportserviceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
