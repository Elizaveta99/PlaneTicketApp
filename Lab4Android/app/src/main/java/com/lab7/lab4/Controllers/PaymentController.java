package com.lab7.lab4.Controllers;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.lab7.lab4.MainActivity;
import com.lab7.lab4.Models.MethodDateUsage;
import com.lab7.lab4.Models.PaymentResponse;
import com.lab7.lab4.Models.Route;
import com.lab7.lab4.ServiceAPI.PaymentServiceClient;
import com.lab7.lab4.ServiceAPI.PlaneTicketServiceInterface;
import com.lab7.lab4.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentController {

    private PlaneTicketServiceInterface paymentService;
    private ProgressDialog progressDialog;
    private OrdersController ordersController;

    public PaymentController(OrdersController ordersController){
        paymentService = PaymentServiceClient.getRetrofitInstance().
                create(PlaneTicketServiceInterface.class);
        progressDialog = new ProgressDialog(MainActivity.getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        this.ordersController = ordersController;
    }

    public void payForMethod(MethodDateUsage dates, final String methodName, final Route parameter){
        final PaymentResponse[] payment = new PaymentResponse[1];
        payment[0] = new PaymentResponse();
        Call<PaymentResponse> call = paymentService.payForMethod("PlaneTicketService",
                methodName, dates);
        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                progressDialog.dismiss();
                payment[0] = response.body();
                callFunctionContinue(methodName, payment[0], parameter);
            }
            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                progressDialog.dismiss();
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
                    ordersController.authorize(payment.getToken());
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
