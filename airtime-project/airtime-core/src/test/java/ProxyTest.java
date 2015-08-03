import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class ProxyTest {
	
	public static void main(String[] args) {
		
		
		InvocationHandler handler = new MyInvocationHandler();
		
		MyInterface proxy = (MyInterface)Proxy.newProxyInstance(MyInterface.class.getClassLoader(), new Class [] {
			MyInterface.class
		}, handler);
		
		System.err.println("sys..");
		
		proxy.test();
		
	}

}
