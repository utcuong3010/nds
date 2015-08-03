import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mbv.module.spring.context.ContextManager;



public class ContextManagerTest {
	
	public static void main(String[] args) {
		
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:airtime-core.xml");
		
		ContextManager contextManager = context.getBean(ContextManager.class);
		contextManager.assignCurrentContext("la");
		
		
		System.err.println(contextManager.getContextAdapters());
		
		contextManager.disposeCurrentContext();
	}

}
