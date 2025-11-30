package home.autoai.automation;


import home.autoai.automation.exception.GeminiApiException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {


    public record ErrorResponse(String error, String message) {}


    // Not secure !
    @ExceptionHandler(GeminiApiException.class)
    public ResponseEntity<ErrorResponse> handleGeminiException(GeminiApiException ex) {
        return ResponseEntity.status(500).body(new ErrorResponse("Cannot connect to gemini!","check in Logs..."));
    }
}
