package com.android.planeticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.planeticketapp.Controllers.OrdersController;
import com.android.planeticketapp.Models.MethodDateUsage;
import com.android.planeticketapp.Models.Route;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.android.planeticketapp.Controllers.PaymentController;

public class OrderTicketActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String LOG_TAG =
            OrderTicketActivity.class.getSimpleName();

    private static Context context;
    private String userId;

    public static Context getContext(){
        return OrderTicketActivity.context;
    }

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
        OrderTicketActivity.context = getApplicationContext();

        tableLayout = findViewById(R.id.tableLayout);

        Intent intent = getIntent();
        String login = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView helloMessage = findViewById(R.id.helloMessage);
        userId = login;
        paymentController = new PaymentController(new OrdersController(userId));
        helloMessage.setText("Hello, " + login);

        startReadRoutes(); // оплата

        //test
//        List<Route> flights = new ArrayList<>();
//        flights.add(new Route("1", "1", "1"));
//        flights.get(0).setTime("1");
//        flights.get(0).setPrice("1");
//        flights.add(new Route("2", "2", "2"));
//        flights.get(1).setTime("2");
//        flights.get(1).setPrice("2");
        //showFullUserFlightsInfo(flights);

    }

    private static TableLayout tableLayout;

    public static void showFullUserFlightsInfo(List<Route> flights)
    {

        Integer rows = flights.size();
        //Log.d(LOG_TAG, rows.toString());

        //TableLayout tableLayout = findViewById(R.id.tableLayout);

        tableLayout.setStretchAllColumns(true);  // ??

        for (int i = 0; i < rows; i++) {

            TableRow tableRow = new TableRow(OrderTicketActivity.context);
            tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            TextView textView1 = new TextView(OrderTicketActivity.context);
            textView1.setText(flights.get(i).getFrom());
            tableRow.addView(textView1, 0);  // without index ??

            TextView textView2 = new TextView(OrderTicketActivity.context);
            textView2.setText(flights.get(i).getTo());
            tableRow.addView(textView2, 1);

            TextView textView3 = new TextView(OrderTicketActivity.context);
            textView3.setText(flights.get(i).getDate());
            tableRow.addView(textView3, 2);

            TextView textView4 = new TextView(OrderTicketActivity.context);
            textView4.setText(flights.get(i).getTime());
            tableRow.addView(textView4, 3);

            TextView textView5 = new TextView(OrderTicketActivity.context);
            textView5.setText(flights.get(i).getPrice());
            tableRow.addView(textView5, 4);

            tableLayout.addView(tableRow, i);
        }
    }

    public void edit(View view) {
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        List<Route> allRoutes = new ArrayList<>();
        allRoutes.add(new Route("1", "1", "1"));
        allRoutes.get(0).setTime("1");
        allRoutes.get(0).setPrice("1");
        allRoutes.add(new Route("2", "2", "2"));
        allRoutes.get(1).setTime("2");
        allRoutes.get(1).setPrice("2");

        // get allRoutes !!

        List<String> fieldsFrom = new ArrayList<>();
        for (Route st: allRoutes)
            fieldsFrom.add(st.getFrom());
        List<String> fieldsTo = new ArrayList<>();
        for (Route st: allRoutes)
            fieldsTo.add(st.getTo());
        List<String> fieldsDate = new ArrayList<>();
        for (Route st: allRoutes)
            fieldsDate.add(st.getDate());

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fieldsFrom);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //Toast.makeText(getApplicationContext(), bankNames[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }
}
