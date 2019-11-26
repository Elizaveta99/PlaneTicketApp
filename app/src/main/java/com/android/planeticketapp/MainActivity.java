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

    public void startAuthorize(){
        paymentController = new PaymentController(new OrdersController(userId));
        paymentController.payForMethod(new MethodDateUsage(new Date(), new Date()),
                "getUser", null);
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
        //startAuthorize();
        Intent intent = new Intent(this, OrderTicketActivity.class);
        String message = mLogin.getText().toString();
        userId = message;
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
