import java.util.HashMap;
import java.util.Map;


public class MapTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Map<Integer, String>map = new HashMap<Integer, String>();
		map.put(1, "1");

	

		map.put(2, "1");
		map.put(1, "1");
		
		
		System.err.println(map);

	}

}
