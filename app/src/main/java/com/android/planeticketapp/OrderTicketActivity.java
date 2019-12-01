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


    public static ArrayList<Route> allRoutes = new ArrayList<>();
    public static void setAllRoutes(ArrayList<Route> routes)
    {
        allRoutes = routes;
        Log.e(LOG_TAG, String.format("size of allRoutes is SET = %d", allRoutes.size()));

        fieldsFrom.add("");
        for (Route st: allRoutes)
            fieldsFrom.add(st.getFrom());
        Log.e(LOG_TAG, String.format("amount od fieldsFrom first = %d", fieldsFrom.size()));
        fieldsTo.add("");
        for (Route st: allRoutes)
            fieldsTo.add(st.getTo());
        fieldsDate.add("");
        for (Route st: allRoutes)
            fieldsDate.add(st.getDate());





    }
    public ArrayList<Route> getAllRoutes()
    {
        return  allRoutes;
    }

    private static List<CheckBox> listCheckBoxes = new ArrayList<>();
    //private static List<TextView>[] tableTextViews = new ArrayList[]{new ArrayList<>()};
    private static ArrayList<ArrayList<TextView> > tableTextViews = new ArrayList<ArrayList<TextView> >();
    private static List<Route> userFlights = new ArrayList<Route>();
    private static Spinner spin1 = null;
    private static Spinner spin2 = null;
    private static Spinner spin3 = null;
    public static List<String> fieldsFrom = new ArrayList<>();
    public static List<String> fieldsTo = new ArrayList<>();
    public static List<String> fieldsDate = new ArrayList<>();


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





        // get allRoutes !!
        startReadAllRoutes();
        //test
        //List<Route> allRoutes = new ArrayList<>();
//        allRoutes.add(new Route("1", "11", "111"));
//        allRoutes.get(0).setTime("1111");
//        allRoutes.get(0).setPrice("111111");
//        allRoutes.add(new Route("2", "22", "222"));
//        allRoutes.get(1).setTime("2222");
//        allRoutes.get(1).setPrice("22222");


//        ArrayList<Route> tempRoutes = getAllRoutes();
//        Log.e(LOG_TAG, String.format("size of tempRoutes = %d", tempRoutes.size()));
//
//        List<String> fieldsFrom = new ArrayList<>();
//        for (Route st: tempRoutes)
//            fieldsFrom.add(st.getFrom());
//        Log.e(LOG_TAG, String.format("amount od fieldsFrom first = %d", fieldsFrom.size()));
//
//        List<String> fieldsTo = new ArrayList<>();
//        for (Route st: tempRoutes)
//            fieldsTo.add(st.getTo());
//        List<String> fieldsDate = new ArrayList<>();
//        for (Route st: tempRoutes)
//            fieldsDate.add(st.getDate());



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

        tableLayout.addView(tableRow, 0);


