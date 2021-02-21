package com.datastax.workshop.enity.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomError {

    private String error;
    private String message;
}
