package org.examplorfotg.springbootdemo.exception;
import lombok.extern.slf4j.Slf4j;
import org.examplorfotg.springbootdemo.common.BaseResponse;
import org.examplorfotg.springbootdemo.common.BusinessMsgEnum;
import org.examplorfotg.springbootdemo.common.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessEExceptionHandler(BusinessException e) {
      log.error("businessException: " + e.getMessage(), e);
      return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
  }

    @ExceptionHandler(RuntimeException.class)
      public BaseResponse<?> runtimeExceptionHandler(RuntimeException e){
          log.error("runtimeException: " , e);
          return ResultUtils.error( BusinessMsgEnum.SYSTEM_ERROR, e.getMessage(), "");
      }
}
