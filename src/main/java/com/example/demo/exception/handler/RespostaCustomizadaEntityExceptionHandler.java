package com.example.demo.exception.handler;

import com.example.demo.exception.CpfInvalidoException;
import com.example.demo.exception.ExceptionResponse;
import com.example.demo.exception.OperacaoNaoAutorizadaException;
import com.example.demo.exception.SaldoInicialInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class RespostaCustomizadaEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CpfInvalidoException.class)
    public final ResponseEntity<ExceptionResponse> handleCpfInvalidoException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaldoInicialInvalidoException.class)
    public final ResponseEntity<ExceptionResponse> handleSaldoIncialInvalidoException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperacaoNaoAutorizadaException.class)
    public final ResponseEntity<ExceptionResponse> handleOperacaoNaoAutorizadaException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}
