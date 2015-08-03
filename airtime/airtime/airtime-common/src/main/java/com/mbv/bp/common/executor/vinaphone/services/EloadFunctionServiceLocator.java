/**
 * EloadFunctionServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.bp.common.executor.vinaphone.services;

public class EloadFunctionServiceLocator extends org.apache.axis.client.Service implements com.mbv.bp.common.executor.vinaphone.services.EloadFunctionService {

    public EloadFunctionServiceLocator() {
    }


    public EloadFunctionServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EloadFunctionServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EloadFunction
    private java.lang.String EloadFunction_address = "http://localhost:16680/eload/services/EloadFunction";

    public java.lang.String getEloadFunctionAddress() {
        return EloadFunction_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EloadFunctionWSDDServiceName = "EloadFunction";

    public java.lang.String getEloadFunctionWSDDServiceName() {
        return EloadFunctionWSDDServiceName;
    }

    public void setEloadFunctionWSDDServiceName(java.lang.String name) {
        EloadFunctionWSDDServiceName = name;
    }

    public com.mbv.bp.common.executor.vinaphone.services.EloadFunction getEloadFunction() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EloadFunction_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEloadFunction(endpoint);
    }

    public com.mbv.bp.common.executor.vinaphone.services.EloadFunction getEloadFunction(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub _stub = new com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub(portAddress, this);
            _stub.setPortName(getEloadFunctionWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEloadFunctionEndpointAddress(java.lang.String address) {
        EloadFunction_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.mbv.bp.common.executor.vinaphone.services.EloadFunction.class.isAssignableFrom(serviceEndpointInterface)) {
                com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub _stub = new com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub(new java.net.URL(EloadFunction_address), this);
                _stub.setPortName(getEloadFunctionWSDDServiceName());
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
        if ("EloadFunction".equals(inputPortName)) {
            return getEloadFunction();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://mwallet.fss.com", "EloadFunctionService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://mwallet.fss.com", "EloadFunction"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EloadFunction".equals(portName)) {
            setEloadFunctionEndpointAddress(address);
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
