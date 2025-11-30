package home.autoai.automation.service.impl;


import home.autoai.automation.service.KafkaProducerService;
import home.autoai.automation.dto.RequestLogMessage;
import home.autoai.automation.model.GetAllAnswers200Response;
import home.autoai.automation.model.GetAllAnswersRequest;
import home.autoai.automation.service.AggregatorService;
import home.autoai.automation.service.GeminiService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;


@Log4j2
@Service
public class AggregatorServiceImp implements AggregatorService {


    private final GeminiService geminiService;
    private final KafkaProducerService kafkaProducer;

    public AggregatorServiceImp(KafkaProducerService kafkaProducer, GeminiService geminiService) {
        this.geminiService = geminiService;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public GetAllAnswers200Response aggregateAnswerFromQuestion(GetAllAnswersRequest getAnswerRequest) {

        CompletableFuture<String> geminiReq = geminiService.getGeminiAnswer(getAnswerRequest.getQuestion());
        String answerFromGemini = geminiReq.join();

        StringBuilder answerBuilder = new StringBuilder();
        answerBuilder.append("Gemini: ").append(answerFromGemini).append(System.lineSeparator());

        GetAllAnswers200Response getAllAnswers200Response = new GetAllAnswers200Response();
        getAllAnswers200Response.setAnswer(answerBuilder.toString());
        kafkaProducer.sendMsg(new RequestLogMessage(getAllAnswers200Response.toString()));
        return getAllAnswers200Response;
    }
}
