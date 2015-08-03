import org.springframework.context.support.GenericXmlApplicationContext;



public class InitTest {
	
	public static void main(String[] args) {
		System.err.println("testing");
		
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		
		ctx.load("classpath:hotelservice-context.xml");
		ctx.registerShutdownHook();
		ctx.refresh();
		
		
		
		
		
	}

}
