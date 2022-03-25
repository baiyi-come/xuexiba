package com.tzf.servicebase.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileIsNullHandler extends RuntimeException{

    private Integer code;
    private String Message;
}
