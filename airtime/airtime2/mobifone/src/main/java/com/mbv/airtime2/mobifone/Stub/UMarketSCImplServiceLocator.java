/**
 * UMarketSCImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.airtime2.mobifone.Stub;

public class UMarketSCImplServiceLocator extends org.apache.axis.client.Service implements com.mbv.airtime2.mobifone.Stub.UMarketSCImplService {

    public UMarketSCImplServiceLocator() {
    }


    public UMarketSCImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UMarketSCImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for UMarketSC
    private java.lang.String UMarketSC_address = "http://192.168.254.63:8083/services/UMarketSC";

    public java.lang.String getUMarketSCAddress() {
        return UMarketSC_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String UMarketSCWSDDServiceName = "UMarketSC";

    public java.lang.String getUMarketSCWSDDServiceName() {
        return UMarketSCWSDDServiceName;
    }

    public void setUMarketSCWSDDServiceName(java.lang.String name) {
        UMarketSCWSDDServiceName = name;
    }

    public com.mbv.airtime2.mobifone.Stub.UMarketSCImpl getUMarketSC() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(UMarketSC_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUMarketSC(endpoint);
    }

    public com.mbv.airtime2.mobifone.Stub.UMarketSCImpl getUMarketSC(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.mbv.airtime2.mobifone.Stub.UMarketSCSoapBindingStub _stub = new com.mbv.airtime2.mobifone.Stub.UMarketSCSoapBindingStub(portAddress, this);
            _stub.setPortName(getUMarketSCWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUMarketSCEndpointAddress(java.lang.String address) {
        UMarketSC_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.mbv.airtime2.mobifone.Stub.UMarketSCImpl.class.isAssignableFrom(serviceEndpointInterface)) {
                com.mbv.airtime2.mobifone.Stub.UMarketSCSoapBindingStub _stub = new com.mbv.airtime2.mobifone.Stub.UMarketSCSoapBindingStub(new java.net.URL(UMarketSC_address), this);
                _stub.setPortName(getUMarketSCWSDDServiceName());
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
        if ("UMarketSC".equals(inputPortName)) {
            return getUMarketSC();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://UMARKETSCWS", "UMarketSCImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://UMARKETSCWS", "UMarketSC"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("UMarketSC".equals(portName)) {
            setUMarketSCEndpointAddress(address);
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
