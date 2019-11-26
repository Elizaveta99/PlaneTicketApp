package com.android.planeticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.planeticketapp.Models.MethodDateUsage;
import com.android.planeticketapp.Models.Route;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.android.planeticketapp.Controllers.PaymentController;

public class OrderTicketActivity extends AppCompatActivity {
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    private PaymentController paymentController;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ticket);
        Intent intent = getIntent();
        String login = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView helloMessage = findViewById(R.id.helloMessage);
        helloMessage.setText("Hello, " + login);

        //startReadRoutes(); // оплата
        List<Route> flights = new ArrayList<>();
        flights.add(new Route("1", "1", "1"));
        flights.get(0).setTime("1");
        flights.get(0).setPrice("1");
        flights.add(new Route("2", "2", "2"));
        flights.get(1).setTime("2");
        flights.get(1).setPrice("2");

        showFullUserFlightsInfo(flights);

    }

    public void showFullUserFlightsInfo(List<Route> flights)
    {

        Integer rows = flights.size();
        Log.d(LOG_TAG, rows.toString());
        //int columns = 5;

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        tableLayout.setStretchAllColumns(true);  // ??

        for (int i = 0; i < rows; i++) {

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            TextView textView1 = new TextView(this);
            textView1.setText(flights.get(i).getFrom());
            tableRow.addView(textView1, 0);

            TextView textView2 = new TextView(this);
            textView2.setText(flights.get(i).getTo());
            tableRow.addView(textView2, 1);

            TextView textView3 = new TextView(this);
            textView3.setText(flights.get(i).getDate());
            tableRow.addView(textView3, 2);

            TextView textView4 = new TextView(this);
            textView4.setText(flights.get(i).getTime());
            tableRow.addView(textView4, 3);

            TextView textView5 = new TextView(this);
            textView5.setText(flights.get(i).getPrice());
            tableRow.addView(textView5, 4);

            tableLayout.addView(tableRow, i);
        }

        //setContentView(tableLayout);

    }
}
