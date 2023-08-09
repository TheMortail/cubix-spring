package hu.cobix.airport.controller;

import hu.cobix.airport.service.NonUniqueIATAException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NonUniqueIATAException.class)
    public ResponseEntity<MyError> handleNonUniqueIata(NonUniqueIATAException e, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MyError(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MyError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request){
        MyError error = new MyError(e.getMessage());
        error.setFieldErrors(e.getBindingResult().getFieldErrors());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
