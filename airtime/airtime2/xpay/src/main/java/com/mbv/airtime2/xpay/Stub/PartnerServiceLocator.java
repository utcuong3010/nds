/**
 * PartnerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.airtime2.xpay.Stub;

public class PartnerServiceLocator extends org.apache.axis.client.Service implements com.mbv.airtime2.xpay.Stub.PartnerService {

    public PartnerServiceLocator() {
    }


    public PartnerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PartnerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PartnerServiceSoap
    private java.lang.String PartnerServiceSoap_address = "http://test.simso888.com:2024/PartnerServices/PartnerService.asmx";

    public java.lang.String getPartnerServiceSoapAddress() {
        return PartnerServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PartnerServiceSoapWSDDServiceName = "PartnerServiceSoap";

    public java.lang.String getPartnerServiceSoapWSDDServiceName() {
        return PartnerServiceSoapWSDDServiceName;
    }

    public void setPartnerServiceSoapWSDDServiceName(java.lang.String name) {
        PartnerServiceSoapWSDDServiceName = name;
    }

    public com.mbv.airtime2.xpay.Stub.PartnerServiceSoap getPartnerServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PartnerServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPartnerServiceSoap(endpoint);
    }

    public com.mbv.airtime2.xpay.Stub.PartnerServiceSoap getPartnerServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.mbv.airtime2.xpay.Stub.PartnerServiceSoapStub _stub = new com.mbv.airtime2.xpay.Stub.PartnerServiceSoapStub(portAddress, this);
            _stub.setPortName(getPartnerServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPartnerServiceSoapEndpointAddress(java.lang.String address) {
        PartnerServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.mbv.airtime2.xpay.Stub.PartnerServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.mbv.airtime2.xpay.Stub.PartnerServiceSoapStub _stub = new com.mbv.airtime2.xpay.Stub.PartnerServiceSoapStub(new java.net.URL(PartnerServiceSoap_address), this);
                _stub.setPortName(getPartnerServiceSoapWSDDServiceName());
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
        if ("PartnerServiceSoap".equals(inputPortName)) {
            return getPartnerServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://partner.logich.vn/", "PartnerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://partner.logich.vn/", "PartnerServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PartnerServiceSoap".equals(portName)) {
            setPartnerServiceSoapEndpointAddress(address);
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
