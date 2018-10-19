package nsit.com.canteenapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.PaytmIntegration.PaytmMainActivity;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.GetRecentOrdersDetailsCall;

public class MyRecentOrders extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView awaitingConfirmationRecyclerView;
    RecyclerView makePaymentRecyclerView;
    RecyclerView inReadyProcessRecyclerView;
    RecyclerView cancelledOrdersRecyclerView;

    private TextView totalPriceTextView;
    private SwipeRefreshLayout swipeRefreshLayoutRecentOrders;
    private LoginDTO loginDTO;

    private Button makePaymentButton;

    private void getUserDetails(){
        Intent intent = getIntent();
        loginDTO = intent.getParcelableExtra("LoginDTO");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recent_orders);

        getUserDetails();

        System.out.println(loginDTO.getUsername());

        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        swipeRefreshLayoutRecentOrders = findViewById(R.id.swipeRefreshLayoutRecentOrders);
        makePaymentButton = findViewById(R.id.makePaymentButton);

        swipeRefreshLayoutRecentOrders.setColorSchemeColors(getResources().getColor(R.color.Red),getResources().getColor(R.color.Yellow),getResources().getColor(R.color.Blue),getResources().getColor(R.color.Green));
        swipeRefreshLayoutRecentOrders.setOnRefreshListener(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        awaitingConfirmationRecyclerView = findViewById(R.id.awaitingConfirmationRecyclerView);
        makePaymentRecyclerView = findViewById(R.id.makePaymentRecyclerView);
        inReadyProcessRecyclerView = findViewById(R.id.inReadyProcessRecyclerView);
        cancelledOrdersRecyclerView = findViewById(R.id.cancelledOrdersRecyclerView);

        LinearLayoutManager awaitingConfirmationLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false);
        LinearLayoutManager awaitingPaymentLayoutManager = new GridLayoutManager(this,2);
        LinearLayoutManager inReadyProcessLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false);
        LinearLayoutManager cancelledOrdersLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false);

        awaitingConfirmationRecyclerView.setLayoutManager(awaitingConfirmationLayoutManager);
        makePaymentRecyclerView.setLayoutManager(awaitingPaymentLayoutManager);
        inReadyProcessRecyclerView.setLayoutManager(inReadyProcessLayoutManager);
        cancelledOrdersRecyclerView.setLayoutManager(cancelledOrdersLayoutManager);

        GetRecentOrdersDetailsCall getOrdersDetailsCall = new GetRecentOrdersDetailsCall(awaitingConfirmationRecyclerView, makePaymentRecyclerView, inReadyProcessRecyclerView, cancelledOrdersRecyclerView,totalPriceTextView, swipeRefreshLayoutRecentOrders,loginDTO,this);
        getOrdersDetailsCall.execute();

        makePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String totalPrice = totalPriceTextView.getText().toString();
                new PaytmMainActivity(totalPrice,MyRecentOrders.this);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home :
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayoutRecentOrders.setRefreshing(true);
        System.out.println("Hello we are this");
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                System.out.println("I m runnning brother RecentOrders");
                swipeRefreshLayoutRecentOrders.setRefreshing(false);
                getUserDetails();
                GetRecentOrdersDetailsCall getOrdersDetailsCall = new GetRecentOrdersDetailsCall(awaitingConfirmationRecyclerView, makePaymentRecyclerView, inReadyProcessRecyclerView, cancelledOrdersRecyclerView,totalPriceTextView, swipeRefreshLayoutRecentOrders,loginDTO,MyRecentOrders.this);
                getOrdersDetailsCall.execute();

            }
        });
    }
}
