package com.interveiw.poppulo.lotteryticket.Exceptions;

import com.interveiw.poppulo.lotteryticket.Exceptions.CustomExceptions.*;
import com.interveiw.poppulo.lotteryticket.data.dto.ResponseDTO;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Log
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StatusAlreadyExistsException.class)
    @ResponseBody
    public ResponseDTO handleStatusAlreadyExistsException(HttpServletRequest req,
                                                          HttpServletResponse response,
                                                          StatusAlreadyExistsException e)
    {
        log.log(Level.SEVERE, e.getMessage());
        return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, true, e.getMessage());
    }

    @ExceptionHandler(ErrorGeneratingLineNumbers.class)
    @ResponseBody
    public ResponseDTO handleErrorGeneratingLineNumbers(HttpServletRequest req,
                                                        HttpServletResponse response,
                                                        ErrorGeneratingLineNumbers e)
    {
        log.log(Level.SEVERE, e.getMessage());
        return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, true, e.getMessage());
    }

    @ExceptionHandler(LotteryTicketDoesNotExistException.class)
    @ResponseBody
    public ResponseDTO handleLotteryTicketDoesNotExistException(HttpServletRequest req,
                                                                HttpServletResponse response,
                                                                LotteryTicketDoesNotExistException e)
    {
        log.log(Level.SEVERE, e.getMessage());
        return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, true, e.getMessage());
    }

    @ExceptionHandler(LotteryTicketNullException.class)
    @ResponseBody
    public ResponseDTO handleLotteryTicketNullException(HttpServletRequest req,
                                                        HttpServletResponse response,
                                                        LotteryTicketNullException e)
    {
        log.log(Level.SEVERE, e.getMessage());
        return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, true, e.getMessage());
    }


    @ExceptionHandler(TicketIdNullException.class)
    @ResponseBody
    public ResponseDTO handleTicketIdNullException(HttpServletRequest req,
                                                   HttpServletResponse response,
                                                   TicketIdNullException e)
    {
        log.log(Level.SEVERE, e.getMessage());
        return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, true, e.getMessage());
    }

    @ExceptionHandler(TicketInputMalformedException.class)
    @ResponseBody
    public ResponseDTO handleTicketInputMalformedException(HttpServletRequest req,
                                                           HttpServletResponse response,
                                                           TicketInputMalformedException e)
    {
        log.log(Level.SEVERE, e.getMessage());
        return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, true, e.getMessage());
    }

    @ExceptionHandler(ConstantLineException.class)
    @ResponseBody
    public ResponseDTO handleConstantLineException(HttpServletRequest req,
                                                   HttpServletResponse response,
                                                   TicketInputMalformedException e)
    {
        log.log(Level.SEVERE, e.getMessage());
        return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, true, e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request)
    {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        // Get all errors
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(x -> x.getDefaultMessage()).collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException
    {
        String errorMessage = "Numbers constraint has not met the specification, " +
                "(either the digits or line of the ticket)";
        log.log(Level.SEVERE, errorMessage);
        response.sendError(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }
}
