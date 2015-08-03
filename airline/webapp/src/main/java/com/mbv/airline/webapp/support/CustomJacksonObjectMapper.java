package com.mbv.airline.webapp.support;

import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


public class CustomJacksonObjectMapper extends ObjectMapper {
    public CustomJacksonObjectMapper() {
        super();
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
        setSerializationInclusion(Inclusion.NON_NULL);
    }
}
