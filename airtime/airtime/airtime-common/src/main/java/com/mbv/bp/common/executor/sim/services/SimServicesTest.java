

/**
 * SimServicesTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */
    package com.mbv.bp.common.executor.sim.services;

    /*
     *  SimServicesTest Junit test case
    */

    public class SimServicesTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testtxnRequest() throws java.lang.Exception{

        com.mbv.bp.common.executor.sim.services.SimServicesStub stub =
                    new com.mbv.bp.common.executor.sim.services.SimServicesStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.sim.services.SimServicesStub.TxnRequest txnRequest4=
                                                        (com.mbv.bp.common.executor.sim.services.SimServicesStub.TxnRequest)getTestObject(com.mbv.bp.common.executor.sim.services.SimServicesStub.TxnRequest.class);
                    // TODO : Fill in the txnRequest4 here
                
                        assertNotNull(stub.txnRequest(
                        txnRequest4));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStarttxnRequest() throws java.lang.Exception{
            com.mbv.bp.common.executor.sim.services.SimServicesStub stub = new com.mbv.bp.common.executor.sim.services.SimServicesStub();
             com.mbv.bp.common.executor.sim.services.SimServicesStub.TxnRequest txnRequest4=
                                                        (com.mbv.bp.common.executor.sim.services.SimServicesStub.TxnRequest)getTestObject(com.mbv.bp.common.executor.sim.services.SimServicesStub.TxnRequest.class);
                    // TODO : Fill in the txnRequest4 here
                

                stub.starttxnRequest(
                         txnRequest4,
                    new tempCallbackN1000C()
                );
              


        }

        private class tempCallbackN1000C  extends com.mbv.bp.common.executor.sim.services.SimServicesCallbackHandler{
            public tempCallbackN1000C(){ super(null);}

            public void receiveResulttxnRequest(
                         com.mbv.bp.common.executor.sim.services.SimServicesStub.TxnResponse result
                            ) {
                
            }

            public void receiveErrortxnRequest(java.lang.Exception e) {
                fail();
            }

        }
      
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    