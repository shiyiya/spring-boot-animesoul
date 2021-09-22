package com.app.animesoul.config;

import com.app.animesoul.exception.ErrorsRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

// https://blog.csdn.net/jaryun260716001/article/details/87984254

@ControllerAdvice
public class RestErrorHandler {

    @ResponseBody
    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorsRequestException> handleBindException(BindException e) {
        Logger.getGlobal().info("RestErrorHandler" + e.toString());
        final ErrorsRequestException response = new ErrorsRequestException();
        final BindingResult bindingResult = e.getBindingResult();

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrorList) {
                final HashMap<String, String> map = new HashMap<>();
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
                response.addError(map);
            }
        }

        return ResponseEntity.status(400).body(response);
    }


//    @ResponseBody
//    @ExceptionHandler(Exception.class) // @ExceptionHandler(BindException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public Object handleException(Exception e) {
//        ErrorsRequestException response = new ErrorsRequestException();
//        Logger.getGlobal().info("RestErrorHandler" + e.getClass());
//
//        BindingResult bindingResult = null;
//        if (e instanceof MethodArgumentNotValidException) {
//            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
//
//        } else if (e instanceof BindException) {
//            bindingResult = ((BindException) e).getBindingResult();
//        }
//
//        if (bindingResult == null) return e;
//
//        if (bindingResult.hasErrors()) {
//            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//            Logger.getGlobal().info("RestErrorHandler" + fieldErrorList);
//
//            for (FieldError fieldError : fieldErrorList) {
//                Logger.getGlobal().info("RestErrorHandler22" + fieldError.getDefaultMessage());
//                response.addError(fieldError.getDefaultMessage());
//            }
//        }
//        Logger.getGlobal().info("RestErrorHandler response" + response);
//
//        return response;
//    }


}