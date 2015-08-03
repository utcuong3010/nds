package com.mbv.bp.queue.utils;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.integration.ContextBase;
public class Utils {
	private static Log log = LogFactory.getLog(Utils.class);
	public static ContextBase getContextBaseFromMap(Map<String, String> map){
		ContextBase contextBase=new ContextBase();
		if (map==null) return contextBase;
		for(Entry< String, String> entry:map.entrySet()){
			contextBase.put(entry.getKey(), entry.getValue());
		}
		return contextBase;
	}
}
