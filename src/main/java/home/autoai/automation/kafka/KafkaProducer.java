package home.autoai.automation.kafka;
import home.autoai.automation.kafka.dto.RequestLogMessage;
import home.autoai.automation.model.GetAllAnswers200Response;


public interface KafkaProducer {
    void sendMsg(RequestLogMessage msg);

 }