//        // add spinners
        TableRow tableRow1 = new TableRow(this);
        tableRow1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spin1 = new Spinner(this);
        tableRow1.addView(spin1);
        //Spinner spin1 = findViewById(R.id.spinner1);
        spin1.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        Log.e(LOG_TAG, String.format("amount od fieldsFrom = %d", fieldsFrom.size()));
        ArrayAdapter a1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fieldsFrom);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin1.setAdapter(a1);

        spin2 = new Spinner(this);
        tableRow1.addView(spin2);
        spin2.setOnItemSelectedListener(this);
        ArrayAdapter a2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fieldsTo);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(a2);

        spin3 = new Spinner(this);
        tableRow1.addView(spin3);
        spin3.setOnItemSelectedListener(this);
        ArrayAdapter a3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fieldsDate);
        a3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(a3);

        tableLayout.addView(tableRow1, 1);

        startReadRoutes(); // оплата

    }

    private static TableLayout tableLayout;

    public static void showFullUserFlightsInfo(List<Route> flights)
    {
//        flights.add(new Route("1", "1", "1"));
//        flights.get(0).setTime("1");
//        flights.get(0).setPrice("1");
//        flights.add(new Route("2", "2", "2"));
//        flights.get(1).setTime("2");
//        flights.get(1).setPrice("2");

        userFlights = flights;
        Log.e(LOG_TAG, String.format("userFlightsSize = %d", userFlights.size()));

        Integer rows = flights.size();
        Log.e(LOG_TAG, rows.toString());

        for (int i = 2; i < rows + 2; i++) {
            ArrayList<TextView> rowTextViewList = new ArrayList<TextView>();

            TableRow tableRow = new TableRow(OrderTicketActivity.context);
            tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            TextView textView1 = new TextView(OrderTicketActivity.context);
            textView1.setText(flights.get(i - 2).getFrom());
            tableRow.addView(textView1, 0);  // without index ??
            rowTextViewList.add(textView1);
            //tableTextViews

            TextView textView2 = new TextView(OrderTicketActivity.context);
            textView2.setText(flights.get(i - 2).getTo());
            tableRow.addView(textView2, 1);
            rowTextViewList.add(textView2);

            TextView textView3 = new TextView(OrderTicketActivity.context);
            textView3.setText(flights.get(i - 2).getDate());
            tableRow.addView(textView3, 2);
            rowTextViewList.add(textView3);

            TextView textView4 = new TextView(OrderTicketActivity.context);
            textView4.setText(flights.get(i - 2).getTime());
            tableRow.addView(textView4, 3);
            rowTextViewList.add(textView4);

            TextView textView5 = new TextView(OrderTicketActivity.context);
            textView5.setText(flights.get(i - 2).getPrice());
            tableRow.addView(textView5, 4);
            rowTextViewList.add(textView5);

            CheckBox checkBox1 = new CheckBox(OrderTicketActivity.context);
            tableRow.addView(checkBox1);
            listCheckBoxes.add(checkBox1);

            tableLayout.addView(tableRow, i);
            tableTextViews.add(rowTextViewList);
        }
    }

    public void edit(View view) {
        ArrayAdapter a1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fieldsFrom);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin1.setAdapter(a1);

        ArrayAdapter a2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fieldsTo);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(a2);

        ArrayAdapter a3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fieldsDate);
        a3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(a3);

    }

    private int rowIndexGlob;


    public void save(View view) {
        Log.e(LOG_TAG, String.format("amount od fieldsFrom in edit = %d", fieldsFrom.size()));

        int rowIndex = -1;
        for (CheckBox ch: listCheckBoxes)
        {
            rowIndex++;
            if (ch.isChecked())
            {
                //int rowIndex = ((TableLayout)((TableRow)ch.getParent()).getParent()).indexOfChild(ch) - 2; // ??
                Log.e(LOG_TAG, String.format("checkbox is checked on row: %d", rowIndex));
                tableTextViews.get(rowIndex).get(0).setText(String.valueOf(spin1.getSelectedItem()));
                tableTextViews.get(rowIndex).get(1).setText(String.valueOf(spin2.getSelectedItem()));
                tableTextViews.get(rowIndex).get(2).setText(String.valueOf(spin3.getSelectedItem()));
                Route tempUpdatedRoute = new Route(tableTextViews.get(rowIndex).get(0).getText().toString(),
                        tableTextViews.get(rowIndex).get(1).getText().toString(),
                        tableTextViews.get(rowIndex).get(2).getText().toString());
                tempUpdatedRoute.setTime(tableTextViews.get(rowIndex).get(3).getText().toString());
                tempUpdatedRoute.setPrice(tableTextViews.get(rowIndex).get(4).getText().toString());
                Log.e(LOG_TAG, String.format("userFlightsSize in edit = %d", userFlights.size()));
                Log.e(LOG_TAG, String.format("Old id =  %s", userFlights.get(rowIndex).getId()));
                tempUpdatedRoute.setId(userFlights.get(rowIndex).getId()); // old id

                Log.e(LOG_TAG, String.format("Updated FROM =  %s", tempUpdatedRoute.getFrom()));

                startUpdateRoute(tempUpdatedRoute); // setUpdatedRoute - in OrdersController




//                        Log.e(LOG_TAG, String.format("New FROM =  %s", updatedRoute.getFrom()));
//
//                        tableTextViews.get(rowIndex).get(0).setText(updatedRoute.getFrom());
//                        tableTextViews.get(rowIndex).get(1).setText(updatedRoute.getTo());
//                        tableTextViews.get(rowIndex).get(2).setText(updatedRoute.getDate());
//                        tableTextViews.get(rowIndex).get(3).setText(updatedRoute.getTime());
//                        tableTextViews.get(rowIndex).get(4).setText(updatedRoute.getPrice());

                break;
            }
        }

        if (isUpdated == true) {
            Log.e(LOG_TAG, String.format("isUpdated is true"));
            userFlights.set(rowIndex, updatedRoute);
            isUpdated = false;
            tableTextViews.get(rowIndex).get(3).setText(updatedRoute.getTime());
            tableTextViews.get(rowIndex).get(4).setText(updatedRoute.getPrice());
        }

    }


    private static boolean isUpdated = false;
    public static Route updatedRoute;
    public static void setUpdatedRoute(Route _updatedRoute)
    {
        updatedRoute = _updatedRoute;
        isUpdated = true;
    }
    public static Route getUpdatedRoute()
    {
        return updatedRoute;
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
        //Toast.makeText(getApplicationContext(), bankNames[position], Toast.LENGTH_LONG).show();
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }

    private static boolean isAddedRoute = false;
    public static Route addedRoute;
    public static void setAddedRoute(Route _addedRoute)
    {
        addedRoute = _addedRoute;
        isAddedRoute = true;

    }
    public static Route getAddedRoute()
    {
        return addedRoute;
    }

    public void add(View view) {
        ArrayAdapter a1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fieldsFrom);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin1.setAdapter(a1);

        ArrayAdapter a2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fieldsTo);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(a2);

        ArrayAdapter a3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fieldsDate);
        a3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(a3);





        ArrayList<TextView> rowTextViewList = new ArrayList<TextView>();

        TableRow tableRow = new TableRow(OrderTicketActivity.context);
        tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        TextView textView1 = new TextView(OrderTicketActivity.context);
        textView1.setText(".");
        tableRow.addView(textView1, 0);  // without index ??
        rowTextViewList.add(textView1);
        //tableTextViews

        TextView textView2 = new TextView(OrderTicketActivity.context);
        textView2.setText(".");
        tableRow.addView(textView2, 1);
        rowTextViewList.add(textView2);

        TextView textView3 = new TextView(OrderTicketActivity.context);
        textView3.setText(".");
        tableRow.addView(textView3, 2);
        rowTextViewList.add(textView3);

        TextView textView4 = new TextView(OrderTicketActivity.context);
        textView4.setText(".");
        tableRow.addView(textView4, 3);
        rowTextViewList.add(textView4);

        TextView textView5 = new TextView(OrderTicketActivity.context);
        textView5.setText(".");
        tableRow.addView(textView5, 4);
        rowTextViewList.add(textView5);

        CheckBox checkBox1 = new CheckBox(OrderTicketActivity.context);
        tableRow.addView(checkBox1);
        listCheckBoxes.add(checkBox1);

        Log.e(LOG_TAG, String.format("index in tableLayout: %d", tableTextViews.size() + 2));
        tableLayout.addView(tableRow, tableTextViews.size() + 2); // ?? index of row in table -  ?
        tableTextViews.add(rowTextViewList);


        int rowIndex = tableTextViews.size() - 1; // ??
        Log.e(LOG_TAG, String.format("adding on row: %d", rowIndex));
        rowIndexGlob = rowIndex;

