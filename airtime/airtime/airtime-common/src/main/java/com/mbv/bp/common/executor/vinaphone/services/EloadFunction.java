/**
 * EloadFunction.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.bp.common.executor.vinaphone.services;

public interface EloadFunction extends java.rmi.Remote {
    public com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput load(com.mbv.bp.common.executor.vinaphone.services.TransactionInput in) throws java.rmi.RemoteException;
    public com.mbv.bp.common.executor.vinaphone.services.LoginOutput login(com.mbv.bp.common.executor.vinaphone.services.BasicInput in) throws java.rmi.RemoteException;
    public com.mbv.bp.common.executor.vinaphone.services.BasicOutput logout(java.lang.String sessionId) throws java.rmi.RemoteException;
    public com.mbv.bp.common.executor.vinaphone.services.BasicOutput change_password(com.mbv.bp.common.executor.vinaphone.services.ChangeInput in) throws java.rmi.RemoteException;
    public com.mbv.bp.common.executor.vinaphone.services.BasicOutput change_mpin(com.mbv.bp.common.executor.vinaphone.services.ChangeInput in) throws java.rmi.RemoteException;
    public com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput stock_enquiry(com.mbv.bp.common.executor.vinaphone.services.BasicInput in) throws java.rmi.RemoteException;
    public com.mbv.bp.common.executor.vinaphone.services.CheckTransactionOutput check_transaction(com.mbv.bp.common.executor.vinaphone.services.CheckTransactionInput in) throws java.rmi.RemoteException;
    public com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput today_enquiry(com.mbv.bp.common.executor.vinaphone.services.BasicInput in) throws java.rmi.RemoteException;
    public com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput stock_transfer(com.mbv.bp.common.executor.vinaphone.services.TransactionInput in) throws java.rmi.RemoteException;
}
