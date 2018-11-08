package com.oddschecker.mockdiffusionpublisher.models;

import java.util.Set;

public class Topic {

    private String path;
    private Set<Message> messages;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
