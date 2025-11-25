package home.autoai.automation;


import home.autoai.automation.exception.GeminiApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(String error, String message) {}
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Not secure !
    @ExceptionHandler(GeminiApiException.class)
    public ResponseEntity<ErrorResponse> handleGeminiException(GeminiApiException ex) {
        return ResponseEntity.status(500).body(new ErrorResponse("Cannot connect to gemini!","check in Logs..."));
    }
}
