package com.fadli.demo.base.exceptions;

import java.util.ArrayList;

public class BatchBusinessException extends ParentBusinessException {

    private static final long serialVersionUID = 3879663532063569387L;
    private ArrayList<BusinessException> businessExceptions;

    public BatchBusinessException() {
        businessExceptions = new ArrayList<BusinessException>();
    }

    public static BatchBusinessException create() {
        return new BatchBusinessException();
    }

    public BatchBusinessException addBusinessException(BusinessException e) {
        this.businessExceptions.add(e);
        return this;
    }

    public void throwBatchBusinessException(){
        if (!businessExceptions.isEmpty()) {
            throw this;
        }
    }

    public ArrayList<BusinessException> getBusinessExceptions() {
        return businessExceptions;
    }

    public void setBusinessExceptions(ArrayList<BusinessException> businessExceptions) {
        this.businessExceptions = businessExceptions;
    }
}
