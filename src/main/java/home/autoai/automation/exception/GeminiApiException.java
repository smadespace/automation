package home.autoai.automation.exception;


public class GeminiApiException extends RuntimeException {

    public GeminiApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeminiApiException(String message) {
        super(message);
    }
}
