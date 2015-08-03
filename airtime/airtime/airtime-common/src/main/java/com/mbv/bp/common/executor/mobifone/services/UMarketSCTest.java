

/**
 * UMarketSCTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */
    package com.mbv.bp.common.executor.mobifone.services;

    /*
     *  UMarketSCTest Junit test case
    */

    public class UMarketSCTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testdispenseVoucher() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint
        
           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DispenseVoucher dispenseVoucher390=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DispenseVoucher)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DispenseVoucher.class);
                    // TODO : Fill in the dispenseVoucher390 here
                
                        assertNotNull(stub.dispenseVoucher(
                        dispenseVoucher390));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdispenseVoucher() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DispenseVoucher dispenseVoucher390=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DispenseVoucher)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DispenseVoucher.class);
                    // TODO : Fill in the dispenseVoucher390 here
                

                stub.startdispenseVoucher(
                         dispenseVoucher390,
                    new tempCallbackN1000C()
                );
              


        }

        private class tempCallbackN1000C  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1000C(){ super(null);}

            public void receiveResultdispenseVoucher(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DispenseVoucherResponse result
                            ) {
                
            }

            public void receiveErrordispenseVoucher(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testadjustWallet() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AdjustWalletRequest adjustWalletRequest392=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AdjustWalletRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AdjustWalletRequest.class);
                    // TODO : Fill in the adjustWalletRequest392 here
                
                        assertNotNull(stub.adjustWallet(
                        adjustWalletRequest392));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartadjustWallet() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AdjustWalletRequest adjustWalletRequest392=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AdjustWalletRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AdjustWalletRequest.class);
                    // TODO : Fill in the adjustWalletRequest392 here
                

                stub.startadjustWallet(
                         adjustWalletRequest392,
                    new tempCallbackN10033()
                );
              


        }

        private class tempCallbackN10033  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10033(){ super(null);}

            public void receiveResultadjustWallet(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AdjustWalletResponse result
                            ) {
                
            }

            public void receiveErroradjustWallet(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdelRuleInstance() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleInstanceRequest delRuleInstanceRequest394=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleInstanceRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleInstanceRequest.class);
                    // TODO : Fill in the delRuleInstanceRequest394 here
                
                        assertNotNull(stub.delRuleInstance(
                        delRuleInstanceRequest394));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdelRuleInstance() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleInstanceRequest delRuleInstanceRequest394=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleInstanceRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleInstanceRequest.class);
                    // TODO : Fill in the delRuleInstanceRequest394 here
                

                stub.startdelRuleInstance(
                         delRuleInstanceRequest394,
                    new tempCallbackN1005A()
                );
              


        }

        private class tempCallbackN1005A  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1005A(){ super(null);}

            public void receiveResultdelRuleInstance(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleInstanceResponse result
                            ) {
                
            }

            public void receiveErrordelRuleInstance(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdeleteGroup() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteGroupRequest deleteGroupRequest396=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteGroupRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteGroupRequest.class);
                    // TODO : Fill in the deleteGroupRequest396 here
                
                        assertNotNull(stub.deleteGroup(
                        deleteGroupRequest396));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdeleteGroup() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteGroupRequest deleteGroupRequest396=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteGroupRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteGroupRequest.class);
                    // TODO : Fill in the deleteGroupRequest396 here
                

                stub.startdeleteGroup(
                         deleteGroupRequest396,
                    new tempCallbackN10081()
                );
              


        }

        private class tempCallbackN10081  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10081(){ super(null);}

            public void receiveResultdeleteGroup(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteGroupResponse result
                            ) {
                
            }

            public void receiveErrordeleteGroup(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testreverse() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Reverse reverse398=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Reverse)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Reverse.class);
                    // TODO : Fill in the reverse398 here
                
                        assertNotNull(stub.reverse(
                        reverse398));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartreverse() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Reverse reverse398=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Reverse)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Reverse.class);
                    // TODO : Fill in the reverse398 here
                

                stub.startreverse(
                         reverse398,
                    new tempCallbackN100A8()
                );
              


        }

        private class tempCallbackN100A8  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN100A8(){ super(null);}

            public void receiveResultreverse(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ReverseResponse result
                            ) {
                
            }

            public void receiveErrorreverse(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdelRule() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleRequest delRuleRequest400=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleRequest.class);
                    // TODO : Fill in the delRuleRequest400 here
                
                        assertNotNull(stub.delRule(
                        delRuleRequest400));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdelRule() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleRequest delRuleRequest400=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleRequest.class);
                    // TODO : Fill in the delRuleRequest400 here
                

                stub.startdelRule(
                         delRuleRequest400,
                    new tempCallbackN100CF()
                );
              


        }

        private class tempCallbackN100CF  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN100CF(){ super(null);}

            public void receiveResultdelRule(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleResponse result
                            ) {
                
            }

            public void receiveErrordelRule(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testauthorise() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise authorise402=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise.class);
                    // TODO : Fill in the authorise402 here
                
                        assertNotNull(stub.authorise(
                        authorise402));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartauthorise() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise authorise402=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise.class);
                    // TODO : Fill in the authorise402 here
                

                stub.startauthorise(
                         authorise402,
                    new tempCallbackN100F6()
                );
              


        }

        private class tempCallbackN100F6  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN100F6(){ super(null);}

            public void receiveResultauthorise(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AuthoriseResponse result
                            ) {
                
            }

            public void receiveErrorauthorise(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testmoveRuleEntityUp() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityUpRequest moveRuleEntityUpRequest404=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityUpRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityUpRequest.class);
                    // TODO : Fill in the moveRuleEntityUpRequest404 here
                
                        assertNotNull(stub.moveRuleEntityUp(
                        moveRuleEntityUpRequest404));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartmoveRuleEntityUp() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityUpRequest moveRuleEntityUpRequest404=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityUpRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityUpRequest.class);
                    // TODO : Fill in the moveRuleEntityUpRequest404 here
                

                stub.startmoveRuleEntityUp(
                         moveRuleEntityUpRequest404,
                    new tempCallbackN1011D()
                );
              


        }

        private class tempCallbackN1011D  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1011D(){ super(null);}

            public void receiveResultmoveRuleEntityUp(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityUpResponse result
                            ) {
                
            }

            public void receiveErrormoveRuleEntityUp(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testresetPin() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ResetPinRequest resetPinRequest406=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ResetPinRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ResetPinRequest.class);
                    // TODO : Fill in the resetPinRequest406 here
                
                        assertNotNull(stub.resetPin(
                        resetPinRequest406));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartresetPin() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ResetPinRequest resetPinRequest406=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ResetPinRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ResetPinRequest.class);
                    // TODO : Fill in the resetPinRequest406 here
                

                stub.startresetPin(
                         resetPinRequest406,
                    new tempCallbackN10144()
                );
              


        }

        private class tempCallbackN10144  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10144(){ super(null);}

            public void receiveResultresetPin(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ResetPinResponse result
                            ) {
                
            }

            public void receiveErrorresetPin(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetTransactionByID() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetTransactionByID getTransactionByID408=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetTransactionByID)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetTransactionByID.class);
                    // TODO : Fill in the getTransactionByID408 here
                
                        assertNotNull(stub.getTransactionByID(
                        getTransactionByID408));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetTransactionByID() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetTransactionByID getTransactionByID408=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetTransactionByID)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetTransactionByID.class);
                    // TODO : Fill in the getTransactionByID408 here
                

                stub.startgetTransactionByID(
                         getTransactionByID408,
                    new tempCallbackN1016B()
                );
              


        }

        private class tempCallbackN1016B  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1016B(){ super(null);}

            public void receiveResultgetTransactionByID(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.TransactionResponse result
                            ) {
                
            }

            public void receiveErrorgetTransactionByID(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testaddCapSet() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapSet addCapSet410=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapSet)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapSet.class);
                    // TODO : Fill in the addCapSet410 here
                
                        assertNotNull(stub.addCapSet(
                        addCapSet410));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartaddCapSet() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapSet addCapSet410=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapSet)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapSet.class);
                    // TODO : Fill in the addCapSet410 here
                

                stub.startaddCapSet(
                         addCapSet410,
                    new tempCallbackN10192()
                );
              


        }

        private class tempCallbackN10192  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10192(){ super(null);}

            public void receiveResultaddCapSet(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapSetResponse result
                            ) {
                
            }

            public void receiveErroraddCapSet(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdeactRuleInstance() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeactRuleInstanceRequest deactRuleInstanceRequest412=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeactRuleInstanceRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeactRuleInstanceRequest.class);
                    // TODO : Fill in the deactRuleInstanceRequest412 here
                
                        assertNotNull(stub.deactRuleInstance(
                        deactRuleInstanceRequest412));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdeactRuleInstance() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeactRuleInstanceRequest deactRuleInstanceRequest412=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeactRuleInstanceRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeactRuleInstanceRequest.class);
                    // TODO : Fill in the deactRuleInstanceRequest412 here
                

                stub.startdeactRuleInstance(
                         deactRuleInstanceRequest412,
                    new tempCallbackN101B9()
                );
              


        }

        private class tempCallbackN101B9  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN101B9(){ super(null);}

            public void receiveResultdeactRuleInstance(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeactRuleInstanceResponse result
                            ) {
                
            }

            public void receiveErrordeactRuleInstance(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetAllRules() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllRules getAllRules414=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllRules)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllRules.class);
                    // TODO : Fill in the getAllRules414 here
                
                        assertNotNull(stub.getAllRules(
                        getAllRules414));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetAllRules() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllRules getAllRules414=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllRules)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllRules.class);
                    // TODO : Fill in the getAllRules414 here
                

                stub.startgetAllRules(
                         getAllRules414,
                    new tempCallbackN101E0()
                );
              


        }

        private class tempCallbackN101E0  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN101E0(){ super(null);}

            public void receiveResultgetAllRules(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllRulesResponse result
                            ) {
                
            }

            public void receiveErrorgetAllRules(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testunmapRule() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapRuleRequest unmapRuleRequest416=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapRuleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapRuleRequest.class);
                    // TODO : Fill in the unmapRuleRequest416 here
                
                        assertNotNull(stub.unmapRule(
                        unmapRuleRequest416));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartunmapRule() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapRuleRequest unmapRuleRequest416=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapRuleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapRuleRequest.class);
                    // TODO : Fill in the unmapRuleRequest416 here
                

                stub.startunmapRule(
                         unmapRuleRequest416,
                    new tempCallbackN10207()
                );
              


        }

        private class tempCallbackN10207  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10207(){ super(null);}

            public void receiveResultunmapRule(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapRuleResponse result
                            ) {
                
            }

            public void receiveErrorunmapRule(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testactivate() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Activate activate418=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Activate)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Activate.class);
                    // TODO : Fill in the activate418 here
                
                        assertNotNull(stub.activate(
                        activate418));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartactivate() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Activate activate418=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Activate)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Activate.class);
                    // TODO : Fill in the activate418 here
                

                stub.startactivate(
                         activate418,
                    new tempCallbackN1022E()
                );
              


        }

        private class tempCallbackN1022E  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1022E(){ super(null);}

            public void receiveResultactivate(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ActivateResponse result
                            ) {
                
            }

            public void receiveErroractivate(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testmodifyAccount() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyAccountRequest modifyAccountRequest420=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyAccountRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyAccountRequest.class);
                    // TODO : Fill in the modifyAccountRequest420 here
                
                        assertNotNull(stub.modifyAccount(
                        modifyAccountRequest420));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartmodifyAccount() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyAccountRequest modifyAccountRequest420=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyAccountRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyAccountRequest.class);
                    // TODO : Fill in the modifyAccountRequest420 here
                

                stub.startmodifyAccount(
                         modifyAccountRequest420,
                    new tempCallbackN10255()
                );
              


        }

        private class tempCallbackN10255  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10255(){ super(null);}

            public void receiveResultmodifyAccount(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyAccountResponse result
                            ) {
                
            }

            public void receiveErrormodifyAccount(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testcreatesession() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Createsession createsession422=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Createsession)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Createsession.class);
                    // TODO : Fill in the createsession422 here
                
                        assertNotNull(stub.createsession(
                        createsession422));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartcreatesession() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Createsession createsession422=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Createsession)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Createsession.class);
                    // TODO : Fill in the createsession422 here
                

                stub.startcreatesession(
                         createsession422,
                    new tempCallbackN1027C()
                );
              


        }

        private class tempCallbackN1027C  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1027C(){ super(null);}

            public void receiveResultcreatesession(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreatesessionResponse result
                            ) {
                
            }

            public void receiveErrorcreatesession(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetAllCapDetailByCapsetId() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetailByCapsetIdRequest getAllCapDetailByCapsetIdRequest424=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetailByCapsetIdRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetailByCapsetIdRequest.class);
                    // TODO : Fill in the getAllCapDetailByCapsetIdRequest424 here
                
                        assertNotNull(stub.getAllCapDetailByCapsetId(
                        getAllCapDetailByCapsetIdRequest424));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetAllCapDetailByCapsetId() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetailByCapsetIdRequest getAllCapDetailByCapsetIdRequest424=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetailByCapsetIdRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetailByCapsetIdRequest.class);
                    // TODO : Fill in the getAllCapDetailByCapsetIdRequest424 here
                

                stub.startgetAllCapDetailByCapsetId(
                         getAllCapDetailByCapsetIdRequest424,
                    new tempCallbackN102A3()
                );
              


        }

        private class tempCallbackN102A3  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN102A3(){ super(null);}

            public void receiveResultgetAllCapDetailByCapsetId(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetailByCapsetIdResponse result
                            ) {
                
            }

            public void receiveErrorgetAllCapDetailByCapsetId(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testmodCapSet() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapSet modCapSet426=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapSet)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapSet.class);
                    // TODO : Fill in the modCapSet426 here
                
                        assertNotNull(stub.modCapSet(
                        modCapSet426));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartmodCapSet() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapSet modCapSet426=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapSet)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapSet.class);
                    // TODO : Fill in the modCapSet426 here
                

                stub.startmodCapSet(
                         modCapSet426,
                    new tempCallbackN102CA()
                );
              


        }

        private class tempCallbackN102CA  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN102CA(){ super(null);}

            public void receiveResultmodCapSet(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapSetResponse result
                            ) {
                
            }

            public void receiveErrormodCapSet(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdelCapDetail() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapDetail delCapDetail428=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapDetail)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapDetail.class);
                    // TODO : Fill in the delCapDetail428 here
                
                        assertNotNull(stub.delCapDetail(
                        delCapDetail428));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdelCapDetail() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapDetail delCapDetail428=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapDetail)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapDetail.class);
                    // TODO : Fill in the delCapDetail428 here
                

                stub.startdelCapDetail(
                         delCapDetail428,
                    new tempCallbackN102F1()
                );
              


        }

        private class tempCallbackN102F1  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN102F1(){ super(null);}

            public void receiveResultdelCapDetail(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapDetailResponse result
                            ) {
                
            }

            public void receiveErrordelCapDetail(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetAllCapDetails() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetails getAllCapDetails430=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetails)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetails.class);
                    // TODO : Fill in the getAllCapDetails430 here
                
                        assertNotNull(stub.getAllCapDetails(
                        getAllCapDetails430));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetAllCapDetails() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetails getAllCapDetails430=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetails)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetails.class);
                    // TODO : Fill in the getAllCapDetails430 here
                

                stub.startgetAllCapDetails(
                         getAllCapDetails430,
                    new tempCallbackN10318()
                );
              


        }

        private class tempCallbackN10318  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10318(){ super(null);}

            public void receiveResultgetAllCapDetails(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetailsResponse result
                            ) {
                
            }

            public void receiveErrorgetAllCapDetails(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testsales() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Sales sales432=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Sales)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Sales.class);
                    // TODO : Fill in the sales432 here
                
                        assertNotNull(stub.sales(
                        sales432));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartsales() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Sales sales432=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Sales)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Sales.class);
                    // TODO : Fill in the sales432 here
                

                stub.startsales(
                         sales432,
                    new tempCallbackN1033F()
                );
              


        }

        private class tempCallbackN1033F  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1033F(){ super(null);}

            public void receiveResultsales(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.SalesResponse result
                            ) {
                
            }

            public void receiveErrorsales(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetGroup() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupRequest getGroupRequest434=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupRequest.class);
                    // TODO : Fill in the getGroupRequest434 here
                
                        assertNotNull(stub.getGroup(
                        getGroupRequest434));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetGroup() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupRequest getGroupRequest434=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupRequest.class);
                    // TODO : Fill in the getGroupRequest434 here
                

                stub.startgetGroup(
                         getGroupRequest434,
                    new tempCallbackN10366()
                );
              


        }

        private class tempCallbackN10366  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10366(){ super(null);}

            public void receiveResultgetGroup(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupResponse result
                            ) {
                
            }

            public void receiveErrorgetGroup(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetRuleEntity() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityRequest getRuleEntityRequest436=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityRequest.class);
                    // TODO : Fill in the getRuleEntityRequest436 here
                
                        assertNotNull(stub.getRuleEntity(
                        getRuleEntityRequest436));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetRuleEntity() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityRequest getRuleEntityRequest436=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityRequest.class);
                    // TODO : Fill in the getRuleEntityRequest436 here
                

                stub.startgetRuleEntity(
                         getRuleEntityRequest436,
                    new tempCallbackN1038D()
                );
              


        }

        private class tempCallbackN1038D  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1038D(){ super(null);}

            public void receiveResultgetRuleEntity(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityResponse result
                            ) {
                
            }

            public void receiveErrorgetRuleEntity(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testactRuleInstance() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ActRuleInstanceRequest actRuleInstanceRequest438=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ActRuleInstanceRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ActRuleInstanceRequest.class);
                    // TODO : Fill in the actRuleInstanceRequest438 here
                
                        assertNotNull(stub.actRuleInstance(
                        actRuleInstanceRequest438));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartactRuleInstance() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ActRuleInstanceRequest actRuleInstanceRequest438=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ActRuleInstanceRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ActRuleInstanceRequest.class);
                    // TODO : Fill in the actRuleInstanceRequest438 here
                

                stub.startactRuleInstance(
                         actRuleInstanceRequest438,
                    new tempCallbackN103B4()
                );
              


        }

        private class tempCallbackN103B4  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN103B4(){ super(null);}

            public void receiveResultactRuleInstance(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ActRuleInstanceResponse result
                            ) {
                
            }

            public void receiveErroractRuleInstance(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdeactivate() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Deactivate deactivate440=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Deactivate)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Deactivate.class);
                    // TODO : Fill in the deactivate440 here
                
                        assertNotNull(stub.deactivate(
                        deactivate440));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdeactivate() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Deactivate deactivate440=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Deactivate)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Deactivate.class);
                    // TODO : Fill in the deactivate440 here
                

                stub.startdeactivate(
                         deactivate440,
                    new tempCallbackN103DB()
                );
              


        }

        private class tempCallbackN103DB  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN103DB(){ super(null);}

            public void receiveResultdeactivate(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeactivateResponse result
                            ) {
                
            }

            public void receiveErrordeactivate(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testnextid() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Nextid nextid442=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Nextid)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Nextid.class);
                    // TODO : Fill in the nextid442 here
                
                        assertNotNull(stub.nextid(
                        nextid442));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartnextid() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Nextid nextid442=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Nextid)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Nextid.class);
                    // TODO : Fill in the nextid442 here
                

                stub.startnextid(
                         nextid442,
                    new tempCallbackN10402()
                );
              


        }

        private class tempCallbackN10402  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10402(){ super(null);}

            public void receiveResultnextid(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.NextidResponse result
                            ) {
                
            }

            public void receiveErrornextid(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetCapDetailByID() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapDetailByID getCapDetailByID444=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapDetailByID)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapDetailByID.class);
                    // TODO : Fill in the getCapDetailByID444 here
                
                        assertNotNull(stub.getCapDetailByID(
                        getCapDetailByID444));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetCapDetailByID() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapDetailByID getCapDetailByID444=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapDetailByID)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapDetailByID.class);
                    // TODO : Fill in the getCapDetailByID444 here
                

                stub.startgetCapDetailByID(
                         getCapDetailByID444,
                    new tempCallbackN10429()
                );
              


        }

        private class tempCallbackN10429  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10429(){ super(null);}

            public void receiveResultgetCapDetailByID(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapDetailResponse result
                            ) {
                
            }

            public void receiveErrorgetCapDetailByID(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testjoinparent() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Joinparent joinparent446=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Joinparent)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Joinparent.class);
                    // TODO : Fill in the joinparent446 here
                
                        assertNotNull(stub.joinparent(
                        joinparent446));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartjoinparent() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Joinparent joinparent446=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Joinparent)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Joinparent.class);
                    // TODO : Fill in the joinparent446 here
                

                stub.startjoinparent(
                         joinparent446,
                    new tempCallbackN10450()
                );
              


        }

        private class tempCallbackN10450  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10450(){ super(null);}

            public void receiveResultjoinparent(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.JoinparentResponse result
                            ) {
                
            }

            public void receiveErrorjoinparent(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetRuleEntitiesByParent() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntitiesByParentRequest getRuleEntitiesByParentRequest448=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntitiesByParentRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntitiesByParentRequest.class);
                    // TODO : Fill in the getRuleEntitiesByParentRequest448 here
                
                        assertNotNull(stub.getRuleEntitiesByParent(
                        getRuleEntitiesByParentRequest448));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetRuleEntitiesByParent() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntitiesByParentRequest getRuleEntitiesByParentRequest448=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntitiesByParentRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntitiesByParentRequest.class);
                    // TODO : Fill in the getRuleEntitiesByParentRequest448 here
                

                stub.startgetRuleEntitiesByParent(
                         getRuleEntitiesByParentRequest448,
                    new tempCallbackN10477()
                );
              


        }

        private class tempCallbackN10477  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10477(){ super(null);}

            public void receiveResultgetRuleEntitiesByParent(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntitiesByParentResponse result
                            ) {
                
            }

            public void receiveErrorgetRuleEntitiesByParent(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testlink() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Link link450=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Link)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Link.class);
                    // TODO : Fill in the link450 here
                
                        assertNotNull(stub.link(
                        link450));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartlink() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Link link450=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Link)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Link.class);
                    // TODO : Fill in the link450 here
                

                stub.startlink(
                         link450,
                    new tempCallbackN1049E()
                );
              


        }

        private class tempCallbackN1049E  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1049E(){ super(null);}

            public void receiveResultlink(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LinkResponse result
                            ) {
                
            }

            public void receiveErrorlink(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testquery() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Query query452=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Query)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Query.class);
                    // TODO : Fill in the query452 here
                
                        assertNotNull(stub.query(
                        query452));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartquery() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Query query452=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Query)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Query.class);
                    // TODO : Fill in the query452 here
                

                stub.startquery(
                         query452,
                    new tempCallbackN104C5()
                );
              


        }

        private class tempCallbackN104C5  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN104C5(){ super(null);}

            public void receiveResultquery(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.QueryResponse result
                            ) {
                
            }

            public void receiveErrorquery(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testaddRuleInstance() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleInstanceRequest addRuleInstanceRequest454=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleInstanceRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleInstanceRequest.class);
                    // TODO : Fill in the addRuleInstanceRequest454 here
                
                        assertNotNull(stub.addRuleInstance(
                        addRuleInstanceRequest454));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartaddRuleInstance() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleInstanceRequest addRuleInstanceRequest454=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleInstanceRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleInstanceRequest.class);
                    // TODO : Fill in the addRuleInstanceRequest454 here
                

                stub.startaddRuleInstance(
                         addRuleInstanceRequest454,
                    new tempCallbackN104EC()
                );
              


        }

        private class tempCallbackN104EC  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN104EC(){ super(null);}

            public void receiveResultaddRuleInstance(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleInstanceResponse result
                            ) {
                
            }

            public void receiveErroraddRuleInstance(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testlogin() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Login login456=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Login)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Login.class);
                    // TODO : Fill in the login456 here
                
                        assertNotNull(stub.login(
                        login456));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartlogin() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Login login456=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Login)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Login.class);
                    // TODO : Fill in the login456 here
                

                stub.startlogin(
                         login456,
                    new tempCallbackN10513()
                );
              


        }

        private class tempCallbackN10513  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10513(){ super(null);}

            public void receiveResultlogin(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LoginResponse result
                            ) {
                
            }

            public void receiveErrorlogin(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetRuleTemplateList() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateListRequest getRuleTemplateListRequest458=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateListRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateListRequest.class);
                    // TODO : Fill in the getRuleTemplateListRequest458 here
                
                        assertNotNull(stub.getRuleTemplateList(
                        getRuleTemplateListRequest458));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetRuleTemplateList() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateListRequest getRuleTemplateListRequest458=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateListRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateListRequest.class);
                    // TODO : Fill in the getRuleTemplateListRequest458 here
                

                stub.startgetRuleTemplateList(
                         getRuleTemplateListRequest458,
                    new tempCallbackN1053A()
                );
              


        }

        private class tempCallbackN1053A  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1053A(){ super(null);}

            public void receiveResultgetRuleTemplateList(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateListResponse result
                            ) {
                
            }

            public void receiveErrorgetRuleTemplateList(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdonate() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Donate donate460=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Donate)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Donate.class);
                    // TODO : Fill in the donate460 here
                
                        assertNotNull(stub.donate(
                        donate460));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdonate() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Donate donate460=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Donate)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Donate.class);
                    // TODO : Fill in the donate460 here
                

                stub.startdonate(
                         donate460,
                    new tempCallbackN10561()
                );
              


        }

        private class tempCallbackN10561  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10561(){ super(null);}

            public void receiveResultdonate(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DonateResponse result
                            ) {
                
            }

            public void receiveErrordonate(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetVoucher() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetVoucherRequest getVoucherRequest462=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetVoucherRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetVoucherRequest.class);
                    // TODO : Fill in the getVoucherRequest462 here
                
                        assertNotNull(stub.getVoucher(
                        getVoucherRequest462));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetVoucher() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetVoucherRequest getVoucherRequest462=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetVoucherRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetVoucherRequest.class);
                    // TODO : Fill in the getVoucherRequest462 here
                

                stub.startgetVoucher(
                         getVoucherRequest462,
                    new tempCallbackN10588()
                );
              


        }

        private class tempCallbackN10588  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10588(){ super(null);}

            public void receiveResultgetVoucher(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetVoucherResponse result
                            ) {
                
            }

            public void receiveErrorgetVoucher(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testaddCapDetail() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapDetail addCapDetail464=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapDetail)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapDetail.class);
                    // TODO : Fill in the addCapDetail464 here
                
                        assertNotNull(stub.addCapDetail(
                        addCapDetail464));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartaddCapDetail() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapDetail addCapDetail464=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapDetail)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapDetail.class);
                    // TODO : Fill in the addCapDetail464 here
                

                stub.startaddCapDetail(
                         addCapDetail464,
                    new tempCallbackN105AF()
                );
              


        }

        private class tempCallbackN105AF  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN105AF(){ super(null);}

            public void receiveResultaddCapDetail(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapDetailResponse result
                            ) {
                
            }

            public void receiveErroraddCapDetail(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetAccountByReferenceIDAndType() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAccountByReferenceIDAndType getAccountByReferenceIDAndType466=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAccountByReferenceIDAndType)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAccountByReferenceIDAndType.class);
                    // TODO : Fill in the getAccountByReferenceIDAndType466 here
                
                        assertNotNull(stub.getAccountByReferenceIDAndType(
                        getAccountByReferenceIDAndType466));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetAccountByReferenceIDAndType() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAccountByReferenceIDAndType getAccountByReferenceIDAndType466=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAccountByReferenceIDAndType)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAccountByReferenceIDAndType.class);
                    // TODO : Fill in the getAccountByReferenceIDAndType466 here
                

                stub.startgetAccountByReferenceIDAndType(
                         getAccountByReferenceIDAndType466,
                    new tempCallbackN105D6()
                );
              


        }

        private class tempCallbackN105D6  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN105D6(){ super(null);}

            public void receiveResultgetAccountByReferenceIDAndType(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AccountResponse result
                            ) {
                
            }

            public void receiveErrorgetAccountByReferenceIDAndType(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testaddRule() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleRequest addRuleRequest468=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleRequest.class);
                    // TODO : Fill in the addRuleRequest468 here
                
                        assertNotNull(stub.addRule(
                        addRuleRequest468));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartaddRule() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleRequest addRuleRequest468=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleRequest.class);
                    // TODO : Fill in the addRuleRequest468 here
                

                stub.startaddRule(
                         addRuleRequest468,
                    new tempCallbackN105FD()
                );
              


        }

        private class tempCallbackN105FD  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN105FD(){ super(null);}

            public void receiveResultaddRule(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleResponse result
                            ) {
                
            }

            public void receiveErroraddRule(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testcreateVoucher() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateVoucher createVoucher470=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateVoucher)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateVoucher.class);
                    // TODO : Fill in the createVoucher470 here
                
                        assertNotNull(stub.createVoucher(
                        createVoucher470));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartcreateVoucher() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateVoucher createVoucher470=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateVoucher)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateVoucher.class);
                    // TODO : Fill in the createVoucher470 here
                

                stub.startcreateVoucher(
                         createVoucher470,
                    new tempCallbackN10624()
                );
              


        }

        private class tempCallbackN10624  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10624(){ super(null);}

            public void receiveResultcreateVoucher(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateVoucherResponse result
                            ) {
                
            }

            public void receiveErrorcreateVoucher(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testmodRule() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleRequest modRuleRequest472=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleRequest.class);
                    // TODO : Fill in the modRuleRequest472 here
                
                        assertNotNull(stub.modRule(
                        modRuleRequest472));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartmodRule() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleRequest modRuleRequest472=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleRequest.class);
                    // TODO : Fill in the modRuleRequest472 here
                

                stub.startmodRule(
                         modRuleRequest472,
                    new tempCallbackN1064B()
                );
              


        }

        private class tempCallbackN1064B  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1064B(){ super(null);}

            public void receiveResultmodRule(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleResponse result
                            ) {
                
            }

            public void receiveErrormodRule(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testmodCapDetail() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapDetailRequest modCapDetailRequest474=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapDetailRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapDetailRequest.class);
                    // TODO : Fill in the modCapDetailRequest474 here
                
                        assertNotNull(stub.modCapDetail(
                        modCapDetailRequest474));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartmodCapDetail() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapDetailRequest modCapDetailRequest474=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapDetailRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapDetailRequest.class);
                    // TODO : Fill in the modCapDetailRequest474 here
                

                stub.startmodCapDetail(
                         modCapDetailRequest474,
                    new tempCallbackN10672()
                );
              


        }

        private class tempCallbackN10672  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10672(){ super(null);}

            public void receiveResultmodCapDetail(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapDetailResponse result
                            ) {
                
            }

            public void receiveErrormodCapDetail(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testsell() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Sell sell476=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Sell)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Sell.class);
                    // TODO : Fill in the sell476 here
                
                        assertNotNull(stub.sell(
                        sell476));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartsell() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Sell sell476=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Sell)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Sell.class);
                    // TODO : Fill in the sell476 here
                

                stub.startsell(
                         sell476,
                    new tempCallbackN10699()
                );
              


        }

        private class tempCallbackN10699  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10699(){ super(null);}

            public void receiveResultsell(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.SellResponse result
                            ) {
                
            }

            public void receiveErrorsell(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetGroupsByType() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupsByTypeRequest getGroupsByTypeRequest478=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupsByTypeRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupsByTypeRequest.class);
                    // TODO : Fill in the getGroupsByTypeRequest478 here
                
                        assertNotNull(stub.getGroupsByType(
                        getGroupsByTypeRequest478));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetGroupsByType() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupsByTypeRequest getGroupsByTypeRequest478=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupsByTypeRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupsByTypeRequest.class);
                    // TODO : Fill in the getGroupsByTypeRequest478 here
                

                stub.startgetGroupsByType(
                         getGroupsByTypeRequest478,
                    new tempCallbackN106C0()
                );
              


        }

        private class tempCallbackN106C0  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN106C0(){ super(null);}

            public void receiveResultgetGroupsByType(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupsByTypeResponse result
                            ) {
                
            }

            public void receiveErrorgetGroupsByType(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testaddRuleEntity() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleEntityRequest addRuleEntityRequest480=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleEntityRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleEntityRequest.class);
                    // TODO : Fill in the addRuleEntityRequest480 here
                
                        assertNotNull(stub.addRuleEntity(
                        addRuleEntityRequest480));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartaddRuleEntity() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleEntityRequest addRuleEntityRequest480=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleEntityRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleEntityRequest.class);
                    // TODO : Fill in the addRuleEntityRequest480 here
                

                stub.startaddRuleEntity(
                         addRuleEntityRequest480,
                    new tempCallbackN106E7()
                );
              


        }

        private class tempCallbackN106E7  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN106E7(){ super(null);}

            public void receiveResultaddRuleEntity(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleEntityResponse result
                            ) {
                
            }

            public void receiveErroraddRuleEntity(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testtransfer() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Transfer transfer482=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Transfer)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Transfer.class);
                    // TODO : Fill in the transfer482 here
                
                        assertNotNull(stub.transfer(
                        transfer482));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStarttransfer() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Transfer transfer482=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Transfer)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Transfer.class);
                    // TODO : Fill in the transfer482 here
                

                stub.starttransfer(
                         transfer482,
                    new tempCallbackN1070E()
                );
              


        }

        private class tempCallbackN1070E  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1070E(){ super(null);}

            public void receiveResulttransfer(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.TransferResponse result
                            ) {
                
            }

            public void receiveErrortransfer(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testjoinchild() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Joinchild joinchild484=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Joinchild)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Joinchild.class);
                    // TODO : Fill in the joinchild484 here
                
                        assertNotNull(stub.joinchild(
                        joinchild484));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartjoinchild() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Joinchild joinchild484=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Joinchild)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Joinchild.class);
                    // TODO : Fill in the joinchild484 here
                

                stub.startjoinchild(
                         joinchild484,
                    new tempCallbackN10735()
                );
              


        }

        private class tempCallbackN10735  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10735(){ super(null);}

            public void receiveResultjoinchild(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.JoinchildResponse result
                            ) {
                
            }

            public void receiveErrorjoinchild(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetGroupByName() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupByNameRequest getGroupByNameRequest486=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupByNameRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupByNameRequest.class);
                    // TODO : Fill in the getGroupByNameRequest486 here
                
                        assertNotNull(stub.getGroupByName(
                        getGroupByNameRequest486));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetGroupByName() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupByNameRequest getGroupByNameRequest486=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupByNameRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupByNameRequest.class);
                    // TODO : Fill in the getGroupByNameRequest486 here
                

                stub.startgetGroupByName(
                         getGroupByNameRequest486,
                    new tempCallbackN1075C()
                );
              


        }

        private class tempCallbackN1075C  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1075C(){ super(null);}

            public void receiveResultgetGroupByName(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupByNameResponse result
                            ) {
                
            }

            public void receiveErrorgetGroupByName(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdelschedule() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delschedule delschedule488=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delschedule)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delschedule.class);
                    // TODO : Fill in the delschedule488 here
                
                        assertNotNull(stub.delschedule(
                        delschedule488));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdelschedule() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delschedule delschedule488=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delschedule)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delschedule.class);
                    // TODO : Fill in the delschedule488 here
                

                stub.startdelschedule(
                         delschedule488,
                    new tempCallbackN10783()
                );
              


        }

        private class tempCallbackN10783  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10783(){ super(null);}

            public void receiveResultdelschedule(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelscheduleResponse result
                            ) {
                
            }

            public void receiveErrordelschedule(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetAccountByReferenceID() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAccountByReferenceID getAccountByReferenceID490=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAccountByReferenceID)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAccountByReferenceID.class);
                    // TODO : Fill in the getAccountByReferenceID490 here
                
                        assertNotNull(stub.getAccountByReferenceID(
                        getAccountByReferenceID490));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetAccountByReferenceID() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAccountByReferenceID getAccountByReferenceID490=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAccountByReferenceID)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAccountByReferenceID.class);
                    // TODO : Fill in the getAccountByReferenceID490 here
                

                stub.startgetAccountByReferenceID(
                         getAccountByReferenceID490,
                    new tempCallbackN107AA()
                );
              


        }

        private class tempCallbackN107AA  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN107AA(){ super(null);}

            public void receiveResultgetAccountByReferenceID(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AccountsResponse result
                            ) {
                
            }

            public void receiveErrorgetAccountByReferenceID(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testbillcollect() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Billcollect billcollect492=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Billcollect)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Billcollect.class);
                    // TODO : Fill in the billcollect492 here
                
                        assertNotNull(stub.billcollect(
                        billcollect492));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartbillcollect() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Billcollect billcollect492=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Billcollect)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Billcollect.class);
                    // TODO : Fill in the billcollect492 here
                

                stub.startbillcollect(
                         billcollect492,
                    new tempCallbackN107D1()
                );
              


        }

        private class tempCallbackN107D1  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN107D1(){ super(null);}

            public void receiveResultbillcollect(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BillcollectResponse result
                            ) {
                
            }

            public void receiveErrorbillcollect(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetRuleInstance() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceRequest getRuleInstanceRequest494=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceRequest.class);
                    // TODO : Fill in the getRuleInstanceRequest494 here
                
                        assertNotNull(stub.getRuleInstance(
                        getRuleInstanceRequest494));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetRuleInstance() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceRequest getRuleInstanceRequest494=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceRequest.class);
                    // TODO : Fill in the getRuleInstanceRequest494 here
                

                stub.startgetRuleInstance(
                         getRuleInstanceRequest494,
                    new tempCallbackN107F8()
                );
              


        }

        private class tempCallbackN107F8  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN107F8(){ super(null);}

            public void receiveResultgetRuleInstance(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceResponse result
                            ) {
                
            }

            public void receiveErrorgetRuleInstance(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetRuleListByContextId() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleListByContextIdRequest getRuleListByContextIdRequest496=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleListByContextIdRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleListByContextIdRequest.class);
                    // TODO : Fill in the getRuleListByContextIdRequest496 here
                
                        assertNotNull(stub.getRuleListByContextId(
                        getRuleListByContextIdRequest496));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetRuleListByContextId() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleListByContextIdRequest getRuleListByContextIdRequest496=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleListByContextIdRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleListByContextIdRequest.class);
                    // TODO : Fill in the getRuleListByContextIdRequest496 here
                

                stub.startgetRuleListByContextId(
                         getRuleListByContextIdRequest496,
                    new tempCallbackN1081F()
                );
              


        }

        private class tempCallbackN1081F  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1081F(){ super(null);}

            public void receiveResultgetRuleListByContextId(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleListByContextIdResponse result
                            ) {
                
            }

            public void receiveErrorgetRuleListByContextId(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetRuleEntityList() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityListRequest getRuleEntityListRequest498=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityListRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityListRequest.class);
                    // TODO : Fill in the getRuleEntityListRequest498 here
                
                        assertNotNull(stub.getRuleEntityList(
                        getRuleEntityListRequest498));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetRuleEntityList() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityListRequest getRuleEntityListRequest498=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityListRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityListRequest.class);
                    // TODO : Fill in the getRuleEntityListRequest498 here
                

                stub.startgetRuleEntityList(
                         getRuleEntityListRequest498,
                    new tempCallbackN10846()
                );
              


        }

        private class tempCallbackN10846  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10846(){ super(null);}

            public void receiveResultgetRuleEntityList(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityListResponse result
                            ) {
                
            }

            public void receiveErrorgetRuleEntityList(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetCapByID() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapByID getCapByID500=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapByID)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapByID.class);
                    // TODO : Fill in the getCapByID500 here
                
                        assertNotNull(stub.getCapByID(
                        getCapByID500));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetCapByID() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapByID getCapByID500=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapByID)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapByID.class);
                    // TODO : Fill in the getCapByID500 here
                

                stub.startgetCapByID(
                         getCapByID500,
                    new tempCallbackN1086D()
                );
              


        }

        private class tempCallbackN1086D  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1086D(){ super(null);}

            public void receiveResultgetCapByID(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapResponse result
                            ) {
                
            }

            public void receiveErrorgetCapByID(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testmodify() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Modify modify502=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Modify)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Modify.class);
                    // TODO : Fill in the modify502 here
                
                        assertNotNull(stub.modify(
                        modify502));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartmodify() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Modify modify502=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Modify)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Modify.class);
                    // TODO : Fill in the modify502 here
                

                stub.startmodify(
                         modify502,
                    new tempCallbackN10894()
                );
              


        }

        private class tempCallbackN10894  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10894(){ super(null);}

            public void receiveResultmodify(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyResponse result
                            ) {
                
            }

            public void receiveErrormodify(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdelparent() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delparent delparent504=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delparent)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delparent.class);
                    // TODO : Fill in the delparent504 here
                
                        assertNotNull(stub.delparent(
                        delparent504));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdelparent() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delparent delparent504=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delparent)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delparent.class);
                    // TODO : Fill in the delparent504 here
                

                stub.startdelparent(
                         delparent504,
                    new tempCallbackN108BB()
                );
              


        }

        private class tempCallbackN108BB  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN108BB(){ super(null);}

            public void receiveResultdelparent(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelparentResponse result
                            ) {
                
            }

            public void receiveErrordelparent(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testmodifyGroup() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyGroupRequest modifyGroupRequest506=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyGroupRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyGroupRequest.class);
                    // TODO : Fill in the modifyGroupRequest506 here
                
                        assertNotNull(stub.modifyGroup(
                        modifyGroupRequest506));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartmodifyGroup() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyGroupRequest modifyGroupRequest506=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyGroupRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyGroupRequest.class);
                    // TODO : Fill in the modifyGroupRequest506 here
                

                stub.startmodifyGroup(
                         modifyGroupRequest506,
                    new tempCallbackN108E2()
                );
              


        }

        private class tempCallbackN108E2  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN108E2(){ super(null);}

            public void receiveResultmodifyGroup(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyGroupResponse result
                            ) {
                
            }

            public void receiveErrormodifyGroup(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetAgentGroupByAgentID() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentGroupByAgentID getAgentGroupByAgentID508=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentGroupByAgentID)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentGroupByAgentID.class);
                    // TODO : Fill in the getAgentGroupByAgentID508 here
                
                        assertNotNull(stub.getAgentGroupByAgentID(
                        getAgentGroupByAgentID508));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetAgentGroupByAgentID() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentGroupByAgentID getAgentGroupByAgentID508=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentGroupByAgentID)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentGroupByAgentID.class);
                    // TODO : Fill in the getAgentGroupByAgentID508 here
                

                stub.startgetAgentGroupByAgentID(
                         getAgentGroupByAgentID508,
                    new tempCallbackN10909()
                );
              


        }

        private class tempCallbackN10909  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10909(){ super(null);}

            public void receiveResultgetAgentGroupByAgentID(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AgentGroupsResponse result
                            ) {
                
            }

            public void receiveErrorgetAgentGroupByAgentID(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testbillpay() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Billpay billpay510=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Billpay)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Billpay.class);
                    // TODO : Fill in the billpay510 here
                
                        assertNotNull(stub.billpay(
                        billpay510));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartbillpay() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Billpay billpay510=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Billpay)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Billpay.class);
                    // TODO : Fill in the billpay510 here
                

                stub.startbillpay(
                         billpay510,
                    new tempCallbackN10930()
                );
              


        }

        private class tempCallbackN10930  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10930(){ super(null);}

            public void receiveResultbillpay(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BillpayResponse result
                            ) {
                
            }

            public void receiveErrorbillpay(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testlastTransactions() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LastTransactionsRequest lastTransactionsRequest512=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LastTransactionsRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LastTransactionsRequest.class);
                    // TODO : Fill in the lastTransactionsRequest512 here
                
                        assertNotNull(stub.lastTransactions(
                        lastTransactionsRequest512));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartlastTransactions() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LastTransactionsRequest lastTransactionsRequest512=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LastTransactionsRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LastTransactionsRequest.class);
                    // TODO : Fill in the lastTransactionsRequest512 here
                

                stub.startlastTransactions(
                         lastTransactionsRequest512,
                    new tempCallbackN10957()
                );
              


        }

        private class tempCallbackN10957  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10957(){ super(null);}

            public void receiveResultlastTransactions(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LastTransactionsResponse result
                            ) {
                
            }

            public void receiveErrorlastTransactions(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testfinalizeExtTrans() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.FinalizeExtTrans finalizeExtTrans514=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.FinalizeExtTrans)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.FinalizeExtTrans.class);
                    // TODO : Fill in the finalizeExtTrans514 here
                
                        assertNotNull(stub.finalizeExtTrans(
                        finalizeExtTrans514));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartfinalizeExtTrans() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.FinalizeExtTrans finalizeExtTrans514=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.FinalizeExtTrans)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.FinalizeExtTrans.class);
                    // TODO : Fill in the finalizeExtTrans514 here
                

                stub.startfinalizeExtTrans(
                         finalizeExtTrans514,
                    new tempCallbackN1097E()
                );
              


        }

        private class tempCallbackN1097E  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN1097E(){ super(null);}

            public void receiveResultfinalizeExtTrans(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.FinalizeExtTransResponse result
                            ) {
                
            }

            public void receiveErrorfinalizeExtTrans(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdelRuleEntity() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleEntityRequest delRuleEntityRequest516=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleEntityRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleEntityRequest.class);
                    // TODO : Fill in the delRuleEntityRequest516 here
                
                        assertNotNull(stub.delRuleEntity(
                        delRuleEntityRequest516));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdelRuleEntity() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleEntityRequest delRuleEntityRequest516=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleEntityRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleEntityRequest.class);
                    // TODO : Fill in the delRuleEntityRequest516 here
                

                stub.startdelRuleEntity(
                         delRuleEntityRequest516,
                    new tempCallbackN109A5()
                );
              


        }

        private class tempCallbackN109A5  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN109A5(){ super(null);}

            public void receiveResultdelRuleEntity(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleEntityResponse result
                            ) {
                
            }

            public void receiveErrordelRuleEntity(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testregister() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Register register518=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Register)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Register.class);
                    // TODO : Fill in the register518 here
                
                        assertNotNull(stub.register(
                        register518));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartregister() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Register register518=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Register)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Register.class);
                    // TODO : Fill in the register518 here
                

                stub.startregister(
                         register518,
                    new tempCallbackN109CC()
                );
              


        }

        private class tempCallbackN109CC  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN109CC(){ super(null);}

            public void receiveResultregister(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.RegisterResponse result
                            ) {
                
            }

            public void receiveErrorregister(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdelCapSet() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapSet delCapSet520=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapSet)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapSet.class);
                    // TODO : Fill in the delCapSet520 here
                
                        assertNotNull(stub.delCapSet(
                        delCapSet520));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdelCapSet() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapSet delCapSet520=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapSet)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapSet.class);
                    // TODO : Fill in the delCapSet520 here
                

                stub.startdelCapSet(
                         delCapSet520,
                    new tempCallbackN109F3()
                );
              


        }

        private class tempCallbackN109F3  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN109F3(){ super(null);}

            public void receiveResultdelCapSet(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapSetResponse result
                            ) {
                
            }

            public void receiveErrordelCapSet(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testaddSchedule() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddScheduleRequest addScheduleRequest522=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddScheduleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddScheduleRequest.class);
                    // TODO : Fill in the addScheduleRequest522 here
                
                        assertNotNull(stub.addSchedule(
                        addScheduleRequest522));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartaddSchedule() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddScheduleRequest addScheduleRequest522=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddScheduleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddScheduleRequest.class);
                    // TODO : Fill in the addScheduleRequest522 here
                

                stub.startaddSchedule(
                         addScheduleRequest522,
                    new tempCallbackN10A1A()
                );
              


        }

        private class tempCallbackN10A1A  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10A1A(){ super(null);}

            public void receiveResultaddSchedule(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddScheduleResponse result
                            ) {
                
            }

            public void receiveErroraddSchedule(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdeleteVoucher() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteVoucher deleteVoucher524=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteVoucher)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteVoucher.class);
                    // TODO : Fill in the deleteVoucher524 here
                
                        assertNotNull(stub.deleteVoucher(
                        deleteVoucher524));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdeleteVoucher() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteVoucher deleteVoucher524=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteVoucher)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteVoucher.class);
                    // TODO : Fill in the deleteVoucher524 here
                

                stub.startdeleteVoucher(
                         deleteVoucher524,
                    new tempCallbackN10A41()
                );
              


        }

        private class tempCallbackN10A41  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10A41(){ super(null);}

            public void receiveResultdeleteVoucher(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteVoucherResponse result
                            ) {
                
            }

            public void receiveErrordeleteVoucher(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testbankcashout() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Bankcashout bankcashout526=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Bankcashout)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Bankcashout.class);
                    // TODO : Fill in the bankcashout526 here
                
                        assertNotNull(stub.bankcashout(
                        bankcashout526));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartbankcashout() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Bankcashout bankcashout526=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Bankcashout)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Bankcashout.class);
                    // TODO : Fill in the bankcashout526 here
                

                stub.startbankcashout(
                         bankcashout526,
                    new tempCallbackN10A68()
                );
              


        }

        private class tempCallbackN10A68  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10A68(){ super(null);}

            public void receiveResultbankcashout(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BankcashoutResponse result
                            ) {
                
            }

            public void receiveErrorbankcashout(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testcreatecoupon() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Createcoupon createcoupon528=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Createcoupon)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Createcoupon.class);
                    // TODO : Fill in the createcoupon528 here
                
                        assertNotNull(stub.createcoupon(
                        createcoupon528));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartcreatecoupon() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Createcoupon createcoupon528=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Createcoupon)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Createcoupon.class);
                    // TODO : Fill in the createcoupon528 here
                

                stub.startcreatecoupon(
                         createcoupon528,
                    new tempCallbackN10A8F()
                );
              


        }

        private class tempCallbackN10A8F  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10A8F(){ super(null);}

            public void receiveResultcreatecoupon(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreatecouponResponse result
                            ) {
                
            }

            public void receiveErrorcreatecoupon(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testmoveRuleEntityDown() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityDownRequest moveRuleEntityDownRequest530=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityDownRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityDownRequest.class);
                    // TODO : Fill in the moveRuleEntityDownRequest530 here
                
                        assertNotNull(stub.moveRuleEntityDown(
                        moveRuleEntityDownRequest530));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartmoveRuleEntityDown() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityDownRequest moveRuleEntityDownRequest530=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityDownRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityDownRequest.class);
                    // TODO : Fill in the moveRuleEntityDownRequest530 here
                

                stub.startmoveRuleEntityDown(
                         moveRuleEntityDownRequest530,
                    new tempCallbackN10AB6()
                );
              


        }

        private class tempCallbackN10AB6  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10AB6(){ super(null);}

            public void receiveResultmoveRuleEntityDown(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityDownResponse result
                            ) {
                
            }

            public void receiveErrormoveRuleEntityDown(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetRule() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleRequest getRuleRequest532=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleRequest.class);
                    // TODO : Fill in the getRuleRequest532 here
                
                        assertNotNull(stub.getRule(
                        getRuleRequest532));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetRule() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleRequest getRuleRequest532=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleRequest.class);
                    // TODO : Fill in the getRuleRequest532 here
                

                stub.startgetRule(
                         getRuleRequest532,
                    new tempCallbackN10ADD()
                );
              


        }

        private class tempCallbackN10ADD  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10ADD(){ super(null);}

            public void receiveResultgetRule(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleResponse result
                            ) {
                
            }

            public void receiveErrorgetRule(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testconfirm() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Confirm confirm534=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Confirm)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Confirm.class);
                    // TODO : Fill in the confirm534 here
                
                        assertNotNull(stub.confirm(
                        confirm534));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartconfirm() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Confirm confirm534=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Confirm)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Confirm.class);
                    // TODO : Fill in the confirm534 here
                

                stub.startconfirm(
                         confirm534,
                    new tempCallbackN10B04()
                );
              


        }

        private class tempCallbackN10B04  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10B04(){ super(null);}

            public void receiveResultconfirm(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ConfirmResponse result
                            ) {
                
            }

            public void receiveErrorconfirm(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testauthorise2() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise2 authorise2536=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise2)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise2.class);
                    // TODO : Fill in the authorise2536 here
                
                        assertNotNull(stub.authorise2(
                        authorise2536));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartauthorise2() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise2 authorise2536=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise2)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise2.class);
                    // TODO : Fill in the authorise2536 here
                

                stub.startauthorise2(
                         authorise2536,
                    new tempCallbackN10B2B()
                );
              


        }

        private class tempCallbackN10B2B  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10B2B(){ super(null);}

            public void receiveResultauthorise2(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise2Response result
                            ) {
                
            }

            public void receiveErrorauthorise2(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testsellStock() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.SellStockRequest sellStockRequest538=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.SellStockRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.SellStockRequest.class);
                    // TODO : Fill in the sellStockRequest538 here
                
                        assertNotNull(stub.sellStock(
                        sellStockRequest538));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartsellStock() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.SellStockRequest sellStockRequest538=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.SellStockRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.SellStockRequest.class);
                    // TODO : Fill in the sellStockRequest538 here
                

                stub.startsellStock(
                         sellStockRequest538,
                    new tempCallbackN10B52()
                );
              


        }

        private class tempCallbackN10B52  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10B52(){ super(null);}

            public void receiveResultsellStock(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.SellStockResponse result
                            ) {
                
            }

            public void receiveErrorsellStock(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetAgentByReference() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentByReference getAgentByReference540=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentByReference)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentByReference.class);
                    // TODO : Fill in the getAgentByReference540 here
                
                        assertNotNull(stub.getAgentByReference(
                        getAgentByReference540));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetAgentByReference() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentByReference getAgentByReference540=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentByReference)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentByReference.class);
                    // TODO : Fill in the getAgentByReference540 here
                

                stub.startgetAgentByReference(
                         getAgentByReference540,
                    new tempCallbackN10B79()
                );
              


        }

        private class tempCallbackN10B79  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10B79(){ super(null);}

            public void receiveResultgetAgentByReference(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AgentResponse result
                            ) {
                
            }

            public void receiveErrorgetAgentByReference(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetAllAgentGroups() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllAgentGroups getAllAgentGroups542=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllAgentGroups)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllAgentGroups.class);
                    // TODO : Fill in the getAllAgentGroups542 here
                
                        assertNotNull(stub.getAllAgentGroups(
                        getAllAgentGroups542));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetAllAgentGroups() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllAgentGroups getAllAgentGroups542=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllAgentGroups)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllAgentGroups.class);
                    // TODO : Fill in the getAllAgentGroups542 here
                

                stub.startgetAllAgentGroups(
                         getAllAgentGroups542,
                    new tempCallbackN10BA0()
                );
              


        }

        private class tempCallbackN10BA0  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10BA0(){ super(null);}

            public void receiveResultgetAllAgentGroups(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AgentGroupsResponse result
                            ) {
                
            }

            public void receiveErrorgetAllAgentGroups(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testmodRuleEntity() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleEntityRequest modRuleEntityRequest544=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleEntityRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleEntityRequest.class);
                    // TODO : Fill in the modRuleEntityRequest544 here
                
                        assertNotNull(stub.modRuleEntity(
                        modRuleEntityRequest544));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartmodRuleEntity() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleEntityRequest modRuleEntityRequest544=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleEntityRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleEntityRequest.class);
                    // TODO : Fill in the modRuleEntityRequest544 here
                

                stub.startmodRuleEntity(
                         modRuleEntityRequest544,
                    new tempCallbackN10BC7()
                );
              


        }

        private class tempCallbackN10BC7  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10BC7(){ super(null);}

            public void receiveResultmodRuleEntity(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleEntityResponse result
                            ) {
                
            }

            public void receiveErrormodRuleEntity(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetAgentByReferenceID() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentByReferenceID getAgentByReferenceID546=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentByReferenceID)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentByReferenceID.class);
                    // TODO : Fill in the getAgentByReferenceID546 here
                
                        assertNotNull(stub.getAgentByReferenceID(
                        getAgentByReferenceID546));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetAgentByReferenceID() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentByReferenceID getAgentByReferenceID546=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentByReferenceID)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAgentByReferenceID.class);
                    // TODO : Fill in the getAgentByReferenceID546 here
                

                stub.startgetAgentByReferenceID(
                         getAgentByReferenceID546,
                    new tempCallbackN10BEE()
                );
              


        }

        private class tempCallbackN10BEE  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10BEE(){ super(null);}

            public void receiveResultgetAgentByReferenceID(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AgentResponse result
                            ) {
                
            }

            public void receiveErrorgetAgentByReferenceID(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdelchild() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delchild delchild548=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delchild)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delchild.class);
                    // TODO : Fill in the delchild548 here
                
                        assertNotNull(stub.delchild(
                        delchild548));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdelchild() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delchild delchild548=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delchild)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delchild.class);
                    // TODO : Fill in the delchild548 here
                

                stub.startdelchild(
                         delchild548,
                    new tempCallbackN10C15()
                );
              


        }

        private class tempCallbackN10C15  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10C15(){ super(null);}

            public void receiveResultdelchild(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelchildResponse result
                            ) {
                
            }

            public void receiveErrordelchild(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testmapAgent() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapAgentRequest mapAgentRequest550=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapAgentRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapAgentRequest.class);
                    // TODO : Fill in the mapAgentRequest550 here
                
                        assertNotNull(stub.mapAgent(
                        mapAgentRequest550));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartmapAgent() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapAgentRequest mapAgentRequest550=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapAgentRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapAgentRequest.class);
                    // TODO : Fill in the mapAgentRequest550 here
                

                stub.startmapAgent(
                         mapAgentRequest550,
                    new tempCallbackN10C3C()
                );
              


        }

        private class tempCallbackN10C3C  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10C3C(){ super(null);}

            public void receiveResultmapAgent(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapAgentResponse result
                            ) {
                
            }

            public void receiveErrormapAgent(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testbuy() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Buy buy552=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Buy)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Buy.class);
                    // TODO : Fill in the buy552 here
                
                        assertNotNull(stub.buy(
                        buy552));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartbuy() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Buy buy552=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Buy)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Buy.class);
                    // TODO : Fill in the buy552 here
                

                stub.startbuy(
                         buy552,
                    new tempCallbackN10C63()
                );
              


        }

        private class tempCallbackN10C63  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10C63(){ super(null);}

            public void receiveResultbuy(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BuyResponse result
                            ) {
                
            }

            public void receiveErrorbuy(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdelete() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delete delete554=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delete)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delete.class);
                    // TODO : Fill in the delete554 here
                
                        assertNotNull(stub.delete(
                        delete554));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdelete() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delete delete554=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delete)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Delete.class);
                    // TODO : Fill in the delete554 here
                

                stub.startdelete(
                         delete554,
                    new tempCallbackN10C8A()
                );
              


        }

        private class tempCallbackN10C8A  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10C8A(){ super(null);}

            public void receiveResultdelete(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteResponse result
                            ) {
                
            }

            public void receiveErrordelete(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testbalance() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Balance balance556=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Balance)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Balance.class);
                    // TODO : Fill in the balance556 here
                
                        assertNotNull(stub.balance(
                        balance556));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartbalance() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Balance balance556=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Balance)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Balance.class);
                    // TODO : Fill in the balance556 here
                

                stub.startbalance(
                         balance556,
                    new tempCallbackN10CB1()
                );
              


        }

        private class tempCallbackN10CB1  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10CB1(){ super(null);}

            public void receiveResultbalance(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BalanceResponse result
                            ) {
                
            }

            public void receiveErrorbalance(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testpin() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Pin pin558=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Pin)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Pin.class);
                    // TODO : Fill in the pin558 here
                
                        assertNotNull(stub.pin(
                        pin558));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartpin() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Pin pin558=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Pin)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Pin.class);
                    // TODO : Fill in the pin558 here
                

                stub.startpin(
                         pin558,
                    new tempCallbackN10CD8()
                );
              


        }

        private class tempCallbackN10CD8  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10CD8(){ super(null);}

            public void receiveResultpin(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.PinResponse result
                            ) {
                
            }

            public void receiveErrorpin(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testmapRule() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapRuleRequest mapRuleRequest560=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapRuleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapRuleRequest.class);
                    // TODO : Fill in the mapRuleRequest560 here
                
                        assertNotNull(stub.mapRule(
                        mapRuleRequest560));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartmapRule() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapRuleRequest mapRuleRequest560=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapRuleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapRuleRequest.class);
                    // TODO : Fill in the mapRuleRequest560 here
                

                stub.startmapRule(
                         mapRuleRequest560,
                    new tempCallbackN10CFF()
                );
              


        }

        private class tempCallbackN10CFF  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10CFF(){ super(null);}

            public void receiveResultmapRule(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapRuleResponse result
                            ) {
                
            }

            public void receiveErrormapRule(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testcreateGroup() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateGroupRequest createGroupRequest562=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateGroupRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateGroupRequest.class);
                    // TODO : Fill in the createGroupRequest562 here
                
                        assertNotNull(stub.createGroup(
                        createGroupRequest562));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartcreateGroup() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateGroupRequest createGroupRequest562=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateGroupRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateGroupRequest.class);
                    // TODO : Fill in the createGroupRequest562 here
                

                stub.startcreateGroup(
                         createGroupRequest562,
                    new tempCallbackN10D26()
                );
              


        }

        private class tempCallbackN10D26  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10D26(){ super(null);}

            public void receiveResultcreateGroup(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateGroupResponse result
                            ) {
                
            }

            public void receiveErrorcreateGroup(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testmodRuleInstance() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleInstanceRequest modRuleInstanceRequest564=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleInstanceRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleInstanceRequest.class);
                    // TODO : Fill in the modRuleInstanceRequest564 here
                
                        assertNotNull(stub.modRuleInstance(
                        modRuleInstanceRequest564));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartmodRuleInstance() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleInstanceRequest modRuleInstanceRequest564=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleInstanceRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleInstanceRequest.class);
                    // TODO : Fill in the modRuleInstanceRequest564 here
                

                stub.startmodRuleInstance(
                         modRuleInstanceRequest564,
                    new tempCallbackN10D4D()
                );
              


        }

        private class tempCallbackN10D4D  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10D4D(){ super(null);}

            public void receiveResultmodRuleInstance(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleInstanceResponse result
                            ) {
                
            }

            public void receiveErrormodRuleInstance(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testbankcashin() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Bankcashin bankcashin566=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Bankcashin)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Bankcashin.class);
                    // TODO : Fill in the bankcashin566 here
                
                        assertNotNull(stub.bankcashin(
                        bankcashin566));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartbankcashin() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Bankcashin bankcashin566=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Bankcashin)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Bankcashin.class);
                    // TODO : Fill in the bankcashin566 here
                

                stub.startbankcashin(
                         bankcashin566,
                    new tempCallbackN10D74()
                );
              


        }

        private class tempCallbackN10D74  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10D74(){ super(null);}

            public void receiveResultbankcashin(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BankcashinResponse result
                            ) {
                
            }

            public void receiveErrorbankcashin(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetRuleInstanceList() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceListRequest getRuleInstanceListRequest568=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceListRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceListRequest.class);
                    // TODO : Fill in the getRuleInstanceListRequest568 here
                
                        assertNotNull(stub.getRuleInstanceList(
                        getRuleInstanceListRequest568));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetRuleInstanceList() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceListRequest getRuleInstanceListRequest568=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceListRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceListRequest.class);
                    // TODO : Fill in the getRuleInstanceListRequest568 here
                

                stub.startgetRuleInstanceList(
                         getRuleInstanceListRequest568,
                    new tempCallbackN10D9B()
                );
              


        }

        private class tempCallbackN10D9B  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10D9B(){ super(null);}

            public void receiveResultgetRuleInstanceList(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceListResponse result
                            ) {
                
            }

            public void receiveErrorgetRuleInstanceList(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetAllGroups() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllGroupsRequest getAllGroupsRequest570=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllGroupsRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllGroupsRequest.class);
                    // TODO : Fill in the getAllGroupsRequest570 here
                
                        assertNotNull(stub.getAllGroups(
                        getAllGroupsRequest570));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetAllGroups() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllGroupsRequest getAllGroupsRequest570=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllGroupsRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllGroupsRequest.class);
                    // TODO : Fill in the getAllGroupsRequest570 here
                

                stub.startgetAllGroups(
                         getAllGroupsRequest570,
                    new tempCallbackN10DC2()
                );
              


        }

        private class tempCallbackN10DC2  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10DC2(){ super(null);}

            public void receiveResultgetAllGroups(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllGroupsResponse result
                            ) {
                
            }

            public void receiveErrorgetAllGroups(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetRuleTemplate() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateRequest getRuleTemplateRequest572=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateRequest.class);
                    // TODO : Fill in the getRuleTemplateRequest572 here
                
                        assertNotNull(stub.getRuleTemplate(
                        getRuleTemplateRequest572));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetRuleTemplate() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateRequest getRuleTemplateRequest572=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateRequest.class);
                    // TODO : Fill in the getRuleTemplateRequest572 here
                

                stub.startgetRuleTemplate(
                         getRuleTemplateRequest572,
                    new tempCallbackN10DE9()
                );
              


        }

        private class tempCallbackN10DE9  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10DE9(){ super(null);}

            public void receiveResultgetRuleTemplate(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateResponse result
                            ) {
                
            }

            public void receiveErrorgetRuleTemplate(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testdeleteSchedule() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteScheduleRequest deleteScheduleRequest574=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteScheduleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteScheduleRequest.class);
                    // TODO : Fill in the deleteScheduleRequest574 here
                
                        assertNotNull(stub.deleteSchedule(
                        deleteScheduleRequest574));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartdeleteSchedule() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteScheduleRequest deleteScheduleRequest574=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteScheduleRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteScheduleRequest.class);
                    // TODO : Fill in the deleteScheduleRequest574 here
                

                stub.startdeleteSchedule(
                         deleteScheduleRequest574,
                    new tempCallbackN10E10()
                );
              


        }

        private class tempCallbackN10E10  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10E10(){ super(null);}

            public void receiveResultdeleteSchedule(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteScheduleResponse result
                            ) {
                
            }

            public void receiveErrordeleteSchedule(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testunmapAgent() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapAgentRequest unmapAgentRequest576=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapAgentRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapAgentRequest.class);
                    // TODO : Fill in the unmapAgentRequest576 here
                
                        assertNotNull(stub.unmapAgent(
                        unmapAgentRequest576));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartunmapAgent() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapAgentRequest unmapAgentRequest576=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapAgentRequest)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapAgentRequest.class);
                    // TODO : Fill in the unmapAgentRequest576 here
                

                stub.startunmapAgent(
                         unmapAgentRequest576,
                    new tempCallbackN10E37()
                );
              


        }

        private class tempCallbackN10E37  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10E37(){ super(null);}

            public void receiveResultunmapAgent(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapAgentResponse result
                            ) {
                
            }

            public void receiveErrorunmapAgent(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testgetAllCapSets() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapSets getAllCapSets578=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapSets)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapSets.class);
                    // TODO : Fill in the getAllCapSets578 here
                
                        assertNotNull(stub.getAllCapSets(
                        getAllCapSets578));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartgetAllCapSets() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapSets getAllCapSets578=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapSets)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapSets.class);
                    // TODO : Fill in the getAllCapSets578 here
                

                stub.startgetAllCapSets(
                         getAllCapSets578,
                    new tempCallbackN10E5E()
                );
              


        }

        private class tempCallbackN10E5E  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10E5E(){ super(null);}

            public void receiveResultgetAllCapSets(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CapSetsResponse result
                            ) {
                
            }

            public void receiveErrorgetAllCapSets(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void teststop() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Stop stop580=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Stop)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Stop.class);
                    // TODO : Fill in the stop580 here
                
                        assertNotNull(stub.stop(
                        stop580));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartstop() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Stop stop580=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Stop)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Stop.class);
                    // TODO : Fill in the stop580 here
                

                stub.startstop(
                         stop580,
                    new tempCallbackN10E85()
                );
              


        }

        private class tempCallbackN10E85  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10E85(){ super(null);}

            public void receiveResultstop(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.StopResponse result
                            ) {
                
            }

            public void receiveErrorstop(java.lang.Exception e) {
                fail();
            }

        }
      
        /**
         * Auto generated test method
         */
        public  void testtransquery() throws java.lang.Exception{

        com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub =
                    new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();//the default implementation should point to the right endpoint

           com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Transquery transquery582=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Transquery)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Transquery.class);
                    // TODO : Fill in the transquery582 here
                
                        assertNotNull(stub.transquery(
                        transquery582));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStarttransquery() throws java.lang.Exception{
            com.mbv.bp.common.executor.mobifone.services.UMarketSCStub stub = new com.mbv.bp.common.executor.mobifone.services.UMarketSCStub();
             com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Transquery transquery582=
                                                        (com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Transquery)getTestObject(com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Transquery.class);
                    // TODO : Fill in the transquery582 here
                

                stub.starttransquery(
                         transquery582,
                    new tempCallbackN10EAC()
                );
              


        }

        private class tempCallbackN10EAC  extends com.mbv.bp.common.executor.mobifone.services.UMarketSCCallbackHandler{
            public tempCallbackN10EAC(){ super(null);}

            public void receiveResulttransquery(
                         com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.TransqueryResponse result
                            ) {
                
            }

            public void receiveErrortransquery(java.lang.Exception e) {
                fail();
            }

        }
      
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    