import java.lang.reflect.Method;

import com.mbv.airtime.AirtimeConfiguration;


public class ReflectionTest1 {

	public static void main(String[] args) {
		
		AirtimeConfiguration obj = new AirtimeConfiguration("localhosy", "2019");
		
		Method[] methods = obj.getClass().getMethods();
		for (Method method: methods) {
			System.err.println("method=" +  method.getName());
		}

	}

}
