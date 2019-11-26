package com.android.planeticketapp.Models;

import com.google.gson.annotations.SerializedName;

public class Route {

    @SerializedName("routeId")
    private String id;

    @SerializedName("routeFrom")
    private String from;

    @SerializedName("routeWhere")
    private String to;

    @SerializedName("routeDate")
    private String date;

    @SerializedName("routeTime")
    private String time;

    @SerializedName("routePrice")
    private String price;

    public Route(String routeFrom, String routeWhere, String routeDate)
    {
        from = routeFrom;
        to = routeWhere;
        date = routeDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
