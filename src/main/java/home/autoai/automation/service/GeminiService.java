package home.autoai.automation.service;


import java.util.concurrent.CompletableFuture;

;

public interface GeminiService {
    CompletableFuture<String> getGeminiAnswer(String text);
}
