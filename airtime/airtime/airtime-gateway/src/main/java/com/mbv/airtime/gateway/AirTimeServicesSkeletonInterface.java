
/**
 * AirTimeServicesSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */
    package com.mbv.airtime.gateway;
    /**
     *  AirTimeServicesSkeletonInterface java skeleton interface for the axisService
     */
    public interface AirTimeServicesSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param createTxnId
         */

        
                public com.mbv.airtime.gateway.CreateTxnIdResponse createTxnId
                (
                  com.mbv.airtime.gateway.CreateTxnId createTxnId
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param debitLockAccount
         */

        
                public com.mbv.airtime.gateway.LockTxnResponse debitLockAccount
                (
                  com.mbv.airtime.gateway.DebitLockAccount debitLockAccount
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createLockAccount
         */

        
                public com.mbv.airtime.gateway.LockAccountResponse createLockAccount
                (
                  com.mbv.airtime.gateway.CreateLockAccount createLockAccount
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param reservedTopup
         */

        
                public com.mbv.airtime.gateway.TopupResponse reservedTopup
                (
                  com.mbv.airtime.gateway.ReservedTopup reservedTopup
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param lockAccountTxnQuery
         */

        
                public com.mbv.airtime.gateway.LockAccountTxnQueryResponse lockAccountTxnQuery
                (
                  com.mbv.airtime.gateway.LockAccountTxnQuery lockAccountTxnQuery
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param inquiry
         */

        
                public com.mbv.airtime.gateway.InquiryResponse inquiry
                (
                  com.mbv.airtime.gateway.Inquiry inquiry
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param lockAccountQuery
         */

        
                public com.mbv.airtime.gateway.LockAccountQueryResponse lockAccountQuery
                (
                  com.mbv.airtime.gateway.LockAccountQuery lockAccountQuery
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param txnRequest
         */

        
                public com.mbv.airtime.gateway.TxnResponse txnRequest
                (
                  com.mbv.airtime.gateway.TxnRequest txnRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param creditLockAccount
         */

        
                public com.mbv.airtime.gateway.LockTxnResponse creditLockAccount
                (
                  com.mbv.airtime.gateway.CreditLockAccount creditLockAccount
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param topup
         */

        
                public com.mbv.airtime.gateway.TopupResponse topup
                (
                  com.mbv.airtime.gateway.Topup topup
                 )
            ;
        
         }
    