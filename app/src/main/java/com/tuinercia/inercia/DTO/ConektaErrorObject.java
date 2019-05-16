package com.tuinercia.inercia.DTO;

import com.google.gson.annotations.SerializedName;

public class ConektaErrorObject {
    @SerializedName("debug_message")
    String debug_message;
    @SerializedName("message")
    String message;
    @SerializedName("code")
    String code;
    @SerializedName("param")
    String param;

    public String getDebug_message() {
        return debug_message;
    }

    public void setDebug_message(String debug_message) {
        this.debug_message = debug_message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public ConektaErrorObject(String debug_message, String message, String code, String param) {
        this.debug_message = debug_message;
        this.message = message;
        this.code = code;
        this.param = param;
    }
}
