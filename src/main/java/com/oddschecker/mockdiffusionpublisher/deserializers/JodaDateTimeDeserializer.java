package com.oddschecker.mockdiffusionpublisher.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;

public class JodaDateTimeDeserializer extends JsonDeserializer {

    @Override
    public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String dateTime = jsonParser.getValueAsString();
        if(dateTime == null || dateTime.isEmpty()) { return null; }
        return DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss").parseDateTime(dateTime);
    }
}
