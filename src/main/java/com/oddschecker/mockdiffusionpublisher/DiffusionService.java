package com.oddschecker.mockdiffusionpublisher;

import com.oddschecker.mockdiffusionpublisher.models.Message;
import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.features.control.topics.TopicControl;
import com.pushtechnology.diffusion.client.features.control.topics.TopicUpdateControl;
import com.pushtechnology.diffusion.client.session.Session;
import com.pushtechnology.diffusion.client.topics.details.TopicType;
import com.pushtechnology.diffusion.datatype.json.JSON;
import com.pushtechnology.diffusion.datatype.json.JSONDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DiffusionService {

    private final static Logger logger = LoggerFactory.getLogger(DiffusionService.class);

    private Session session;
    private final JSONDataType jsonDataType = Diffusion.dataTypes().json();

    @PostConstruct
    private void init() {
        session = connect();
    }

    public void publish(String topic, Message message) {
        try {
            session.feature(TopicControl.class).addTopic(topic, TopicType.JSON);
            final JSON value = jsonDataType.fromJsonString(message.getMessage());
            session.feature(TopicUpdateControl.class).updater().update(topic, value, new TopicUpdateControl.Updater.UpdateCallback.Default());
        } catch (Exception ex) {
            logger.error("Couldn't publish topic {}", topic, ex);
        }
    }

    private static Session connect() {
        try {
            return Diffusion.sessions()
                    .principal("admin")
                    .password("admin")
                    .open("ws://localhost:6458");

        } catch (Exception ex) {
            logger.error("Couldn't connect to diffusion", ex);
            return null;
        }
    }
}
