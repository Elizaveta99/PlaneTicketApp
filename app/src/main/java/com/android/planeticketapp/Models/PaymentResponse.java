package com.android.planeticketapp.Models;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse {

    @SerializedName("token")
    private String token;

    @SerializedName("status_code")
    private int statusCode;

    @SerializedName("status_message")
    private String statusMessage;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
