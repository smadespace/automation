package home.autoai.automation.service.impl;


import home.autoai.automation.exception.GeminiApiException;
import home.autoai.automation.service.GeminiService;
import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.model.GenerateContent200Response;
import org.openapitools.client.model.GenerateContentRequest;
import org.openapitools.client.model.GenerateContentRequestContentsInner;
import org.openapitools.client.model.GenerateContentRequestContentsInnerPartsInner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import java.util.concurrent.CompletableFuture;


@Service
public class GeminiServiceImp implements GeminiService {

    private static final Logger LOG = LoggerFactory.getLogger(GeminiServiceImp.class);
    private final DefaultApi defaultApi;

    public GeminiServiceImp(DefaultApi defaultApi) {
        this.defaultApi = defaultApi;
    }

    @Async
    @Override
    public CompletableFuture<String> getGeminiAnswer(String text) {
        GenerateContentRequest request = new GenerateContentRequest()
                .addContentsItem(
                        new GenerateContentRequestContentsInner()
                                .addPartsItem(
                                        new GenerateContentRequestContentsInnerPartsInner()
                                                .text(text)
                                )
                );

        ResponseEntity<GenerateContent200Response> response;

        try {
             LOG.info("Gemini API request...");
            response = defaultApi.generateContentWithHttpInfo(request);
        } catch (HttpClientErrorException e) {
             LOG.error("Gemini API HTTP error: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new GeminiApiException(
                    "Gemini API HTTP error: " + e.getStatusCode() , e);
        } catch (HttpServerErrorException e) {
             LOG.error("Gemini API Server error: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new GeminiApiException(
                    "Gemini API Server error: " + e.getStatusCode() , e);
        } catch (RestClientException e) {
            LOG.error("Gemini API unexpected error", e);
            throw new GeminiApiException("Gemini API unexpected error", e);
        }

        if (response == null || response.getBody() == null) {
            throw new GeminiApiException("Gemini API returned empty response");
        }
        LOG.info("Gemini API RESPONSE_CODE: {}", response.getStatusCode());
        return CompletableFuture.completedFuture(getGetGeminiAnswerRes(response));
    }

    private static String getGetGeminiAnswerRes(ResponseEntity<GenerateContent200Response> response) {
        GenerateContent200Response body = response.getBody();

        assert body != null;
        if (body.getCandidates() == null || body.getCandidates().isEmpty()) {
            LOG.warn("Gemini API returned no candidates");
            throw new GeminiApiException("Gemini API returned no candidates");
        }
        var candidate = body.getCandidates().get(0);
        if (candidate.getContent() == null ||
                candidate.getContent().getParts() == null ||
                candidate.getContent().getParts().isEmpty()) {
            LOG.warn("Gemini candidate has no content");
            throw new GeminiApiException("Gemini candidate has no content");
        }

        return candidate.getContent().getParts().get(0).getText();
    }

}
