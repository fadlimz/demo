package com.fadli.demo.base.responses;

import com.fadli.demo.base.constants.BaseConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Message {

    private String code;
    private String desc;
    @JsonInclude(Include.ALWAYS)
    private Object hint;
    @JsonInclude(Include.ALWAYS)
    private Object arguments;

    public Message() {
        this.code = BaseConstants.EMPTY;
        this.desc = BaseConstants.EMPTY;
        this.hint = BaseConstants.EMPTY;
        this.arguments = BaseConstants.EMPTY;
    }

    public static Message create(){
        return new Message();
    }

    public Message setCode(String code){
        this.code = code;
        return this;
    }

    public Message setDesc(String desc){
        this.desc = desc;
        return this;
    }

    public Message setHint(Object hint){
        this.hint = hint;
        return this;
    }

    public Message setArguments(Object... args){
        this.arguments = args;
        return this;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public Object getHint() {
        return this.hint;
    }

    public Object getArguments() {
        return arguments;
    }

}
