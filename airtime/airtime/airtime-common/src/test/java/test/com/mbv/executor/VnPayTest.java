package test.com.mbv.executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.exception.IntegrationException;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;

public class VnPayTest {
public static void main(String[] args) {
	 try {
		 /*
		 ExecutorFactory.getInstance().initExecutor(ExecutorFactory.VNPAY_EXECUTOR,"vnpay-client","127.0.0.1",10002);
		 
		 IExecutor<ContextBase> executor= ExecutorFactory.getInstance().getExecutor(ExecutorFactory.VNPAY_EXECUTOR) ;
		 ContextBase context=new ContextBase();
		 context.put(AppAttribute.ATTR_REQUEST_MESSAGE, "01570200F000400100010001000000000000000110091234567800000000000010000060111210100000000203801014201104181448400201420110418000159862CDF3BB191BC22A928931622D6CB2B");
		 executor.process(context);
		 System.out.println(context);
		 
		 executor.process(context);
		 System.out.println(context);
		 ExecutorFactory.getInstance().destroyExecutor(ExecutorFactory.VNPAY_EXECUTOR);
		 */
		 
		 ExecutorFactory.getInstance().initExecutor(ExecutorFactory.WFP_EXECUTOR);
		 long startTime =System.currentTimeMillis(); 
		 IExecutor<ContextBase> executor= ExecutorFactory.getInstance().getExecutor(ExecutorFactory.VNPAY_EXECUTOR) ;
		 ContextBase context=new ContextBase();
		 context.put(Attributes.ATTR_REQUEST_MESSAGE, "01570200F000400100010001000000000000000110091234567800000000000010000060111210100000000203801014201104181448400201420110418000159862CDF3BB191BC22A928931622D6CB2B");
		 
		 context.put(Attributes.ATTR_DEST_WFP, "dest-wfp");
		 context.put(Attributes.ATTR_QUEUE_ID, "VNPAY_DELIVERY");
		 context.put(Attributes.ATTR_QUEUE_REQUEST_ID, "request-id");
		 try {
				String s="";
				while (!s.equalsIgnoreCase("bye")){
				InputStreamReader isr = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(isr);
				s = br.readLine();
				
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 executor.process(context);
		 System.out.println("result-"+context+"|elapsedTime-"+(System.currentTimeMillis()-startTime));
		 //executor.process(context);
		 //System.out.println("result-"+context+"|elapsedTime-"+(System.currentTimeMillis()-startTime));
		 
		// ExecutorFactory.getInstance().destroyExecutor(ExecutorFactory.QUEUE_EXECUTOR);
		 
	} catch (IntegrationException e) {
		e.printStackTrace();
	}
}
}
