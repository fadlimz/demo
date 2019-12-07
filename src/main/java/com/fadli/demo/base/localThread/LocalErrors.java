package com.fadli.demo.base.localThread;

import com.fadli.demo.base.exceptions.BatchBusinessException;
import com.fadli.demo.base.exceptions.BatchBusinessExceptionWrapper;
import com.fadli.demo.base.exceptions.BusinessException;
import com.fadli.demo.base.exceptions.MultiBatchBusinessException;
import org.springframework.stereotype.Component;

@Component
public class LocalErrors {

    private static ThreadLocal<BatchBusinessException> batchBusinessExceptionsHolder = new ThreadLocal<BatchBusinessException>();
    private static ThreadLocal<MultiBatchBusinessException> multiBatchBusinessExceptionsHolder = new ThreadLocal<MultiBatchBusinessException>();

    public static boolean hasErrors() {
        return batchBusinessExceptionsHolder.get() != null;
    }

    private static boolean hasMultiErrors() {
        return multiBatchBusinessExceptionsHolder.get() != null;
    }

    private static void createIfEmpty() {
        if (!hasErrors()) {
            batchBusinessExceptionsHolder.set(new BatchBusinessException());
        }
    }

    private static void createMultiIfEmpty() {
        if (!hasMultiErrors()) {
            multiBatchBusinessExceptionsHolder.set(new MultiBatchBusinessException());
        }
    }

    public void addError(String code, Object... args) {
        getErrors().addBusinessException(new BusinessException(code, args));
    }

    public void wrapBatchErrors(String name) {
        getMultiErrors().addBatchBusinessException(name, getErrors());
    }

    public static BatchBusinessException getErrors() {
        createIfEmpty();
        return batchBusinessExceptionsHolder.get();
    }

    public static MultiBatchBusinessException getMultiErrors() {
        createMultiIfEmpty();
        return multiBatchBusinessExceptionsHolder.get();
    }

    public void throwBatchError() {
        if (batchBusinessExceptionsHolder.get() != null) batchBusinessExceptionsHolder.get().throwBatchBusinessException();
    }

    public void throwMultiBatchError() {
        if (isAnyErrorsInMultiBatchError()) multiBatchBusinessExceptionsHolder.get().throwMultiBatchBusinessException();
    }

    private boolean isAnyErrorsInMultiBatchError() {
        if (multiBatchBusinessExceptionsHolder.get() != null) {
            for (BatchBusinessExceptionWrapper dbe : multiBatchBusinessExceptionsHolder.get().getBatchBusinessExceptionWrappers()) {
                if (dbe.getBatchBusinessExceptions().size() > 0) {
                    for (BatchBusinessException bbe : dbe.getBatchBusinessExceptions()) {
                        if (bbe.getBusinessExceptions().size() > 0) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static void remove() {
        batchBusinessExceptionsHolder.remove();
    }

    public static void removeMulti() {
        multiBatchBusinessExceptionsHolder.remove();
    }
}
