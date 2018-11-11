package com.oddschecker.mockdiffusionpublisher.containers;

import com.oddschecker.mockdiffusionpublisher.models.Topic;

import java.util.List;

public class PublishRequestContainer {
    private List<Topic> topics;

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
