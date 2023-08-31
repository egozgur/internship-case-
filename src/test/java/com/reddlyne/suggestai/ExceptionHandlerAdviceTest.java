package com.reddlyne.suggestai;

import com.reddlyne.suggestai.aop.ExceptionHandlerAdvice;
import com.reddlyne.suggestai.aop.ExceptionResult;
import com.reddlyne.suggestai.controller.response.UserLoginResponse;
import com.reddlyne.suggestai.exception.RegistirationNotCompleted;
import com.reddlyne.suggestai.exception.UnexpectedAIFailure;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ExceptionHandlerAdviceTest {

    private ExceptionHandlerAdvice exceptionHandlerAdvice;
    private HttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        exceptionHandlerAdvice = new ExceptionHandlerAdvice();
        mockRequest = mock(HttpServletRequest.class);
    }

    @Test
    void testHandleDatabaseExceptions() {
        PSQLException mockException = mock(PSQLException.class);
        when(mockException.getMessage()).thenReturn("Database error.");

        ResponseEntity<ExceptionResult> response = exceptionHandlerAdvice.handleDatabaseExceptions(mockRequest, mockException);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        ExceptionResult responseBody = response.getBody();
        assertEquals("Database error.", responseBody.getDescription());
        assertEquals("Database error.", responseBody.getMessage());
    }

    @Test
    void testHandleConstraintViolationException() {
        ConstraintViolationException mockException = mock(ConstraintViolationException.class);
        SQLException mockCause = mock(SQLException.class);
        when(mockCause.getSQLState()).thenReturn("23505");
        when(mockException.getCause()).thenReturn(mockCause);

        ResponseEntity<Object> response = exceptionHandlerAdvice.handleConstraintViolationException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username or email is already in use. Please choose a different one.", response.getBody());
    }

    @Test
    void testHandleUnexpectedAIFailureException() {
        UnexpectedAIFailure mockException = mock(UnexpectedAIFailure.class);

        ResponseEntity<Object> response = exceptionHandlerAdvice.handleUnexpectedAIFailureException(mockException);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals(mockException.getMessage(), response.getBody());
    }

}
