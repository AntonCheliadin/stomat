package com.stomat.controllers.api.errors;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class ApiResponseError {
    private HttpStatus status;
    private String message;
    private List<ApiError> errors;

    ApiResponseError(HttpStatus status, String message, List<ApiError> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiResponseError(HttpStatus status, String message, ApiError error) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }


    ApiResponseError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.emptyList();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ApiError> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiError> errors) {
        this.errors = errors;
    }
}
