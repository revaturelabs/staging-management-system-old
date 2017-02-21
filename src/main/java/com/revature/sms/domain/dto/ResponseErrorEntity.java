package com.revature.sms.domain.dto;
/**
 * DTO object for ResponseErrorEntity. This class is used to send error messages.
 *
 */
public class ResponseErrorEntity {
/**
 * String representing the errorMessage being sent.
 */
    private String errorMessage;
/**
 * Constructor that creates the ResponseErrorEntity object.
 * @param errorMessage String containing the error message.
 */
    public ResponseErrorEntity(String errorMessage) {
        this.errorMessage = errorMessage;
    }
/**
 * Method to retrieve the error message contained in this object.
 * @return String value that represents the error message being sent.
 */
    public String getErrorMessage() {
        return errorMessage;
    }
/**
 * Method to set the error message manually.
 * @param errorMessage String representing what the error message will be set to.
 */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
