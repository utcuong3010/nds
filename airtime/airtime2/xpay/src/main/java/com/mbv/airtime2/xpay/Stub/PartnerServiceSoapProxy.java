package com.mbv.airtime2.xpay.Stub;

public class PartnerServiceSoapProxy implements com.mbv.airtime2.xpay.Stub.PartnerServiceSoap {
  private String _endpoint = null;
  private com.mbv.airtime2.xpay.Stub.PartnerServiceSoap partnerServiceSoap = null;
  
  public PartnerServiceSoapProxy() {
    _initPartnerServiceSoapProxy();
  }
  
  public PartnerServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initPartnerServiceSoapProxy();
  }
  
  private void _initPartnerServiceSoapProxy() {
    try {
      partnerServiceSoap = (new com.mbv.airtime2.xpay.Stub.PartnerServiceLocator()).getPartnerServiceSoap();
      if (partnerServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)partnerServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)partnerServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (partnerServiceSoap != null)
      ((javax.xml.rpc.Stub)partnerServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.mbv.airtime2.xpay.Stub.PartnerServiceSoap getPartnerServiceSoap() {
    if (partnerServiceSoap == null)
      _initPartnerServiceSoapProxy();
    return partnerServiceSoap;
  }
  
  public java.lang.String about() throws java.rmi.RemoteException{
    if (partnerServiceSoap == null)
      _initPartnerServiceSoapProxy();
    return partnerServiceSoap.about();
  }
  
  public java.lang.String echo() throws java.rmi.RemoteException{
    if (partnerServiceSoap == null)
      _initPartnerServiceSoapProxy();
    return partnerServiceSoap.echo();
  }
  
  public java.lang.String userRequest(java.lang.String request) throws java.rmi.RemoteException{
    if (partnerServiceSoap == null)
      _initPartnerServiceSoapProxy();
    return partnerServiceSoap.userRequest(request);
  }
  
  
}