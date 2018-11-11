package com.oddschecker.mockdiffusionpublisher.services;

import com.oddschecker.mockdiffusionpublisher.models.Message;
import com.oddschecker.mockdiffusionpublisher.models.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DiffusionPublisher {

    private static ConcurrentHashMap<String, Set<Message>> topicMessageMap = new ConcurrentHashMap<>();

    @Autowired
    private DiffusionService diffusionService;

    @Scheduled(initialDelay = 10000, fixedDelay = 2000)
    public void run() {

        Iterator<Map.Entry<String, Set<Message>>> entrySetIterator = topicMessageMap.entrySet().iterator();
        while(entrySetIterator.hasNext()) {

            Map.Entry<String, Set<Message>> entry = entrySetIterator.next();
            if(entry.getValue() == null || entry.getValue().isEmpty()) {
                entrySetIterator.remove();
                continue;
            }

            Iterator<Message> iterator = entry.getValue().iterator();

            while (iterator.hasNext()) {

                Message message = iterator.next();
                if(message.getPublishDate().isAfterNow()) { continue; }
                diffusionService.publish(entry.getKey(), message);
                iterator.remove();
            }
        }
    }

    public void addTopicToQueue(Topic topic) {

        if(topic == null || topic.getPath() == null || topic.getPath().isEmpty()) {
            return;
        }

        if (topicMessageMap.containsKey(topic.getPath())) {
            Set<Message> messages = topicMessageMap.get(topic.getPath());
            messages.addAll(topic.getMessages());
            topicMessageMap.put(topic.getPath(), messages);
        } else {
            topicMessageMap.put(topic.getPath(), topic.getMessages());
        }
    }

    public void evictQueue() {
        topicMessageMap.clear();
    }
}
