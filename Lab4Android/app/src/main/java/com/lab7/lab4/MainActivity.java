package com.lab7.lab4;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.lab7.lab4.Controllers.OrdersController;
import com.lab7.lab4.Controllers.PaymentController;
import com.lab7.lab4.Models.MethodDateUsage;
import com.lab7.lab4.Models.PaymentResponse;
import com.lab7.lab4.Models.Route;
import com.lab7.lab4.Models.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private PaymentController paymentController;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

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
}
