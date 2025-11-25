package home.autoai.automation.service;

import home.autoai.automation.model.GetAllAnswers200Response;
import home.autoai.automation.model.GetAllAnswersRequest;

public interface AggregatorService {
    GetAllAnswers200Response aggregateAnswerFromQuestion(GetAllAnswersRequest getAnswerRequest);
}
