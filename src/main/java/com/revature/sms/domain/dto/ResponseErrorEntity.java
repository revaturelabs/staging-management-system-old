package com.revature.sms.domain.dto;

public class ResponseErrorEntity {

    private String errorMessage;

    public ResponseErrorEntity(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
