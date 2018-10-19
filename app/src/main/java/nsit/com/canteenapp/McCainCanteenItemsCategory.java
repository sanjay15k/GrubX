package nsit.com.canteenapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Objects;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.GettingCanteenItemsCall;

public class McCainCanteenItemsCategory extends AppCompatActivity {

    private RelativeLayout mcCainSnacksRelativeLayout;
    private RelativeLayout mcCainDrinksRelativeLayout;
    private RelativeLayout mcCainSouthIndianRelativeLayout;
    private LinearLayout mcCainSnacksLinearLayout;
    private LinearLayout mcCainDrinksLinearLayout;
    private LinearLayout mcCainSouthIndianLinearLayout;
    private Intent intent;
    private LoginDTO loginDTO;
    private static final int CART_ID =30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mc_cain_canteen_items_category);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginDTO = getIntent().getParcelableExtra("LoginDTO");

        mcCainSnacksRelativeLayout = findViewById(R.id.mcCainSnacksRelativeLayout);
        mcCainDrinksRelativeLayout = findViewById(R.id.mcCainDrinksRelativeLayout);
        mcCainSouthIndianRelativeLayout = findViewById(R.id.mcCainSouthIndianRelativeLayout);
        mcCainSnacksLinearLayout = findViewById(R.id.mcCainSnacksLinearLayout);
        mcCainDrinksLinearLayout = findViewById(R.id.mcCainDrinksLinearLayout);
        mcCainSouthIndianLinearLayout = findViewById(R.id.mcCainSouthIndianLinearLayout);

        CommonUtilsClass.loadingBackgroundImagesToLayout(R.drawable.mc_cain_snacks_background,mcCainSnacksRelativeLayout,this);
        CommonUtilsClass.loadingBackgroundImagesToLayout(R.drawable.mc_cain_drinks_background, mcCainDrinksRelativeLayout,this);
        CommonUtilsClass.loadingBackgroundImagesToLayout(R.drawable.mc_cain_south_indian_background, mcCainSouthIndianRelativeLayout,this);

        mcCainSnacksLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtilsClass.isNetworkConnected(McCainCanteenItemsCategory.this)){
                    GettingCanteenItemsCall gettingCanteenItemsCall = new GettingCanteenItemsCall(McCainCanteenItemsCategory.this,loginDTO,"McCain","snacks");
                    gettingCanteenItemsCall.execute();
                }
                else{
                    Snackbar.make(findViewById(R.id.parentLayout),"No Internet Connection Available",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        mcCainDrinksLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtilsClass.isNetworkConnected(McCainCanteenItemsCategory.this)){
                    GettingCanteenItemsCall gettingCanteenItemsCall = new GettingCanteenItemsCall(McCainCanteenItemsCategory.this,loginDTO,"McCain","Drinks");
                    gettingCanteenItemsCall.execute();
                }
                else{
                    Snackbar.make(findViewById(R.id.parentLayout),"No Internet Connection Available",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        mcCainSouthIndianLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtilsClass.isNetworkConnected(McCainCanteenItemsCategory.this)){
                    GettingCanteenItemsCall gettingCanteenItemsCall = new GettingCanteenItemsCall(McCainCanteenItemsCategory.this,loginDTO,"McCain","southIndian");
                    gettingCanteenItemsCall.execute();
                }
                else{
                    Snackbar.make(findViewById(R.id.parentLayout),"No Internet Connection Available",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(1,CART_ID,100,"Cart").setIcon(R.drawable.cart_top_bar_icon).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case CART_ID :
                Intent intent = new Intent(this,MyCart.class);
                intent.putExtra("LoginDTO",loginDTO);
                startActivity(intent);
                break;
            case android.R.id.home :
                System.out.println("I m home up");
                onBackPressed();
                break;
        }
        return true;
    }

}
