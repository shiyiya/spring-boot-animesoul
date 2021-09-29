package com.app.animesoul.config;

import com.app.animesoul.exception.ValidationError;
import com.app.animesoul.payload.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

// https://blog.csdn.net/jaryun260716001/article/details/87984254
// https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
// https://www.bezkoder.com/spring-boot-controlleradvice-exceptionhandler/
// https://www.tabnine.com/code/java/methods/org.springframework.validation.BindException/getAllErrors

// https://blog.codeleak.pl/2013/11/controlleradvice-improvements-in-spring.html

@ControllerAdvice
public class RestErrorHandler {

    @ResponseBody
    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBindException(BindException e) {
        Logger.getGlobal().info("RestErrorHandler handleBindException " + e.toString());

        return getErrors(e, e.getBindingResult());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        Logger.getGlobal().info("RestErrorHandler handleMethodArgumentNotValid " + exception.toString());
        return getErrors(exception, exception.getBindingResult());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException exception) {
        Logger.getGlobal().info("RestErrorHandler handleConstraintViolation " + exception.toString());
        return ResponseEntity.badRequest().body(exception);
    }

    private ResponseEntity<?> getErrors(Exception e, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            final ValidationError response = new ValidationError();
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrorList) {
                final HashMap<String, String> map = new HashMap<>();
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
                response.addError(map);
            }
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.badRequest().body(ApiResponse.x(400, e.getLocalizedMessage()));
    }

}