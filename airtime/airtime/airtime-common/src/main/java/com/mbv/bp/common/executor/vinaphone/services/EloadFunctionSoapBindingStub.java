/**
 * EloadFunctionSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.bp.common.executor.vinaphone.services;

public class EloadFunctionSoapBindingStub extends org.apache.axis.client.Stub implements com.mbv.bp.common.executor.vinaphone.services.EloadFunction {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[9];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("load");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://mwallet.fss.com", "TransactionInput"), com.mbv.bp.common.executor.vinaphone.services.TransactionInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://mwallet.fss.com", "EnquiryOutput"));
        oper.setReturnClass(com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "loadReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("login");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://mwallet.fss.com", "BasicInput"), com.mbv.bp.common.executor.vinaphone.services.BasicInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://mwallet.fss.com", "LoginOutput"));
        oper.setReturnClass(com.mbv.bp.common.executor.vinaphone.services.LoginOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "loginReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("logout");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sessionId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://mwallet.fss.com", "BasicOutput"));
        oper.setReturnClass(com.mbv.bp.common.executor.vinaphone.services.BasicOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "logoutReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("change_password");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://mwallet.fss.com", "ChangeInput"), com.mbv.bp.common.executor.vinaphone.services.ChangeInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://mwallet.fss.com", "BasicOutput"));
        oper.setReturnClass(com.mbv.bp.common.executor.vinaphone.services.BasicOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "change_passwordReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("change_mpin");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://mwallet.fss.com", "ChangeInput"), com.mbv.bp.common.executor.vinaphone.services.ChangeInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://mwallet.fss.com", "BasicOutput"));
        oper.setReturnClass(com.mbv.bp.common.executor.vinaphone.services.BasicOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "change_mpinReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("stock_enquiry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://mwallet.fss.com", "BasicInput"), com.mbv.bp.common.executor.vinaphone.services.BasicInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://mwallet.fss.com", "EnquiryOutput"));
        oper.setReturnClass(com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "stock_enquiryReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("check_transaction");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://mwallet.fss.com", "CheckTransactionInput"), com.mbv.bp.common.executor.vinaphone.services.CheckTransactionInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://mwallet.fss.com", "CheckTransactionOutput"));
        oper.setReturnClass(com.mbv.bp.common.executor.vinaphone.services.CheckTransactionOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "check_transactionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("today_enquiry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://mwallet.fss.com", "BasicInput"), com.mbv.bp.common.executor.vinaphone.services.BasicInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://mwallet.fss.com", "EnquiryOutput"));
        oper.setReturnClass(com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "today_enquiryReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("stock_transfer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://mwallet.fss.com", "TransactionInput"), com.mbv.bp.common.executor.vinaphone.services.TransactionInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://mwallet.fss.com", "EnquiryOutput"));
        oper.setReturnClass(com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "stock_transferReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[8] = oper;

    }

    public EloadFunctionSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public EloadFunctionSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public EloadFunctionSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://mwallet.fss.com", "BasicInput");
            cachedSerQNames.add(qName);
            cls = com.mbv.bp.common.executor.vinaphone.services.BasicInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://mwallet.fss.com", "BasicOutput");
            cachedSerQNames.add(qName);
            cls = com.mbv.bp.common.executor.vinaphone.services.BasicOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://mwallet.fss.com", "ChangeInput");
            cachedSerQNames.add(qName);
            cls = com.mbv.bp.common.executor.vinaphone.services.ChangeInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://mwallet.fss.com", "CheckTransactionInput");
            cachedSerQNames.add(qName);
            cls = com.mbv.bp.common.executor.vinaphone.services.CheckTransactionInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://mwallet.fss.com", "CheckTransactionOutput");
            cachedSerQNames.add(qName);
            cls = com.mbv.bp.common.executor.vinaphone.services.CheckTransactionOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://mwallet.fss.com", "EnquiryOutput");
            cachedSerQNames.add(qName);
            cls = com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://mwallet.fss.com", "LoginOutput");
            cachedSerQNames.add(qName);
            cls = com.mbv.bp.common.executor.vinaphone.services.LoginOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://mwallet.fss.com", "TransactionInput");
            cachedSerQNames.add(qName);
            cls = com.mbv.bp.common.executor.vinaphone.services.TransactionInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://trans.mwallet.fss.com", "Transaction");
            cachedSerQNames.add(qName);
            cls = com.mbv.bp.common.executor.vinaphone.services.Transaction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput load(com.mbv.bp.common.executor.vinaphone.services.TransactionInput in) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://mwallet.fss.com", "load"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.mbv.bp.common.executor.vinaphone.services.LoginOutput login(com.mbv.bp.common.executor.vinaphone.services.BasicInput in) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://mwallet.fss.com", "login"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.mbv.bp.common.executor.vinaphone.services.LoginOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.mbv.bp.common.executor.vinaphone.services.LoginOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.mbv.bp.common.executor.vinaphone.services.LoginOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.mbv.bp.common.executor.vinaphone.services.BasicOutput logout(java.lang.String sessionId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://mwallet.fss.com", "logout"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sessionId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.mbv.bp.common.executor.vinaphone.services.BasicOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.mbv.bp.common.executor.vinaphone.services.BasicOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.mbv.bp.common.executor.vinaphone.services.BasicOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.mbv.bp.common.executor.vinaphone.services.BasicOutput change_password(com.mbv.bp.common.executor.vinaphone.services.ChangeInput in) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://mwallet.fss.com", "change_password"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.mbv.bp.common.executor.vinaphone.services.BasicOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.mbv.bp.common.executor.vinaphone.services.BasicOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.mbv.bp.common.executor.vinaphone.services.BasicOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.mbv.bp.common.executor.vinaphone.services.BasicOutput change_mpin(com.mbv.bp.common.executor.vinaphone.services.ChangeInput in) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://mwallet.fss.com", "change_mpin"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.mbv.bp.common.executor.vinaphone.services.BasicOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.mbv.bp.common.executor.vinaphone.services.BasicOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.mbv.bp.common.executor.vinaphone.services.BasicOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput stock_enquiry(com.mbv.bp.common.executor.vinaphone.services.BasicInput in) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://mwallet.fss.com", "stock_enquiry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.mbv.bp.common.executor.vinaphone.services.CheckTransactionOutput check_transaction(com.mbv.bp.common.executor.vinaphone.services.CheckTransactionInput in) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://mwallet.fss.com", "check_transaction"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.mbv.bp.common.executor.vinaphone.services.CheckTransactionOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.mbv.bp.common.executor.vinaphone.services.CheckTransactionOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.mbv.bp.common.executor.vinaphone.services.CheckTransactionOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput today_enquiry(com.mbv.bp.common.executor.vinaphone.services.BasicInput in) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://mwallet.fss.com", "today_enquiry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput stock_transfer(com.mbv.bp.common.executor.vinaphone.services.TransactionInput in) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://mwallet.fss.com", "stock_transfer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
