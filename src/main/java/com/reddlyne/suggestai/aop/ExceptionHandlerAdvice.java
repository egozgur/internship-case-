package com.reddlyne.suggestai.aop;

import com.reddlyne.suggestai.controller.response.UserLoginResponse;
import com.reddlyne.suggestai.exception.RegistirationNotCompleted;
import com.reddlyne.suggestai.exception.UnexpectedAIFailure;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

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


    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = "Invalid input. Please check your data.";

        Throwable cause = e.getCause();
        if (cause instanceof SQLException) {
            String sqlState = ((SQLException) cause).getSQLState();
            if ("23505".equals(sqlState)) { // PostgreSQL unique violation code
                errorMessage = "Username or email is already in use. Please choose a different one.";
            }
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
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
