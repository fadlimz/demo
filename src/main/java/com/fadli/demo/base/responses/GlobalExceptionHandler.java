package com.fadli.demo.base.responses;

import com.fadli.demo.base.constants.BaseConstants;
import com.fadli.demo.base.exceptions.BatchBusinessException;
import com.fadli.demo.base.exceptions.BusinessException;
import com.fadli.demo.base.localThread.LocalErrors;
import com.fadli.demo.base.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String GMPMNA = "method.not.allowed";
    private static final String GMPISE = "internal.server.exception";
    private static final String GMPDEV = "developer.exception";
    private static final String GMPBR = "bad.request";
    private static final String GMPQE = "query.exception";
    private static final String GMPOLE = "optimistic.lock.exception";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(BatchBusinessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse batchBusiness(HttpServletRequest req, BatchBusinessException ex) {
        printStackTrace(ex);
        ArrayList<Message> errors = new ArrayList<>();
        ex.getBusinessExceptions().forEach(
                businessException -> errors.add(getMessageForBusinessException(businessException)));
        removeLocalErrors();
        return ApiResponse.errors(errors);
    }

/*
    @ExceptionHandler(DevException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ApiResponse devs(HttpServletRequest req, DevException ex) {
        String logCode = logError(GMPDEV, req, ex);
        return ApiResponse.error(Message.create().setCode(GMPDEV).setDesc(getDevErrorMessage(logCode, ex.getMessage())));
    }
*/

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse uncaughterror(HttpServletRequest req, Exception ex) {
        String logCode = logError(GMPISE, req, ex);
        /*logger.error("===================User: " + CurrentUser.getUserCode() + "====================");*/

        return ApiResponse.error(Message.create().setCode(GMPISE).setDesc(getDescForUncaughtErrors(logCode)));
    }

    private Message getMessageForBusinessException(BusinessException businessException) {
        Object[] parameters = businessException.getMessageParameters();
        Object[] newParameters;

        if (parameters == null || parameters.length == 0) {
            newParameters = new Object[0];
        } else {
            newParameters = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] == null) {
                    newParameters[i] = BaseConstants.EMPTY;
                } else if (parameters[i] instanceof Date) {
                    newParameters[i] = DateUtil.convertDateToIso8601((Date) parameters[i]);
                } else if (parameters[i] instanceof YearMonth) {
                    newParameters[i] = parameters[i].toString();
                } else {
                    newParameters[i] = parameters[i];
                }
            }
        }

        return Message.create()
                .setCode(businessException.getMessageCode())
                .setDesc(businessException.getMessageCode())
                .setArguments(newParameters);
    }

    private String getDescForHnsErrors(HttpServletRequest req) {
        return "Uri: " + req.getRequestURI() + ", Method: " + req.getMethod() + " not supported";
    }

    private String getDescForJmeErrors() {
        return "Request body not readable";
    }

    private String getDescForNonUniqueResultErrors() {
        return "Data's key anomaly! Wrong bk or wrong query?";
    }

    private String getDevErrorMessage(String logCode, String message) {
        return "Contact the dev if you see this! If you happened to be one of the dev(s), then please fix it "
                + "\nLogCode: '" + logCode + "'"
                + "\nMessage: '" + message + "'";
    }

    private String getDescForUncaughtErrors(String logCode) {
        return "Unexpected error occurred, please contact administrator and attach this code: " + logCode;
    }

    private String logError(String code, HttpServletRequest req, Exception e) {
        String logCode = code + System.currentTimeMillis();
        logger.error("=============================[" + logCode + "]=============================");
        logger.error("request: " + req.getRequestURL());
        logger.error("method: " + req.getMethod());
        printStackTrace(e);
        logger.error("=============================[" + logCode + "]=============================");
        return logCode;
    }

    private void printStackTrace(Exception e) {
        logger.error("trace: ", e);
    }

    public static void removeLocalErrors() {
        LocalErrors.remove();
        LocalErrors.removeMulti();
    }
}
