package xpay;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mbv.airtime2.integration.IntegrationPayload;
import com.mbv.airtime2.xpay.XpayService;

public class TestMain {

	public static void main(String[] args) {
//
//		ActorSystem actorSystem = ActorSystem.create("XpayService");
//		
//		Props MobifoneRouterProps = new Props(new UntypedActorFactory() {			
//			private static final long serialVersionUID = 1L;
//			public Actor create() throws Exception {
//				XpayConfig config = new XpayConfig();
//				Mapper mapper = null;
//				return new XpayMaster(config, mapper);
//			}
//		});
//		
//		ActorRef master = actorSystem.actorOf(MobifoneRouterProps, "XpayMaster");
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("TestContext.xml");
		XpayService service = (XpayService) context.getBean("XpayService");
		
		IntegrationPayload payload = new IntegrationPayload();
		payload.put("telco_id","MOBIP2");
		payload.put("transaction_id", "201506040000545482");
		payload.put("msisdn", "0902421990");
		payload.put("amount", "3000");
		try {
			Thread.sleep(10000);
//			service.process(payload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
