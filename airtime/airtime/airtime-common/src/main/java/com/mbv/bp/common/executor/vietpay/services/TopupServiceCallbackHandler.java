
/**
 * TopupServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */

    package com.mbv.bp.common.executor.vietpay.services;

    /**
     *  TopupServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class TopupServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public TopupServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public TopupServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for checkTransaction method
            * override this method for handling normal response from checkTransaction operation
            */
           public void receiveResultcheckTransaction(
                    com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckTransactionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkTransaction operation
           */
            public void receiveErrorcheckTransaction(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getServerList method
            * override this method for handling normal response from getServerList operation
            */
           public void receiveResultgetServerList(
                    com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.GetServerListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getServerList operation
           */
            public void receiveErrorgetServerList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for checkAccount method
            * override this method for handling normal response from checkAccount operation
            */
           public void receiveResultcheckAccount(
                    com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckAccountResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkAccount operation
           */
            public void receiveErrorcheckAccount(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for topupLite method
            * override this method for handling normal response from topupLite operation
            */
           public void receiveResulttopupLite(
                    com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.TopupLiteResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from topupLite operation
           */
            public void receiveErrortopupLite(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for partnerBalance method
            * override this method for handling normal response from partnerBalance operation
            */
           public void receiveResultpartnerBalance(
                    com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.PartnerBalanceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from partnerBalance operation
           */
            public void receiveErrorpartnerBalance(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for topup method
            * override this method for handling normal response from topup operation
            */
           public void receiveResulttopup(
                    com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.TopupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from topup operation
           */
            public void receiveErrortopup(java.lang.Exception e) {
            }
                


    }
    