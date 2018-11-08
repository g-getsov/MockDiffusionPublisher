package com.oddschecker.mockdiffusionpublisher;

import com.google.gson.JsonObject;
import com.oddschecker.mockdiffusionpublisher.models.PublishRequestContainer;
import com.oddschecker.mockdiffusionpublisher.models.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.util.CollectionUtils.isEmpty;

@Controller
@RequestMapping("/diffusion")
public class InputController {

    @Autowired
    private DiffusionPublisher diffusionPublisher;

    @PostMapping(value = "/publish")
    public JsonObject handlePublishRequest(@RequestBody PublishRequestContainer request) {

        if(request == null || isEmpty(request.getTopics())) { return new JsonObject(); }

        for (Topic topic: request.getTopics()) {
            diffusionPublisher.addTopicToQueue(topic);
        }

        return new JsonObject();
    }

    @DeleteMapping(value = "/evict")
    public void handleEvictRequest() {
        diffusionPublisher.evictQueue();
    }

}