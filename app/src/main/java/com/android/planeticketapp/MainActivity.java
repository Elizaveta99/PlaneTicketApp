package com.android.planeticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.planeticketapp.Controllers.OrdersController;
import com.android.planeticketapp.Controllers.PaymentController;
import com.android.planeticketapp.Models.MethodDateUsage;
import com.android.planeticketapp.Models.Route;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private PaymentController paymentController;
    private String userId;

    public static Context getContext(){
        return getContext();
    }

    private void startAuthorize(){
        paymentController = new PaymentController(new OrdersController(userId));
        paymentController.payForMethod(new MethodDateUsage(new Date(), new Date()),
                "getUser", null);
    }

    private void startReadRoutes(){
        paymentController.payForMethod(new MethodDateUsage(new Date(), new Date()),
                "getFullUserFlightsInfo", null);
    }

    private void startReadAllRoutes(){
        paymentController.payForMethod(new MethodDateUsage(new Date(), new Date()),
                "getFlightsInfo", null);
    }

    private void startAddRoute(Route route){
        paymentController.payForMethod(new MethodDateUsage(new Date(), new Date()),
                "addFlight", route);
    }

    private void startUpdateRoute(Route route){
        paymentController.payForMethod(new MethodDateUsage(new Date(), new Date()),
                "updateFlight", route);
    }

    private void startDeleteRoute(Route route){
        paymentController.payForMethod(new MethodDateUsage(new Date(), new Date()),
                "deleteFlight", route);
    }





    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    public static final String EXTRA_MESSAGE =
            "com.android.planeticketapp.extra.MESSAGE";

    private EditText mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLogin = findViewById(R.id.login);
    }

    public void orderTicket(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        // оплата - userEmail сохранить в переменной
        Intent intent = new Intent(this, OrderTicketActivity.class);
        String message = mLogin.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
