package com.baecon.rockpapersissersapp.rest.response;

public class MissingParameterErrorResponse extends ErrorResponse {

    private String missingParameter;

    public MissingParameterErrorResponse() {
        super();
    }

    public MissingParameterErrorResponse(String errorCode, String errorMessage, String missingParameter) {
        super(errorCode, errorMessage);
        this.missingParameter = missingParameter;
    }

    public MissingParameterErrorResponse(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public String getMissingParameter() {
        return missingParameter;
    }

    public void setMissingParameter(String missingParameter) {
        this.missingParameter = missingParameter;
    }
}
