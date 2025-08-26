package com.shyamfurniture.dtos;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ApiResponseaMessage<T>{

    private String message;
    private boolean success;
    private HttpStatus status;
    private T data;








}
