
/**
 * UMarketSCCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */

    package com.mbv.bp.common.executor.mobifone.services;

    /**
     *  UMarketSCCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class UMarketSCCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public UMarketSCCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public UMarketSCCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for dispenseVoucher method
            * override this method for handling normal response from dispenseVoucher operation
            */
           public void receiveResultdispenseVoucher(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DispenseVoucherResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from dispenseVoucher operation
           */
            public void receiveErrordispenseVoucher(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for adjustWallet method
            * override this method for handling normal response from adjustWallet operation
            */
           public void receiveResultadjustWallet(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AdjustWalletResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from adjustWallet operation
           */
            public void receiveErroradjustWallet(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for delRuleInstance method
            * override this method for handling normal response from delRuleInstance operation
            */
           public void receiveResultdelRuleInstance(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleInstanceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from delRuleInstance operation
           */
            public void receiveErrordelRuleInstance(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteGroup method
            * override this method for handling normal response from deleteGroup operation
            */
           public void receiveResultdeleteGroup(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteGroup operation
           */
            public void receiveErrordeleteGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for reverse method
            * override this method for handling normal response from reverse operation
            */
           public void receiveResultreverse(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ReverseResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from reverse operation
           */
            public void receiveErrorreverse(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for delRule method
            * override this method for handling normal response from delRule operation
            */
           public void receiveResultdelRule(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from delRule operation
           */
            public void receiveErrordelRule(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for authorise method
            * override this method for handling normal response from authorise operation
            */
           public void receiveResultauthorise(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AuthoriseResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from authorise operation
           */
            public void receiveErrorauthorise(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for moveRuleEntityUp method
            * override this method for handling normal response from moveRuleEntityUp operation
            */
           public void receiveResultmoveRuleEntityUp(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityUpResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from moveRuleEntityUp operation
           */
            public void receiveErrormoveRuleEntityUp(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for resetPin method
            * override this method for handling normal response from resetPin operation
            */
           public void receiveResultresetPin(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ResetPinResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from resetPin operation
           */
            public void receiveErrorresetPin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getTransactionByID method
            * override this method for handling normal response from getTransactionByID operation
            */
           public void receiveResultgetTransactionByID(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.TransactionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getTransactionByID operation
           */
            public void receiveErrorgetTransactionByID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addCapSet method
            * override this method for handling normal response from addCapSet operation
            */
           public void receiveResultaddCapSet(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapSetResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCapSet operation
           */
            public void receiveErroraddCapSet(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deactRuleInstance method
            * override this method for handling normal response from deactRuleInstance operation
            */
           public void receiveResultdeactRuleInstance(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeactRuleInstanceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deactRuleInstance operation
           */
            public void receiveErrordeactRuleInstance(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllRules method
            * override this method for handling normal response from getAllRules operation
            */
           public void receiveResultgetAllRules(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllRulesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllRules operation
           */
            public void receiveErrorgetAllRules(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unmapRule method
            * override this method for handling normal response from unmapRule operation
            */
           public void receiveResultunmapRule(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapRuleResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unmapRule operation
           */
            public void receiveErrorunmapRule(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for activate method
            * override this method for handling normal response from activate operation
            */
           public void receiveResultactivate(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ActivateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from activate operation
           */
            public void receiveErroractivate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyAccount method
            * override this method for handling normal response from modifyAccount operation
            */
           public void receiveResultmodifyAccount(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyAccountResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyAccount operation
           */
            public void receiveErrormodifyAccount(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createsession method
            * override this method for handling normal response from createsession operation
            */
           public void receiveResultcreatesession(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreatesessionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createsession operation
           */
            public void receiveErrorcreatesession(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllCapDetailByCapsetId method
            * override this method for handling normal response from getAllCapDetailByCapsetId operation
            */
           public void receiveResultgetAllCapDetailByCapsetId(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetailByCapsetIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllCapDetailByCapsetId operation
           */
            public void receiveErrorgetAllCapDetailByCapsetId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modCapSet method
            * override this method for handling normal response from modCapSet operation
            */
           public void receiveResultmodCapSet(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapSetResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modCapSet operation
           */
            public void receiveErrormodCapSet(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for delCapDetail method
            * override this method for handling normal response from delCapDetail operation
            */
           public void receiveResultdelCapDetail(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from delCapDetail operation
           */
            public void receiveErrordelCapDetail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllCapDetails method
            * override this method for handling normal response from getAllCapDetails operation
            */
           public void receiveResultgetAllCapDetails(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllCapDetailsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllCapDetails operation
           */
            public void receiveErrorgetAllCapDetails(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sales method
            * override this method for handling normal response from sales operation
            */
           public void receiveResultsales(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.SalesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sales operation
           */
            public void receiveErrorsales(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGroup method
            * override this method for handling normal response from getGroup operation
            */
           public void receiveResultgetGroup(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGroup operation
           */
            public void receiveErrorgetGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRuleEntity method
            * override this method for handling normal response from getRuleEntity operation
            */
           public void receiveResultgetRuleEntity(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRuleEntity operation
           */
            public void receiveErrorgetRuleEntity(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for actRuleInstance method
            * override this method for handling normal response from actRuleInstance operation
            */
           public void receiveResultactRuleInstance(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ActRuleInstanceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from actRuleInstance operation
           */
            public void receiveErroractRuleInstance(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deactivate method
            * override this method for handling normal response from deactivate operation
            */
           public void receiveResultdeactivate(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeactivateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deactivate operation
           */
            public void receiveErrordeactivate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for nextid method
            * override this method for handling normal response from nextid operation
            */
           public void receiveResultnextid(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.NextidResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from nextid operation
           */
            public void receiveErrornextid(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCapDetailByID method
            * override this method for handling normal response from getCapDetailByID operation
            */
           public void receiveResultgetCapDetailByID(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCapDetailByID operation
           */
            public void receiveErrorgetCapDetailByID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for joinparent method
            * override this method for handling normal response from joinparent operation
            */
           public void receiveResultjoinparent(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.JoinparentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from joinparent operation
           */
            public void receiveErrorjoinparent(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRuleEntitiesByParent method
            * override this method for handling normal response from getRuleEntitiesByParent operation
            */
           public void receiveResultgetRuleEntitiesByParent(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntitiesByParentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRuleEntitiesByParent operation
           */
            public void receiveErrorgetRuleEntitiesByParent(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for link method
            * override this method for handling normal response from link operation
            */
           public void receiveResultlink(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LinkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from link operation
           */
            public void receiveErrorlink(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for query method
            * override this method for handling normal response from query operation
            */
           public void receiveResultquery(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.QueryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from query operation
           */
            public void receiveErrorquery(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addRuleInstance method
            * override this method for handling normal response from addRuleInstance operation
            */
           public void receiveResultaddRuleInstance(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleInstanceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addRuleInstance operation
           */
            public void receiveErroraddRuleInstance(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for login method
            * override this method for handling normal response from login operation
            */
           public void receiveResultlogin(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LoginResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from login operation
           */
            public void receiveErrorlogin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRuleTemplateList method
            * override this method for handling normal response from getRuleTemplateList operation
            */
           public void receiveResultgetRuleTemplateList(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRuleTemplateList operation
           */
            public void receiveErrorgetRuleTemplateList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for donate method
            * override this method for handling normal response from donate operation
            */
           public void receiveResultdonate(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DonateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from donate operation
           */
            public void receiveErrordonate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getVoucher method
            * override this method for handling normal response from getVoucher operation
            */
           public void receiveResultgetVoucher(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetVoucherResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getVoucher operation
           */
            public void receiveErrorgetVoucher(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addCapDetail method
            * override this method for handling normal response from addCapDetail operation
            */
           public void receiveResultaddCapDetail(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddCapDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCapDetail operation
           */
            public void receiveErroraddCapDetail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAccountByReferenceIDAndType method
            * override this method for handling normal response from getAccountByReferenceIDAndType operation
            */
           public void receiveResultgetAccountByReferenceIDAndType(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AccountResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAccountByReferenceIDAndType operation
           */
            public void receiveErrorgetAccountByReferenceIDAndType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addRule method
            * override this method for handling normal response from addRule operation
            */
           public void receiveResultaddRule(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addRule operation
           */
            public void receiveErroraddRule(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createVoucher method
            * override this method for handling normal response from createVoucher operation
            */
           public void receiveResultcreateVoucher(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateVoucherResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createVoucher operation
           */
            public void receiveErrorcreateVoucher(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modRule method
            * override this method for handling normal response from modRule operation
            */
           public void receiveResultmodRule(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modRule operation
           */
            public void receiveErrormodRule(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modCapDetail method
            * override this method for handling normal response from modCapDetail operation
            */
           public void receiveResultmodCapDetail(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModCapDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modCapDetail operation
           */
            public void receiveErrormodCapDetail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sell method
            * override this method for handling normal response from sell operation
            */
           public void receiveResultsell(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.SellResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sell operation
           */
            public void receiveErrorsell(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGroupsByType method
            * override this method for handling normal response from getGroupsByType operation
            */
           public void receiveResultgetGroupsByType(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupsByTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGroupsByType operation
           */
            public void receiveErrorgetGroupsByType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addRuleEntity method
            * override this method for handling normal response from addRuleEntity operation
            */
           public void receiveResultaddRuleEntity(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddRuleEntityResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addRuleEntity operation
           */
            public void receiveErroraddRuleEntity(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for transfer method
            * override this method for handling normal response from transfer operation
            */
           public void receiveResulttransfer(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.TransferResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from transfer operation
           */
            public void receiveErrortransfer(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for joinchild method
            * override this method for handling normal response from joinchild operation
            */
           public void receiveResultjoinchild(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.JoinchildResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from joinchild operation
           */
            public void receiveErrorjoinchild(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGroupByName method
            * override this method for handling normal response from getGroupByName operation
            */
           public void receiveResultgetGroupByName(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetGroupByNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGroupByName operation
           */
            public void receiveErrorgetGroupByName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for delschedule method
            * override this method for handling normal response from delschedule operation
            */
           public void receiveResultdelschedule(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelscheduleResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from delschedule operation
           */
            public void receiveErrordelschedule(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAccountByReferenceID method
            * override this method for handling normal response from getAccountByReferenceID operation
            */
           public void receiveResultgetAccountByReferenceID(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AccountsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAccountByReferenceID operation
           */
            public void receiveErrorgetAccountByReferenceID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for billcollect method
            * override this method for handling normal response from billcollect operation
            */
           public void receiveResultbillcollect(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BillcollectResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from billcollect operation
           */
            public void receiveErrorbillcollect(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRuleInstance method
            * override this method for handling normal response from getRuleInstance operation
            */
           public void receiveResultgetRuleInstance(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRuleInstance operation
           */
            public void receiveErrorgetRuleInstance(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRuleListByContextId method
            * override this method for handling normal response from getRuleListByContextId operation
            */
           public void receiveResultgetRuleListByContextId(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleListByContextIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRuleListByContextId operation
           */
            public void receiveErrorgetRuleListByContextId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRuleEntityList method
            * override this method for handling normal response from getRuleEntityList operation
            */
           public void receiveResultgetRuleEntityList(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleEntityListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRuleEntityList operation
           */
            public void receiveErrorgetRuleEntityList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCapByID method
            * override this method for handling normal response from getCapByID operation
            */
           public void receiveResultgetCapByID(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetCapResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCapByID operation
           */
            public void receiveErrorgetCapByID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modify method
            * override this method for handling normal response from modify operation
            */
           public void receiveResultmodify(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modify operation
           */
            public void receiveErrormodify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for delparent method
            * override this method for handling normal response from delparent operation
            */
           public void receiveResultdelparent(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelparentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from delparent operation
           */
            public void receiveErrordelparent(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyGroup method
            * override this method for handling normal response from modifyGroup operation
            */
           public void receiveResultmodifyGroup(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModifyGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyGroup operation
           */
            public void receiveErrormodifyGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAgentGroupByAgentID method
            * override this method for handling normal response from getAgentGroupByAgentID operation
            */
           public void receiveResultgetAgentGroupByAgentID(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AgentGroupsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAgentGroupByAgentID operation
           */
            public void receiveErrorgetAgentGroupByAgentID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for billpay method
            * override this method for handling normal response from billpay operation
            */
           public void receiveResultbillpay(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BillpayResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from billpay operation
           */
            public void receiveErrorbillpay(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for lastTransactions method
            * override this method for handling normal response from lastTransactions operation
            */
           public void receiveResultlastTransactions(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LastTransactionsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from lastTransactions operation
           */
            public void receiveErrorlastTransactions(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for finalizeExtTrans method
            * override this method for handling normal response from finalizeExtTrans operation
            */
           public void receiveResultfinalizeExtTrans(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.FinalizeExtTransResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from finalizeExtTrans operation
           */
            public void receiveErrorfinalizeExtTrans(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for delRuleEntity method
            * override this method for handling normal response from delRuleEntity operation
            */
           public void receiveResultdelRuleEntity(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelRuleEntityResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from delRuleEntity operation
           */
            public void receiveErrordelRuleEntity(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for register method
            * override this method for handling normal response from register operation
            */
           public void receiveResultregister(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.RegisterResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from register operation
           */
            public void receiveErrorregister(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for delCapSet method
            * override this method for handling normal response from delCapSet operation
            */
           public void receiveResultdelCapSet(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelCapSetResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from delCapSet operation
           */
            public void receiveErrordelCapSet(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addSchedule method
            * override this method for handling normal response from addSchedule operation
            */
           public void receiveResultaddSchedule(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AddScheduleResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addSchedule operation
           */
            public void receiveErroraddSchedule(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteVoucher method
            * override this method for handling normal response from deleteVoucher operation
            */
           public void receiveResultdeleteVoucher(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteVoucherResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteVoucher operation
           */
            public void receiveErrordeleteVoucher(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for bankcashout method
            * override this method for handling normal response from bankcashout operation
            */
           public void receiveResultbankcashout(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BankcashoutResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from bankcashout operation
           */
            public void receiveErrorbankcashout(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createcoupon method
            * override this method for handling normal response from createcoupon operation
            */
           public void receiveResultcreatecoupon(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreatecouponResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createcoupon operation
           */
            public void receiveErrorcreatecoupon(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for moveRuleEntityDown method
            * override this method for handling normal response from moveRuleEntityDown operation
            */
           public void receiveResultmoveRuleEntityDown(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MoveRuleEntityDownResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from moveRuleEntityDown operation
           */
            public void receiveErrormoveRuleEntityDown(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRule method
            * override this method for handling normal response from getRule operation
            */
           public void receiveResultgetRule(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRule operation
           */
            public void receiveErrorgetRule(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for confirm method
            * override this method for handling normal response from confirm operation
            */
           public void receiveResultconfirm(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ConfirmResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from confirm operation
           */
            public void receiveErrorconfirm(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for authorise2 method
            * override this method for handling normal response from authorise2 operation
            */
           public void receiveResultauthorise2(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.Authorise2Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from authorise2 operation
           */
            public void receiveErrorauthorise2(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sellStock method
            * override this method for handling normal response from sellStock operation
            */
           public void receiveResultsellStock(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.SellStockResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sellStock operation
           */
            public void receiveErrorsellStock(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAgentByReference method
            * override this method for handling normal response from getAgentByReference operation
            */
           public void receiveResultgetAgentByReference(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AgentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAgentByReference operation
           */
            public void receiveErrorgetAgentByReference(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllAgentGroups method
            * override this method for handling normal response from getAllAgentGroups operation
            */
           public void receiveResultgetAllAgentGroups(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AgentGroupsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllAgentGroups operation
           */
            public void receiveErrorgetAllAgentGroups(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modRuleEntity method
            * override this method for handling normal response from modRuleEntity operation
            */
           public void receiveResultmodRuleEntity(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleEntityResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modRuleEntity operation
           */
            public void receiveErrormodRuleEntity(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAgentByReferenceID method
            * override this method for handling normal response from getAgentByReferenceID operation
            */
           public void receiveResultgetAgentByReferenceID(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.AgentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAgentByReferenceID operation
           */
            public void receiveErrorgetAgentByReferenceID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for delchild method
            * override this method for handling normal response from delchild operation
            */
           public void receiveResultdelchild(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DelchildResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from delchild operation
           */
            public void receiveErrordelchild(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for mapAgent method
            * override this method for handling normal response from mapAgent operation
            */
           public void receiveResultmapAgent(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapAgentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from mapAgent operation
           */
            public void receiveErrormapAgent(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for buy method
            * override this method for handling normal response from buy operation
            */
           public void receiveResultbuy(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BuyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from buy operation
           */
            public void receiveErrorbuy(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for delete method
            * override this method for handling normal response from delete operation
            */
           public void receiveResultdelete(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from delete operation
           */
            public void receiveErrordelete(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for balance method
            * override this method for handling normal response from balance operation
            */
           public void receiveResultbalance(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BalanceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from balance operation
           */
            public void receiveErrorbalance(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for pin method
            * override this method for handling normal response from pin operation
            */
           public void receiveResultpin(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.PinResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from pin operation
           */
            public void receiveErrorpin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for mapRule method
            * override this method for handling normal response from mapRule operation
            */
           public void receiveResultmapRule(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.MapRuleResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from mapRule operation
           */
            public void receiveErrormapRule(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createGroup method
            * override this method for handling normal response from createGroup operation
            */
           public void receiveResultcreateGroup(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreateGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createGroup operation
           */
            public void receiveErrorcreateGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modRuleInstance method
            * override this method for handling normal response from modRuleInstance operation
            */
           public void receiveResultmodRuleInstance(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.ModRuleInstanceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modRuleInstance operation
           */
            public void receiveErrormodRuleInstance(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for bankcashin method
            * override this method for handling normal response from bankcashin operation
            */
           public void receiveResultbankcashin(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BankcashinResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from bankcashin operation
           */
            public void receiveErrorbankcashin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRuleInstanceList method
            * override this method for handling normal response from getRuleInstanceList operation
            */
           public void receiveResultgetRuleInstanceList(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleInstanceListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRuleInstanceList operation
           */
            public void receiveErrorgetRuleInstanceList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllGroups method
            * override this method for handling normal response from getAllGroups operation
            */
           public void receiveResultgetAllGroups(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetAllGroupsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllGroups operation
           */
            public void receiveErrorgetAllGroups(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRuleTemplate method
            * override this method for handling normal response from getRuleTemplate operation
            */
           public void receiveResultgetRuleTemplate(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.GetRuleTemplateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRuleTemplate operation
           */
            public void receiveErrorgetRuleTemplate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteSchedule method
            * override this method for handling normal response from deleteSchedule operation
            */
           public void receiveResultdeleteSchedule(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.DeleteScheduleResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteSchedule operation
           */
            public void receiveErrordeleteSchedule(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unmapAgent method
            * override this method for handling normal response from unmapAgent operation
            */
           public void receiveResultunmapAgent(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.UnmapAgentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unmapAgent operation
           */
            public void receiveErrorunmapAgent(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllCapSets method
            * override this method for handling normal response from getAllCapSets operation
            */
           public void receiveResultgetAllCapSets(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CapSetsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllCapSets operation
           */
            public void receiveErrorgetAllCapSets(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for stop method
            * override this method for handling normal response from stop operation
            */
           public void receiveResultstop(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.StopResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from stop operation
           */
            public void receiveErrorstop(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for transquery method
            * override this method for handling normal response from transquery operation
            */
           public void receiveResulttransquery(
                    com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.TransqueryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from transquery operation
           */
            public void receiveErrortransquery(java.lang.Exception e) {
            }
                


    }
    