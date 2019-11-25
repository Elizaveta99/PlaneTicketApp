package com.android.planeticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

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
