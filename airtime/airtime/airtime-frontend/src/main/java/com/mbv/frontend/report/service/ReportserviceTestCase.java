/**
 * ReportserviceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mbv.frontend.report.service;

public class ReportserviceTestCase extends junit.framework.TestCase {
    public ReportserviceTestCase(java.lang.String name) {
        super(name);
    }

    public void testreportserviceWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new ReportserviceLocator().getreportserviceAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new ReportserviceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1reportserviceCreateReport() throws Exception {
        ReportserviceStub binding;
        try {
            binding = (ReportserviceStub)
                          new ReportserviceLocator().getreportservice();
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
        java.lang.String value = null;
        value = binding.createReport(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test2reportserviceRetrieveReport() throws Exception {
        ReportserviceStub binding;
        try {
            binding = (ReportserviceStub)
                          new ReportserviceLocator().getreportservice();
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
        binding.retrieveReport(new java.lang.String(), new java.lang.String(), new javax.xml.rpc.holders.StringHolder(new java.lang.String()), 0, new javax.xml.rpc.holders.StringHolder(), new javax.xml.rpc.holders.StringHolder());
        // TBD - validate results
    }

    public void test3reportserviceGetReportParameters() throws Exception {
        ReportserviceStub binding;
        try {
            binding = (ReportserviceStub)
                          new ReportserviceLocator().getreportservice();
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
        java.lang.String value = null;
        value = binding.getReportParameters(new java.lang.String(), new java.lang.String(), new javax.xml.rpc.holders.StringHolder(new java.lang.String()));
        // TBD - validate results
    }

    public void test4reportserviceQueryReport() throws Exception {
        ReportserviceStub binding;
        try {
            binding = (ReportserviceStub)
                          new ReportserviceLocator().getreportservice();
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
        java.lang.String value = null;
        value = binding.queryReport(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test5reportserviceCountReport() throws Exception {
        ReportserviceStub binding;
        try {
            binding = (ReportserviceStub)
                          new ReportserviceLocator().getreportservice();
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
        int value = -3;
        value = binding.countReport(new java.lang.String(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

}
