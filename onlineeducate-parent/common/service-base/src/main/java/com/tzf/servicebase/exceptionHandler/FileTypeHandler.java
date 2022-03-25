package com.tzf.servicebase.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件类型异常
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTypeHandler extends RuntimeException {
    private Integer code;
    private String message;
}
