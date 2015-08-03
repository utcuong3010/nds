package com.mbv.hotel.http.converter;

import java.text.SimpleDateFormat;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


public class JacksonObjectMapper extends ObjectMapper {
    public JacksonObjectMapper() {
        super();
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
        setSerializationInclusion(Inclusion.NON_NULL);
    }
}
