package com.stomat.controllers.api.errors;

import com.stomat.exceptions.NotFoundException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice(basePackages = "com.stomat.controllers.api")
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        ApiResponseError apiResponseError = new ApiResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
        return new ResponseEntity<Object>(
                apiResponseError, new HttpHeaders(), apiResponseError.getStatus());
    }

    // @Validate For Validating Path Variables and Request Parameters
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(Exception ex, WebRequest request, HttpServletResponse response) throws IOException {
        List<ApiError> errors = new ArrayList<ApiError>();
        for (ConstraintViolation cv : ((ConstraintViolationException) ex).getConstraintViolations()) {
            PathImpl propertyPath = (PathImpl) cv.getPropertyPath();
            errors.add(new ApiError(propertyPath.getLeafNode().getName(), cv.getMessage()));
        }

        ApiResponseError apiResponseError =
                new ApiResponseError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity(apiResponseError, apiResponseError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<ApiError> errors = new ArrayList<ApiError>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ApiError(error.getField(), error.getDefaultMessage()));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(new ApiError(error.getObjectName(), error.getDefaultMessage()));
        }

        ApiResponseError apiResponseError =
                new ApiResponseError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
                ex, apiResponseError, headers, apiResponseError.getStatus(), request);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handlesNotFoundException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
