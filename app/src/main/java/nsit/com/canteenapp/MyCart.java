package nsit.com.canteenapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import nsit.com.canteenapp.Adapters.MyCartAdapter;
import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.CommonUtils.MyCartOperations;
import nsit.com.canteenapp.DTO.FavouritesItemDTO;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.DTO.MyCartDTO;
import nsit.com.canteenapp.DTO.MyCartScreenDTO;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.AddToCartCall;

public class MyCart extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private Toolbar myCartToolbar;
    private RecyclerView myCartRecyclerView;
    private Button proceedToCheckoutBtn;
    private ArrayList<MyCartScreenDTO> myCartScreenDTOArrayList = new ArrayList<>();
    private MyCartOperations myCartOperations;
    private MyCartAdapter myCartAdapter;
    private ImageView emptyCartImageView;
    private TextView totalItemsTextView;
    private TextView totalPriceTextView;
    private int totalItems;
    private int totalPrice;
    private MyCartDTO myCartDTO;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoginDTO loginDTO;
    private RelativeLayout myCartRelativeLayout;
    private LinearLayout pickupTimeLinearLayout;
    private static int DIALOG_ID = 10;
    private int hours;
    private int minutes;
    private String AM_PM;
    private TextView showTimeTextView;


    public void readItemsTotalsAndPrice(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyCartTotals",MODE_PRIVATE);
        totalItems = sharedPreferences.getInt("totalItems",0);
        totalPrice = sharedPreferences.getInt("totalPrice",0);
    }

    @Override
    public void onRefresh() {
        readItemsTotalsAndPrice();
        swipeRefreshLayout.setRefreshing(true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("I m runnning brother");
                swipeRefreshLayout.setRefreshing(false);
                totalItemsTextView.setText(String.valueOf(totalItems));
                totalPriceTextView.setText("Rs "+String.valueOf(totalPrice)+"/-");
            }
        },800);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        pickupTimeLinearLayout = findViewById(R.id.pickupTimeLinearLayout);
        showTimeTextView = findViewById(R.id.showTimeTextView);

        Intent intent = getIntent();
        loginDTO = intent.getParcelableExtra("LoginDTO");

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.Red),getResources().getColor(R.color.Yellow),getResources().getColor(R.color.Blue),getResources().getColor(R.color.Green));
        swipeRefreshLayout.setOnRefreshListener(this);

        totalItemsTextView = findViewById(R.id.totalItemsTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        proceedToCheckoutBtn = findViewById(R.id.proceedToCheckoutBtn);
        myCartRelativeLayout = findViewById(R.id.myCartRelativeLayout);

        onRefresh();

        myCartOperations = new MyCartOperations(this);

        myCartRecyclerView = findViewById(R.id.myCartRecyclerView);
        emptyCartImageView = findViewById(R.id.emptyCartImageView);
        myCartToolbar = findViewById(R.id.myCartToolbar);
        setSupportActionBar(myCartToolbar);

        ((SimpleItemAnimator) myCartRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        try {
            myCartScreenDTOArrayList = myCartOperations.readFromFile();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (myCartScreenDTOArrayList.size() == 0){
            emptyCartImageView.setImageResource(R.drawable.empty_cart_icon);
        }

        myCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myCartAdapter = new MyCartAdapter(myCartScreenDTOArrayList,this,emptyCartImageView);
        myCartRecyclerView.setAdapter(myCartAdapter);

        pickupTimeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });

        proceedToCheckoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOrderCanBePlaced()){
                    System.out.println("Hours is : "+hours);
                    System.out.println("AM_PM is : "+AM_PM);
                    if ((hours>=10 && hours<12 && AM_PM.equals("AM")) || (hours>1 && hours<7 && AM_PM.equals("PM")) || (hours==12 && AM_PM.equals("PM"))){
                        readItemsTotalsAndPrice();
                        System.out.println("Hey my total items is : "+totalItems);
                        if (totalItems>0){
                            myCartDTO = setMyCartDTO();
                            AddToCartCall addToCartCall = new AddToCartCall(myCartDTO,MyCart.this,myCartRelativeLayout,loginDTO);
                            addToCartCall.execute();
                        }
                        else{
                            Snackbar.make(swipeRefreshLayout,"Add items to cart before placing any order!",Snackbar.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Snackbar.make(swipeRefreshLayout,"Order cannot be placed at the selected pickup time!",Snackbar.LENGTH_LONG).show();
                    }
                }
                else{
                    Snackbar.make(swipeRefreshLayout,"Order can only be placed between 10:00 AM to 7:00 PM!",Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    public boolean isOrderCanBePlaced() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        return timeOfDay >= 10 && timeOfDay <= 19;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID){
            return new TimePickerDialog(MyCart.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    System.out.println("HoursPfDay : "+hourOfDay);
                    if (hourOfDay>12){
                        AM_PM = "PM";
                        hours = hourOfDay-12;
                    } else if (hourOfDay == 12){
                        AM_PM = "PM";
                        hours = hourOfDay;
                    } else if (hourOfDay == 0){
                        AM_PM = "PM";
                        hours = 12;
                    }
                    else {
                        AM_PM = "AM";
                        hours = hourOfDay;
                    }
                    minutes = minute;
                    showTimeTextView.setText(hours+":"+minutes+" "+AM_PM);
                }
            },hours,minutes,false);
        }
        return null;
    }

    public MyCartDTO setMyCartDTO(){

        JsonArray jsonArray = new JsonArray();
        JsonObject itemJsonObject;

        for (int i=0; i<myCartScreenDTOArrayList.size(); i++){
            itemJsonObject = new JsonObject();
            itemJsonObject.addProperty("name",myCartScreenDTOArrayList.get(i).getItemName());
            itemJsonObject.addProperty("qty",myCartScreenDTOArrayList.get(i).getItemQty());
            itemJsonObject.addProperty("price",Integer.parseInt(myCartScreenDTOArrayList.get(i).getItemPrice()));
            jsonArray.add(itemJsonObject);
        }


        MyCartDTO myCartDTO = new MyCartDTO();

        myCartDTO.setCanteenName("testCanteen");
        myCartDTO.setCustomerName(loginDTO.getFullName());
        myCartDTO.setPhoneNumber(loginDTO.getMobileNumber());
        myCartDTO.setTotalPrice(String.valueOf(totalPrice));
        myCartDTO.setDeliveryTime(showTimeTextView.getText().toString());
        myCartDTO.setItemsJsonArray(jsonArray);
        myCartDTO.setUsername(loginDTO.getUsername());

        return myCartDTO;

    }


    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Mine size in onPause is : "+myCartScreenDTOArrayList.size());
        try {
            myCartOperations.writeToFile(myCartScreenDTOArrayList);
            File file = CommonUtilsClass.openFile(this,"MyCart.ser");
            ArrayList<FavouritesItemDTO> myCartScreenDTO = CommonUtilsClass.readFromFile(file);
            System.out.println("Size 1 is ****** : "+myCartScreenDTO.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
