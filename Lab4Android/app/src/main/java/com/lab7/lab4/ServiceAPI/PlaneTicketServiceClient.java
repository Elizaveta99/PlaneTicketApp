package com.lab7.lab4.ServiceAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaneTicketServiceClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://voiteshenko-lab3-plane-ticket-service.azurewebsites.net/PlaneTicketService.svc/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
