package home.autoai.automation.kafka.impl;

import home.autoai.automation.kafka.KafkaProducer;
import home.autoai.automation.kafka.dto.RequestLogMessage;
import home.autoai.automation.model.GetAllAnswers200Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class KafkaProducerImp implements KafkaProducer {

    @Value("${kafka.topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String, RequestLogMessage> kafkaTemplate;

    public KafkaProducerImp(KafkaTemplate<String, RequestLogMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Override
    public void sendMsg(RequestLogMessage msg) {
        kafkaTemplate.send(kafkaTopic, msg);
    }


}
