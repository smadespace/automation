package home.autoai.automation;
import home.autoai.automation.kafka.KafkaProducer;
import home.autoai.automation.model.GetAllAnswers200Response;
import home.autoai.automation.model.GetAllAnswersRequest;
import home.autoai.automation.service.GeminiService;
import home.autoai.automation.service.impl.AggregatorServiceImp;
import org.junit.jupiter.api.Test;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AggregatorServiceImpTest {

    @Test
    void testAggregateAnswerFromQuestion() {
        // Arrange
        GeminiService geminiServiceMock = mock(GeminiService.class);
        //not cover kafka!
        KafkaProducer kafkaProducer = mock(KafkaProducer.class);
        AggregatorServiceImp service = new AggregatorServiceImp(kafkaProducer,geminiServiceMock);

        GetAllAnswersRequest request = new GetAllAnswersRequest();
        request.setQuestion("What is Java?");

        when(geminiServiceMock.getGeminiAnswer("What is Java?"))
                .thenReturn(CompletableFuture.completedFuture("Java is a programming language."));

        // Act
        GetAllAnswers200Response response = service.aggregateAnswerFromQuestion(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.getAnswer().contains("Gemini: Java is a programming language."));

        // Verify GeminiService was called exactly once
        verify(geminiServiceMock, times(1)).getGeminiAnswer("What is Java?");
    }
}
