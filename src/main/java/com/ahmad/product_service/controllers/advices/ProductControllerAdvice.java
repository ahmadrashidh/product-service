package com.ahmad.product_service.controllers.advices;

import com.ahmad.product_service.dtos.ExceptionDto;
import com.ahmad.product_service.exceptions.ProductNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    private ExceptionDto handleProductNotFoundException(ProductNotFoundException ex){
        ExceptionDto exDto = new ExceptionDto();
        exDto.setMessage(ex.getMessage());
        exDto.setStatus("Failure");
        return exDto;
    }

}
