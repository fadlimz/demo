package com.fadli.demo.base.responses;

import com.fadli.demo.base.constants.BaseConstants;
import com.fadli.demo.base.localThread.LocalErrors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.HashMap;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class ApiResponse {

    private static final String status_ok = BaseConstants.STATUS_OK;
    private static final String status_error = BaseConstants.STATUS_ERROR;

    private HashMap<String, Object> data;
    private ArrayList<Message> errors;
    @JsonInclude(Include.NON_EMPTY)
    private ArrayList<MultiMessage> multiErrors;
    private ArrayList<Message> notices;
    private String status;

    public ApiResponse() {
        data = new HashMap<String, Object>();
        errors = new ArrayList<Message>();
        multiErrors = new ArrayList<>();
        notices = new ArrayList<Message>();
    }

    public static ApiResponse data(String key, Object value){
        ApiResponse apiResponse = ApiResponse.create()
                .addData(key, value);

        removeThreadLocals();
        return apiResponse;
    }

    public static ApiResponse error(Message error){
        ApiResponse apiResponse = ApiResponse.create()
                .addError(error)
                .changeStatus(status_error);

        removeThreadLocals();
        return apiResponse;
    }

    public static ApiResponse errors(ArrayList<Message> errors){
        ApiResponse apiResponse = ApiResponse.create()
                .addErrors(errors)
                .changeStatus(status_error);

        removeThreadLocals();
        return apiResponse;
    }

    public static ApiResponse multiErrors(ArrayList<MultiMessage> multiErrors){
        ApiResponse apiResponse = ApiResponse.create().changeStatus(status_error);
        apiResponse.setMultiErrors(multiErrors);

        removeThreadLocals();
        return apiResponse;
    }

    public static ApiResponse upload(int rowsUploaded){
        ApiResponse apiResponse = ApiResponse.create()
                .changeStatus(status_ok);

        removeThreadLocals();
        return apiResponse;
    }

    public static ApiResponse ok(){
        ApiResponse apiResponse = ApiResponse.create().changeStatus(status_ok);

        removeThreadLocals();
        return apiResponse;
    }

    public static void removeThreadLocals() {
        LocalErrors.remove();
        LocalErrors.removeMulti();
    }

    public static ApiResponse create(){
        return new ApiResponse();
    }

    public ApiResponse addData(String key, Object value){
        this.data.put(key, value);
        this.changeStatus(status_ok);
        return this;
    }

    public ApiResponse addError(Message myMessage) {
        this.errors.add(myMessage);
        return this;
    }

    public ApiResponse addErrors(ArrayList<Message> errors) {
        errors.forEach(error -> {
            this.errors.add(error);
            this.changeStatus(status_error);
        });
        return this;
    }

    public ApiResponse changeStatus(String status){
        this.status = status;
        return this;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Message> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<Message> errors) {
        this.errors = errors;
    }

    public ArrayList<MultiMessage> getMultiErrors() {
        return multiErrors;
    }

    public void setMultiErrors(ArrayList<MultiMessage> multiErrors) {
        this.multiErrors = multiErrors;
    }

    public ArrayList<Message> getNotices() {
        return notices;
    }

    public ApiResponse setNotices(ArrayList<Message> notices) {
        this.notices = notices;
        return this;
    }

}
