package com.oddschecker.mockdiffusionpublisher.controllers;

import com.oddschecker.mockdiffusionpublisher.services.DiffusionPublisher;
import com.oddschecker.mockdiffusionpublisher.containers.PublishResponseContainer;
import com.oddschecker.mockdiffusionpublisher.containers.PublishRequestContainer;
import com.oddschecker.mockdiffusionpublisher.enums.ResponseStatus;
import com.oddschecker.mockdiffusionpublisher.models.Topic;
import com.oddschecker.mockdiffusionpublisher.services.DiffusionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Controller
@RequestMapping("/diffusion")
public class DiffusionController {

    @Autowired
    private DiffusionPublisher diffusionPublisher;

    @Autowired
    private DiffusionService diffusionService;

    @PostMapping(value = "/publish")
    @ResponseBody
    public PublishResponseContainer handlePublishRequest(@RequestBody PublishRequestContainer request) {

        if(request == null || isEmpty(request.getTopics())) { return new PublishResponseContainer(ResponseStatus.ERROR); }

        for (Topic topic: request.getTopics()) {
            diffusionPublisher.addTopicToQueue(topic);
        }

        return new PublishResponseContainer(ResponseStatus.OK);
    }

    @DeleteMapping(value = "/delete-topics")
    @ResponseBody
    public PublishResponseContainer handleDeleteTopicsRequest(@RequestParam("selectors") List<String> selectors) {

        if(isEmpty(selectors)) { return new PublishResponseContainer(ResponseStatus.ERROR); }

        diffusionService.removeTopics(selectors);

        return new PublishResponseContainer(ResponseStatus.OK);
    }
}