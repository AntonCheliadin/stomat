package com.stomat.controllers.api.errors;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//todo: use handler in api for return readable errors
@ControllerAdvice
public class StomatRestExceptionHandler extends ResponseEntityExceptionHandler {

}
