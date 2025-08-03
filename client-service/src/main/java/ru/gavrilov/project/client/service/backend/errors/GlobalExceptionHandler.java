package ru.gavrilov.project.client.service.backend.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDto> catchResourceNotFoundException(ResourceNotFoundException e) {
//        ErrorDto errorDto = new ErrorDto("RESOURCE_NOT_FOUND", e.getMessage());
//        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(AppLogicException.class)
    public ResponseEntity<ErrorDto> catchAppLogicException(AppLogicException e) {
        log.error("Error occurred: {} - {}", e.getCode(), e.getMessage());
        ErrorDto errorDto = new ErrorDto(e.getCode(), e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(RepeatSubscriptionException.class)
//    public ResponseEntity<ErrorDto> catchRepeatSubscriptionException(RepeatSubscriptionException e) {
//        ErrorDto errorDto = new ErrorDto("REPEAT_SUBSCRIPTION", e.getMessage());
//        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
//    }
}
