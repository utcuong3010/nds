package com.mbv.bp.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.map.JavaTypeMapper;

import com.mbv.bp.common.executor.Executor;

public class JacksonUtils {

    // ----------- Private static members --------

	private static Logger logger = Logger.getLogger(Executor.class);    // Logging object

    private static JsonFactory jf = new JsonFactory();                 // JSON factory

    @SuppressWarnings("unchecked")
	public static List<Map<String, String>> getListFromJsonArray(String str) {
        try {
            if (StringUtils.isNotBlank(str)) {
                ArrayList<Map<String, String>> arrList = (ArrayList<Map<String, String>>) new JavaTypeMapper().read(jf.createJsonParser(new StringReader(str)));
                return arrList;
            } else {
                logger.warn("JacksonUtil.getListsFromJsonArray error| ErrMsg: input string is null ");
                return null;
            }
        } catch (Exception e) {
            logger.error("JacksonUtil.getListsFromJsonArray error| ErrMsg: " + e.getMessage(), e);
            return null;
        }

    }

    @SuppressWarnings("unchecked")
	public static Map<String, String> getMapFromJsonString(String str) {
        try {
            if (StringUtils.isNotBlank(str)) {
                Map<String, String> map = (Map<String, String>) new JavaTypeMapper().read(jf.createJsonParser(new StringReader(str)));
                return map;
            } else {
                logger.warn("ErrMsg: input string is null ");
                return null;
            }
        } catch (Exception e) {
            logger.warn("ErrMsg: " + e.getMessage());
            return null;
        }

    }

    public static String getJsonStringFromList(List<Map<String, String>> list) {
        try {
            StringWriter sw = new StringWriter();
            JsonGenerator gen = jf.createJsonGenerator(sw);
            new JavaTypeMapper().writeAny(gen, list);
            gen.flush();
            return sw.toString();
        } catch (Exception e) {
            logger.error("JacksonUtil.getJsonStringFromMap error| ErrMsg: " + e.getMessage(), e);
            return null;
        }
    }

    public static String getJsonStringFromMap(Map<String, String> aMap) {
        try {
            StringWriter sw = new StringWriter();
            JsonGenerator gen = jf.createJsonGenerator(sw);
            new JavaTypeMapper().writeAny(gen, aMap);
            gen.flush();
            return sw.toString();
        } catch (Exception e) {
            logger.error("ErrMsg: " + e.getMessage(), e);
            return null;
        }
    }

}
