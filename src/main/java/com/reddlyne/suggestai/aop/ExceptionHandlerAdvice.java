package com.reddlyne.suggestai.aop;

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

    /*@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResult> handleConstraintViolationExceptions(ConstraintViolationException e, WebRequest request) {
        ExceptionResult exceptionResult = new ExceptionResult();
        exceptionResult.setDescription("Constraint violation.");
        exceptionResult.setMessage(e.getMessage());
        exceptionResult.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionResult);
    }*/

    /*@ExceptionHandler(value = RegistirationNotCompleted.class)
    @ExceptionHandler(value = UserNotFoundException.class)
    @ExceptionHandler(value = AuthenticationFailure.class)
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    @ExceptionHandler(value = UserNotCreatedException.class)
    @ExceptionHandler(value = UserNotUpdatedException.class)
    @ExceptionHandler(value = UserNotDeletedException.class)





   /*@ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ExceptionResult> handleExceptions(Exception e) {
        ExceptionResult exceptionResult = new ExceptionResult();
        exceptionResult.setDescription("Something went wrong.");
        exceptionResult.setMessage(e.getMessage());
        exceptionResult.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResult);
    }*/
}
