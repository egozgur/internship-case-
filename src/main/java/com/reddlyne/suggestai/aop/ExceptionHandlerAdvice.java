package com.reddlyne.suggestai.aop;

import com.reddlyne.suggestai.controller.response.UserLoginResponse;
import com.reddlyne.suggestai.exception.RegistirationNotCompleted;
import com.reddlyne.suggestai.exception.UnexpectedAIFailure;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.util.Set;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = PSQLException.class)
    public ResponseEntity<ExceptionResult> handleDatabaseExceptions(HttpServletRequest req, Exception e) {
        ExceptionResult exceptionResult = new ExceptionResult();
        exceptionResult.setDescription("Database error.");
        exceptionResult.setMessage(e.getMessage());
        exceptionResult.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionResult);
    }

    @ExceptionHandler ({ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler ({UnexpectedAIFailure.class})
    protected ResponseEntity<Object> handleUnexpectedAIFailureException(
            UnexpectedAIFailure e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(RegistirationNotCompleted.class)
    public ResponseEntity<UserLoginResponse> handleRegistrationNotCompleted(RegistirationNotCompleted ex) {
        String errorMessage = "Wrong Username or Password ";
        UserLoginResponse errorResponse = new UserLoginResponse(false, errorMessage);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
