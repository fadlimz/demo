package com.fadli.demo.base.exceptions;

public class BusinessException extends ParentBusinessException {

    private static final long serialVersionUID = -3439496890608068421L;

    private String code;
    private Object[] args;

    public BusinessException(String code, Object... args) {
        super(code);
        this.code = code;
        this.args = args;
    }

    public String getMessageCode() {
        return this.code;
    }

    public Object[] getMessageParameters() {
        return this.args;
    }

}
