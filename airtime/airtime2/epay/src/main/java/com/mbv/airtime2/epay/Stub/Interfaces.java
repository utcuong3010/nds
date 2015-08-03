/**
 * Interfaces.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.airtime2.epay.Stub;

public interface Interfaces extends java.rmi.Remote {
    public com.mbv.airtime2.epay.Stub.PaymentCdvResult paymentCDV(java.lang.String requestId, java.lang.String partnerName, java.lang.String provider, int type, java.lang.String account, long amount, int timeOut, java.lang.String sign) throws java.rmi.RemoteException;
    public com.mbv.airtime2.epay.Stub.CheckOrdesrCDVResult checkOrdersCDV(java.lang.String requestId, java.lang.String partnerName, java.lang.String sign) throws java.rmi.RemoteException;
    public com.mbv.airtime2.epay.Stub.QueryBalanceResult queryBalance(java.lang.String partnerName, java.lang.String sign) throws java.rmi.RemoteException;
    public com.mbv.airtime2.epay.Stub.TopupResult topup(java.lang.String requestId, java.lang.String partnerName, java.lang.String provider, java.lang.String target, int amount, java.lang.String sign) throws java.rmi.RemoteException;
    public com.mbv.airtime2.epay.Stub.CheckTransResult checkTrans(java.lang.String requestId, java.lang.String partnerName, int type, java.lang.String sign) throws java.rmi.RemoteException;
    public com.mbv.airtime2.epay.Stub.DownloadSoftpinResult downloadSoftpin(java.lang.String requestId, java.lang.String partnerName, java.lang.String provider, int amount, int quantity, java.lang.String sign) throws java.rmi.RemoteException;
    public com.mbv.airtime2.epay.Stub.DownloadSoftpinResult reDownloadSoftpin(java.lang.String requestId, java.lang.String partnerName, java.lang.String sign) throws java.rmi.RemoteException;
    public int checkStore(java.lang.String partnerName, java.lang.String provider, int amount, java.lang.String sign) throws java.rmi.RemoteException;
}
