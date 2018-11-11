package com.oddschecker.mockdiffusionpublisher.services;

import com.oddschecker.mockdiffusionpublisher.models.Message;
import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.features.control.topics.TopicControl;
import com.pushtechnology.diffusion.client.features.control.topics.TopicUpdateControl;
import com.pushtechnology.diffusion.client.session.Session;
import com.pushtechnology.diffusion.client.session.SessionAttributes;
import com.pushtechnology.diffusion.client.topics.details.TopicType;
import com.pushtechnology.diffusion.datatype.json.JSON;
import com.pushtechnology.diffusion.datatype.json.JSONDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

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

        // attempt to fix wrongly formatted topics
        if(topic.charAt(0) == '/') { topic = topic.substring(1); }

        try {
            TopicControl topicControl = session.feature(TopicControl.class);
            topicControl.addTopic(topic, TopicType.JSON);

            TopicUpdateControl topicUpdateControl = session.feature(TopicUpdateControl.class);

            final JSON value = jsonDataType.fromJsonString(message.getMessage());

            topicUpdateControl.updater().update(topic, value, new TopicUpdateControl.Updater.UpdateCallback.Default());
        } catch (Exception ex) {
            logger.error("Couldn't publish topic {}", topic, ex);
        }
    }

    public void removeTopics(List<String> selectors) {

        String topicsStr = StringUtils.collectionToDelimitedString(selectors, ",");

        try{
            TopicControl topicControl = session.feature(TopicControl.class);
            topicControl.removeTopics(topicsStr);
        } catch (Exception ex) {
            logger.error("Couldn't delete topics based on selectors {}", topicsStr, ex);
        }
    }

    private static Session connect() {
        try {
            return Diffusion.sessions()
                    .principal("admin")
                    .password("password")
                    .serverHost("diffusion")
                    .serverPort(6785)
                    .transports(SessionAttributes.Transport.WEBSOCKET)
                    .secureTransport(false)
                    .open();

        } catch (Exception ex) {
            logger.error("Couldn't connect to diffusion", ex);
            return null;
        }
    }

    @PreDestroy
    private void destroy() {
        session.close();
    }
}
