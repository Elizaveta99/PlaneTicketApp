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
import android.widget.CheckBox;
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
        paymentController = new PaymentController(new OrdersController(userId));
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


    private static List<Route> allRoutes = new ArrayList<>();

    public static void setAllRoutes(List<Route> routes)
    {
        allRoutes = routes;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ticket);
        OrderTicketActivity.context = getApplicationContext();

        tableLayout = findViewById(R.id.tableLayout);
        tableLayout.setStretchAllColumns(true);  // ??

        Intent intent = getIntent();
        String login = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView helloMessage = findViewById(R.id.helloMessage);
        userId = login;
        helloMessage.setText("Hello, " + login);






        //test
        //List<Route> allRoutes = new ArrayList<>();
        allRoutes.add(new Route("1", "11", "111"));
        allRoutes.get(0).setTime("1111");
        allRoutes.get(0).setPrice("111111");
        allRoutes.add(new Route("2", "22", "222"));
        allRoutes.get(1).setTime("2222");
        allRoutes.get(1).setPrice("22222");

        // get allRoutes !!
        startReadAllRoutes();

        List<String> fieldsFrom = new ArrayList<>();
        for (Route st: allRoutes)
            fieldsFrom.add(st.getFrom());
        List<String> fieldsTo = new ArrayList<>();
        for (Route st: allRoutes)
            fieldsTo.add(st.getTo());
        List<String> fieldsDate = new ArrayList<>();
        for (Route st: allRoutes)
            fieldsDate.add(st.getDate());




        TableRow tableRow1 = new TableRow(this);
        tableRow1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin1 = new Spinner(this);
        tableRow1.addView(spin1);
        //Spinner spin1 = findViewById(R.id.spinner1);
        spin1.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter a1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fieldsFrom);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin1.setAdapter(a1);

        Spinner spin2 = new Spinner(this);
        tableRow1.addView(spin2);
        spin2.setOnItemSelectedListener(this);
        ArrayAdapter a2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fieldsTo);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(a2);

        Spinner spin3 = new Spinner(this);
        tableRow1.addView(spin3);
        spin3.setOnItemSelectedListener(this);
        ArrayAdapter a3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fieldsDate);
        a3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(a3);

        tableLayout.addView(tableRow1, 0);




        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        TextView textView1 = new TextView(this);
        textView1.setText("From");
        tableRow.addView(textView1, 0);  // without index ??


        TextView textView2 = new TextView(this);
        textView2.setText("To");
        tableRow.addView(textView2, 1);


        TextView textView3 = new TextView(this);
        textView3.setText("Date");
        tableRow.addView(textView3, 2);


        TextView textView4 = new TextView(this);
        textView4.setText("Time");
        tableRow.addView(textView4, 3);

        TextView textView5 = new TextView(this);
        textView5.setText("Price");
        tableRow.addView(textView5, 4);


        tableLayout.addView(tableRow, 1);



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
        flights.add(new Route("1", "1", "1"));
        flights.get(0).setTime("1");
        flights.get(0).setPrice("1");
        flights.add(new Route("2", "2", "2"));
        flights.get(1).setTime("2");
        flights.get(1).setPrice("2");

        Integer rows = flights.size();
        Log.d(LOG_TAG, rows.toString());

        //TableLayout tableLayout = findViewById(R.id.tableLayout);


        for (int i = 2; i < rows + 2; i++) {

            TableRow tableRow = new TableRow(OrderTicketActivity.context);
            tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            TextView textView1 = new TextView(OrderTicketActivity.context);
            textView1.setText(flights.get(i - 2).getFrom());
            tableRow.addView(textView1, 0);  // without index ??

            TextView textView2 = new TextView(OrderTicketActivity.context);
            textView2.setText(flights.get(i - 2).getTo());
            tableRow.addView(textView2, 1);

            TextView textView3 = new TextView(OrderTicketActivity.context);
            textView3.setText(flights.get(i - 2).getDate());
            tableRow.addView(textView3, 2);

            TextView textView4 = new TextView(OrderTicketActivity.context);
            textView4.setText(flights.get(i - 2).getTime());
            tableRow.addView(textView4, 3);

            TextView textView5 = new TextView(OrderTicketActivity.context);
            textView5.setText(flights.get(i - 2).getPrice());
            tableRow.addView(textView5, 4);

            CheckBox checkBox1 = new CheckBox(OrderTicketActivity.context);
            tableRow.addView(checkBox1);
            tableLayout.addView(tableRow, i);
        }
    }

    public void edit(View view) {
        //String.valueOf(spinner.getSelectedItem()) - забрать выбранный элемент

    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
        //Toast.makeText(getApplicationContext(), bankNames[position], Toast.LENGTH_LONG).show();
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }
}
