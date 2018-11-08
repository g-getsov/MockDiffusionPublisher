package com.oddschecker.mockdiffusionpublisher.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.oddschecker.mockdiffusionpublisher.deserializers.JodaDateTimeDeserializer;
import org.joda.time.DateTime;

public class Message {

    @JsonDeserialize(using= JodaDateTimeDeserializer.class)
    private DateTime publishDate;
    private String message;

    public DateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(DateTime publishDate) {
        this.publishDate = publishDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
