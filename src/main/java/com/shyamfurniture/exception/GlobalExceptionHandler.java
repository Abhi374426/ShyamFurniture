package com.shyamfurniture.exception;

import com.shyamfurniture.dtos.ApiResponseaMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException notFoundException) {
        ApiResponseaMessage<NotFoundException> responseMessage = ApiResponseaMessage.<NotFoundException>builder()
                .message(notFoundException.getMessage())
                .success(false)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception ex) {
        ApiResponseaMessage<NotFoundException> responseMessage = ApiResponseaMessage.<NotFoundException>builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> exception(BadRequestException ex) {
        ApiResponseaMessage<BadRequestException> responseMessage = ApiResponseaMessage.<BadRequestException>builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<?> exception(ResourceAlreadyExistsException ex) {
        ApiResponseaMessage<ResourceAlreadyExistsException> responseMessage = ApiResponseaMessage.<ResourceAlreadyExistsException>builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.CONFLICT)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String,Object>  responseMessage=new HashMap<>();
          List<ObjectError> allError= ex.getBindingResult().getAllErrors();
          allError.stream().forEach(objectError -> {
              String message=objectError.getDefaultMessage();
              String field=((FieldError) objectError).getField();
              responseMessage.put(field,message);
          });
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }



}
