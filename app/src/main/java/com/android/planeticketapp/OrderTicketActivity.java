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
import android.widget.Button;
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
        paymentController = new PaymentController(new OrdersController(userId), this);
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
    private static ArrayList<ArrayList<TextView> > tableTextViews = new ArrayList<ArrayList<TextView> >();
    private static List<Route> userFlights = new ArrayList<Route>();
    private static Spinner spin1 = null;
    private static Spinner spin2 = null;
    private static Spinner spin3 = null;
    public static List<String> fieldsFrom = new ArrayList<>();
    public static List<String> fieldsTo = new ArrayList<>();
    public static List<String> fieldsDate = new ArrayList<>();

    private static TableLayout tableLayout;

    private Button save;
    private Button saveAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ticket);
        OrderTicketActivity.context = getApplicationContext();

        save = findViewById(R.id.saveEditButton);
        saveAdd = findViewById(R.id.saveAddButton);

        tableLayout = findViewById(R.id.tableLayout);
        tableLayout.setStretchAllColumns(true);  // ??

        Intent intent = getIntent();
        String login = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView helloMessage = findViewById(R.id.helloMessage);
        userId = login;
        helloMessage.setText("Hello, " + login);

        // get allRoutes
        startReadAllRoutes();

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


        // add spinners
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

    public static void showFullUserFlightsInfo(List<Route> flights)
    {
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

    private static boolean isUpdated = false;
    public static Route updatedRoute;
    public void setUpdatedRoute(Route _updatedRoute)
    {
        updatedRoute = _updatedRoute;
        isUpdated = true;
        save.callOnClick();
    }
    public static Route getUpdatedRoute()
    {
        return updatedRoute;
    }

    private int countEdit = 0;

    public void edit(View view) {
        countEdit = 0;

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
    private int rowEditIndexGlob;

    public void save(View view) {

        countEdit++;

        Log.e(LOG_TAG, String.format("CountEdit = %d", countEdit));


        if (isUpdated == true)
        //if (countEdit % 2 == 0)
        {
            Log.e(LOG_TAG, String.format("isUpdated is true"));
            userFlights.set(rowEditIndexGlob, updatedRoute);
            isUpdated = false;
            tableTextViews.get(rowEditIndexGlob).get(3).setText(updatedRoute.getTime());
            tableTextViews.get(rowEditIndexGlob).get(4).setText(updatedRoute.getPrice());
        }



        Log.e(LOG_TAG, String.format("amount od fieldsFrom in edit = %d", fieldsFrom.size()));

        int rowIndex = -1;

        for (CheckBox ch: listCheckBoxes)
        {
            rowIndex++;
            if (ch.isChecked())
            {
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

                rowEditIndexGlob = rowIndex;

                if (countEdit == 1) {
                    startUpdateRoute(tempUpdatedRoute); // setUpdatedRoute - in OrdersController
                }

                break;
            }
        }



    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
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
    public void setAddedRoute(Route _addedRoute)
    {
        addedRoute = _addedRoute;
        isAddedRoute = true;
        saveAdd.callOnClick();

    }
    public static Route getAddedRoute()
    {
        return addedRoute;
    }


    private int countAdd = 0;

    public void add(View view) {
        countAdd = 0;

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
    }

    private int rowAddIndexGlob;

    public void saveAdd(View view) {
        countAdd++;

        if (isAddedRoute == true) {
            Log.e(LOG_TAG, String.format("isAdded is true"));
            userFlights.add(addedRoute);
            isAddedRoute = false;
            tableTextViews.get(rowAddIndexGlob).get(3).setText(addedRoute.getTime());
            tableTextViews.get(rowAddIndexGlob).get(4).setText(addedRoute.getPrice());
        }

        int rowIndex = rowIndexGlob;

            Log.e(LOG_TAG, String.format("number of row to add: %d countAdd = %d", rowIndex, countAdd));
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
                tempAddedRoute.setId(""); // don't know id yet

                rowAddIndexGlob = rowIndex;

                if (countAdd == 1) {
                    startAddRoute(tempAddedRoute); // setAddedRoute - in OrdersController
                }

            }



    }


    private static boolean isDeleted = false;
    public static void setDeleted()
    {
        isDeleted = true;
    }

    private int countDelete = 0;
    private int rowIndexDelete = -1;

    public void delete(View view) {

        countDelete = 0;

        countDelete++;
        int rowIndex = -1;
        for (CheckBox ch: listCheckBoxes) {
            rowIndex++;
            if (ch.isChecked()) {
                Route tempDeletedRoute = userFlights.get(rowIndex);
                if (countDelete == 1)
                {
                    rowIndexDelete = rowIndex;
                    startDeleteRoute(tempDeletedRoute);
                }
                break;
            }
        }

    }

    public void saveDelete(View view) {
        if (isDeleted == true) {
            Log.e(LOG_TAG, String.format("isDeleted is true"));
            userFlights.remove(rowIndexDelete);
            View deletedRow = (View)listCheckBoxes.get(rowIndexDelete).getParent();
            tableTextViews.remove(rowIndexDelete);
            listCheckBoxes.remove(rowIndexDelete);
            tableLayout.removeView(deletedRow);
            isDeleted = false;
        }
    }
}
