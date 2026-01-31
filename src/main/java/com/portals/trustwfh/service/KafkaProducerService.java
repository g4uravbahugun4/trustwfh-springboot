package com.portals.trustwfh.service;

import com.portals.trustwfh.config.KafkaConfig;
import com.portals.trustwfh.dto.PunchInEventMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, PunchInEventMessage> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, PunchInEventMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPunchInEvent(PunchInEventMessage message) {
        kafkaTemplate.send(KafkaConfig.PUNCH_IN_TOPIC, message.getUserId(), message);
    }
}
