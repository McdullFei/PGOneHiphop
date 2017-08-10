package com.atlas.business.exception;

import com.atlas.core.web.AcResult;
import com.atlas.framework.exception.PGException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 业务异常返回
 *
 * Created by renfei on 17/6/5.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = PGException.class)
    public AcResult<String> jsonErrorHandler(PGException e){
        logger.error("server error.", e);
        return AcResult.error(e.getErrorMessage());
    }

}
