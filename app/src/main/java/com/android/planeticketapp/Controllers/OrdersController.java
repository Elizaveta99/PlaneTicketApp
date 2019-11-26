package com.android.planeticketapp.Controllers;

import android.util.Log;
import android.widget.Toast;
import com.android.planeticketapp.MainActivity;
import com.android.planeticketapp.Models.Route;
import com.android.planeticketapp.Models.User;
import com.android.planeticketapp.OrderTicketActivity;
import com.android.planeticketapp.ServiceAPI.PlaneTicketServiceClient;
import com.android.planeticketapp.ServiceAPI.PlaneTicketServiceInterface;
import com.android.planeticketapp.R;

import java.util.ArrayList;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.planeticketapp.MainActivity.*;

public class OrdersController {
    private static final String LOG_TAG =
            OrdersController.class.getSimpleName();

    private PlaneTicketServiceInterface service;
    //public MainActivity mainActivity;

    private String userId;

    public OrdersController(String userId){
        this.userId = userId;
        //this.mainActivity = _mainActivity;

        service = PlaneTicketServiceClient.getRetrofitInstance().create(PlaneTicketServiceInterface.class);
        Log.e(LOG_TAG, "constructor ordersController");


    }

    private void showError(String message){
        String initialMessage = MainActivity.getContext().getString(R.string.errorMessage);
        if(message != null)
            initialMessage +=  " " + message;
        Toast.makeText(MainActivity.getContext(), initialMessage, Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.getContext(), initialMessage, Toast.LENGTH_SHORT).show();
    }

    //add
    public void authorize(String token){
        final User[] user = new User[1];
        Call<User> call = service.authorize(userId, token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user[0] = response.body();
                //change activity
                //MainActivity.foSmth(), doSmth - static
                Log.e(LOG_TAG, "authorize from ordersController");
                //MainActivity mA = new MainActivity();
                //mA.authorize();
                MainActivity.authorize();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showError(t.getMessage());
            }
        });
    }

    //add
    public void readRoutes(String token) {
        final ArrayList<Route>[] routes = new ArrayList[]{new ArrayList<>()};
        Call<ArrayList<Route>> call = service.readData(userId, token);
        call.enqueue(new Callback<ArrayList<Route>>() {
            @Override
            public void onResponse(Call<ArrayList<Route>> call, Response<ArrayList<Route>> response) {
                routes[0] = response.body();
                //next
                //pass list of routes - routes[0]
            }
            @Override
            public void onFailure(Call<ArrayList<Route>> call, Throwable t) {
                showError(t.getMessage());
            }
        });
    }

    //add
    public void readAllRoutes(String token) {
        final ArrayList<Route>[] routes = new ArrayList[]{new ArrayList<>()};
        Call<ArrayList<Route>> call = service.readAllData(token);
        call.enqueue(new Callback<ArrayList<Route>>() {
            @Override
            public void onResponse(Call<ArrayList<Route>> call, Response<ArrayList<Route>> response) {
                routes[0] = response.body();
                HashSet<String> from = new HashSet<String>();
                HashSet<String> to = new HashSet<String>();
                HashSet<String> dates = new HashSet<String>();
                ArrayList<Route> uniqueRoutes = new ArrayList<Route>();
                for (Route current : routes[0]) {
                    from.add(current.getFrom());
                    to.add(current.getTo());
                    dates.add(current.getDate());
                }
                ArrayList<String> fromList = new ArrayList<>(from);
                ArrayList<String> toList = new ArrayList<>(to);
                ArrayList<String> dateList = new ArrayList<>(dates);
                for (int i = 0; i < fromList.size(); i++) {
                    uniqueRoutes.add(new Route(fromList.get(i), toList.get(i), dateList.get(i)));
                }
                //call next with uniqueRoutes
            }
            @Override
            public void onFailure(Call<ArrayList<Route>> call, Throwable t) {
                showError(t.getMessage());
            }
        });
    }

    //add
    public void addRoute(String token, Route route) {
        Call<Route> call = service.addFlight(userId, token, route);
        call.enqueue(new Callback<Route>() {
            @Override
            public void onResponse(Call<Route> call, Response<Route> response) {
                Route newRoute = response.body();
                //next to do
            }
            @Override
            public void onFailure(Call<Route> call, Throwable t) {
                showError(t.getMessage());
            }
        });
    }

    //add
    public void updateRoute(String token, Route route) {
        Call<Route> call = service.updateFlight(userId, token, route);
        call.enqueue(new Callback<Route>() {
            @Override
            public void onResponse(Call<Route> call, Response<Route> response) {
                Route updatedRoute = response.body();
                //next to do
            }
            @Override
            public void onFailure(Call<Route> call, Throwable t) {
                showError(t.getMessage());
            }
        });
    }

    //add
    public void deleteRoute(String token, Route route) {
        Call<Integer> call = service.deleteFlight(userId, route.getId(), token);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int code = response.body();
                if(code == -1)
                    onFailure(call, null);
                //next
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                String message = null;
                if(t != null){
                    message = t.getMessage();
                }
                showError(message);
            }
        });
    }
}
