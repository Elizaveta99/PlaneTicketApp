package com.lab7.lab4.Models;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MethodDateUsage {

    public MethodDateUsage(Date from, Date to) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        dateFrom = formatter.format(from);
        dateTo = formatter.format(to);
    }

    @SerializedName("date_from")
    private String dateFrom;

    @SerializedName("date_to")
    private String dateTo;

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}