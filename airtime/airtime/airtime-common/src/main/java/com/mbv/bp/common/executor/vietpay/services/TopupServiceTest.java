

/**
 * TopupServiceTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */
    package com.mbv.bp.common.executor.vietpay.services;

    /*
     *  TopupServiceTest Junit test case
    */

    public class TopupServiceTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testcheckTransaction() throws java.lang.Exception{

        com.mbv.bp.common.executor.vietpay.services.TopupServiceStub stub =
                    new com.mbv.bp.common.executor.vietpay.services.TopupServiceStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckTransaction checkTransaction24=
                                                        (com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckTransaction)getTestObject(com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckTransaction.class);
                    // TODO : Fill in the checkTransaction24 here
                
                        assertNotNull(stub.checkTransaction(
                        checkTransaction24));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartcheckTransaction() throws java.lang.Exception{
            com.mbv.bp.common.executor.vietpay.services.TopupServiceStub stub = new com.mbv.bp.common.executor.vietpay.services.TopupServiceStub();
             com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckTransaction checkTransaction24=
                                                        (com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckTransaction)getTestObject(com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckTransaction.class);
                    // TODO : Fill in the checkTransaction24 here
                

                stub.startcheckTransaction(
                         checkTransaction24,
                    new tempCallbackN1000C()
                );
              


        }

        private class tempCallbackN1000C  extends com.mbv.bp.common.executor.vietpay.services.TopupServiceCallbackHandler{
            public tempCallbackN1000C(){ super(null);}

            public void receiveResultcheckTransaction(
                         com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckTransactionResponse result
                            ) {
                
            }

            public void receiveErrorcheckTransaction(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetServerList() throws java.lang.Exception{

        com.mbv.bp.common.executor.vietpay.services.TopupServiceStub stub =
                    new com.mbv.bp.common.executor.vietpay.services.TopupServiceStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.GetServerList getServerList26=
                                                        (com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.GetServerList)getTestObject(com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.GetServerList.class);
                    // TODO : Fill in the getServerList26 here
                
                        assertNotNull(stub.getServerList(
                        getServerList26));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetServerList() throws java.lang.Exception{
            com.mbv.bp.common.executor.vietpay.services.TopupServiceStub stub = new com.mbv.bp.common.executor.vietpay.services.TopupServiceStub();
             com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.GetServerList getServerList26=
                                                        (com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.GetServerList)getTestObject(com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.GetServerList.class);
                    // TODO : Fill in the getServerList26 here
                

                stub.startgetServerList(
                         getServerList26,
                    new tempCallbackN10033()
                );
              


        }

        private class tempCallbackN10033  extends com.mbv.bp.common.executor.vietpay.services.TopupServiceCallbackHandler{
            public tempCallbackN10033(){ super(null);}

            public void receiveResultgetServerList(
                         com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.GetServerListResponse result
                            ) {
                
            }

            public void receiveErrorgetServerList(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testcheckAccount() throws java.lang.Exception{

        com.mbv.bp.common.executor.vietpay.services.TopupServiceStub stub =
                    new com.mbv.bp.common.executor.vietpay.services.TopupServiceStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckAccount checkAccount28=
                                                        (com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckAccount)getTestObject(com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckAccount.class);
                    // TODO : Fill in the checkAccount28 here
                
                        assertNotNull(stub.checkAccount(
                        checkAccount28));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartcheckAccount() throws java.lang.Exception{
            com.mbv.bp.common.executor.vietpay.services.TopupServiceStub stub = new com.mbv.bp.common.executor.vietpay.services.TopupServiceStub();
             com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckAccount checkAccount28=
                                                        (com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckAccount)getTestObject(com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckAccount.class);
                    // TODO : Fill in the checkAccount28 here
                

                stub.startcheckAccount(
                         checkAccount28,
                    new tempCallbackN1005A()
                );
              


        }

        private class tempCallbackN1005A  extends com.mbv.bp.common.executor.vietpay.services.TopupServiceCallbackHandler{
            public tempCallbackN1005A(){ super(null);}

            public void receiveResultcheckAccount(
                         com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckAccountResponse result
                            ) {
                
            }

            public void receiveErrorcheckAccount(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testtopupLite() throws java.lang.Exception{

        com.mbv.bp.common.executor.vietpay.services.TopupServiceStub stub =
                    new com.mbv.bp.common.executor.vietpay.services.TopupServiceStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.TopupLite topupLite30=
                                                        (com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.TopupLite)getTestObject(com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.TopupLite.class);
                    // TODO : Fill in the topupLite30 here
                
                        assertNotNull(stub.topupLite(
                        topupLite30));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStarttopupLite() throws java.lang.Exception{
            com.mbv.bp.common.executor.vietpay.services.TopupServiceStub stub = new com.mbv.bp.common.executor.vietpay.services.TopupServiceStub();
             com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.TopupLite topupLite30=
                                                        (com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.TopupLite)getTestObject(com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.TopupLite.class);
                    // TODO : Fill in the topupLite30 here
                

                stub.starttopupLite(
                         topupLite30,
                    new tempCallbackN10081()
                );
              


        }

        private class tempCallbackN10081  extends com.mbv.bp.common.executor.vietpay.services.TopupServiceCallbackHandler{
            public tempCallbackN10081(){ super(null);}

            public void receiveResulttopupLite(
                         com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.TopupLiteResponse result
                            ) {
                
            }

            public void receiveErrortopupLite(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testpartnerBalance() throws java.lang.Exception{

        com.mbv.bp.common.executor.vietpay.services.TopupServiceStub stub =
                    new com.mbv.bp.common.executor.vietpay.services.TopupServiceStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.PartnerBalance partnerBalance32=
                                                        (com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.PartnerBalance)getTestObject(com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.PartnerBalance.class);
                    // TODO : Fill in the partnerBalance32 here
                
                        assertNotNull(stub.partnerBalance(
                        partnerBalance32));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartpartnerBalance() throws java.lang.Exception{
            com.mbv.bp.common.executor.vietpay.services.TopupServiceStub stub = new com.mbv.bp.common.executor.vietpay.services.TopupServiceStub();
             com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.PartnerBalance partnerBalance32=
                                                        (com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.PartnerBalance)getTestObject(com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.PartnerBalance.class);
                    // TODO : Fill in the partnerBalance32 here
                

                stub.startpartnerBalance(
                         partnerBalance32,
                    new tempCallbackN100A8()
                );
              


        }

        private class tempCallbackN100A8  extends com.mbv.bp.common.executor.vietpay.services.TopupServiceCallbackHandler{
            public tempCallbackN100A8(){ super(null);}

            public void receiveResultpartnerBalance(
                         com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.PartnerBalanceResponse result
                            ) {
                
            }

            public void receiveErrorpartnerBalance(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testtopup() throws java.lang.Exception{

        com.mbv.bp.common.executor.vietpay.services.TopupServiceStub stub =
                    new com.mbv.bp.common.executor.vietpay.services.TopupServiceStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.Topup topup34=
                                                        (com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.Topup)getTestObject(com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.Topup.class);
                    // TODO : Fill in the topup34 here
                
                        assertNotNull(stub.topup(
                        topup34));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStarttopup() throws java.lang.Exception{
            com.mbv.bp.common.executor.vietpay.services.TopupServiceStub stub = new com.mbv.bp.common.executor.vietpay.services.TopupServiceStub();
             com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.Topup topup34=
                                                        (com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.Topup)getTestObject(com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.Topup.class);
                    // TODO : Fill in the topup34 here
                

                stub.starttopup(
                         topup34,
                    new tempCallbackN100CF()
                );
              


        }

        private class tempCallbackN100CF  extends com.mbv.bp.common.executor.vietpay.services.TopupServiceCallbackHandler{
            public tempCallbackN100CF(){ super(null);}

            public void receiveResulttopup(
                         com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.TopupResponse result
                            ) {
                
            }

            public void receiveErrortopup(java.lang.Exception e) {
                fail();
            }

        }
      
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    