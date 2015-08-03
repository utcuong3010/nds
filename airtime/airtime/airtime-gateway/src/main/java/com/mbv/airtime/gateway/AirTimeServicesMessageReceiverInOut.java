
/**
 * AirTimeServicesMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */
        package com.mbv.airtime.gateway;

        /**
        *  AirTimeServicesMessageReceiverInOut message receiver
        */

        public class AirTimeServicesMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        AirTimeServicesSkeletonInterface skel = (AirTimeServicesSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){

        

            if("createTxnId".equals(methodName)){
                
                com.mbv.airtime.gateway.CreateTxnIdResponse createTxnIdResponse23 = null;
	                        com.mbv.airtime.gateway.CreateTxnId wrappedParam =
                                                             (com.mbv.airtime.gateway.CreateTxnId)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.mbv.airtime.gateway.CreateTxnId.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createTxnIdResponse23 =
                                                   
                                                   
                                                         skel.createTxnId(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createTxnIdResponse23, false);
                                    } else 

            if("debitLockAccount".equals(methodName)){
                
                com.mbv.airtime.gateway.LockTxnResponse lockTxnResponse25 = null;
	                        com.mbv.airtime.gateway.DebitLockAccount wrappedParam =
                                                             (com.mbv.airtime.gateway.DebitLockAccount)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.mbv.airtime.gateway.DebitLockAccount.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               lockTxnResponse25 =
                                                   
                                                   
                                                         skel.debitLockAccount(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), lockTxnResponse25, false);
                                    } else 

            if("createLockAccount".equals(methodName)){
                
                com.mbv.airtime.gateway.LockAccountResponse lockAccountResponse27 = null;
	                        com.mbv.airtime.gateway.CreateLockAccount wrappedParam =
                                                             (com.mbv.airtime.gateway.CreateLockAccount)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.mbv.airtime.gateway.CreateLockAccount.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               lockAccountResponse27 =
                                                   
                                                   
                                                         skel.createLockAccount(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), lockAccountResponse27, false);
                                    } else 

            if("reservedTopup".equals(methodName)){
                
                com.mbv.airtime.gateway.TopupResponse topupResponse29 = null;
	                        com.mbv.airtime.gateway.ReservedTopup wrappedParam =
                                                             (com.mbv.airtime.gateway.ReservedTopup)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.mbv.airtime.gateway.ReservedTopup.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               topupResponse29 =
                                                   
                                                   
                                                         skel.reservedTopup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), topupResponse29, false);
                                    } else 

            if("lockAccountTxnQuery".equals(methodName)){
                
                com.mbv.airtime.gateway.LockAccountTxnQueryResponse lockAccountTxnQueryResponse31 = null;
	                        com.mbv.airtime.gateway.LockAccountTxnQuery wrappedParam =
                                                             (com.mbv.airtime.gateway.LockAccountTxnQuery)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.mbv.airtime.gateway.LockAccountTxnQuery.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               lockAccountTxnQueryResponse31 =
                                                   
                                                   
                                                         skel.lockAccountTxnQuery(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), lockAccountTxnQueryResponse31, false);
                                    } else 

            if("inquiry".equals(methodName)){
                
                com.mbv.airtime.gateway.InquiryResponse inquiryResponse33 = null;
	                        com.mbv.airtime.gateway.Inquiry wrappedParam =
                                                             (com.mbv.airtime.gateway.Inquiry)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.mbv.airtime.gateway.Inquiry.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               inquiryResponse33 =
                                                   
                                                   
                                                         skel.inquiry(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), inquiryResponse33, false);
                                    } else 

            if("lockAccountQuery".equals(methodName)){
                
                com.mbv.airtime.gateway.LockAccountQueryResponse lockAccountQueryResponse35 = null;
	                        com.mbv.airtime.gateway.LockAccountQuery wrappedParam =
                                                             (com.mbv.airtime.gateway.LockAccountQuery)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.mbv.airtime.gateway.LockAccountQuery.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               lockAccountQueryResponse35 =
                                                   
                                                   
                                                         skel.lockAccountQuery(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), lockAccountQueryResponse35, false);
                                    } else 

            if("txnRequest".equals(methodName)){
                
                com.mbv.airtime.gateway.TxnResponse txnResponse37 = null;
	                        com.mbv.airtime.gateway.TxnRequest wrappedParam =
                                                             (com.mbv.airtime.gateway.TxnRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.mbv.airtime.gateway.TxnRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               txnResponse37 =
                                                   
                                                   
                                                         skel.txnRequest(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), txnResponse37, false);
                                    } else 

            if("creditLockAccount".equals(methodName)){
                
                com.mbv.airtime.gateway.LockTxnResponse lockTxnResponse39 = null;
	                        com.mbv.airtime.gateway.CreditLockAccount wrappedParam =
                                                             (com.mbv.airtime.gateway.CreditLockAccount)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.mbv.airtime.gateway.CreditLockAccount.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               lockTxnResponse39 =
                                                   
                                                   
                                                         skel.creditLockAccount(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), lockTxnResponse39, false);
                                    } else 

            if("topup".equals(methodName)){
                
                com.mbv.airtime.gateway.TopupResponse topupResponse41 = null;
	                        com.mbv.airtime.gateway.Topup wrappedParam =
                                                             (com.mbv.airtime.gateway.Topup)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.mbv.airtime.gateway.Topup.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               topupResponse41 =
                                                   
                                                   
                                                         skel.topup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), topupResponse41, false);
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        }
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.CreateTxnId param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.CreateTxnId.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.CreateTxnIdResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.CreateTxnIdResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.DebitLockAccount param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.DebitLockAccount.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.LockTxnResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.LockTxnResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.CreateLockAccount param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.CreateLockAccount.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.LockAccountResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.LockAccountResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.ReservedTopup param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.ReservedTopup.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.TopupResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.TopupResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.LockAccountTxnQuery param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.LockAccountTxnQuery.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.LockAccountTxnQueryResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.LockAccountTxnQueryResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.Inquiry param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.Inquiry.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.InquiryResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.InquiryResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.LockAccountQuery param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.LockAccountQuery.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.LockAccountQueryResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.LockAccountQueryResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.TxnRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.TxnRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.TxnResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.TxnResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.CreditLockAccount param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.CreditLockAccount.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.mbv.airtime.gateway.Topup param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.mbv.airtime.gateway.Topup.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.mbv.airtime.gateway.CreateTxnIdResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.mbv.airtime.gateway.CreateTxnIdResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.mbv.airtime.gateway.CreateTxnIdResponse wrapcreateTxnId(){
                                com.mbv.airtime.gateway.CreateTxnIdResponse wrappedElement = new com.mbv.airtime.gateway.CreateTxnIdResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.mbv.airtime.gateway.LockTxnResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.mbv.airtime.gateway.LockTxnResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.mbv.airtime.gateway.LockTxnResponse wrapdebitLockAccount(){
                                com.mbv.airtime.gateway.LockTxnResponse wrappedElement = new com.mbv.airtime.gateway.LockTxnResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.mbv.airtime.gateway.LockAccountResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.mbv.airtime.gateway.LockAccountResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.mbv.airtime.gateway.LockAccountResponse wrapcreateLockAccount(){
                                com.mbv.airtime.gateway.LockAccountResponse wrappedElement = new com.mbv.airtime.gateway.LockAccountResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.mbv.airtime.gateway.TopupResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.mbv.airtime.gateway.TopupResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.mbv.airtime.gateway.TopupResponse wrapreservedTopup(){
                                com.mbv.airtime.gateway.TopupResponse wrappedElement = new com.mbv.airtime.gateway.TopupResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.mbv.airtime.gateway.LockAccountTxnQueryResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.mbv.airtime.gateway.LockAccountTxnQueryResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.mbv.airtime.gateway.LockAccountTxnQueryResponse wraplockAccountTxnQuery(){
                                com.mbv.airtime.gateway.LockAccountTxnQueryResponse wrappedElement = new com.mbv.airtime.gateway.LockAccountTxnQueryResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.mbv.airtime.gateway.InquiryResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.mbv.airtime.gateway.InquiryResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.mbv.airtime.gateway.InquiryResponse wrapinquiry(){
                                com.mbv.airtime.gateway.InquiryResponse wrappedElement = new com.mbv.airtime.gateway.InquiryResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.mbv.airtime.gateway.LockAccountQueryResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.mbv.airtime.gateway.LockAccountQueryResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.mbv.airtime.gateway.LockAccountQueryResponse wraplockAccountQuery(){
                                com.mbv.airtime.gateway.LockAccountQueryResponse wrappedElement = new com.mbv.airtime.gateway.LockAccountQueryResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.mbv.airtime.gateway.TxnResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.mbv.airtime.gateway.TxnResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.mbv.airtime.gateway.TxnResponse wraptxnRequest(){
                                com.mbv.airtime.gateway.TxnResponse wrappedElement = new com.mbv.airtime.gateway.TxnResponse();
                                return wrappedElement;
                         }
                    
                         private com.mbv.airtime.gateway.LockTxnResponse wrapcreditLockAccount(){
                                com.mbv.airtime.gateway.LockTxnResponse wrappedElement = new com.mbv.airtime.gateway.LockTxnResponse();
                                return wrappedElement;
                         }
                    
                         private com.mbv.airtime.gateway.TopupResponse wraptopup(){
                                com.mbv.airtime.gateway.TopupResponse wrappedElement = new com.mbv.airtime.gateway.TopupResponse();
                                return wrappedElement;
                         }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (com.mbv.airtime.gateway.CreateTxnId.class.equals(type)){
                
                           return com.mbv.airtime.gateway.CreateTxnId.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.CreateTxnIdResponse.class.equals(type)){
                
                           return com.mbv.airtime.gateway.CreateTxnIdResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.DebitLockAccount.class.equals(type)){
                
                           return com.mbv.airtime.gateway.DebitLockAccount.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.LockTxnResponse.class.equals(type)){
                
                           return com.mbv.airtime.gateway.LockTxnResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.CreateLockAccount.class.equals(type)){
                
                           return com.mbv.airtime.gateway.CreateLockAccount.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.LockAccountResponse.class.equals(type)){
                
                           return com.mbv.airtime.gateway.LockAccountResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.ReservedTopup.class.equals(type)){
                
                           return com.mbv.airtime.gateway.ReservedTopup.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.TopupResponse.class.equals(type)){
                
                           return com.mbv.airtime.gateway.TopupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.LockAccountTxnQuery.class.equals(type)){
                
                           return com.mbv.airtime.gateway.LockAccountTxnQuery.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.LockAccountTxnQueryResponse.class.equals(type)){
                
                           return com.mbv.airtime.gateway.LockAccountTxnQueryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.Inquiry.class.equals(type)){
                
                           return com.mbv.airtime.gateway.Inquiry.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.InquiryResponse.class.equals(type)){
                
                           return com.mbv.airtime.gateway.InquiryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.LockAccountQuery.class.equals(type)){
                
                           return com.mbv.airtime.gateway.LockAccountQuery.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.LockAccountQueryResponse.class.equals(type)){
                
                           return com.mbv.airtime.gateway.LockAccountQueryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.TxnRequest.class.equals(type)){
                
                           return com.mbv.airtime.gateway.TxnRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.TxnResponse.class.equals(type)){
                
                           return com.mbv.airtime.gateway.TxnResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.CreditLockAccount.class.equals(type)){
                
                           return com.mbv.airtime.gateway.CreditLockAccount.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.LockTxnResponse.class.equals(type)){
                
                           return com.mbv.airtime.gateway.LockTxnResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.Topup.class.equals(type)){
                
                           return com.mbv.airtime.gateway.Topup.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.mbv.airtime.gateway.TopupResponse.class.equals(type)){
                
                           return com.mbv.airtime.gateway.TopupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    