
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:19:26 CET)
 */

        
            package com.mbv.airtime.gateway;
        
            /**
            *  ExtensionMapper class
            */
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://gateway.airtime.mbv.com".equals(namespaceURI) &&
                  "ArrayOfTxnInfo".equals(typeName)){
                   
                            return  com.mbv.airtime.gateway.ArrayOfTxnInfo.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gateway.airtime.mbv.com".equals(namespaceURI) &&
                  "LockTxnInfo".equals(typeName)){
                   
                            return  com.mbv.airtime.gateway.LockTxnInfo.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gateway.airtime.mbv.com".equals(namespaceURI) &&
                  "LockAccountInfo".equals(typeName)){
                   
                            return  com.mbv.airtime.gateway.LockAccountInfo.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gateway.airtime.mbv.com".equals(namespaceURI) &&
                  "KeyValuePairs".equals(typeName)){
                   
                            return  com.mbv.airtime.gateway.KeyValuePairs.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gateway.airtime.mbv.com".equals(namespaceURI) &&
                  "AtTxnInfo".equals(typeName)){
                   
                            return  com.mbv.airtime.gateway.AtTxnInfo.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gateway.airtime.mbv.com".equals(namespaceURI) &&
                  "KeyValuePair".equals(typeName)){
                   
                            return  com.mbv.airtime.gateway.KeyValuePair.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    