package com.ahmad.product_service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailEventDto {
    private String to;
    private String emailId;
    private String from;
    private String subject;
    private String body;
}