//        while (true)
//        {
//            Log.e(LOG_TAG, String.format("number of row to add: %d", rowIndex));
//            if (listCheckBoxes.get(rowIndex).isChecked())
//            {
//                tableTextViews.get(rowIndex).get(0).setText(String.valueOf(spin1.getSelectedItem()));
//                tableTextViews.get(rowIndex).get(1).setText(String.valueOf(spin2.getSelectedItem()));
//                tableTextViews.get(rowIndex).get(2).setText(String.valueOf(spin3.getSelectedItem()));
//                Route tempAddedRoute = new Route(tableTextViews.get(rowIndex).get(0).getText().toString(),
//                        tableTextViews.get(rowIndex).get(1).getText().toString(),
//                        tableTextViews.get(rowIndex).get(2).getText().toString());
//                tempAddedRoute.setTime("");
//                tempAddedRoute.setPrice("");
//                //Log.e(LOG_TAG, String.format("userFlightsSize in edit = %d", userFlights.size()));
//                //Log.e(LOG_TAG, String.format("Old id =  %s", userFlights.get(rowIndex).getId()));
//                tempAddedRoute.setId(""); // don't know id yet
//
//                startAddRoute(tempAddedRoute); // setAddedRoute - in OrdersController
//                userFlights.add(getAddedRoute());
//                // вылетает на индексе??
//                //TextView temp  = new TextView(this);
//                //temp.setText(getAddedRoute().getTime());
//                //tableTextViews.get(rowIndex).set(3, temp);
//                tableTextViews.get(rowIndex).get(3).setText(getAddedRoute().getTime());
//                tableTextViews.get(rowIndex).get(4).setText(getAddedRoute().getPrice());
//
//                break;
//            }
//        }

    }

    public void saveAdd(View view) {
        int rowIndex = rowIndexGlob;

        //while (true)
        //{
            Log.e(LOG_TAG, String.format("number of row to add: %d", rowIndex));
            if (listCheckBoxes.get(rowIndex).isChecked())
            {
                tableTextViews.get(rowIndex).get(0).setText(String.valueOf(spin1.getSelectedItem()));
                tableTextViews.get(rowIndex).get(1).setText(String.valueOf(spin2.getSelectedItem()));
                tableTextViews.get(rowIndex).get(2).setText(String.valueOf(spin3.getSelectedItem()));
                Route tempAddedRoute = new Route(tableTextViews.get(rowIndex).get(0).getText().toString(),
                        tableTextViews.get(rowIndex).get(1).getText().toString(),
                        tableTextViews.get(rowIndex).get(2).getText().toString());
                tempAddedRoute.setTime("");
                tempAddedRoute.setPrice("");
                //Log.e(LOG_TAG, String.format("userFlightsSize in edit = %d", userFlights.size()));
                //Log.e(LOG_TAG, String.format("Old id =  %s", userFlights.get(rowIndex).getId()));
                tempAddedRoute.setId(""); // don't know id yet

                startAddRoute(tempAddedRoute); // setAddedRoute - in OrdersController

            }
        //}


        if (isAddedRoute == true) {
            Log.e(LOG_TAG, String.format("isAdded is true"));
            //userFlights.set(rowIndex, addedRoute);
            userFlights.add(getAddedRoute());
            isAddedRoute = false;
            tableTextViews.get(rowIndex).get(3).setText(addedRoute.getTime());
            tableTextViews.get(rowIndex).get(4).setText(addedRoute.getPrice());
        }
    }

    public void saveDelete(View view) {
    }
}
