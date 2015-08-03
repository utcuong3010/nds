/**
 * UMarketSCImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.airtime2.mobifone.Stub;

public interface UMarketSCImpl extends java.rmi.Remote {
    public com.mbv.airtime2.mobifone.Stub.StandardBizResponse login(com.mbv.airtime2.mobifone.Stub.LoginRequestType parameters) throws java.rmi.RemoteException;
    public com.mbv.airtime2.mobifone.Stub.StandardBizResponse pin(com.mbv.airtime2.mobifone.Stub.PinRequestType paramPin) throws java.rmi.RemoteException;
    public com.mbv.airtime2.mobifone.Stub.CreatesessionResponseType createsession() throws java.rmi.RemoteException;
    public com.mbv.airtime2.mobifone.Stub.BalanceResponseType balance(com.mbv.airtime2.mobifone.Stub.BalanceRequestType paramBalance) throws java.rmi.RemoteException;
    public com.mbv.airtime2.mobifone.Stub.StandardBizResponse buy(com.mbv.airtime2.mobifone.Stub.BuyRequestType paramBuy) throws java.rmi.RemoteException;
    public java.lang.String generateSOAPpin(java.lang.String sessionid, java.lang.String user_name, java.lang.String password) throws java.rmi.RemoteException;
    public java.lang.String getTransaction_id(java.lang.Object conn, java.lang.String ez_trans_id) throws java.rmi.RemoteException;
}
