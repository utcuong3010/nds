package daos;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mbv.hotel.service.HotelService;
import com.mbv.hotel.service.HotelServiceImpl;

public class MongoRepoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:daos-context.xml");
		
		
//		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
//		ctx.load("classpath:dao-context.xml");
//		ctx.refresh();
//		HotelRepository repos = (HotelRepository)ctx.getBean("HotelRepository");
		HotelService hotelService = ctx.getBean(HotelServiceImpl.class);
		
		System.err.println(hotelService.countHotel());
	

	}

}
