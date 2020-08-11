package com.interveiw.poppulo.lotteryticket.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    private Date timestamp;

    private HttpStatus status;

    private Boolean error;

    private String message;
}
