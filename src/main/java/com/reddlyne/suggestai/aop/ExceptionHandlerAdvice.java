package com.reddlyne.suggestai.aop;

import com.reddlyne.suggestai.controller.response.UserLoginResponse;
import com.reddlyne.suggestai.exception.RegistirationNotCompleted;
import com.reddlyne.suggestai.exception.UnexpectedAIFailure;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import static com.reddlyne.suggestai.constant.DBValidationConstants.*;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = PSQLException.class)
    public ResponseEntity<ExceptionResult> handleDatabaseExceptions(HttpServletRequest req, Exception e) {
        ExceptionResult exceptionResult = new ExceptionResult();
        exceptionResult.setDescription(UNIQUE_CONSTRAINT_DATABASE_MESSAGE);
        exceptionResult.setMessage(e.getMessage());
        exceptionResult.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionResult);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = UNIQUE_CONSTRAINT_BASE_MESSAGE;

        Throwable cause = e.getCause();
        if (cause instanceof SQLException) {
            String sqlState = ((SQLException) cause).getSQLState();
            if (UNIQUE_CONSTRAINT_VIOLATION.equals(sqlState)) {
                errorMessage = UNIQUE_CONSTRAINT_VIOLATION_MESSAGE;
            }
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler ({UnexpectedAIFailure.class})
    public ResponseEntity<Object> handleUnexpectedAIFailureException(
            UnexpectedAIFailure e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(RegistirationNotCompleted.class)
    public ResponseEntity<UserLoginResponse> handleRegistrationNotCompleted(RegistirationNotCompleted ex) {
        String errorMessage = UNIQUE_CONSTRAINT_REGISTER_ERROR;
        UserLoginResponse errorResponse = new UserLoginResponse(false, errorMessage);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResult> handleGeneralException(Exception e) {
        ExceptionResult exceptionResult = new ExceptionResult();
        exceptionResult.setDescription(UNIQUE_CONSTRAINT_UNEXPECTED_ERROR);
        exceptionResult.setMessage(e.getMessage());
        exceptionResult.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResult);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<UserLoginResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        String errorMessage = ex.getMessage();
        UserLoginResponse errorResponse = new UserLoginResponse(false, errorMessage);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
