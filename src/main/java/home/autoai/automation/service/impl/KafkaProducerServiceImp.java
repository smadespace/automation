package home.autoai.automation.service.impl;

import home.autoai.automation.service.KafkaProducerService;
import home.autoai.automation.dto.RequestLogMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducerServiceImp implements KafkaProducerService {

    @Value("${kafka.topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String, RequestLogMessage> kafkaTemplate;

    public KafkaProducerServiceImp(KafkaTemplate<String, RequestLogMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Override
    public void sendMsg(RequestLogMessage msg) {
        kafkaTemplate.send(kafkaTopic, msg);
    }


}
