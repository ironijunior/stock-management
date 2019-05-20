package br.com.ironijunior.stock.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(OutdatedException.class)
    protected ResponseEntity<String> handleOutdatedException(OutdatedException ex) {
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
