package com.blog.exception;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  public final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public final ResponseEntity<ErrorDetails> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails();
    Throwable mostSpecificCause = ex.getMostSpecificCause();
    if (mostSpecificCause != null) {
      String exceptionName = mostSpecificCause.getClass().getName();
      String message = mostSpecificCause.getMessage();
      errorDetails = setErrorDetails(exceptionName + " -- " + message,
          request.getDescription(false), ex.getClass().getName());
    } else {
      errorDetails =
          setErrorDetails(ex.getMessage(), request.getDescription(false), ex.getClass().getName());
    }
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public final ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex, WebRequest request) {

    ErrorDetails errorDetails =
        setErrorDetails(ex.getMessage(), request.getDescription(false), ex.getClass().getName());

    ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
      errorDetails
          .setMessage(fieldError.getDefaultMessage() + " - Field name - " + fieldError.getField());
      LOGGER.info("Invalid {} value submitted for {}", fieldError.getDefaultMessage(),
          fieldError.getField());
    });

    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public final ResponseEntity<ErrorDetails> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex, WebRequest request) {
    ErrorDetails errorDetails =
        setErrorDetails(ex.getMessage(), request.getDescription(false), ex.getClass().getName());
    return new ResponseEntity<>(errorDetails, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public final ResponseEntity<ErrorDetails> handleHttpMediaTypeNotSupportedException(
      HttpMediaTypeNotSupportedException ex, WebRequest request) {
    ErrorDetails errorDetails =
        setErrorDetails(ex.getMessage(), request.getDescription(false), ex.getClass().getName());
    return new ResponseEntity<>(errorDetails, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler(MissingPathVariableException.class)
  public final ResponseEntity<ErrorDetails> handleMissingPathVariableException(
      MissingPathVariableException ex, WebRequest request) {
    ErrorDetails errorDetails =
        setErrorDetails(ex.getMessage(), request.getDescription(false), ex.getClass().getName());
    return new ResponseEntity<>(errorDetails, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public final ResponseEntity<ErrorDetails> handleNoHandlerFoundException(
      NoHandlerFoundException ex, WebRequest request) {
    ErrorDetails errorDetails =
        setErrorDetails(ex.getMessage(), request.getDescription(false), ex.getClass().getName());
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  // @ExceptionHandler(HttpMessageNotWritableException.class)
  public final ResponseEntity<ErrorDetails> handleHttpMessageNotWritableException(
      HttpMessageNotWritableException ex, WebRequest request) {
    ErrorDetails errorDetails =
        setErrorDetails(ex.getMessage(), request.getDescription(false), ex.getClass().getName());
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  private ErrorDetails setErrorDetails(String message, String details, String exceptionName) {
    LOGGER.info("Error message {} - details - {} - exception - {} ", message, details,
        exceptionName);
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), message, details);
    return errorDetails;
  }
}
