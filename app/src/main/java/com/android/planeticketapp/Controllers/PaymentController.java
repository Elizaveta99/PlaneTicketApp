package com.android.planeticketapp.Controllers;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.planeticketapp.MainActivity;
import com.android.planeticketapp.Models.MethodDateUsage;
import com.android.planeticketapp.Models.PaymentResponse;
import com.android.planeticketapp.Models.Route;
import com.android.planeticketapp.OrderTicketActivity;
import com.android.planeticketapp.ServiceAPI.PaymentServiceClient;
import com.android.planeticketapp.ServiceAPI.PlaneTicketServiceInterface;
import com.android.planeticketapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentController {
    private static final String LOG_TAG =
            PaymentController.class.getSimpleName();


    private PlaneTicketServiceInterface paymentService;
    private ProgressDialog progressDialog;
    private OrdersController ordersController;

    public OrderTicketActivity orderTicketActivity;

    public PaymentController(OrdersController ordersController, OrderTicketActivity orderTicketActivity){
        paymentService = PaymentServiceClient.getRetrofitInstance().
                create(PlaneTicketServiceInterface.class);

        progressDialog = new ProgressDialog(MainActivity.getContext());

        progressDialog.setMessage("Loading...");
        //progressDialog.show();

        this.orderTicketActivity = orderTicketActivity;
        this.ordersController = ordersController;
        ordersController.orderTicketActivity = this.orderTicketActivity;
        Log.e(LOG_TAG, "controller paymentController");
    }

    public void payForMethod(MethodDateUsage dates, final String methodName, final Route parameter){
        final PaymentResponse[] payment = new PaymentResponse[1];
        payment[0] = new PaymentResponse();
        Call<PaymentResponse> call = paymentService.payForMethod("PlaneTicketService",
                methodName, dates);
        Log.e(LOG_TAG, "waiting for payForMethod");

        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                progressDialog.dismiss();
                payment[0] = response.body();
                Log.e(LOG_TAG, "response payForMethod");
                callFunctionContinue(methodName, payment[0], parameter);
            }
            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(LOG_TAG, "failure for payForMethod");
                Toast.makeText(MainActivity.getContext(),
                        MainActivity.getContext().getString(R.string.errorMessage)
                                + " " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callFunctionContinue(String method, PaymentResponse payment, Route parameter) {
        if(payment.getStatusCode() == 201) {
            switch (method) {
                case "getUser":
                    Log.e(LOG_TAG, "authorize from paymentController");
                    ordersController.authorize(payment.getToken());
                    //ordersController.authorize("1234");
                    break;
                case "getFullUserFlightsInfo":
                    ordersController.readRoutes(payment.getToken());
                    break;
                case "getFlightsInfo":
                    ordersController.readAllRoutes(payment.getToken());
                    break;
                case "addFlight":
                    ordersController.addRoute(payment.getToken(), parameter);
                    break;
                case "updateFlight":
                    ordersController.updateRoute(payment.getToken(), parameter);
                    break;
                case "deleteFlight":
                    ordersController.deleteRoute(payment.getToken(), parameter);
                    break;
            }
        }
        else{
            Toast.makeText(MainActivity.getContext(),
                    payment.getStatusMessage() + ", method: " + method,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
