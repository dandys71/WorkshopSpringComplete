package cz.uhk.fim.workshop.controller;

import cz.uhk.fim.workshop.exeption.EventNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler  {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleDtoInExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getAllErrors().forEach((e) -> {
            String mapName = ((FieldError) e).getField();
            String errorMessage = e.getDefaultMessage();
            errors.put(mapName, errorMessage);
        });
        errors.put("message", ex.getBody().getDetail());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleReadableException(HttpMessageNotReadableException ex){
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EventNotFoundException.class)
    public Map<String, String> handleEventNotFoundException(EventNotFoundException ex){
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return error;
    }
}
