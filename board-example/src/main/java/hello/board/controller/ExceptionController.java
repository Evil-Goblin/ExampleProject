package hello.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

    public static final String BAD_REQUEST_STRING = "잘못된 요청입니다.";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentHandler(IllegalArgumentException exception, Model model) {
        setModelAttribute(model, BAD_REQUEST_STRING, exception.getMessage());

        return "error";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public String illegalStateHandler(IllegalStateException exception, Model model) {
        setModelAttribute(model, BAD_REQUEST_STRING, exception.getMessage());

        return "error";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String fieldValidHandler(MethodArgumentNotValidException exception, Model model) {
        FieldError fieldError = exception.getFieldError();
        String field = fieldError.getField();
        String defaultMessage = fieldError.getDefaultMessage();

        setModelAttribute(model, field, defaultMessage);

        return "error";
    }

    private void setModelAttribute(Model model, String title, String message) {
        model.addAttribute("error_title", title);
        model.addAttribute("error_message", message);
    }
}
