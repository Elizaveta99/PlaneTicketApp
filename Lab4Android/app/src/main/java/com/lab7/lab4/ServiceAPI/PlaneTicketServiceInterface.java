package com.lab7.lab4.ServiceAPI;

import com.lab7.lab4.Models.MethodDateUsage;
import com.lab7.lab4.Models.PaymentResponse;
import com.lab7.lab4.Models.Route;
import com.lab7.lab4.Models.User;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlaneTicketServiceInterface {

    @POST("token/{service}/{method}")
    Call<PaymentResponse> payForMethod(@Path("service") String serviceName,
                                       @Path("method") String methodName,
                                       @Body MethodDateUsage dates);

    @GET("{userId}/{tokenValue}")
    Call<User> authorize(@Path("userId") String userId, @Path("tokenValue") String token);

    @GET("flights/{tokenValue}")
    Call<ArrayList<Route>> readAllData(@Path("tokenValue") String token);

    @GET("flights/{userId}/{tokenValue}")
    Call<ArrayList<Route>> readData( @Path("userId") String userId,
                                     @Path("tokenValue") String token);

    @POST("addFlight/{userId}/{tokenValue}")
    Call<Route> addFlight(@Path("userId") String userId, @Path("tokenValue") String token,
                          @Body Route route);

    @POST("updateFlight/{userId}/{tokenValue}")
    Call<Route> updateFlight(@Path("userId") String userId, @Path("tokenValue") String token,
                             @Body Route route);

    @POST("deleteFlight/{userId}/{routeId}")
    Call<Integer> deleteFlight(@Path("userId") String userId, @Path("routeId") String routeId,
                               @Body String token);
}
