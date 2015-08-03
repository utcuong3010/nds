package com.mbv.bp.common.executor.mobifone.services;

import java.rmi.RemoteException;



import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreatesessionResponse;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreatesessionResponseType;



public class TestMobiFone {
	
public static void main(String[] args) throws RemoteException {
//	UMarketSCStub stub = new UMarketSCStub();//the default implementation should point to the right endpoint
//	CreatesessionDocument createsessionDocument=CreatesessionDocument.Factory.newInstance();
//	CreatesessionResponseDocument createsessionResponseDocument= stub.createsession(createsessionDocument);
//	System.out.println(createsessionResponseDocument.toString());
//	
//	CreatesessionDocument.

//	TransqueryDocument transquery582=TransqueryDocument.Factory.newInstance();
//	TransqueryDocument.Transquery transquery=transquery582.addNewTransquery();
//	TransqueryRequestType transqueryRequest=transquery.addNewTransqueryRequest();
//	transqueryRequest.setTransid(1234545);
//	transqueryRequest.setSessionid("sessionid");
	
	UMarketSCStub stub =new UMarketSCStub();//the default implementation should point to the right endpoint

	UMarketSCStub.Createsession createsessionReq= new UMarketSCStub.Createsession();
    CreatesessionResponse createsessionRes=stub.createsession(createsessionReq);
    CreatesessionResponseType createsessionResponseType=createsessionRes.getCreatesessionReturn();
    System.out.println(createsessionResponseType.getResult()+" "+createsessionResponseType.getSessionid()+" "+createsessionResponseType.getResult_namespace());
//    UMarketSCStub.Login loginReq=new UMarketSCStub.Login();
//    LoginRequestType param=new LoginRequestType();
//    param.set
//    loginReq.setLoginRequest(param);
      
  

}

}
