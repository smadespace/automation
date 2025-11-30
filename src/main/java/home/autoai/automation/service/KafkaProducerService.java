package home.autoai.automation.service;

import home.autoai.automation.dto.RequestLogMessage;


public interface KafkaProducerService {

    void sendMsg(RequestLogMessage msg);

}
