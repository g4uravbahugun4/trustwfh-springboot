package com.portals.trustwfh.service;

import com.portals.trustwfh.config.KafkaConfig;
import com.portals.trustwfh.dto.PunchInEventMessage;
import com.portals.trustwfh.entity.PunchinEventEntity;
import com.portals.trustwfh.entity.PunchinEventSignalEntity;
import com.portals.trustwfh.repository.PunchinEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KafkaConsumerService {

    private final PunchinEventRepository repository;
    private final TrustLearningService trustLearningService;

    public KafkaConsumerService(PunchinEventRepository repository, TrustLearningService trustLearningService) {
        this.repository = repository;
        this.trustLearningService = trustLearningService;
    }

    @KafkaListener(topics = KafkaConfig.PUNCH_IN_TOPIC, groupId = "trustwfh-group")
    @Transactional
    public void consume(PunchInEventMessage message) {
        System.out.println("Received PunchInEvent for user: " + message.getUserId());

        // 1. Map DTO back to Entity for Audit Log
        PunchinEventEntity entity = new PunchinEventEntity(message.getUserId(), message.getConfidence());

        if (message.getSignals() != null) {
            for (PunchInEventMessage.SignalMessage signalMsg : message.getSignals()) {
                PunchinEventSignalEntity signalEntity = new PunchinEventSignalEntity(
                        signalMsg.getSignalName(),
                        signalMsg.getScore(),
                        signalMsg.getWeight(),
                        signalMsg.getRemark());
                entity.addSignal(signalEntity);
            }
        }

        repository.save(entity);

        // 2. Adaptive Trust Learning (Self-Healing Trust)
        trustLearningService.learnFromEvent(
                message.getUserId(),
                message.getIpAddress(),
                message.getDeviceToken(),
                message.getDeviceFingerprint(),
                message.getConfidence());
    }
}
