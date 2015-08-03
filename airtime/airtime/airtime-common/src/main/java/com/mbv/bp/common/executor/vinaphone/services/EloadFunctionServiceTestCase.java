/**
 * EloadFunctionServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.bp.common.executor.vinaphone.services;

public class EloadFunctionServiceTestCase extends junit.framework.TestCase {
    public EloadFunctionServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testEloadFunctionWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.mbv.bp.common.executor.vinaphone.services.EloadFunctionServiceLocator().getEloadFunctionAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.mbv.bp.common.executor.vinaphone.services.EloadFunctionServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1EloadFunctionLoad() throws Exception {
        com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub binding;
        try {
            binding = (com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub)
                          new com.mbv.bp.common.executor.vinaphone.services.EloadFunctionServiceLocator().getEloadFunction();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput value = null;
        value = binding.load(new com.mbv.bp.common.executor.vinaphone.services.TransactionInput());
        // TBD - validate results
    }

    public void test2EloadFunctionLogin() throws Exception {
        com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub binding;
        try {
            binding = (com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub)
                          new com.mbv.bp.common.executor.vinaphone.services.EloadFunctionServiceLocator().getEloadFunction();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.mbv.bp.common.executor.vinaphone.services.LoginOutput value = null;
        value = binding.login(new com.mbv.bp.common.executor.vinaphone.services.BasicInput());
        // TBD - validate results
    }

    public void test3EloadFunctionLogout() throws Exception {
        com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub binding;
        try {
            binding = (com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub)
                          new com.mbv.bp.common.executor.vinaphone.services.EloadFunctionServiceLocator().getEloadFunction();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.mbv.bp.common.executor.vinaphone.services.BasicOutput value = null;
        value = binding.logout(new java.lang.String());
        // TBD - validate results
    }

    public void test4EloadFunctionChange_password() throws Exception {
        com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub binding;
        try {
            binding = (com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub)
                          new com.mbv.bp.common.executor.vinaphone.services.EloadFunctionServiceLocator().getEloadFunction();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.mbv.bp.common.executor.vinaphone.services.BasicOutput value = null;
        value = binding.change_password(new com.mbv.bp.common.executor.vinaphone.services.ChangeInput());
        // TBD - validate results
    }

    public void test5EloadFunctionChange_mpin() throws Exception {
        com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub binding;
        try {
            binding = (com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub)
                          new com.mbv.bp.common.executor.vinaphone.services.EloadFunctionServiceLocator().getEloadFunction();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.mbv.bp.common.executor.vinaphone.services.BasicOutput value = null;
        value = binding.change_mpin(new com.mbv.bp.common.executor.vinaphone.services.ChangeInput());
        // TBD - validate results
    }

    public void test6EloadFunctionStock_enquiry() throws Exception {
        com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub binding;
        try {
            binding = (com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub)
                          new com.mbv.bp.common.executor.vinaphone.services.EloadFunctionServiceLocator().getEloadFunction();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput value = null;
        value = binding.stock_enquiry(new com.mbv.bp.common.executor.vinaphone.services.BasicInput());
        // TBD - validate results
    }

    public void test7EloadFunctionCheck_transaction() throws Exception {
        com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub binding;
        try {
            binding = (com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub)
                          new com.mbv.bp.common.executor.vinaphone.services.EloadFunctionServiceLocator().getEloadFunction();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.mbv.bp.common.executor.vinaphone.services.CheckTransactionOutput value = null;
        value = binding.check_transaction(new com.mbv.bp.common.executor.vinaphone.services.CheckTransactionInput());
        // TBD - validate results
    }

    public void test8EloadFunctionToday_enquiry() throws Exception {
        com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub binding;
        try {
            binding = (com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub)
                          new com.mbv.bp.common.executor.vinaphone.services.EloadFunctionServiceLocator().getEloadFunction();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput value = null;
        value = binding.today_enquiry(new com.mbv.bp.common.executor.vinaphone.services.BasicInput());
        // TBD - validate results
    }

    public void test9EloadFunctionStock_transfer() throws Exception {
        com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub binding;
        try {
            binding = (com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub)
                          new com.mbv.bp.common.executor.vinaphone.services.EloadFunctionServiceLocator().getEloadFunction();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput value = null;
        value = binding.stock_transfer(new com.mbv.bp.common.executor.vinaphone.services.TransactionInput());
        // TBD - validate results
    }

}
