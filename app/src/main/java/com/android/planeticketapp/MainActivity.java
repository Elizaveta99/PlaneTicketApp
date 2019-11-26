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
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE =
            "com.android.planeticketapp.extra.MESSAGE";

    private static Context context;

    private PaymentController paymentController;
    private static String userId;

    public static Context getContext(){
        return MainActivity.context;
    }

    public void startAuthorize(){
        paymentController = new PaymentController(new OrdersController(userId));
        paymentController.payForMethod(new MethodDateUsage(new Date(), new Date()),
                "getUser", null);
    }

    private static EditText mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();
        mLogin = findViewById(R.id.login);
    }

    public void orderTicket(View view) {
        Log.e(LOG_TAG, "Button clicked!");
        String message = mLogin.getText().toString();
        userId = message;
        Log.e(LOG_TAG, String.format("userId = %s", userId));
        startAuthorize();
        //authorize();

    }

    public static void authorize()
    {
        Intent intent = new Intent(MainActivity.context, OrderTicketActivity.class);
        intent.putExtra(EXTRA_MESSAGE, userId);
        Log.e(LOG_TAG, String.format("userId in authorize = %s", userId));
        MainActivity.context.startActivity(intent);
    }
}
