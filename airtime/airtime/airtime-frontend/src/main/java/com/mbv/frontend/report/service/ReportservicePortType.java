/**
 * ReportservicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.frontend.report.service;

public interface ReportservicePortType extends java.rmi.Remote {

    /**
     * Service definition of function reportservice__createReport
     */
    public java.lang.String createReport(java.lang.String authType, java.lang.String authInfo, java.lang.String reportRequest, java.lang.String inputParameters) throws java.rmi.RemoteException;

    /**
     * Service definition of function reportservice__retrieveReport
     */
    public void retrieveReport(java.lang.String authType, java.lang.String authInfo, javax.xml.rpc.holders.StringHolder reportID, int timeout, javax.xml.rpc.holders.StringHolder reportStatus, javax.xml.rpc.holders.StringHolder reportData) throws java.rmi.RemoteException;

    /**
     * Service definition of function reportservice__getReportParameters
     */
    public java.lang.String getReportParameters(java.lang.String authType, java.lang.String authInfo, javax.xml.rpc.holders.StringHolder reportType) throws java.rmi.RemoteException;

    /**
     * Service definition of function reportservice__queryReport
     */
    public java.lang.String queryReport(java.lang.String authType, java.lang.String authInfo, java.lang.String filter, java.lang.String order, java.lang.String limit) throws java.rmi.RemoteException;

    /**
     * Service definition of function reportservice__countReport
     */
    public int countReport(java.lang.String authType, java.lang.String authInfo, java.lang.String filter) throws java.rmi.RemoteException;
}
