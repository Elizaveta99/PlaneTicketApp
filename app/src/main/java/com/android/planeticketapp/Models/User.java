package com.android.planeticketapp.Models;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("userId")
    private String id;

    @SerializedName("userFirstName")
    private String firstName ;

    @SerializedName("userSecondName")
    private String secondName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}