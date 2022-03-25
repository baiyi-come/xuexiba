package com.tzf.servicebase.exceptionHandler;

import com.tzf.commonutil.Resouce;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody// 作为json输出
    public Resouce error(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return Resouce.error().message("出错了/(ㄒoㄒ)/~~");
    }

    /**
     * 全局通用异常设置
     * @param e
     * @return
     */
    @ExceptionHandler(GlobalCommonExceptionHandler.class)
    @ResponseBody
    public Resouce globalCommon(GlobalCommonExceptionHandler e) {
        e.printStackTrace();

        return Resouce.error().code(e.getCode()).message(e.getMessage());
    }

    /**
     * 文件为空时报此异常
     * @param e
     * @return
     */
    @ExceptionHandler(FileIsNullHandler.class)
    @ResponseBody
    public Resouce fileIsNull(FileIsNullHandler e) {
        e.printStackTrace();
        return Resouce.error().code(e.getCode()).message(e.getMessage());
    }

    /**
     * 文件类型错误异常
     * @param e
     * @return
     */
    @ExceptionHandler(FileTypeHandler.class)
    @ResponseBody
    public Resouce fileTypeError(FileTypeHandler e) {
        e.printStackTrace();
        return Resouce.error().code(e.getCode()).message(e.getMessage());
    }


}
