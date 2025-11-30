package home.autoai.automation.controller;

import home.autoai.automation.api.QuestionApi;
import home.autoai.automation.model.GetAllAnswers200Response;
import home.autoai.automation.model.GetAllAnswersRequest;
import home.autoai.automation.service.AggregatorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import java.util.Optional;



@Log4j2
@RestController
@RequestMapping("/api/v1")
public class QuestionApiImp implements QuestionApi {


    private final AggregatorService aggregatorService;
    private final HttpServletRequest request;


    public QuestionApiImp(AggregatorService aggregatorService, HttpServletRequest request) {
        this.aggregatorService = aggregatorService;
        this.request = request;
    }


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(new ServletWebRequest(request));
    }


    @Override
    public ResponseEntity<GetAllAnswers200Response> getAllAnswers(GetAllAnswersRequest getAnswerRequest) {
        var clientInfo = getRequest().get();
        log.info("Session IP of the client: {} ", (request.getRemoteAddr().equals("0:0:0:0:0:0:0:1") ?
                "localhost" : request.getRemoteAddr()));
        log.info("Session ID of the client: {} ", clientInfo.getSessionId());
        log.info("User ask: {} ", getAnswerRequest.getQuestion());
        var answer = aggregatorService.aggregateAnswerFromQuestion(getAnswerRequest);
        return ResponseEntity.status(200).body(answer);
    }
}
