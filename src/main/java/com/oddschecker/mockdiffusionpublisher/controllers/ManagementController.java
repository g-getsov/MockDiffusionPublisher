package com.oddschecker.mockdiffusionpublisher.controllers;

import com.oddschecker.mockdiffusionpublisher.containers.EvictResponseContainer;
import com.oddschecker.mockdiffusionpublisher.enums.ResponseStatus;
import com.oddschecker.mockdiffusionpublisher.services.DiffusionPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    private DiffusionPublisher diffusionPublisher;

    @DeleteMapping(value = "/evict-queued-messages")
    @ResponseBody
    public EvictResponseContainer handleEvictQueuedMessagesRequest() {
        diffusionPublisher.evictQueue();
        return new EvictResponseContainer(ResponseStatus.OK);
    }
}
